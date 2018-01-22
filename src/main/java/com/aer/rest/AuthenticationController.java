package com.aer.rest;

import com.aer.model.User;
import com.aer.security.TokenHelper;
import com.aer.security.UserTokenState;
import com.aer.security.auth.JwtAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan.jin on 2017-05-10.
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        LdapUserDetailsImpl ldapUserDetail = (LdapUserDetailsImpl) authentication.getPrincipal();

        //We need to move from LadpUserDetails interface type to our User model
        //which implements the UserDetails interface. The reason is that when we are going to create the token
        //we can not serialize the authorities attribute due to does not have a concrete implementation.
        List<SimpleGrantedAuthority> privileges = new ArrayList<>();
        for (GrantedAuthority authority : ldapUserDetail.getAuthorities()) {
            privileges.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        User user = new User();
        user.setAuthorities(privileges);
        user.setUsername(ldapUserDetail.getUsername());
        user.setEnabled(ldapUserDetail.isEnabled());
        user.setFirstName(user.getUsername().split("\\.", -1)[0]);
        user.setLastName(user.getUsername().split("\\.", -1)[1]);
        user.setEmail(user.getUsername().concat("@tui.com"));
        String jws = tokenHelper.generateToken(user);

        int expiresIn = tokenHelper.getExpiredIn();
        // Return the token
        return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request, Principal principal
    ) {

        String authToken = tokenHelper.getToken(request);

        if (authToken != null && principal != null) {

            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);
            int expiresIn = tokenHelper.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

}
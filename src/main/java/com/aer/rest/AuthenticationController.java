package com.aer.rest;

import com.aer.model.ApiClient;
import com.aer.model.Permission;
import com.aer.model.Role;
import com.aer.model.User;
import com.aer.security.TokenHelper;
import com.aer.security.UserTokenState;
import com.aer.security.auth.JwtAuthenticationRequest;
import com.aer.service.ApiClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fan.jin on 2017-05-10.
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Authentication Token Api ", description = "Authentication Token API")
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApiClientService apiClientService;

    @ApiOperation(
            tags = "Token",
            value = "Create a new web token resource",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
        String jws = tokenHelper.generateToken(ldapUserDetail);

        int expiresIn = tokenHelper.getExpiredIn();
        // Return the token
        return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
    }

    @ApiOperation(
            tags = "Token",
            value = "Refresh a new token api resource",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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

    @ApiOperation(
            tags = "Token",
            value = "Create a new token api resource",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })

    @RequestMapping(value = "/apiToken", method = RequestMethod.GET)
    public ResponseEntity<?> create2B2ApiToken(@RequestHeader(value = "X-Api-Key") String apiKey) {

        ApiClient apiClient = apiClientService.findByApiKey(apiKey);
        String jws = tokenHelper.generateToken(apiClient);
        UserTokenState userTokenState = new UserTokenState();
        userTokenState.setAccess_token(jws);
        return ResponseEntity.ok(userTokenState);
    }

}
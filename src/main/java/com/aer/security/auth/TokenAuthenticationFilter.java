package com.aer.security.auth;

import com.aer.security.TokenHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fan.jin on 2016-10-19.
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final TokenHelper tokenHelper;

    @Autowired
    public TokenAuthenticationFilter(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String username;
        String authToken = tokenHelper.getToken(request);
        username = tokenHelper.getUsernameFromToken(authToken);
        if (authToken != null && username != null) {
            if (tokenHelper.isApiToken(username)) {
                UserDetails userDetails = tokenHelper.getUserDetailFromToken(authToken);
                if (tokenHelper.validateToken(authToken, userDetails)) {
                    createAuthentication(authToken, userDetails);
                }
            } else {
                LdapUserDetails ldapUserDetails = tokenHelper.getLdapUserDetailFromToken(authToken);
                if (tokenHelper.validateToken(authToken, ldapUserDetails)) {
                    createAuthentication(authToken, ldapUserDetails);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private void createAuthentication(String authToken, UserDetails userDetails) {
        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
        authentication.setToken(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
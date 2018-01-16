package com.aer.security.auth;

import com.aer.model.User;
import com.aer.service.UserService;
import com.aer.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MyAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations dirContextOperations, String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No userEntity found with username '%s'.", username));
        } else {
            return user.getAuthorities();
        }
    }
}

package com.aer.service.impl;

import com.aer.entities.Privilege;
import com.aer.entities.Role;
import com.aer.model.User;
import com.aer.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by fan.jin on 2016-10-31.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No userEntity found with username '%s'.", username));
        } else {
            return user;
        }
    }


//    public void changePassword(String oldPassword, String newPassword) {
//
//        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = currentUser.getName();
//
//        if (authenticationManager != null) {
//            LOGGER.debug("Re-authenticating user '"+ username + "' for password change request.");
//
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
//        } else {
//            LOGGER.debug("No authentication manager set. can't change Password!");
//
//            return;
//        }
//
//        LOGGER.debug("Changing password for user '"+ username + "'");
//
//        UserEntity user = (UserEntity) loadUserByUsername(username);
//
//        user.setPassword(newPassword);
//        userRepository.save(user);
//
//    }
}

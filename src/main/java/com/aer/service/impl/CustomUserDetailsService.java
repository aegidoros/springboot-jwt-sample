package com.aer.service.impl;

import com.aer.model.Privilege;
import com.aer.model.Role;
import com.aer.model.User;
import com.aer.repository.RoleRepository;
import com.aer.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = userRepository.findByUsername(username);
//        if (user != null) {
//            return new org.springframework.security.core.userdetails.User(
//                    user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
//                    true, getAuthorities(user.getRoles()));
//
//
////            return new org.springframework.security.core.userdetails.User(
////                    " ", " ", true, true, true, true,
////                    getAuthorities(Arrays.asList(
////                            roleRepository.findByName("ROLE_USER"))));
//        } else {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
//        }


        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }


    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return getGrantedAuthorities((List<String>) getPrivileges(roles));
    }

    private Collection<String> getPrivileges(Collection<Role> roles) {

        final Collection<String> privilegesNames = null;
        final Collection<Privilege> privileges = null;
        for (final Role role : roles) {
            privileges.add((Privilege) role.getAuthorities());
        }
        for (final Privilege item : privileges) {
            privilegesNames.add(item.getName());
        }
        return privilegesNames;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
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
//        User user = (User) loadUserByUsername(username);
//
//        user.setPassword(newPassword);
//        userRepository.save(user);
//
//    }
}

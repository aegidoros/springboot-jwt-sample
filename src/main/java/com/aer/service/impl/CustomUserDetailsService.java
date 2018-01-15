package com.aer.service.impl;

import com.aer.model.User;
import com.aer.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by fan.jin on 2016-10-31.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        UserEntity userEntity = userRepository.findByUsername(username);
//        if (userEntity != null) {
//            return new org.springframework.security.core.userdetails.UserEntity(
//                    userEntity.getEmail(), userEntity.getPassword(), userEntity.isEnabled(), true, true,
//                    true, getAuthorities(userEntity.getRoles()));
//
//
////            return new org.springframework.security.core.userdetails.UserEntity(
////                    " ", " ", true, true, true, true,
////                    getAuthorities(Arrays.asList(
////                            roleRepository.findByName("ROLE_USER"))));
//        } else {
//            throw new UsernameNotFoundException(String.format("No userEntity found with username '%s'.", username));
//        }


        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No userEntity found with username '%s'.", username));
        } else {
            return user;
        }
    }

//
//    private Collection<? extends GrantedAuthority> getAuthorities(
//            Collection<Role> roles) {
//        return getGrantedAuthorities((List<String>) getPrivileges(roles));
//    }
//
//    private Collection<String> getPrivileges(Collection<Role> roles) {
//
//        final Collection<String> privilegesNames = null;
//        final Collection<Privilege> privileges = null;
//        for (final Role role : roles) {
//            privilegesNames.add(role.getName());
//            privileges.add((Privilege) role.getAuthorities());
//        }
//        for (final Privilege item : privileges) {
//            privilegesNames.add(item.getName());
//        }
//        return privilegesNames;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }


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

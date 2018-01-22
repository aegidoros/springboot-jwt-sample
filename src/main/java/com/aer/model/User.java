package com.aer.model;

import com.aer.entities.PrivilegeEntity;
import com.aer.entities.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean enabled;

    private List<Role> roles;

    private static final long serialVersionUID = 2668653261891276609L;

    private Collection<SimpleGrantedAuthority> authorities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @Override
//    public List<Privilege> getAuthorities() {
//
//        if (authorities != null) {
//            return authorities;
//        } else {
//            authorities = new ArrayList<>();
//            for (Role role : this.getRoles()) {
//                authorities.addAll(role.getAuthorities());
//            }
//            return authorities;
//        }
//    }

    public void setAuthorities(Collection<SimpleGrantedAuthority> privileges) {
        authorities = privileges;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        return getGrantedAuthorities((Set<String>) getPrivileges(roles));
    }

    private Collection<String> getPrivileges(Collection<Role> roles) {

        final Set<String> privilegesNames = new HashSet<>();
        final Set<Privilege> privileges = new HashSet<>();
        for (final Role role : roles) {
            //   privilegesNames.add(role.getName());
            privileges.addAll(role.getAuthorities());
        }
        for (final Privilege item : privileges) {
            privilegesNames.add(item.getAuthority());
        }
        return privilegesNames;
    }

    private Collection<SimpleGrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


}

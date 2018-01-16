package com.aer.model;

import com.aer.entities.Privilege;
import com.aer.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean enabled;

    private Timestamp lastPasswordResetDate;

    private List<Role> roles;

    private static final long serialVersionUID = 2668653261891276609L;

    private List<Privilege> authorities;


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

    @Override
    public List<Privilege> getAuthorities() {

        if (authorities != null) {
            return authorities;
        } else {
            authorities = new ArrayList<>();
            for (Role role : this.getRoles()) {
                authorities.addAll(role.getAuthorities());
            }
            return authorities;
        }
    }

    public void setAuthorities(List<Privilege> privileges) {
        authorities = privileges;
    }
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (authorities != null) {
//            return authorities;
//        }
//        return authorities = getGrantedAuthorities((List<String>) getPrivileges(roles));
//    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
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

    private Collection<String> getPrivileges(Collection<Role> roles) {

        final Collection<String> privilegesNames = new ArrayList<>();
        final Collection<Privilege> privileges = new ArrayList<>();
        for (final Role role : roles) {
            //   privilegesNames.add(role.getName());
            privileges.addAll(role.getAuthorities());
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


}

package com.aer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private boolean enabled;
    private List<Role> roles;
    protected String username;
    private Collection<SimpleGrantedAuthority> authorities;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return getGrantedAuthorities((Set<String>) getPermissions(getRoles()));
    }

    private Collection<String> getPermissions(Collection<Role> roles) {

        final Set<String> permissionsNames = new HashSet<>();
        final Set<Permission> permissions = new HashSet<>();
        for (final Role role : roles) {
            permissions.addAll(role.getAuthorities());
        }
        for (final Permission item : permissions) {
            permissionsNames.add(item.getAuthority());
        }
        return permissionsNames;
    }

    private Collection<SimpleGrantedAuthority> getGrantedAuthorities(Set<String> permissions) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String authority : permissions) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
    }

}

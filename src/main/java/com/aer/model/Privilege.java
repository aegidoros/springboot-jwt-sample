package com.aer.model;

import org.springframework.security.core.GrantedAuthority;

public class Privilege implements GrantedAuthority {


    private static final long serialVersionUID = 6039981147482244093L;

    private Long id;

    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}

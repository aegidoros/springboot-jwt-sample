package com.aer.model;

import com.aer.entities.PrivilegeEntity;

import java.io.Serializable;
import java.util.List;

public class Role  implements Serializable {

    private static final long serialVersionUID = -7800616144725121399L;

    private Long id;

    private String name;

    private List<Privilege> authorities;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Privilege> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(List<Privilege> authorities) {
        this.authorities = authorities;
    }

}

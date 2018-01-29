package com.aer.model;

import java.util.List;

public class ApiClient extends UserDetailsImpl {


    private Long id;

    private String apiKey;

    //private String username;

    private String secretId;

    //private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

//    @Override
//    public List<Role> getRoles() {
//        return roles;
//    }
//
//    @Override
//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }


    @Override
    public String getUsername() {
        username=apiKey;
        return username;
    }

    public void setUsername(String username) {
        this.apiKey=username;
    }
}

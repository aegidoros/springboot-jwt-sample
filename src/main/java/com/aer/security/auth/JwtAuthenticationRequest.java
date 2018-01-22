package com.aer.security.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by fanjin on 2017-10-09.
 */
@JsonInclude
public class JwtAuthenticationRequest implements Serializable {


    private static final long serialVersionUID = 4366321616391124008L;

    private String username;


    private String password;

//    public JwtAuthenticationRequest(String username, String password) {
//        this.setUsername(username);
//        this.setPassword(password);
//    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

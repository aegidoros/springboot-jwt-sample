package com.aer.model;

import java.util.List;

public class User extends  UserDetailsImpl {

    private Long id;

   // private String username;

    private String firstName;

    private String lastName;

    private String email;



   // private List<Role> roles;

    private static final long serialVersionUID = 2668653261891276609L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    @Override
//    public List<Role> getRoles() {
//        return roles;
//    }
//
//    @Override
//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }





}

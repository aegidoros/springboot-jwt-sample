package com.aer.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "privilege")
public class PrivilegeEntity implements Serializable {


    private static final long serialVersionUID = -5649595619617014688L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

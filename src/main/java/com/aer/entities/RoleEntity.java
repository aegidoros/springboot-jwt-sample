package com.aer.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by fan.jin on 2016-11-03.
 */
@Entity
@Table(name = "role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -7800616144725121399L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(
                    name = "role_id ", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private List<PrivilegeEntity> authorities;

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

    public List<PrivilegeEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<PrivilegeEntity> authorities) {
        this.authorities = authorities;
    }


}

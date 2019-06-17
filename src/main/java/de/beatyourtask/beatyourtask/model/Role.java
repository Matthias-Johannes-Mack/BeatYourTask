package de.beatyourtask.beatyourtask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class that represents the role of an user
 */
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    private String rolename;


    // no-argument constructor for Hibernate.
    public Role() {
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}

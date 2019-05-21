package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Class for the user / wirefire / Spring 19
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;

    private String password;

    private String surname;

    private String lastname;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    

    /**
     * No-Argument Konstruktor für Hibernate.
     */
    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
}

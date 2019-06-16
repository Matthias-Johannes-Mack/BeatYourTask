package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
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

    @NotEmpty(message = "Please provide a valid email address")
    @Email(message="Please provide a valid email address")
    private String email;

    private String password;

    private String surname;

    private String lastname;

    private boolean enabled = true;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_project",
            joinColumns = { @JoinColumn(name = "fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_project") })
    private List<Project> projects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    

    /**
     * No-Argument Konstruktor für Hibernate.
     */
    public User() {

    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.getUsers().add(this);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getUsers().remove(this);
    }

    //Getters and Setters
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}

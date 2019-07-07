package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents projects with corresponding data
 */
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer projectId;

    @Size(min=3,max=20, message="The project name should be between 3 and 20 characters long.")
    private String projectName;

    @Size(min=5,max=30, message="The project description should be between 5 and 30 characters long.")
    private String projectDescription;

    @ManyToMany(mappedBy = "projects")
    private List<User> users = new ArrayList<>();


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Tasklist> tasklists = new ArrayList<Tasklist>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Label> labels = new ArrayList<Label>();


    @ElementCollection
    private List<Integer> orders = new ArrayList<>();

    // no-argument constructor for hibernate
    public Project() {

    }

    public List<Tasklist> getLists() {
        return this.tasklists;
    }

    public void addTasklist(Tasklist tasklist) {
        Tasklist tasklistVar = tasklist;
        tasklistVar.setProject(this);
        tasklists.add(tasklistVar);
    }

    public void removeTasklist(Tasklist tasklist) {
        this.tasklists.remove(tasklist);
    }

    /**
     * adds User to user list
     * @param user user to be added
     */
    public void addUser(User user) {
        this.users.add(user);
        user.getProjects().add(this);
    }

    /**
     * removes user from user list
     * @param user user to be removed
     */
    public void removeUser(User user) {
        this.users.remove(user);
        user.getProjects().remove(this);
    }

    public void setLabel(Label label) { this.labels.add(label); }

    public List<Label> getLabel() { return this.labels; }


    // getters and setters


    public List<Integer> getOrders() {
        return orders;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

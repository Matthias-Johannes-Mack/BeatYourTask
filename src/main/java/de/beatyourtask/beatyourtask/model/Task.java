package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Task {
    @Id
    @GeneratedValue
    private Integer taskId;

    @ManyToOne
    @JoinColumn
    private Tasklist tasklist;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> assignees = new ArrayList<User>();

    /** name of Task */
    private String taskName;


    // Constructor
    public Task(){

    }

    public void addUser(User user){
        assignees.add(user);
    }

    // Getters and Setters
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Tasklist getTasklist() {
        return tasklist;
    }

    public void setTasklist(Tasklist tasklist) {
        this.tasklist = tasklist;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskList(Tasklist tasklist) {this.tasklist = tasklist;}

}

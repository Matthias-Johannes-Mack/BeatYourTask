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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Label> labels = new ArrayList<Label>();

    /** name of Task */
    private String taskName;

    private String date;
    private boolean done = false;



    // Constructor
    public Task(){

    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void addUser(User user){
        this.assignees.add(user);
    }

    public void removeUser(User user){
        this.assignees.remove(user);
    }

    public void addLabel(Label label){
        this.labels.add(label);
    }

    public void removeLabel(Label label){
        this.labels.remove(label);
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

    public List<Label> getLabels() { return labels; }

    public void setLabels(Label label) { this.labels.add(label); }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskList(Tasklist tasklist) {this.tasklist = tasklist;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

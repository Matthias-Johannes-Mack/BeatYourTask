package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;


@Entity
public class Task {
    @Id
    @GeneratedValue
    private Integer taskId;

    @ManyToOne
    @JoinColumn
    private Tasklist tasklist;

    /** name of ... */
    private String taskName;


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskList(Tasklist tasklist) {this.tasklist = tasklist;}
}

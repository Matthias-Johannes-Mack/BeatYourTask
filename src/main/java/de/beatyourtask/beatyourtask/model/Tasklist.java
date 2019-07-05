package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Tasklist that contains tasks
 */
@Entity
public class Tasklist {

    /** Primary kjey*/
    @Id
    @GeneratedValue
    private Integer listId;

    /** name of tasklist */
    private String listName;

    /** color of a list given in htm color names*/
    private String color;

    @ManyToOne
    @JoinColumn
    private Project project;

    @OneToMany(mappedBy = "tasklist", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<Task>();

    /** order of the tasks in the list*/
    @ElementCollection
    private List<Integer> orderTasks = new ArrayList<>();


    /**
     * constructor Tasklist
     */
    public Tasklist() {

    }

    /**
     * set the background color
     * @param color of the list
     */
    public void setColor(String color) {
        this.color = "background-color:"+color;
    }

    /**returns the color of this list */
    public String getColor() {
        return this.color;
    }

    /**
     * returns the list of the tasklist
     * @return listid
     */
    public Integer getListId() {
        return this.listId;
    }

    /**
     * settr fot the id of he tasklist
     * @param listID
     */
    public void setListId(Integer listID) {
        this.listId = listID;
    }

    /**
     * Get the name of a tasklist
     * @return name of tasklist
     */
    public String getListName() {
        return this.listName;
    }

    /**
     * set the name of a tasklist
     * @param listName name of tasklist
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setProject(Project project) {this.project = project;}

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void addTasks(Task task) {
        Task taskVar = task;
        taskVar.setTaskList(this);
        tasks.add(taskVar);
    }

    public void removeTasks(Task task) {
        Task taskVar = task;
        tasks.remove(taskVar);
    }

    public void setOrderTasks(List<Integer> orderTasks) {
        this.orderTasks = orderTasks;
    }

    public List<Integer> getOrderTasks() {
        return orderTasks;
    }

}

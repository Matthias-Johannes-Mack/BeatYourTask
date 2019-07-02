package de.beatyourtask.beatyourtask.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    private String message;
    private String createDate;

    @OneToOne
    private User author;

    @OneToOne
    private Task ownerTask;


    // Constructor

    public Comment() {
    }


    // Getters and Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Task getOwnerTask() {
        return ownerTask;
    }

    public void setOwnerTask(Task ownerTask) {
        this.ownerTask = ownerTask;
    }
}

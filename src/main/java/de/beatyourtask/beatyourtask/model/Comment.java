package de.beatyourtask.beatyourtask.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    private String message;
    private Date createDate;


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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Task getOwnerTask() {
        return ownerTask;
    }

    public void setOwnerTask(Task ownerTask) {
        this.ownerTask = ownerTask;
    }
}

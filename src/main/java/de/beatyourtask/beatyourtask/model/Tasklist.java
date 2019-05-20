package de.beatyourtask.beatyourtask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tasklist {

    @Id
    @GeneratedValue
    private Integer listId;

    private String listName;

    public Tasklist() {

    }

    public Integer getListId() {
        return this.listId;
    }

    public void setListId(Integer listID) {
        this.listId = listID;
    }

    public String getListName() {
        return this.listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}

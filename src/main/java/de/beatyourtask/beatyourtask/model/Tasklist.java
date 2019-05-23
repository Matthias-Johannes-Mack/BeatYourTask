package de.beatyourtask.beatyourtask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
        this.color = "background-color:"+color+";";
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
}
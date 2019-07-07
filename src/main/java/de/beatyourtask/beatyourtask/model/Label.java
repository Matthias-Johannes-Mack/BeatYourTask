package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Label {

    @Id
    @GeneratedValue
    private Integer labelId;

    //name of the label
    private String labelName;

    //color of the label
    private String labelColor;

    @ManyToOne
    @JoinColumn
    private Project project;


    //Constructor
    public Label() {

    }

    //Getter und Setter
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public Integer getLabelId() { return labelId;}

    public void setLabelId(int id) { this.labelId = id; }

}

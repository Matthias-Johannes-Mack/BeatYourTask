package de.beatyourtask.beatyourtask.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class for the User / wirefire / Spring 19
 */
@Entity
public class User {
    /**
     * Variables
     */
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private int level;
    private String Skin;
    private int erfahrungspunkte;
    private int angriff;
    private int lebenspunkte;

    public User() {

    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSkin() {
        return Skin;
    }

    public void setSkin(String skin) {
        Skin = skin;
    }

    public int getErfahrungspunkte() {
        return erfahrungspunkte;
    }

    public void setErfahrungspunkte(int erfahrungspunkte) {
        this.erfahrungspunkte = erfahrungspunkte;
    }

    public int getAngriff() {
        return angriff;
    }

    public void setAngriff(int angriff) {
        this.angriff = angriff;
    }

    public int getLebenspunkte() {
        return lebenspunkte;
    }

    public void setLebenspunkte(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
    }




    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User(String firstName) {
        this.firstName = firstName;
    }
}

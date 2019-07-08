package de.beatyourtask.beatyourtask.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class to display a Level | wirefire07 | Summer ´19
 */
@Entity
public class Level {
    @Id
    private int level;
    private int maxExpLvl;
    private int damage;

    /**
     * Empty Constructor
     */
    public Level() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxExpLvl() {
        return maxExpLvl;
    }

    public void setMaxExpLvl(int maxExpLvl) {
        this.maxExpLvl = maxExpLvl;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

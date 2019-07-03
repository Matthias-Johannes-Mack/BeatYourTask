package de.beatyourtask.beatyourtask.model;

import javax.persistence.*;

/**
 * Class for the Monster | wirefire | Summer ´19
 */
@Entity
public class Monster {
    @Id
    private int monsterId;

    private int lifepoints;
    private int currentLifePoints;
    private String monsterPic;


    /**
     * Empty Costructor
     */
    public Monster() {
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public int getLifepoints() {
        return lifepoints;
    }

    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
    }

    public int getCurrentLifePoints() {
        return currentLifePoints;
    }

    public void setCurrentLifePoints(int currentLifePoints) {
        this.currentLifePoints = currentLifePoints;
    }

    public String getMonsterPic() {
        return monsterPic;
    }

    public void setMonsterPic(String monsterPic) {
        this.monsterPic = monsterPic;
    }

}

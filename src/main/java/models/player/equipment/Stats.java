package models.player.equipment;

import models.exception.DataValidationException;

import java.io.Serializable;

public class Stats implements Serializable {
    private int strength;
    private int critChance;
    private int parryChance;

    public Stats(int strength, int critChance, int parryChance) {
        setStrength(strength);
        setCritChance(critChance);
        setParryChance(parryChance);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength < 0) {
            throw new DataValidationException("statistic can not be below 0");
        }
        this.strength = strength;
    }

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        if (critChance < 0) {
            throw new DataValidationException("statistic can not be below 0");
        }
        this.critChance = critChance;
    }

    public int getParryChance() {
        return parryChance;
    }

    public void setParryChance(int parryChance) {
        if (parryChance < 0) {
            throw new DataValidationException("statistic can not be below 0");
        }
        this.parryChance = parryChance;
    }

    @Override
    public String toString() {
        return String.format("Strength: %d, Crit: %d, Parry: %d ",
                getStrength(), getCritChance(), getParryChance());
    }
}

package models.player.equipment;


import models.exception.DataValidationException;

import models.functionalities.shop.Shop;
import models.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Equipment implements Serializable {

    private static List<Equipment> equipmentExtent = new ArrayList<>();

    private String equipmentName;
    private int requiredLevel;
    private Stats stats;

    private Player owner;

    public Equipment(String equipmentName, int requiredLevel, Stats stats) {
        setEquipmentName(equipmentName);
        setRequiredLevel(requiredLevel);
        setStats(stats);

        equipmentExtent.add(this);
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        if (equipmentName == null) {
            throw new DataValidationException("EQ need a name.");
        }
        this.equipmentName = equipmentName;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        if (requiredLevel <= 0) {
            throw new DataValidationException("EQ must require at least 1 level.");
        }
        this.requiredLevel = requiredLevel;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        if (stats == null) {
            throw new DataValidationException("EQ must have a stats.");
        }
        this.stats = stats;
    }

    /**
     * Player Association
     */
    public Player getOwner() {
        return this.owner;
    }

    public void setShop(Player owner) {

        if (this.owner == owner) {
            return;
        }

        if (this.owner != null) {

            Player tmpOwner = this.owner;
            this.owner = null;
            tmpOwner.removeEquipment(this);

            if (owner != null) {
                this.owner = owner;
                owner.addEquipment(this);
            }

        } else {
            this.owner = owner;
            owner.addEquipment(this);
        }
    }

    /**
     * Log Extension
     */
    public static List<Equipment> getEquipmentsExtent() {
        return Collections.unmodifiableList(equipmentExtent);
    }

    public static void setEquipmentExtent(List<Equipment> equipmentExtent) {
        if (equipmentExtent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        Equipment.equipmentExtent = equipmentExtent;
    }

    //testing
    /*public static void clearExtension() {
        equipmentExtent.clear();
    }*/

    @Override
    public String toString() {
        return String.format("Name: %s, Req Level: %d, Stats: %s",
                getEquipmentName(), getRequiredLevel(), getStats());
    }
}

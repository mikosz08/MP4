package models.functionalities.shop;

import models.exception.DataValidationException;
import models.guild.Guild;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boost implements Serializable {

    private static List<Boost> boostsExtent = new ArrayList<>();

    private String boostName;
    private LocalDate durationTo;
    private int reputationBonus;
    private BoostType boostType;

    private Shop shop;
    private Guild owner;

    public Boost(String boostName, LocalDate durationTo, int reputationBonus, BoostType boostType) {
        this.boostName = boostName;
        this.durationTo = durationTo;
        this.reputationBonus = reputationBonus;
        this.boostType = boostType;

        boostsExtent.add(this);
    }

    /**
     * Dynamic Inheritance
     */
    public void becomeFactionBoost() {
        setBoostType(BoostType.FACTION_BOOST);
    }

    public void becomeRegionBoost() {
        setBoostType(BoostType.REGION_BOOST);
    }

    /**
     * Boost Name
     */
    public String getBoostName() {
        return boostName;
    }

    public void setBoostName(String boostName) {
        if (boostName == null || boostName.trim().isBlank()) {
            throw new DataValidationException("BoostName cannot be null or empty!");
        }
        this.boostName = boostName;
    }

    /**
     * Boost Duration
     */
    public LocalDate getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(LocalDate durationTo) {
        if (durationTo == null) {
            throw new DataValidationException("Duration cannot be null");
        }
        this.durationTo = durationTo;
    }

    /**
     * Boost Reputation Bonus
     */
    public int getReputationBonus() {
        return reputationBonus;
    }

    public void setReputationBonus(int reputationBonus) {
        if (reputationBonus < 0) {
            throw new DataValidationException("Reputation bonus can not be less than 0!");
        }
        this.reputationBonus = reputationBonus;
    }

    /**
     * Boost Type
     */
    public BoostType getBoostType() {
        return boostType;
    }

    public void setBoostType(BoostType boostType) {
        if (boostType == null) {
            throw new DataValidationException("Boost need a type!");
        }
        this.boostType = boostType;
    }

    /**
     * Shop Association
     */
    public Shop getShop() {
        return shop;
    }

    private void setOwner(Shop shop) {
        if (shop == null) {
            throw new DataValidationException("Owner is required!");
        }
        this.shop = shop;
        shop.addBoost(this);
    }

    public void deleteShop() {
        if (this.shop != null) {
            Shop tmpShop = this.shop;
            this.shop = null;
            tmpShop.removeBoost(this);
        }
    }

    /**
     * Guild Association
     */
    public Guild getOwner() {
        return owner;
    }

    private void setOwner(Guild owner) {
        if (owner == null) {
            throw new DataValidationException("Owner is required!");
        }
        this.owner = owner;
        owner.addBoost(this);
    }

    public void deleteGuild() {
        if (this.owner != null) {
            Guild tmpOwner = this.owner;
            this.owner = null;
            tmpOwner.removeGuildBoost(this);
        }
    }

    /**
     * Boost Extension
     */
    public static List<Boost> getBoostsExtent() {
        return Collections.unmodifiableList(boostsExtent);
    }

    public static void setBoostsExtent(List<Boost> boostsExtent) {
        if (boostsExtent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        Boost.boostsExtent = boostsExtent;
    }

    //testing
    public static void clearExtension() {
        boostsExtent.clear();
    }

}

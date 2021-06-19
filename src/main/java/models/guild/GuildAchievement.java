package models.guild;

import models.exception.DataValidationException;

import java.io.Serializable;
import java.time.LocalDate;

public class GuildAchievement implements Serializable {

    private String achievementName;
    private String requirements;
    private LocalDate earnDate;

    private Guild owner;

    /**
     * GuildAchievement Constructor
     */
    public GuildAchievement(String achievementName, String requirements, Guild owner) {
        setAchievementName(achievementName);
        setRequirements(requirements);
        setEarnDate(LocalDate.now());
        setOwner(owner);
    }

    /**
     * Achievement Delete
     */
    public void delete() {
        if (this.owner != null) {
            Guild tmpOwner = this.owner;
            this.owner = null;
            tmpOwner.removeGuildAchievement(this);
        }
    }

    /**
     * Achievement Owner
     */
    public Guild getOwner() {
        return owner;
    }

    private void setOwner(Guild owner) {
        if (owner == null) {
            throw new DataValidationException("Owner is required!");
        }
        this.owner = owner;
        owner.addGuildAchievement(this);
    }

    /**
     * Achievement Name
     */
    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    /**
     * Achievement Requirements
     */
    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    /**
     * Achievement Earn Date
     */
    public LocalDate getEarnDate() {
        return earnDate;
    }

    public void setEarnDate(LocalDate earnDate) {
        this.earnDate = earnDate;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Req: %s, Earn Date: %s, Owner: %s",
                getAchievementName(), getRequirements(), getEarnDate(), getOwner().getGuildName());
    }

}

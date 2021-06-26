package models.guild;

import models.exception.DataValidationException;
import models.functionalities.ApplicationForm;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuildAchievement implements Serializable {

    private static List<GuildAchievement> guildAchievementExtent = new ArrayList<>();

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
        guildAchievementExtent.add(this);
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
        owner.addGuildAchievement(this);
    }

    public void deleteGuild() {
        if (this.owner != null) {
            Guild tmpOwner = this.owner;
            this.owner = null;
            tmpOwner.removeGuildAchievement(this);
        }
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

    /**
     * Guild Achievement Extension
     */
    public static List<GuildAchievement> getGuildAchievementsExtent() {
        return Collections.unmodifiableList(guildAchievementExtent);
    }

    public static void setApplicationFormExtent(List<GuildAchievement> guildAchievementExtent) {
        if (guildAchievementExtent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        GuildAchievement.guildAchievementExtent = guildAchievementExtent;
    }

    //testing
    /*public static void clearExtension() {
        guildAchievementExtent.clear();
    }*/

    @Override
    public String toString() {
        return String.format("Name: %s, Req: %s, Earn Date: %s, Owner: %s",
                getAchievementName(), getRequirements(), getEarnDate(), getOwner().getGuildName());
    }

}

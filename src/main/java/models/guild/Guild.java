package models.guild;

import models.exception.DataValidationException;
import models.player.Player;
import models.player.PlayerType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Guild implements Serializable {

    private static List<Guild> guildsExtent = new ArrayList<>();

    private final String guildName;                     //done
    private final LocalDate dateOfCreation;              //done

    private float reputationPoints;                      //done
    private String founderNickname;                     //done

    private Set<Player> guildMembers = new HashSet<>();

    private final static int STARTING_REP_POINTS = 0;

    private Faction guildFaction;

    /*// Collection of Guild applicants.
    private Set<ApplicationForm> applicationForms = new HashSet<>();

    //Collection of Guild achievements.
    private Set<GuildAchievement> guildAchievements = new HashSet<>();

    /**
     * Guild Constructor
     */
    public Guild(String guildName, Player guildFounder, Faction faction) {
        if (guildFounder.getPlayerType() != PlayerType.GUILD_FOUNDER) {
            throw new DataValidationException("wrong player type!");
        }
        this.guildName = guildName;
        this.dateOfCreation = LocalDate.now();
        setReputationPoints(STARTING_REP_POINTS);
        setFounderNickname(guildFounder.getNickname());
        setFaction(faction);
        addGuildMember(guildFounder);

        guildsExtent.add(this);
    }

    /**
     * Guild Extension
     */
    public static List<Guild> getGuildExtent() {
        return Collections.unmodifiableList(guildsExtent);
    }

    public static void setGuildExtent(List<Guild> guildExtent) {
        if (guildExtent == null) {
            throw new DataValidationException("extent cannot be null");
        }
        Guild.guildsExtent = guildExtent;
    }

    //testing
    public static void clearExtension() {
        guildsExtent.clear();
    }

    /**
     * Guild Name
     */
    public String getGuildName() {
        return guildName;
    }

    /**
     * Date Of Creation
     */
    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    /**
     * Reputation Points
     */
    public float getReputationPoints() {
        return reputationPoints;
    }

    public void setReputationPoints(float reputationPoints) {
        if (reputationPoints < 0) {
            throw new DataValidationException("reputation points cannot be less than 0");
        }
        this.reputationPoints = reputationPoints;
    }

    /**
     * Founder Nickname
     */
    public String getFounderNickname() {
        return founderNickname;
    }

    public void setFounderNickname(String founderNickname) {
        if (founderNickname != null && founderNickname.trim().isBlank()) {
            throw new DataValidationException("message cannot be empty");
        }
        this.founderNickname = founderNickname;
    }

    /**
     * Guild Faction
     */
    public Faction getGuildFaction() {
        return guildFaction;
    }

    public void setGuildFaction(Faction guildFaction) {
        if (guildFaction == null) {
            throw new DataValidationException("faction cannot be null!");
        }
        this.guildFaction = guildFaction;
    }

    /**
     * Player Association
     */
    public Set<Player> getGuildMembers() {
        return Collections.unmodifiableSet(guildMembers);
    }

    public void addGuildMember(Player newMember) {

        if (newMember == null) {
            throw new DataValidationException("Member is set to null!");
        }
        if (this.guildMembers.contains(newMember)) {
            return;
        }
        this.guildMembers.add(newMember);
        newMember.setGuild(this);
    }

    public void removeGuildMember(Player guildMember) {
        if (!this.guildMembers.contains(guildMember)) {
            return;
        }
        if (this.guildMembers.size() == 1) {
            throw new DataValidationException("Cannot remove last guild member!");
        }
        this.guildMembers.remove(guildMember);
        guildMember.setGuild(null);
    }

    /**
     * Faction Association
     */
    public Faction getFaction() {
        return this.guildFaction;
    }

    public void setFaction(Faction newFaction) {

        if (this.guildFaction == newFaction) {
            return;
        }

        if (this.guildFaction != null) {

            Faction tmpFaction = this.guildFaction;
            this.guildFaction = null;
            tmpFaction.removeGuild(this);

            if (newFaction != null) {
                this.guildFaction = newFaction;
                newFaction.addGuild(this);
            }

        } else {
            this.guildFaction = newFaction;
            newFaction.addGuild(this);
        }
    }

    /**
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Founder: %s, Created: %s, Members: %d/99, Faction: %s",
                getGuildName(), getFounderNickname(), getDateOfCreation(), getGuildMembers().size(), getFaction());
    }

}

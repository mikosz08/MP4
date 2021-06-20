package models.guild;

import models.exception.DataValidationException;
import models.functionalities.ApplicationForm;
import models.functionalities.events.EventImpl;
import models.functionalities.shop.Boost;
import models.player.Player;
import models.player.PlayerType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Guild implements Serializable {

    private static List<Guild> guildsExtent = new ArrayList<>();

    private final String guildName;
    private final LocalDate dateOfCreation;

    private Faction guildFaction;

    private float reputationPoints;
    private String founderNickname;

    //Collection of Guild Members
    private Set<Player> guildMembers = new HashSet<>();

    // Collection of Guild applicants.
    private Set<ApplicationForm> applicationForms = new HashSet<>();

    //Collection of Guild achievements.
    private Set<GuildAchievement> guildAchievements = new HashSet<>();

    //Collection of Guild logs.
    private Set<Log> guildLogs = new HashSet<>();

    //Collection of Guild events.
    private Set<EventImpl> guildEvents = new HashSet<>();

    //Collection of Guild Boosts.
    private Set<Boost> guildBoosts = new HashSet<>();

    private final static int STARTING_REP_POINTS = 0;

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
    public static List<Guild> getGuildsExtent() {
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
            throw new DataValidationException("founder nick cannot be empty");
        }
        this.founderNickname = founderNickname;
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

        //if there is one member left - it becomes a founder
        if (this.guildMembers.size() == 1) {
            guildMembers.iterator().next().becomeGuildFounder();
        }

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

    public Set<ApplicationForm> getApplicationForms() {
        return Collections.unmodifiableSet(applicationForms);
    }

    /**
     * ApplicationForm Association
     */
    public void addApplicationForm(ApplicationForm applicationForm) {
        if (applicationForm == null) {
            throw new DataValidationException("Application from is required!");
        }

        if (this.applicationForms.contains(applicationForm)) {
            return;
        }

        if (applicationForm.getGuild() != this) {
            throw new DataValidationException("Guild is not related!");
        }

        this.applicationForms.add(applicationForm);

    }

    public void removeApplicationForm(ApplicationForm applicationForm) {

        if (!this.applicationForms.contains(applicationForm)) {
            return;
        }

        this.applicationForms.remove(applicationForm);
        applicationForm.delete();

    }

    /**
     * GuildAchievement Association
     */
    public Set<GuildAchievement> getGuildAchievements() {
        return Collections.unmodifiableSet(guildAchievements);
    }

    public void addGuildAchievement(GuildAchievement newGuildAchievement) {
        if (newGuildAchievement == null) {
            throw new DataValidationException("Achievement is required!");
        }
        if (newGuildAchievement.getOwner() != this) {
            throw new DataValidationException("Achievement is not related to this Guild!");
        }
        this.guildAchievements.add(newGuildAchievement);
    }

    public void removeGuildAchievement(GuildAchievement guildAchievement) {
        if (!this.guildAchievements.contains(guildAchievement)) {
            return;
        }
        this.guildAchievements.remove(guildAchievement);
        guildAchievement.deleteGuild();
    }

    public void deleteAchievements() {
        List<GuildAchievement> copiedAchievementList = new ArrayList<>(this.guildAchievements);
        for (GuildAchievement ga : copiedAchievementList) {
            ga.deleteGuild();
        }
    }

    /**
     * Log Association
     */
    public Set<Log> getGuildLogs() {
        return Collections.unmodifiableSet(guildLogs);
    }

    public void addLog(Log log) {
        if (log == null) {
            throw new DataValidationException("Log is required!");
        }
        if (log.getOwner() != this) {
            throw new DataValidationException("Log is not related to this Guild!");
        }
        this.guildLogs.add(log);
    }

    public void removeLog(Log log) {
        if (!this.guildLogs.contains(log)) {
            return;
        }
        this.guildLogs.remove(log);
        log.delete();
    }

    public void deleteLogs() {
        List<Log> copiedLogs = new ArrayList<>(this.guildLogs);
        for (Log log : copiedLogs) {
            log.delete();
        }
    }

    /**
     * Event Association
     */
    public void addEvent(EventImpl eventOrganization) {
        if (eventOrganization == null) {
            throw new DataValidationException("Event is required!");
        }
        if (this.guildEvents.contains(eventOrganization)) {
            return;
        }
        if (eventOrganization.getOrganizer() != this) {
            throw new DataValidationException("Guild is not related!");
        }
        this.guildEvents.add(eventOrganization);
    }

    public void removeEvent(EventImpl eventOrganization) {
        if (!this.guildEvents.contains(eventOrganization)) {
            return;
        }
        this.guildEvents.remove(eventOrganization);
        eventOrganization.deleteCreatedEvent();
    }

    /**
     * Boost Association
     */
    public Set<Boost> getGuildBoosts() {
        return Collections.unmodifiableSet(guildBoosts);
    }

    public void addBoost(Boost newBoost) {
        if (newBoost == null) {
            throw new DataValidationException("Boost is required!");
        }
        if (newBoost.getOwner() != this) {
            throw new DataValidationException("Boost is not related to this Guild!");
        }
        this.guildBoosts.add(newBoost);
    }

    public void removeGuildBoost(Boost boost) {
        if (!this.guildBoosts.contains(boost)) {
            return;
        }
        this.guildBoosts.remove(boost);
        boost.deleteGuild();
    }

    public void deleteBoost() {
        List<Boost> copiedBoosts = new ArrayList<>(this.guildBoosts);
        for (Boost b : copiedBoosts) {
            b.deleteGuild();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guild guild = (Guild) o;
        return Float.compare(guild.reputationPoints, reputationPoints) == 0
                && guildName.equals(guild.guildName)
                && dateOfCreation.equals(guild.dateOfCreation)
                && founderNickname.equals(guild.founderNickname)
                && guildMembers.equals(guild.guildMembers)
                && guildFaction.equals(guild.guildFaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guildName, dateOfCreation, reputationPoints,
                founderNickname, guildMembers, guildFaction);
    }
}

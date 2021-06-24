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

    private String guildName;
    private LocalDate dateOfCreation;

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
    private final static int MAX_GUILD_MEMBERS = 99;

    /**
     * Guild Constructor
     */
    public Guild(String guildName, Player guildFounder, Faction faction) {
        if (guildFounder.getPlayerType() != PlayerType.GUILD_FOUNDER) {
            throw new DataValidationException("wrong player type!");
        }
        setGuildName(guildName);
        this.dateOfCreation = LocalDate.now();
        setReputationPoints(MAX_GUILD_MEMBERS);
        setFounderNickname(guildFounder.getNickname());
        setFaction(faction);
        addGuildMember(guildFounder);

        List<Guild> copiedExtent = new ArrayList<>(guildsExtent);
        copiedExtent.add(this);
        setGuildExtent(copiedExtent);
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

    public void setGuildName(String guildName) {
        if (guildName == null || guildName.trim().isBlank()) {
            throw new DataValidationException("Guild needs a Name!");
        }
        this.guildName = guildName;
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
        if (founderNickname == null || founderNickname.trim().isBlank()) {
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

        if(newMember.getPlayerType() != PlayerType.GUILD_FOUNDER){
            newMember.becomeGuildMember();
        }

        if(getGuildMembers().size() >= MAX_GUILD_MEMBERS){
            throw new DataValidationException("Guild is full!");
        }

        this.guildMembers.add(newMember);
        newMember.setGuild(this);
    }

    public void removeGuildMember(Player guildMember) {

        if (!this.guildMembers.contains(guildMember)) {
            return;
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

    public void setFaction(Faction faction) {
        /*if (faction == null) {
            throw new DataValidationException("Faction is required!");
        }
        this.guildFaction = faction;
        faction.addGuild(this);*/
        //-------
        if (this.guildFaction == faction) {
            return;
        }

        if (this.guildFaction != null) {

            Faction tmpFaction = this.guildFaction;
            this.guildFaction = null;
            tmpFaction.removeGuild(this);

            if (faction != null) {
                this.guildFaction = faction;
                faction.addGuild(this);
            }

        } else {
            this.guildFaction = faction;
            faction.addGuild(this);
        }
    }

    //tutaj mozna znullować ;/
    /*public void setFaction(Faction newFaction) {

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
    }*/

    /**
     * ApplicationForm Association
     */
    public Set<ApplicationForm> getApplicationForms() {
        return Collections.unmodifiableSet(applicationForms);
    }

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
    public Set<EventImpl> getGuildEvents() {
        return Collections.unmodifiableSet(guildEvents);
    }

    public void setGuildEvents(Set<EventImpl> guildEvents) {
        if (guildEvents == null) {
            throw new DataValidationException("Event cannot be null!");
        }
        this.guildEvents = guildEvents;
    }

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
    public Set<Boost> getBoosts() {
        return Collections.unmodifiableSet(guildBoosts);
    }

    public void addBoost(Boost boost) {
        if (boost == null) {
            throw new DataValidationException("Boost is null!");
        }
        if (this.guildBoosts.contains(boost)) {
            return;
        }
        this.guildBoosts.add(boost);
        boost.setOwner(this);
    }

    public void removeBoost(Boost boost) {
        if (!this.guildBoosts.contains(boost)) {
            return;
        }

        this.guildBoosts.remove(boost);
        boost.setOwner(null);
    }

    public void deleteGuild() {

        //delete members
        for (Player member : getGuildMembers()) {
            member.abandonGuild();
        }

        //delete applications
        for (ApplicationForm ap : getApplicationForms()) {
            ap.delete();
        }

        //delete faction
        getFaction().removeGuild(this);
        //delete achievements
        deleteAchievements();
        //delete logs
        deleteLogs();

        //delete boost
        for (Boost boost : getBoosts()) {
            removeBoost(boost);
        }

        if(getGuildsExtent().contains(this)){
            List<Guild> copiedExtent = new ArrayList<>(guildsExtent);
            copiedExtent.remove(this);
            setGuildExtent(copiedExtent);
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
    public int hashCode() {
        return Objects.hash(guildName, dateOfCreation, reputationPoints,
                founderNickname, guildMembers, guildFaction);
    }
}

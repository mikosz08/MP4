package models.player;

import models.exception.DataValidationException;
import models.functionalities.ApplicationForm;
import models.functionalities.events.EventImpl;
import models.functionalities.shop.Shop;
import models.guild.Guild;
import models.player.equipment.Equipment;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static models.functionalities.shop.Shop.mainShop;

public class Player implements Serializable {

    private static List<Player> playersExtent = new ArrayList<>();

    private static int playerId = 1;
    private final int id;
    private final static int STARTING_REP_POINTS = 0;

    private String nickname;
    private int level;
    private final Set<String> playerClasses = new HashSet<>(); //1..*
    private String messageOfTheDay;  //0..1
    private String sentenceOfTheDay; //0..1
    private float reputationEarned;  //0..1
    private float reputationAwarded;
    private PlayerLocation playerLocation;
    private LocalDate dateOfAccession;
    private PlayerType playerType;
    private Guild memberGuild;
    private EventImpl participatedEvent;
    private Shop shop;

    //Collection of Player created events.
    private Set<EventImpl> playerCreatedEvents = new HashSet<>();

    //Collection of ApplicationForms
    private final Set<ApplicationForm> applicationForms = new HashSet<>();

    //Collection of Equipment
    private final Set<Equipment> equipment = new HashSet<>();

    /**
     * Player Constructor
     */
    public Player(String nickname, int level, PlayerLocation playerLocation, String playerClass) {
        this.id = playerId++;
        setNickname(nickname);
        setLevel(level);
        addPlayerClass(playerClass);
        setReputationAwarded(STARTING_REP_POINTS);
        setPlayerLocation(playerLocation);
        setDateOfAccession(LocalDate.now());
        setPlayerType(PlayerType.APPLICANT);
        setShop(mainShop);
        playersExtent.add(this);
    }

    /**
     * Dynamic Inheritance
     */
    public void becomeGuildMember() {
        setPlayerType(PlayerType.MEMBER);
    }

    public void becomeGuildOfficer() {
        setPlayerType(PlayerType.OFFICER);
    }

    public void becomeGuildFounder() {
        setPlayerType(PlayerType.FOUNDER);
    }

    public void becomeGuildApplicant() {
        setPlayerType(PlayerType.APPLICANT);
    }

    /**
     * ID
     */
    public int getId() {
        return id;
    }

    /**
     * Nickname
     */
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (nickname == null || nickname.trim().isBlank()) {
            throw new DataValidationException("Nickname cannot be null or empty!");
        }
        this.nickname = nickname;
    }

    /**
     * Player Level
     */
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level < 1) {
            throw new DataValidationException("Level cannot be less than 1!");
        }
        this.level = level;
    }

    /**
     * Player Classes
     */
    public Set<String> getPlayerClasses() {
        return Collections.unmodifiableSet(playerClasses);
    }

    public void addPlayerClass(String playerClass) {
        if (playerClass == null || playerClass.trim().isBlank()) {
            throw new DataValidationException("Player class cannot be null or empty!");
        }
        this.playerClasses.add(playerClass);
    }

    public void removePlayerClass(String playerClass) {
        if (playerClass == null || playerClass.trim().isBlank()) {
            throw new DataValidationException("Player class cannot be null or empty!");
        }
        if (playerClasses.size() <= 1) {
            throw new DataValidationException("Cannot remove last player's class!");
        }
        this.playerClasses.remove(playerClass);
    }

    /**
     * Player MessageOfTheDay
     */
    public Optional<String> getMessageOfTheDay() {
        return Optional.ofNullable(messageOfTheDay);
    }

    public void setMessageOfTheDay(String messageOfTheDay) {
        this.messageOfTheDay = messageOfTheDay;
    }

    /**
     * Player SentenceOfTheDay
     */
    public Optional<String> getSentenceOfTheDay() {
        return Optional.ofNullable(sentenceOfTheDay);
    }

    public void setSentenceOfTheDay(String sentenceOfTheDay) {
        if (playerType != PlayerType.FOUNDER) {
            throw new DataValidationException("Only guild founder can set the sentence!");
        }
        this.sentenceOfTheDay = sentenceOfTheDay;
    }

    /**
     * Player ReputationEarned
     */
    public float getReputationEarned() {
        return reputationEarned;
    }

    public void setReputationEarned(float reputationEarned) {
        if (reputationEarned < 0) {
            throw new DataValidationException("Reputation earned cannot be less than 0!");
        }
        this.reputationEarned = reputationEarned;
    }

    /**
     * Player Reputation Awarded.
     */
    public float getReputationAwarded() {
        return reputationAwarded;
    }

    public void setReputationAwarded(float reputationAwarded) {
        if (reputationAwarded < 0) {
            throw new DataValidationException("Reputation awarded cannot be less than 0!");
        }
        this.reputationAwarded = reputationAwarded;
    }

    /**
     * Player Date of Accession.
     */
    public LocalDate getDateOfAccession() {
        return dateOfAccession;
    }

    public void setDateOfAccession(LocalDate dateOfAccession) {
        if (dateOfAccession == null) {
            throw new DataValidationException("Date of accession cannot be null!");
        }
        this.dateOfAccession = dateOfAccession;
    }

    /**
     * Player Days of Service
     */
    public int getDaysOfService() {
        return (int) Duration.between(dateOfAccession.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
    }

    /**
     * Player Location
     */
    public PlayerLocation getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(PlayerLocation playerLocation) {
        if (playerLocation == null) {
            throw new DataValidationException("Location cannot be null!");
        }
        this.playerLocation = playerLocation;
    }

    /**
     * Player Type
     */
    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        if (playerType == null) {
            throw new DataValidationException("Player need a type!");
        }
        this.playerType = playerType;
    }

    /**
     * Player Extension
     */
    public static List<Player> getPlayersExtent() {
        return Collections.unmodifiableList(playersExtent);
    }

    public static void setPlayersExtent(List<Player> extent) {
        if (extent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        Player.playersExtent = extent;
    }

    public static List<Player> getRankingByLevel() {
        if (playersExtent.size() <= 1) {
            return getPlayersExtent();
        }
        return playersExtent
                .stream()
                .sorted(Comparator.comparing(Player::getLevel).reversed())
                .collect(toList());
    }

    public static List<Player> getRankingByRepPoints() {
        if (playersExtent.size() <= 1) {
            return getPlayersExtent();
        }
        return playersExtent
                .stream()
                .sorted(Comparator.comparing(Player::getReputationAwarded).reversed())
                .collect(toList());
    }

    //testing
    /*public static void clearExtension() {
        playersExtent.clear();
    }*/

    /**
     * Guild Association
     */
    public Guild getGuild() {
        return this.memberGuild;
    }

    public void setGuild(Guild newGuild) {

        if (this.memberGuild == newGuild) {
            return;
        }

        if (this.memberGuild != null) {
            Guild tmpGuild = this.memberGuild;
            this.memberGuild = null;
            tmpGuild.removeGuildMember(this);

            if (newGuild != null) {
                this.memberGuild = newGuild;
                newGuild.addGuildMember(this);
            }

        } else {
            this.memberGuild = newGuild;
            newGuild.addGuildMember(this);
        }

        if (newGuild == null) {
            this.becomeGuildApplicant();
        }

    }

    /**
     * ApplicationForm Association
     */
    public Set<ApplicationForm> getApplicationForms() {
        return Collections.unmodifiableSet(applicationForms);
    }

    public void addApplication(ApplicationForm newApplicationForm) {

        if (newApplicationForm == null) {
            throw new DataValidationException("ApplicationForm is required!");
        }

        if (this.applicationForms.contains(newApplicationForm)) {
            return;
        }

        if (newApplicationForm.getPlayer() != this) {
            throw new DataValidationException("Member is not related to this application!");
        }

        this.applicationForms.add(newApplicationForm);
    }

    public void removeApplicationForm(ApplicationForm applicationForm) {

        if (!this.applicationForms.contains(applicationForm)) {
            return;
        }

        this.applicationForms.remove(applicationForm);
        applicationForm.delete();

    }

    /**
     * Event Creation Association
     */
    public Set<EventImpl> getPlayerCreatedEvents() {
        return Collections.unmodifiableSet(playerCreatedEvents);
    }

    public void addEvent(EventImpl eventToCreate) {
        if (eventToCreate == null) {
            throw new DataValidationException("Event is required!");
        }
        if (this.playerCreatedEvents.contains(eventToCreate)) {
            return;
        }
        if (eventToCreate.getCreator() != this) {
            throw new DataValidationException("Creator is not related!");
        }
        this.playerCreatedEvents.add(eventToCreate);
    }

    public void removeEvent(EventImpl eventOrganization) {
        if (!this.playerCreatedEvents.contains(eventOrganization)) {
            return;
        }
        this.playerCreatedEvents.remove(eventOrganization);
        eventOrganization.deleteCreatedEvent();
    }

    /**
     * Event Participation Association
     */
    public EventImpl getParticipatedEvent() {
        return participatedEvent;
    }

    public void setParticipatedEvent(EventImpl participatedEvent) {
        if (participatedEvent == null) {
            throw new DataValidationException("Event is required!");
        }
        this.participatedEvent = participatedEvent;
        participatedEvent.addEventParticipant(this);
    }

    public void deleteParticipatedEvent() {
        if (this.participatedEvent != null) {
            EventImpl tmpEvent = this.participatedEvent;
            this.participatedEvent = null;
            tmpEvent.removeEventParticipant(this);
        }
    }

    /**
     * Equipment Association
     */
    public Set<Equipment> getEquipment() {
        return Collections.unmodifiableSet(equipment);
    }

    public void addEquipment(Equipment eq) {
        if (eq == null) {
            throw new DataValidationException("Boost is null!");
        }
        if (this.equipment.contains(eq)) {
            return;
        }
        this.equipment.add(eq);
        eq.setShop(this);
    }

    public void removeEquipment(Equipment eq) {
        if (!this.equipment.contains(eq)) {
            return;
        }

        this.equipment.remove(eq);
        eq.setShop(null);
    }

    /**
     * Shop Association
     */
    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop newShop) {

        if (this.shop == newShop) {
            return;
        }

        if (this.shop != null) {

            Shop tmpShop = this.shop;
            this.shop = null;
            tmpShop.removePlayer(this);

            if (newShop != null) {
                this.shop = newShop;
                newShop.addPlayer(this);
            }

        } else {
            this.shop = newShop;
            newShop.addPlayer(this);
        }
    }

    public void abandonGuild() {

        //delete guild
        getGuild().removeGuildMember(this);

        //null message of the day
        setMessageOfTheDay(null);

        //reset awarded reputation
        setReputationAwarded(0);

        //delete participated event
        if (participatedEvent != null) {
            participatedEvent.removeEventParticipant(this);
        }

        //delete created events
        if (this.getPlayerType() == PlayerType.OFFICER || this.getPlayerType() == PlayerType.FOUNDER) {
            for (EventImpl event : getPlayerCreatedEvents()) {
                event.deleteCreatedEvent();
            }
        }

        // player should now be an applicant
        setPlayerType(PlayerType.APPLICANT);
    }

    @Override
    public String toString() {
        return String.format("Nick: %s, Lv: %d, Location: %s, Awarded Rep: %.2f",
                getNickname(), getLevel(), getPlayerLocation(), getReputationAwarded());
    }
}

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
import java.util.stream.Collectors;

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
    public Player(String nickname, int level, PlayerLocation playerLocation,
                  PlayerType playerType, String playerClass) {
        this.id = playerId++;
        setNickname(nickname);
        setLevel(level);
        addPlayerClass(playerClass);
        setReputationAwarded(STARTING_REP_POINTS);
        setPlayerLocation(playerLocation);
        setDateOfAccession(LocalDate.now());
        setPlayerType(playerType);
        playersExtent.add(this);
    }

    /**
     * Dynamic Inheritance
     */
    public void becomeGuildMember() {
        setPlayerType(PlayerType.GUILD_MEMBER);
    }

    public void becomeGuildOfficer() {
        setPlayerType(PlayerType.GUILD_OFFICER);
    }

    public void becomeGuildFounder() {
        setPlayerType(PlayerType.GUILD_FOUNDER);
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
        if (messageOfTheDay != null && messageOfTheDay.trim().isBlank()) {
            throw new DataValidationException("Message cannot be empty!");
        }
        this.messageOfTheDay = messageOfTheDay;
    }

    /**
     * Player SentenceOfTheDay
     */
    public Optional<String> getSentenceOfTheDay() {
        return Optional.ofNullable(sentenceOfTheDay);
    }

    public void setSentenceOfTheDay(String sentenceOfTheDay) {
        if (playerType != PlayerType.GUILD_FOUNDER) {
            throw new DataValidationException("Only guild founder can set the sentence!");
        }
        if (sentenceOfTheDay != null && sentenceOfTheDay.trim().isBlank()) {
            throw new DataValidationException("Sentence cannot be empty!");
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
        this.reputationAwarded += reputationAwarded;
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
                .collect(Collectors.toList());
    }

    public static List<Player> getRankingByRepPoints() {
        if (playersExtent.size() <= 1) {
            return getPlayersExtent();
        }
        return playersExtent
                .stream()
                .sorted(Comparator.comparing(Player::getReputationAwarded).reversed())
                .collect(Collectors.toList());
    }

    //testing
    public static void clearExtension() {
        playersExtent.clear();
    }

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

    public void deleteParticipants() {
        if (this.participatedEvent != null) {
            EventImpl tmpEvent = this.participatedEvent;
            this.participatedEvent = null;
            tmpEvent.removeEventParticipant(this);
        }
    }

    /**
     * Equipment Association
     */
    public void addEquipment(Equipment eq) {
        if (eq == null) {
            throw new DataValidationException("Log is required!");
        }
        if (eq.getOwner() != this) {
            throw new DataValidationException("Log is not related to this Guild!");
        }
        this.equipment.add(eq);
    }

    public void removeEquipment(Equipment eq) {
        if (!this.equipment.contains(eq)) {
            return;
        }
        this.equipment.remove(eq);
        eq.delete();
    }

    public void deleteEquipment() {
        List<Equipment> copiedEq = new ArrayList<>(this.equipment);
        for (Equipment eq : copiedEq) {
            eq.delete();
        }
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
        shop.addPlayer(this);
    }

    /**
     * Shop Delete
     */
    public void deleteShop() {
        if (this.shop != null) {
            Shop tmpShop = this.shop;
            this.shop = null;
            tmpShop.removePlayer(this);
        }
    }

    /**
     * Utilities
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return level == player.level
                && Float.compare(player.reputationEarned, reputationEarned) == 0
                && Float.compare(player.reputationAwarded, reputationAwarded) == 0
                && nickname.equals(player.nickname)
                && playerClasses.equals(player.playerClasses)
                && Objects.equals(messageOfTheDay, player.messageOfTheDay)
                && Objects.equals(sentenceOfTheDay, player.sentenceOfTheDay)
                && playerLocation.equals(player.playerLocation)
                && dateOfAccession.equals(player.dateOfAccession)
                && playerType == player.playerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, level, playerClasses,
                messageOfTheDay, sentenceOfTheDay, reputationEarned,
                reputationAwarded, playerLocation, dateOfAccession, playerType);
    }

    @Override
    public String toString() {
        return String.format("Nick: %s, Lv: %d, Location: %s, Awarded Rep: %.2f",
                getNickname(), getLevel(), getPlayerLocation(), getReputationAwarded());
    }
}

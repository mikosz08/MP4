package models.functionalities.events;

import models.exception.DataValidationException;
import models.guild.Guild;
import models.player.Player;
import models.player.PlayerType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


import static models.functionalities.events.EventLevelConstants.*;
import static models.functionalities.events.EventType.*;

public class EventImpl implements ExpEvent, GoldEvent, SocialEvent, Serializable {

    private static List<EventImpl> eventExtent = new ArrayList<>();

    private static int eventOrganizationId = 0;
    private final int id;

    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private EnumSet<EventType> eventTypes;

    //ExpEvent
    private Integer expPercentageBoost;
    //GoldEvent
    private Integer goldPercentageBoost;
    //SocialEvent
    private Integer reputationPercentageBoost;

    private Player creator;
    private Guild organizer;

    //Collection of Event participants.
    private Set<Player> eventParticipants = new HashSet<>();

    /**
     * Event Constructor
     */
    public EventImpl(Guild organizer, Player creator, String eventName, LocalDate startDate, LocalDate endDate,
                     Integer expPercentageBoost, Integer goldPercentageBoost, Integer reputationPercentageBoost,
                     EnumSet<EventType> eventTypes) {
        id = ++eventOrganizationId;
        setCreator(creator);
        setOrganizer(organizer);
        setEventType(eventTypes);
        setEventName(eventName);
        setStartDate(startDate);
        setEndDate(endDate);

        if (eventTypes.contains(EXP_EVENT)) {
            setExpPercentageBoost(expPercentageBoost);
        }
        if (eventTypes.contains(GOLD_EVENT)) {
            setGoldFindChanceBoost(goldPercentageBoost);
        }
        if (eventTypes.contains(REP_EVENT)) {
            setReputationEarnBoost(reputationPercentageBoost);
        }

        eventExtent.add(this);
    }

    /**
     * Event ID
     */
    public int getId() {
        return id;
    }

    /**
     * Event Required Level
     */
    @Override
    public int getRequiredLevel() {

        if (eventTypes.contains(EXP_EVENT) && eventTypes.contains(GOLD_EVENT) && eventTypes.contains(REP_EVENT)) {
            return EXP_LEVEL_REQ + GOLD_LEVEL_REQ + REP_LEVEL_REQ;
        } else if (eventTypes.contains(EXP_EVENT) && eventTypes.contains(GOLD_EVENT) && !eventTypes.contains(REP_EVENT)) {
            return EXP_LEVEL_REQ + GOLD_LEVEL_REQ;
        } else if (eventTypes.contains(EXP_EVENT) && !eventTypes.contains(GOLD_EVENT) && eventTypes.contains(REP_EVENT)) {
            return EXP_LEVEL_REQ + REP_LEVEL_REQ;
        } else if (!eventTypes.contains(EXP_EVENT) && eventTypes.contains(GOLD_EVENT) && eventTypes.contains(REP_EVENT)) {
            return GOLD_LEVEL_REQ + REP_LEVEL_REQ;
        } else if (!eventTypes.contains(EXP_EVENT) && !eventTypes.contains(GOLD_EVENT) && eventTypes.contains(REP_EVENT)) {
            return REP_LEVEL_REQ;
        } else if (eventTypes.contains(EXP_EVENT) && !eventTypes.contains(GOLD_EVENT) && !eventTypes.contains(REP_EVENT)) {
            return EXP_LEVEL_REQ;
        } else if (!eventTypes.contains(EXP_EVENT) && eventTypes.contains(GOLD_EVENT) && !eventTypes.contains(REP_EVENT)) {
            return GOLD_LEVEL_REQ;
        }

        throw new DataValidationException("Event need a type.");
    }

    /**
     * Event Type
     */
    private void setEventType(EnumSet<EventType> eventTypes) {
        if (eventTypes == null || eventTypes.isEmpty()) {
            throw new DataValidationException("Event need at least one type.");
        }
        this.eventTypes = eventTypes;
    }

    /**
     * Event EXP
     */
    @Override
    public int getExpPercentageBoost() {
        if (eventTypes.contains(EXP_EVENT)) {
            return expPercentageBoost;
        }
        throw new DataValidationException("This is not an EXP_EVENT");
    }

    @Override
    public void setExpPercentageBoost(int percentageBoost) {
        if (percentageBoost == 0) {
            this.expPercentageBoost = null;
        }
        this.expPercentageBoost = percentageBoost;
    }

    /**
     * Event GOLD
     */
    @Override
    public int getGoldFindChanceBoost() {
        if (eventTypes.contains(GOLD_EVENT)) {
            return goldPercentageBoost;
        }
        throw new DataValidationException("This is not an GOLD_EVENT");
    }

    @Override
    public void setGoldFindChanceBoost(int percentageBoost) {
        if (percentageBoost == 0) {
            this.goldPercentageBoost = null;
        }
        this.goldPercentageBoost = percentageBoost;
    }

    /**
     * Event REP
     */
    @Override
    public int getReputationEarnBoost() {
        if (eventTypes.contains(REP_EVENT)) {
            return reputationPercentageBoost;
        }
        throw new DataValidationException("This is not an REP_EVENT");
    }

    @Override
    public void setReputationEarnBoost(int percentageBoost) {
        if (percentageBoost == 0) {
            this.reputationPercentageBoost = null;
        }
        this.reputationPercentageBoost = percentageBoost;
    }

    /**
     * Event Name
     */
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        if (eventName == null) {
            throw new DataValidationException("Event need a name.");
        }
        this.eventName = eventName;
    }

    /**
     * Event Start Date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new DataValidationException("Event need an start date.");
        }
        this.startDate = startDate;
    }

    /**
     * Event End Date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new DataValidationException("Event need an end date.");
        }
        this.endDate = endDate;
    }

    /**
     * Event Creator Association
     */
    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        if (creator == null) {
            throw new DataValidationException("Creator member is required!");
        }

        PlayerType creatorType = creator.getPlayerType();
        if (creatorType != PlayerType.OFFICER && creatorType != PlayerType.FOUNDER) {
            throw new DataValidationException(creatorType + " is not allowed to create an event!");
        }

        this.creator = creator;
        creator.addEvent(this);
    }

    /**
     * Guild Association
     */
    public Guild getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Guild organizer) {
        if (organizer == null) {
            throw new DataValidationException("Guild is required!");
        }
        this.organizer = organizer;
        organizer.addEvent(this);
    }

    /**
     * Delete Created Events
     */
    public void deleteCreatedEvent() {

        if (this.organizer != null) {
            Guild tmpGuild = this.organizer;
            this.organizer = null;
            tmpGuild.removeEvent(this);
        }

        if (this.creator != null) {
            Player tmpPlayer = this.creator;
            this.creator = null;
            tmpPlayer.removeEvent(this);
        }
        eventExtent.remove(this);
    }

    /**
     * Event Participant Association
     */
    public void addEventParticipant(Player player) {
        if (player == null) {
            throw new DataValidationException("Player is required!");
        }
        if (player.getParticipatedEvent() != this) {
            throw new DataValidationException("Player is not related to this Event!");
        }
        this.eventParticipants.add(player);
    }

    public void removeEventParticipant(Player player) {
        if (!this.eventParticipants.contains(player)) {
            return;
        }
        this.eventParticipants.remove(player);
        player.deleteParticipatedEvent();
    }

    public void deleteParticipants() {
        List<Player> copiedEventParticipants = new ArrayList<>(this.eventParticipants);
        for (Player participant : copiedEventParticipants) {
            participant.deleteParticipatedEvent();
        }
    }

    /**
     * Event Extension
     */
    public static List<EventImpl> getEventsExtent() {
        return Collections.unmodifiableList(eventExtent);
    }

    public static void setEventExtent(List<EventImpl> eventExtent) {
        if (eventExtent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        EventImpl.eventExtent = eventExtent;
    }

    //testing
    /*public static void clearExtension() {
        eventExtent.clear();
    }*/

    /**
     * Unique
     */
    private boolean isUnique() {
        for (EventImpl event : eventExtent) {
            if (event.organizer == this.organizer && event.creator == this.creator) {
                return false;
            }
        }
        return true;
    }

    /**
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Start: %s, End: %s, Organizer: %s, Creator: %s",
                getEventName(), getStartDate(), getEndDate(), getOrganizer().getGuildName(), getCreator().getNickname());
    }

}

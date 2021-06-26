package models.functionalities;

import models.exception.DataValidationException;
import models.guild.Guild;
import models.player.Player;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationForm implements Serializable {

    private static int applicationFormId = 0;
    private final int id;

    private String messageContent;
    private LocalDate messagePostDate;
    //private boolean isAccepted;

    private Guild guild;
    private Player player;

    private static List<ApplicationForm> applicationFormExtent = new ArrayList<>();

    /**
     * ApplicationForm Constructor
     */
    public ApplicationForm(String messageContent, Guild guild, Player player) {
        id = ++applicationFormId;
        setMessageContent(messageContent);
        this.messagePostDate = LocalDate.now();
        setGuild(guild);
        setPlayer(player);

        /*if (!isUnique()) {
            throw new DataValidationException("Duplicated ApplicationForm!");
        }*/

        List<ApplicationForm> copiedExtent = new ArrayList<>(applicationFormExtent);
        copiedExtent.add(this);
        setApplicationFormExtent(copiedExtent);

    }

    /**
     * ID
     */
    public int getId() {
        return id;
    }

    /**
     * ApplicationMessage
     */
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        if (messageContent == null) {
            this.messageContent = "-";
        }
        this.messageContent = messageContent;
    }

    /**
     * MessagePostDate
     */
    public LocalDate getMessagePostDate() {
        return messagePostDate;
    }

    public void setMessagePostDate(LocalDate messagePostDate) {
        this.messagePostDate = messagePostDate;
    }

    /**
     * ApplicationGuild
     */
    public Guild getGuild() {
        return guild;
    }

    private void setGuild(Guild guild) {
        if (guild == null) {
            throw new DataValidationException("Guild is required!");
        }
        this.guild = guild;
        guild.addApplicationForm(this);
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        if (player == null) {
            throw new DataValidationException("Player is required!");
        }
        this.player = player;
        player.addApplication(this);
    }

    /**
     * Delete Application
     */
    public void delete() {

        if (this.guild != null) {
            Guild tmpGuild = this.guild;
            this.guild = null;
            tmpGuild.removeApplicationForm(this);
        }

        if (this.player != null) {
            Player tmpPlayer = this.player;
            this.player = null;
            tmpPlayer.removeApplicationForm(this);
        }
        List<ApplicationForm> copiedExtent = new ArrayList<>(ApplicationForm.getApplicationFormsExtent());
        copiedExtent.remove(this);
        ApplicationForm.setApplicationFormExtent(copiedExtent);
    }

    /**
     * Unique
     */
    private boolean isUnique() {
        for (ApplicationForm ap : applicationFormExtent) {
            if (ap.guild == this.guild && ap.player == this.player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Application Extension
     */
    public static List<ApplicationForm> getApplicationFormsExtent() {
        return Collections.unmodifiableList(applicationFormExtent);
    }

    public static void setApplicationFormExtent(List<ApplicationForm> applicationFormExtent) {
        if (applicationFormExtent == null) {
            throw new DataValidationException("extent cannot be null");
        }
        ApplicationForm.applicationFormExtent = applicationFormExtent;
    }

    //testing
    /*public static void clearExtension() {
        applicationFormExtent.clear();
    }*/

    @Override
    public String toString() {
        return String.format("ID: %d, To: %s, From: %s, At: %s, '%s'  ",
                getId(), getGuild().getGuildName(), getPlayer().getNickname(), getMessagePostDate(), getMessageContent());
    }
}
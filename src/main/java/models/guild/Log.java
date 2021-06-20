package models.guild;

import models.exception.DataValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Log implements Serializable {

    private static List<Log> logsExtent = new ArrayList<>();

    private String message;
    private final LocalDate time;

    private Guild owner;

    public Log(String message, Guild owner) {
        setMessage(message);
        this.time = LocalDate.now();
        setOwner(owner);

        logsExtent.add(this);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message != null && message.trim().isBlank()) {
            throw new DataValidationException("message cannot be empty");
        }
        this.message = message;
    }

    public LocalDate getTime() {
        return time;
    }

    /**
     * Log Owner
     */
    public void delete() {
        if (this.owner != null) {
            Guild tmpOwner = this.owner;
            this.owner = null;
            tmpOwner.removeLog(this);
        }
    }

    public Guild getOwner() {
        return owner;
    }

    private void setOwner(Guild owner) {
        if (owner == null) {
            throw new DataValidationException("Owner is required!");
        }
        this.owner = owner;
        owner.addLog(this);
    }

    /**
     * Log Extension
     */
    public static List<Log> getLogsExtent() {
        return Collections.unmodifiableList(logsExtent);
    }

    public static void setLogExtent(List<Log> logsExtent) {
        if (logsExtent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        Log.logsExtent = logsExtent;
    }

    //testing
    public static void clearExtension() {
        logsExtent.clear();
    }

    /**
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("Message: %s [%s]",
                getMessage(), getTime());
    }

}

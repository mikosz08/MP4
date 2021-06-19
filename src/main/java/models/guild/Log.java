package models.guild;

import models.exception.DataValidationException;

import java.time.LocalDate;

public class Log {

    private String message;
    private final LocalDate time;

    private Guild owner;

    public Log(String message, Guild owner) {
        setMessage(message);
        this.time = LocalDate.now();
        setOwner(owner);
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
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("Message: %s [%s]",
                getMessage(), getTime());
    }

}

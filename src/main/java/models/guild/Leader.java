package models.guild;

import models.exception.DataValidationException;

import java.io.Serializable;

public class Leader implements Serializable {

    private String leaderName;

    public Leader(String leaderName) {
        setLeaderName(leaderName);
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        if (leaderName == null) {
            throw new DataValidationException("Leader must have a name!");
        }
        this.leaderName = leaderName;
    }

    /**
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("LeaderName: %s", getLeaderName());
    }

}

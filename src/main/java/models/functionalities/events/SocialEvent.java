package models.functionalities.events;

public interface SocialEvent extends Event{
    int getReputationEarnBoost();
    void setReputationEarnBoost(int percentageBoost);
}

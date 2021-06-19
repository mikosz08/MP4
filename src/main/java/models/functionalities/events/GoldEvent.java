package models.functionalities.events;

public interface GoldEvent extends Event{
    int getGoldFindChanceBoost();
    void setGoldFindChanceBoost(int percentageBoost);
}

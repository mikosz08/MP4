package models.functionalities.events;

public interface ExpEvent  extends Event{
    int getExpPercentageBoost();
    void setExpPercentageBoost(int percentageBoost);
}

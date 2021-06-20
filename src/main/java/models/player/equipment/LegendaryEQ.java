package models.player.equipment;


import models.exception.DataValidationException;

public class LegendaryEQ extends Equipment {

    private int bonusGoldFind;
    private int bonusExpEarn;
    private int bonusRepEarn;

    public LegendaryEQ(String equipmentName, int requiredLevel, Stats stats, int bonusGoldFind, int bonusExpEarn, int bonusRepEarn) {
        super(equipmentName, requiredLevel, stats);
        setBonusGoldFind(bonusGoldFind);
        setBonusExpEarn(bonusExpEarn);
        setRequiredLevel(requiredLevel);
    }

    public int getBonusGoldFind() {
        return bonusGoldFind;
    }

    public void setBonusGoldFind(int bonusGoldFind) {
        if (bonusGoldFind < 0) {
            throw new DataValidationException("Bonus can not be below 0.");
        }
        this.bonusGoldFind = bonusGoldFind;
    }

    public int getBonusExpEarn() {
        return bonusExpEarn;
    }

    public void setBonusExpEarn(int bonusExpEarn) {
        if (bonusExpEarn < 0) {
            throw new DataValidationException("Bonus can not be below 0.");
        }
        this.bonusExpEarn = bonusExpEarn;
    }

    public int getBonusRepEarn() {
        return bonusRepEarn;
    }

    public void setBonusRepEarn(int bonusRepEarn) {
        if (bonusRepEarn < 0) {
            throw new DataValidationException("Bonus can not be below 0.");
        }
        this.bonusRepEarn = bonusRepEarn;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format("Bonus GoldFind: %s, Bonus ExpEarn: %s, Bonus RepEarn: %s ",
                        getBonusGoldFind(), getBonusExpEarn(), getBonusRepEarn());
    }
}

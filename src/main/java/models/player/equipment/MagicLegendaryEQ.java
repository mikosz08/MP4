package models.player.equipment;

import models.exception.DataValidationException;

public class MagicLegendaryEQ extends LegendaryEQ implements MagicEQ {

    private int bonusMagicDamage;

    public MagicLegendaryEQ(String equipmentName, int requiredLevel, Stats stats, int bonusGoldFind, int bonusExpEarn, int bonusRepEarn, int bonusMagicDamage) {
        super(equipmentName, requiredLevel, stats, bonusGoldFind, bonusExpEarn, bonusRepEarn);
        setBonusMagicDamage(bonusMagicDamage);
    }

    @Override
    public int getBonusMagicDamage() {
        return this.bonusMagicDamage;
    }

    @Override
    public void setBonusMagicDamage(int bonusMagicDamage) {
        if (bonusMagicDamage < 0) {
            throw new DataValidationException("Bonus can not be below 0.");
        }
        this.bonusMagicDamage = bonusMagicDamage;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format("Bonus MagicDMG: %s",
                        getBonusMagicDamage());
    }
}

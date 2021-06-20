package models.player.equipment;


import models.exception.DataValidationException;

public class MagicEqImpl extends Equipment implements MagicEQ {

    private int bonusMagicDamage;

    public MagicEqImpl(String equipmentName, int requiredLevel, Stats stats, int bonusMagicDamage) {
        super(equipmentName, requiredLevel, stats);
        setBonusMagicDamage(bonusMagicDamage);
    }

    @Override
    public int getBonusMagicDamage() {
        return bonusMagicDamage;
    }

    @Override
    public void setBonusMagicDamage(int bonusMagicDamage) {
        if(bonusMagicDamage < 0){
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

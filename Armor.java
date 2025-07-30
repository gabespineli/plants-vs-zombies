/**
 * Represents armor that can be equipped by zombies for extra protection.
 * Provides attributes for armor health, speed, damage, type, and active state.
 * Abstract class to be extended by specific armor types (e.g., Bucket, Cone, Flag).
 */
public abstract class Armor {
    protected int armorHP;
    protected int armorSpeed;
    protected int armorDamage;
    protected boolean isActive;
    protected String armorType;

    /**
     * Gets the type of the armor.
     * @return the armor type as a string
     */
    public String getArmorType() {
        return armorType;
    }

    /**
     * Gets the damage modifier provided by the armor.
     * @return the armor damage value
     */
    public int getArmorDamage() {
        return armorDamage;
    }

    /**
     * Gets the speed modifier provided by the armor.
     * @return the armor speed value
     */
    public int getArmorSpeed() {
        return armorSpeed;
    }

    /**
     * Reduces the armor's health by the specified damage amount.
     * Deactivates the armor if health drops to zero or below.
     * @param d the damage to apply
     */
    public void takeDamage(int d){
        if (isActive)
            armorHP -= d;

        if (armorHP <= 0) {
            isActive = false;
        }
    }

    /**
     * Checks if the armor is currently active.
     * @return true if armor is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }
}

/**
 * Represents the Flag armor for zombies, used for special flag-carrying zombies.
 */
public class Flag extends Armor {
    /**
     * Constructs a Flag armor with predefined stats.
     */
    public Flag() {
        armorHP = 0;
        armorSpeed = -1;
        armorDamage = 0;
        isActive = true;
        armorType = "Flag";
    }
}

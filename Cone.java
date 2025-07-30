/**
 * Represents the Cone armor for zombies, providing moderate health and modifiers.
 */
public class Cone extends Armor {
    /**
     * Constructs a Cone armor with predefined stats.
     */
    public Cone() {
        armorHP = 143;
        armorSpeed = 2;
        armorDamage = -2;
        isActive = true;
        armorType = "Cone";
    }
}

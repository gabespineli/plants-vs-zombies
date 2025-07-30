/**
 * Represents the Bucket armor for zombies, providing extra health and modifiers.
 */
public class Bucket extends Armor{
    /**
     * Constructs a Bucket armor with predefined stats.
     */
    public Bucket() {
        armorHP = 425;
        armorSpeed = 3;
        armorDamage = -5;
        isActive = true;
        armorType = "Bucket";
    }
}

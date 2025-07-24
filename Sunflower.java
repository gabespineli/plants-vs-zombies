/**
 * Represents a Sunflower plant that produces suns.
 * Extends the Plant class and provides sun generation capabilities.
 */
public class Sunflower extends Plant {
    private static int lastPlacedSunflower = -9999;

    /**
     * Constructs a new Sunflower with default stats.
     * Sets health to 200, cost to 50, and various cooldown values.
     */
    public Sunflower() {
        super();
        health = 300;
        cost = 50;
        placementCooldown = 8;
        actionCooldown = 24;
        cooldown = 24;
        plantType = "Sunflower";
    }

    /**
     * Gets the tick when the last Sunflower was placed.
     * Used for placement cooldown management.
     * @return the tick value when the last Sunflower was placed
     */
    public static int getLastPlacedSunflower() { return lastPlacedSunflower; }

    /**
     * Sets the tick when a Sunflower was placed.
     * Used for tracking placement cooldowns.
     * @param tick the tick value to set
     */
    public static void setLastPlacedSunflower(int tick) { lastPlacedSunflower = tick; }

    /**
     * Updates the Sunflower's state and potentially produces sun.
     * If the action cooldown is equal to 0, creates a new Sun object worth 25 points.
     * Prints a message when sun is produced.
     * @return a new Sun object if cooldown allows, null otherwise
     */
    public Sun updateSunflower(){
        reduceActionCooldown();
        Sun sun;
        if (checkActionCooldown()) {
            //sun = new Sun(rowPos, columnPos, 25);
            resetActionCooldown(getCooldown());
            System.out.println("Sunflower at (" + rowPos + "," + columnPos + ") produced " + sun.getValue() + " sun!");
            return sun;
        }
        return null;
    }
}
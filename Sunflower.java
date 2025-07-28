/**
 * Represents a Sunflower plant that produces suns.
 * Extends the Plant class and provides sun generation capabilities.
 */
public class Sunflower extends Plant {
    private static final int placementCooldown = 250;
    private static int placementTimer;

    /**
     * Constructs a new Sunflower with default stats.
     * Sets health to 200, cost to 50, and various cooldown values.
     */
    public Sunflower() {
        super();
        health = 30;
        cost = 50;
        actionCooldown = 24;
        cooldown = 24;
        plantType = "Sunflower";
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer--;
        }
    }

    public static int isReady() {
        return placementTimer;
    }

    public static void setPlacementCooldown() {
        placementTimer = placementCooldown;
    }

    /**
     * Updates the Sunflower's state and potentially produces sun.
     * If the action cooldown is equal to 0, creates a new Sun object worth 25 points.
     * Prints a message when sun is produced.
     * @return a new Sun object if cooldown allows, null otherwise
     */
    public Sun updateSunflower(){
        reduceActionCooldown();
        Sun sun;
        if (actionCooldown <= 0) {
            sun = new Sun(rowPos, columnPos+0.5, 25);
            actionCooldown = cooldown;
            System.out.println("Sunflower at (" + rowPos + "," + columnPos + ") produced " + sun.getValue() + " sun!");
            return sun;
        }
        return null;
    }
}
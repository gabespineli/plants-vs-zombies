/**
 * Represents a Sunflower plant that produces suns.
 * Extends the Plant class and provides sun generation capabilities.
 */
public class Sunflower extends Plant {
    private static final double PLACEMENT_COOLDOWN = 7.5;
    private static double placementTimer;

    /**
     * Constructs a Sunflower with default stats and cost.
     */
    public Sunflower() {
        super();
        health = 30;
        cost = 50;
        actionCooldown = 24;
        cooldown = 24;
        plantType = "Sunflower";
    }

    /**
     * Reduces the placement cooldown timer for Sunflower.
     */
    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    /**
     * Checks the current placement cooldown timer for Sunflower.
     * @return the remaining cooldown time
     */
    public static double checkPlacementCD() {
        return placementTimer;
    }

    /**
     * Sets the placement timer to the default cooldown value.
     */
    public static void setPlacementTimer() {
        placementTimer = PLACEMENT_COOLDOWN;
    }

    /**
     * Updates the Sunflower's state and produces sun if cooldown is finished.
     * @return a new Sun object if produced, null otherwise
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
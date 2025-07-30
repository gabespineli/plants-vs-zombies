/**
 * Represents a Wallnut plant that acts as a defensive barrier with high health.
 * Extends Plant and manages cooldowns.
 */
public class Wallnut extends Plant {
    private static final double PLACEMENT_COOLDOWN = 30;
    private static double placementTimer;

    /**
     * Constructs a Wallnut with default stats and health.
     */
    public Wallnut(){
        super();
        health = 2666;
        cost = 50;
        plantType = "Wallnut";
    }

    /**
     * Reduces the placement cooldown timer for Wallnut.
     */
    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    /**
     * Checks the current placement cooldown timer for Wallnut.
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
}

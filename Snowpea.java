import java.util.ArrayList;

/**
 * Represents a Snowpea plant that shoots frost projectiles to damage and freeze zombies.
 * Extends Peashooter and manages cooldowns and frost logic.
 */
public class Snowpea extends Peashooter {
    private static final double PLACEMENT_COOLDOWN = 7.5;
    private static double placementTimer;

    /**
     * Constructs a Snowpea with default stats and cost.
     */
    public Snowpea() {
        super();
        cost = 175;
        plantType = "Snowpea";
    }

    /**
     * Reduces the placement cooldown timer for Snowpea.
     */
    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    /**
     * Checks the current placement cooldown timer for Snowpea.
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
     * Updates the Snowpea's state and shoots a frost projectile if cooldown is finished.
     * @param aliveZombies the list of alive zombies on the board
     * @return a new Frost projectile if shot, null otherwise
     */
    public Frost updateSnowpea(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        Frost frost;

        if (actionCooldown <= 0) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() < RANGE) {
                    if (z.getColumnPos() - columnPos >= 2) {
                        frost = new Frost(columnPos, rowPos, projectileDamage);
                    } else {
                        frost = new Frost(columnPos, rowPos, directDamage);
                    }
                    System.out.println("Snowpea shot");
                    actionCooldown = cooldown;
                    return frost;
                }
            }
        }
        return null;
    }


}

import java.util.ArrayList;

/**
 * Represents a Peashooter plant that shoots peas at zombies.
 * Extends the Plant class and provides offensive capabilities against zombies.
 * Has different damage values for close-range and long-range attacks.
 */
 public class Peashooter extends Plant {
    private static final double PLACEMENT_COOLDOWN = 7.5;
    private static double placementTimer;
    protected int projectileDamage;
    protected int directDamage;
    protected final int RANGE;


    /**
     * Constructs a new Peashooter with default stats.
     * Sets health to 200, cost to 100, and various cooldown values.
     * Initializes damage values and range.
     */
    public Peashooter() {
        super();
        health = 30;
        cost = 100;
        actionCooldown = 0;
        cooldown = 1.5;
        plantType = "Peashooter";
        projectileDamage = 7;
        directDamage = 2 * projectileDamage;
        RANGE = 9;
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    public static double checkPlacementCD() {
        return placementTimer;
    }

    public static void setPlacementTimer() {
        placementTimer = PLACEMENT_COOLDOWN;
    }

    /**
     * Updates the Peashooter's cooldowns and potentially creates a pea projectile.
     * Checks for zombies in range and creates appropriate pea with damage based on distance.
     * Close zombies (within 4 columns) receive direct damage, others receive projectile damage.
     *
     * @param aliveZombies the list of alive zombies to check for targeting
     * @return a new Pea object if a target is found and cooldown allows, null otherwise
     */
    public Pea updatePeashooter(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        Pea pea;

        if (actionCooldown <= 0) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() < RANGE) {
                    if (z.getColumnPos() - columnPos >= 2) {
                        pea = new Pea(columnPos, rowPos, projectileDamage);
                    } else {
                        pea = new Pea(columnPos, rowPos, directDamage);
                    }
                    System.out.println("Peashooter shot");
                    actionCooldown = cooldown;
                    return pea;
                }
            }
        }
        return null; // no pea created
    }
}

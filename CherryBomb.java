import java.util.ArrayList;

/**
 * Represents a CherryBomb plant that explodes and deals massive damage to zombies in a 3x3 area.
 * Extends ExplosivePlant and manages cooldowns and explosion logic.
 */
public class CherryBomb extends ExplosivePlant {
    private static final double PLACEMENT_COOLDOWN = 50;
    private static double placementTimer;
    private int damage;

    /**
     * Constructs a CherryBomb with default stats and damage.
     */
    public CherryBomb() {
        super();
        health = 30;
        cost = 150;
        actionCooldown = 1.2;
        cooldown = 1.2;
        plantType = "CherryBomb";
        damage = 700;
    }

    /**
     * Reduces the placement cooldown timer for CherryBomb.
     */
    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    /**
     * Checks the current placement cooldown timer for CherryBomb.
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
     * Updates the CherryBomb's state and explodes if cooldown is finished.
     * Damages zombies in a 3x3 area around the CherryBomb.
     * @param aliveZombies the list of alive zombies on the board
     */
    @Override
    public void updateExplosive(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        if (actionCooldown <= 0 && !exploded) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() >= rowPos-1 && z.getRowPos() <= rowPos+1 && z.getColumnPos() >= columnPos-1.5 && z.getColumnPos() <= columnPos+1.5) {
                    z.takeDamage(damage);
                }
            }
            isAlive = false;
            startExplosionTimer();
        } else if (exploded) {
            reduceExplosionTimer();
        }
    }
}

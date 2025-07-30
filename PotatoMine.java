import java.util.ArrayList;

/**
 * Represents a PotatoMine plant that explodes after a delay, dealing massive damage to zombies in its cell.
 * Extends ExplosivePlant and manages cooldowns and explosion logic.
 */
public class PotatoMine extends ExplosivePlant {
    private static final double PLACEMENT_COOLDOWN = 30;
    private static double placementTimer;
    private int damage;

    /**
     * Constructs a PotatoMine with default stats and damage.
     */
    public PotatoMine() {
        super();
        health = 30;
        cost = 25;
        actionCooldown = 15;
        cooldown = 15;
        plantType = "PotatoMine";
        damage = 700;
    }

    /**
     * Reduces the placement cooldown timer for PotatoMine.
     */
    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    /**
     * Checks the current placement cooldown timer for PotatoMine.
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
     * Checks if the PotatoMine is ready to detonate.
     * @return true if detonated, false otherwise
     */
    public boolean isDetonated() {
        return actionCooldown <= 0;
    }

    /**
     * Updates the PotatoMine's state and explodes if cooldown is finished.
     * Damages zombies in the same cell as the PotatoMine.
     * @param aliveZombies the list of alive zombies on the board
     */
    @Override
    public void updateExplosive(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        if (!isAlive && !isDetonated()) {
            return;
        }
        if (isDetonated() && !exploded && isAlive) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() >= columnPos-0.5 && z.getColumnPos() <= columnPos+0.5) {
                    z.takeDamage(damage);
                    isAlive = false;
                    startExplosionTimer();
                }
            }
        } else if (exploded) {
            reduceExplosionTimer();
        }
    }
}

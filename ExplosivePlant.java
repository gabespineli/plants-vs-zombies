import java.util.ArrayList;

/**
 * Extends the Plant Class and is a parent class for Cherry Bomb and Potato Mine.
 * Helps manage attributes like Explosion timer and exploded.
 */
public abstract class ExplosivePlant extends Plant {
    protected double explosionTimer = 2; // seconds to show explosion
    protected boolean exploded = false;

    /**
     * Constructs an ExplosivePlant and sets it position
     */
    public ExplosivePlant() {
        super();
    }

    /**
     * Stats a timer once the plant explodes.
     * Sets eploded to true
     */
    public void startExplosionTimer() {
        explosionTimer = 2;
        exploded = true;
    }

    /**
     * Reduces the explosionTimer.
     */
    public void reduceExplosionTimer() {
        if (explosionTimer > 0) {
            explosionTimer -= 0.03;
        }
    }

    /**
     * Checks if the explosion is finished
     * @return true or false whether it has exploded and the timer is less than or equal to 0
     */
    public boolean explosionFinished() {
        return exploded && explosionTimer <= 0;
    }

    /**
     * An abstract method that updates all explosive plants
     */
    public abstract void updateExplosive(ArrayList<Zombie> aliveZombies);
}

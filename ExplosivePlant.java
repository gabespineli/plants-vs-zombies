import java.util.ArrayList;

public abstract class ExplosivePlant extends Plant {
    protected double explosionTimer = 2; // seconds to show explosion
    protected boolean exploded = false;

    public ExplosivePlant() {
        super();
    }

    public void startExplosionTimer() {
        explosionTimer = 2;
        exploded = true;
    }

    public void reduceExplosionTimer() {
        if (explosionTimer > 0) {
            explosionTimer -= 0.03;
        }
    }

    public boolean explosionFinished() {
        return exploded && explosionTimer <= 0;
    }

    public abstract void updateExplosive(ArrayList<Zombie> aliveZombies);
}

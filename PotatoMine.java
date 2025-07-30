import java.util.ArrayList;

public class PotatoMine extends ExplosivePlant {
    private static final double PLACEMENT_COOLDOWN = 30;
    private static double placementTimer;
    private double explosionTimer = 2;
    private int damage;

    public PotatoMine() {
        super();
        health = 30;
        cost = 25;
        actionCooldown = 15;
        cooldown = 15;
        plantType = "PotatoMine";
        damage = 700;
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



    public boolean isDetonated() {
        return actionCooldown <= 0;
    }

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

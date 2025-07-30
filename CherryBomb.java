import java.util.ArrayList;

public class CherryBomb extends ExplosivePlant {
    private static final double PLACEMENT_COOLDOWN = 50;
    private static double placementTimer;
    private int damage;

    public CherryBomb() {
        super();
        health = 30;
        cost = 150;
        actionCooldown = 1.2;
        cooldown = 1.2;
        plantType = "CherryBomb";
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

import java.util.ArrayList;

public class CherryBomb extends Plant {
    private static final int placementCooldown = 1667;
    private static int placementTimer;
    private int damage;

    public CherryBomb() {
        super();
        health = 30;
        cost = 150;
        actionCooldown = 1.2;
        cooldown = 1.2;
        plantType = "Cherry Bomb";
        damage = 700;
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer--;
        }
    }

    public static int isReady() {
        return placementTimer;
    }

    public static void setPlacementCooldown() {
        placementTimer = placementCooldown;
    }

    public void updateCheery(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        if (actionCooldown == 0) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() >= rowPos-1 && z.getRowPos() <= rowPos+1 && z.getColumnPos() >= columnPos-1.5 && z.getColumnPos() <= columnPos+1.5) {
                    z.takeDamage(damage);
                }
            }
            isAlive = false;
        }
    }
}

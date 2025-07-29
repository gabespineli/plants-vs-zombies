import java.util.ArrayList;

public class PotatoMine extends Plant {
    private static final int PLACEMENT_COOLDOWN = 250;
    private static int placementTimer;
    private int damage;

    public PotatoMine() {
        super();
        health = 300;
        cost = 25;
        actionCooldown = 15;
        cooldown = 15;
        plantType = "PotatoMine";
        damage = 1800;
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer--;
        }
    }

    public static int isReady() {
        return placementTimer;
    }

    public static void setPlacementTimer() {
        placementTimer = PLACEMENT_COOLDOWN;
    }

    public void updatePotato(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        if (actionCooldown == 0) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() >= columnPos-0.5 && z.getColumnPos() <= columnPos+0.5) {
                    z.takeDamage(damage);
                }
            }
            isAlive = false;
        }
    }
}

import java.util.ArrayList;

public class CherryBomb extends Plant {
    private static int lastPlacedCherry = -9999;
    private int damage;

    public CherryBomb() {
        super();
        health = 300;
        cost = 150;
        placementCooldown = 50;
        actionCooldown = 1;
        cooldown = 1;
        plantType = "Cherry Bomb";
        damage = 1800;
    }

    public static int getLastPlacedCherry() {
        return lastPlacedCherry;
    }

    public static void setLastPlacedCherry(int tick) {
        lastPlacedCherry = tick;
    }

    public void updateCheery(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();

        if (checkActionCooldown()) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() >= rowPos-1 && z.getRowPos() <= rowPos+1 && z.getColumnPos() >= columnPos-1 && z.getColumnPos() <= columnPos+1) {
                    z.takeDamage(damage);
                }
            }
            isAlive = false;
        }
    }
}

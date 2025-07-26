import java.util.ArrayList;

public class CherryBomb extends Plant {
    private static int lastPlacedCherry = -9999;
    private int damage;

    public CherryBomb() {
        super();
        health = 30;
        cost = 150;
        placementCooldown = 1667;
        actionCooldown = 1.2;
        cooldown = 1.2;
        plantType = "Cherry Bomb";
        damage = 700;
    }

    public static int getLastPlacedCherry() {
        return lastPlacedCherry;
    }

    public static void setLastPlacedCherry(int tick) {
        lastPlacedCherry = tick;
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

import java.util.ArrayList;

public class Potatomine extends Plant {
    private static int lastPlacedPotato = -9999;
    private int damage;

    public Potatomine() {
        super();
        health = 300;
        cost = 25;
        placementCooldown = 30;
        actionCooldown = 15;
        cooldown = 15;
        plantType = "Potato Mine";
        damage = 1800;
    }

    public static int getLastPlacedPotato() {
        return lastPlacedPotato;
    }

    public static void setLastPlacedPotato(int tick) {
        lastPlacedPotato = tick;
    }

    public void updatePotato(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();

        if (checkActionCooldown()) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() == columnPos) {
                    z.takeDamage(damage);
                    isAlive = false;
                }
            }
        }
    }
}

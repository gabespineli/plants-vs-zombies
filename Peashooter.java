import java.util.ArrayList;

public class Peashooter extends Plant {
    private int projectileDamage;
    private int directDamage;
    private int RANGE;
    private static int lastPlacedPeashooter = 0;

    public Peashooter() {
        super();
        setHealth(200);
        setCost(100);
        setPlacementCooldown(3);
        setActionCooldown(0);
        setCooldown(2);
        setPlantType("Peashooter");
        projectileDamage = 7;
        directDamage = 2 * projectileDamage;
        RANGE = 9;
    }

    public static int getLastPlacedPeashooter() {
        return lastPlacedPeashooter;
    }

    public static void setLastPlacedPeashooter(int tick) {
        lastPlacedPeashooter = tick;
    }

    public Pea updatePeashooter(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        Pea pea;

        if (checkActionCooldown()) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == this.getRowPos() && z.getColumnPos() < RANGE) {
                    if (z.getColumnPos() - this.getColumnPos() >= 4) {
                        pea = new Pea(getColumnPos(), getRowPos(), projectileDamage);
                    } else {
                        pea = new Pea(getColumnPos(), getRowPos(), directDamage);
                    }
                    resetActionCooldown(getCooldown());
                    return pea;
                }
            }
        }
        return null; // no pea created
    }
}

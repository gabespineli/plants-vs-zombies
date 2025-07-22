import java.util.ArrayList;

public class Snowpea extends Peashooter {
    private static int lastPlacedSnowpea = -9999;

    public Snowpea() {
        super();
        cost = 175;
    }

    public static int getLastPlacedSnowpea() {
        return lastPlacedSnowpea;
    }

    public static void setLastPlacedSnowpea(int tick) {
        lastPlacedSnowpea = tick;
    }

    public Frostpea updateSnowpea(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        Frostpea pea;

        if (checkActionCooldown()) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() < RANGE) {
                    if (z.getColumnPos() - columnPos >= 4) {
                        pea = new Frostpea(columnPos, rowPos, projectileDamage);
                    } else {
                        pea = new Frostpea(columnPos, rowPos, directDamage);
                    }
                    resetActionCooldown(getCooldown());
                    return pea;
                }
            }
        }
        return null; // no pea created
    }
}

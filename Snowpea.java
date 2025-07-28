import java.util.ArrayList;

public class Snowpea extends Peashooter {
    private static final int placementCooldown = 250;
    private static int placementTimer;

    public Snowpea() {
        super();

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

    public Frost updateSnowpea(ArrayList<Zombie> aliveZombies) {
        reduceActionCooldown();
        Frost frost;

        if (actionCooldown <= 0) {
            for (Zombie z : aliveZombies) {
                if (z.getRowPos() == rowPos && z.getColumnPos() < RANGE) {
                    if (z.getColumnPos() - columnPos >= 2) {
                        frost = new Frost(columnPos, rowPos, projectileDamage);
                    } else {
                        frost = new Frost(columnPos, rowPos, directDamage);
                    }
                    System.out.println("Snowpea shot");
                    actionCooldown = cooldown;
                    return frost;
                }
            }
        }
        return null; // no pea created
    }


}

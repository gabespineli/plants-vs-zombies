import java.util.ArrayList;

public class Snowpea extends Peashooter {
    private static final double PLACEMENT_COOLDOWN = 7.5;
    private static double placementTimer;

    public Snowpea() {
        super();
        plantType = "Snowpea";
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer--;
        }
    }

    public static double checkPlacementCD() {
        return placementTimer;
    }

    public static void setPlacementTimer() {
        placementTimer = PLACEMENT_COOLDOWN;
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

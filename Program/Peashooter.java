package Program;

public class Peashooter extends Plant {
    private int attackSpeedInterval;
    private int lastShotTick;
    private int projectileDamage;
    private int directDamage;
    private final int RANGE;
    private static int lastPlacedPeashooter = -9999;

    public Peashooter() {
        super();
        setHealth(200);
        setCost(100);
        setPlantCD(3);
        setPlantType("Peashooter");
        attackSpeedInterval = 2;
        projectileDamage = 7;
        directDamage = 2 * projectileDamage;
        RANGE = 9;
    }

    public static int getLastPlacedPeashooter() { return lastPlacedPeashooter; }
    public static void setLastPlacedPeashooter(int tick) { lastPlacedPeashooter = tick; }

    public void shoot(Zombie z) {
        // attack interval
        
        if (z.getHealth() > 0){
            if (z.getRowPos() - getRowPos() >= 4) {
                z.takeDamage(projectileDamage);
            } else {
                z.takeDamage(directDamage); // directDamage?
            }
        }
    }
}

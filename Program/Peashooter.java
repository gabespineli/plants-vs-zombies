public class Peashooter extends Plant {
    private int attackSpeedInterval;
    private int lastShotTick;
    private int projectileDamage;
    private int directDamage;
    private final int RANGE = 9;

    public Peashooter() {
        super();
        setHealth(200);
        setCost(100);
        setPlantCd(7);
        setLastPlacedTick(-9999);
        setPlantType("Peashooter");
        attackSpeedInerval = 2;
        projectileDamage = 7;
    }

    public void attack(Zombie z) {
        if (z.getHealth() > 0){
            if (z.getRowPos() - getRowPos() >= 4) {
                z.takeDamage(projectileDamage);
            } else {
                z.takeDamage(projectileDamage * 2);
            }
        }
    }
}

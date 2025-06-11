public class Peashooter extends Plant {
    private int attackSpeed;
    private int projectileDamage;
    private int directDamage;
    private final int RANGE = 9;

    public Peashooter(int aS, int pD, int dD, int c, int h, int x, int y) {
        super(c, h, x, y);
        attackSpeed = aS;
        projectileDamage = pD;
        directDamage = dD;
    }

    public void attack(Zombie z) {
        if (z.getHealth() > 0){
            z.takeDamage(projectileDamage);
        }
    }
}

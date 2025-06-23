package Program;
import java.util.ArrayList;

public class Peashooter extends Plant {
    private int projectileDamage;
    private int directDamage;
    private int RANGE;
    private static int lastPlacedPeashooter = -9999;

    public Peashooter() {
        super();
        setHealth(200);
        setCost(100);
        setPlacementCooldown(3);
        setActionCooldown(2);
        setPlantType("Peashooter");
        projectileDamage = 7;
        directDamage = 2 * projectileDamage;
        RANGE = 9;
    }

    public static int getLastPlacedPeashooter() { return lastPlacedPeashooter; }
    public static void setLastPlacedPeashooter(int tick) { lastPlacedPeashooter = tick; }

    public void updatePea(ArrayList<Zombie> aliveZombies){
        reduceActionCooldown();

        if(checkActionCooldown()){
            for (Zombie z : aliveZombies){
                if (z.getRowPos() == this.getRowPos()){
                    shoot(z);
                    resetActionCooldown(getActionCooldown());
                    break;
                }
            }
        }
    }

    public void shoot(Zombie z) {
        if (z.getRowPos() - getRowPos() >= 4) { z.takeDamage(projectileDamage); }
        else { z.takeDamage(directDamage); }
        System.out.println("Peashooter at row " + this.getRowPos() + " column " + this.getColumnPos() + " shot zombie at row " + z.getRowPos() + " column " + z.getColumnPos() + " (Zombie HP: " + z.getHealth() + ")");
    }
}

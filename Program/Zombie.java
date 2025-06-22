package Program;
import java.util.ArrayList;

public class Zombie extends Entity {
    private int walkInterval;
    private int moveCoolDown;
    private int damage;

    private static int zombieCount = 0;

    public Zombie() {
        super();
        setHealth(70);
        zombieCount++;
        walkInterval = 4;
        moveCoolDown = 4;
        damage = 10;
    }

    public void updateZomb(ArrayList<Plant> alivePlants){
        for (Plant p : alivePlants){
            if (p.getRowPos() == this.getRowPos() && p.getColumnPos() == this.getColumnPos()){
                eat(p);
                return;
            }
        }

        moveCoolDown--;

        if (moveCoolDown == 0){
            move();
            moveCoolDown = walkInterval;
        }
    }

    public void eat(Plant p) {
        p.takeDamage(damage);
        System.out.println("Zombie at row " + this.getRowPos() + " column " + this.getColumnPos() + " attacked " + p.getPlantType() + " at row " + p.getRowPos() + " column " + p.getColumnPos() + " (Plant HP: " + p.getHealth() + ")");
    }

    public void move() {
        System.out.print("Zombie previously in (" + getRowPos() + "," + getColumnPos() + "), ");
        setColumnPos(getColumnPos() - 1);
        System.out.println("now moved to (" + getRowPos() + "," + getColumnPos() + ")");
    }

    public int getWalkInterval() { return walkInterval; }
    public void setWalkInterval(int walkInterval) { this.walkInterval = walkInterval; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
}
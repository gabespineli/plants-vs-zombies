package Program;
import java.util.ArrayList;

public class Zombie extends Entity {
    private int walkInterval;
    private int lastMovementTick;
    private int damage;

    private static int zombieCount = 0;

    public Zombie() {
        super();
        setHealth(70);
        zombieCount++;
        walkInterval = 4;
        damage = 10;
    }

    public void updateZomb(ArrayList<Plant> alivePlants){
        for (int i = 0; i < alivePlants.size(); i++){
            if (alivePlants.get(i).getRowPos() == this.getRowPos()){
                eat(alivePlants.get(i));
                break;
            }
        }
    }

    public void eat(Plant p) {
        if (p.getHealth() > 0){
            p.takeDamage(damage);
        }
        System.out.println("Zombie at row " + this.getRowPos() + " column " + this.getColumnPos() + " shot zombie at row " + p.getRowPos() + " column " + p.getColumnPos());
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public void move(int currentTick) {
        if (currentTick - lastMovementTick >= walkInterval) {
            lastMovementTick = currentTick;
            System.out.print("Zombie previously in (" + getRowPos() + "," + getColumnPos() + "), ");
            setColumnPos(getColumnPos() - 1);
            System.out.println("now moved to (" + getRowPos() + "," + getColumnPos() + ")");
        }
    }

    public int getWalkInterval() { return walkInterval; }
    public void setWalkInterval(int walkInterval) { this.walkInterval = walkInterval; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
}
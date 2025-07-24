import java.util.ArrayList;

/**
 * Represents a zombie entity that moves toward plants and attacks them.
 * Extends Entity and provides zombie-specific behavior including movement, attack patterns, and interaction with plants.
 */
public class Zombie extends Entity {
    protected double walkInterval;
    protected double moveCooldown;
    protected int attackCooldown;
    protected int damage;
    protected boolean isFrozen;
    protected int frozenTime;
    protected double columnPos;
    /**
     * Constructs a new Zombie with default stats.
     * Sets health to 70, walk interval to 4, and damage to 10.
     */
    public Zombie() {
        super();
        health = 181;
        walkInterval = 4.7;
        moveCooldown = 4.7;
        attackCooldown = 1;
        damage = 100;
        isFrozen = false;
    }

    /**
     * Attacks the specified plant, dealing damage to it.
     * Prints appropriate messages based on whether the plant survives or dies.
     * @param p the plant to attack
     */
    protected void attack(Plant p) {
        p.takeDamage(damage);
        if (p.isAlive()) {
            System.out.println("Zombie at (" + this.getRowPos() + "," + this.getColumnPos() + ") attacked " + p.getPlantType() + " at (" + p.getRowPos() + "," + p.getColumnPos() + ") (Plant HP: " + p.getHealth() + ")");
        }
        else {
            System.out.println("Zombie at (" + this.getRowPos() + "," + this.getColumnPos() + ") has killed " + p.getPlantType() + " at (" + p.getRowPos() + "," + p.getColumnPos() + ")");
        }
    }

    /**
     * Moves the zombie one column to the left (toward the player's base).
     * Prints the zombie's previous and new positions.
     */
    protected void move() {
        System.out.print("Zombie previously in (" + getRowPos() + "," + getColumnPos() + "), ");
        columnPos -= 1;
        System.out.println("now moved to (" + getRowPos() + "," + getColumnPos() + ")");
    }

    /**
     * Updates the zombie's state by checking for plants to attack or moving.
     * If a plant is at the same position, attacks it instead of moving.
     * Otherwise, reduces move cooldown and moves when cooldown reaches 0.
     * @param alivePlants the list of alive plants to check for attacks
     */
    public void updateZombie(ArrayList<Plant> alivePlants){
        if (frozenTime == 0){
            walkInterval /= 2;
            moveCooldown /= 2;
            attackCooldown /= 2;
        }
        for (Plant p : alivePlants){
            if (p.getRowPos() == this.getRowPos() && p.getColumnPos() == this.getColumnPos()){
                attack(p);
                return;
            }
        }

        moveCooldown--;

        if (moveCooldown == 0){
            move();
            moveCooldown = walkInterval;
        }
    }

    public void slowDown() {
        if (!isFrozen){
            walkInterval *= 2;
            moveCooldown *= 2;
            attackCooldown *= 2;
            isFrozen = true;
        }
        frozenTime = 5;
    }
}
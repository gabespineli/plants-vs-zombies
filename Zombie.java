import java.util.ArrayList;

/**
 * Represents a zombie entity that moves toward plants and attacks them. shesh
 * Extends Entity and provides zombie-specific behavior including movement, attack patterns, and interaction with plants.
 */
public class Zombie extends Entity {
    private int walkInterval;
    private int moveCooldown;
    private int damage;

    /**
     * Constructs a new Zombie with default stats.
     * Sets health to 70, walk interval to 4, and damage to 10.
     */
    public Zombie() {
        super();
        setHealth(70);
        walkInterval = 4;
        moveCooldown = 4;
        damage = 10;
    }

    /**
     * Attacks the specified plant, dealing damage to it.
     * Prints appropriate messages based on whether the plant survives or dies.
     * @param p the plant to attack
     */
    private void attack(Plant p) {
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
    private void move() {
        System.out.print("Zombie previously in (" + getRowPos() + "," + getColumnPos() + "), ");
        setColumnPos(getColumnPos() - 1);
        System.out.println("now moved to (" + getRowPos() + "," + getColumnPos() + ")");
    }

    /**
     * Updates the zombie's state by checking for plants to attack or moving.
     * If a plant is at the same position, attacks it instead of moving.
     * Otherwise, reduces move cooldown and moves when cooldown reaches 0.
     * @param alivePlants the list of alive plants to check for attacks
     */
    public void updateZombie(ArrayList<Plant> alivePlants){
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
}
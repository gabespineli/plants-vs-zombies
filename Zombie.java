import java.util.ArrayList;

/**
 * Represents a zombie entity that moves toward plants and attacks them.
 * Extends Entity and provides zombie-specific behavior including movement, attack patterns, and interaction with plants.
 */
public class Zombie extends Entity {
    private int speed;
    private int damage;
    private Armor armor;
    /**
     * Constructs a new Zombie with default stats.
     * Sets health to 70, walk interval to 4, and damage to 10.
     */
    public Zombie() {
        super();
        health = 70;
        speed = 4;
        actionCooldown = 0;
        cooldown = 1;
        damage = 10;
        armor = null;
    }

    public Zombie(String type) {
        this();
        armor = createArmor(type);
        armor.setZombie(this);
    }

    public void setDamage(int d) {
        damage = d;
    }

    public int getDamage() {
        return damage;
    }

    public void setspeed(int speed) {
        this.speed = speed;
    }

    public int getspeed() {
        return speed;
    }

    public double getColumnPos() {
        return columnPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    public Armor getArmor() { return armor; }

    /**
     * Attacks the specified plant, dealing damage to it.
     * Prints appropriate messages based on whether the plant survives or dies.
     * @param p the plant to attack
     */
    private void attack(Plant p) {
        reduceActionCooldown();
        if (actionCooldown <= 0){
            p.takeDamage(damage);
            actionCooldown = cooldown;
            if (p.isAlive()) {
                System.out.println("Zombie at (" + rowPos + "," + columnPos + ") attacked " + p.getPlantType() + " at (" + p.getRowPos() + "," + p.getColumnPos() + ") (Plant HP: " + p.getHealth() + ")");
            }
            else {
                System.out.println("Zombie at (" + rowPos + "," + columnPos + ") has killed " + p.getPlantType() + " at (" + p.getRowPos() + "," + p.getColumnPos() + ")");
            }
        }
    }

    /**
     * Moves the zombie one column to the left (toward the player's base).
     * Prints the zombie's previous and new positions.
     */
    private void move() {
        columnPos -= 1/(speed / 0.03);
    }

    /**
     * Updates the zombie's state by checking for plants to attack or moving.
     * If a plant is at the same position, attacks it instead of moving.
     * Otherwise, reduces move cooldown and moves when cooldown reaches 0.
     * @param alivePlants the list of alive plants to check for attacks
     */
    public void updateZombie(ArrayList<Plant> alivePlants){
        for (Plant p : alivePlants){
            if (p.getRowPos() == rowPos && p.getColumnPos()+0.3 >= columnPos && p.getColumnPos()-0.3 <= columnPos){
                attack(p);
                return;
            }
        }
        move();
    }

    @Override
    public void takeDamage(int d){
        if (armor != null){
            if (armor.isActive()){
                armor.takeDamage(d);
                if (!armor.isActive()) {
                    armor = null;
                }
            }
        }


        else if (isAlive)
            health -= d;

        if (health <= 0) {
            isAlive = false;
        }
    }

    public Armor createArmor(String type) {
        switch (type.toLowerCase()) {
            case "flag" -> {
                return new Flag();
            }
            case "cone" -> {
                return new Cone();
            }
            case "bucket" -> {
                return new Bucket();
            }
        }
        return null;
    }
}
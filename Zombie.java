import java.util.ArrayList;

/**
 * Represents a zombie entity that moves toward plants and attacks them.
 * Extends Entity and provides zombie-specific behavior including movement, attack patterns, and interaction with plants.
 */
public class Zombie extends Entity {
    /** The movement speed of the zombie. */
    private double speed;
    /** The damage dealt by the zombie to plants. */
    private int damage;
    /** The armor equipped by the zombie, if any. */
    private Armor armor;
    /** Whether the zombie is currently frozen. */
    private boolean isFrozen;
    /** The remaining time the zombie stays frozen. */
    private double frozenTime;
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

    /**
     * Constructs a new Zombie with a specific armor type.
     * @param type the type of armor to equip ("flag", "cone", "bucket")
     */
    public Zombie(String type) {
        this();
        armor = createArmor(type);
        damage += armor.getArmorDamage();
        speed += armor.getArmorSpeed();
    }

    /**
     * Gets the current column position of the zombie.
     * @return the column position
     */
    public double getColumnPos() {
        return columnPos;
    }

    /**
     * Gets the current row position of the zombie.
     * @return the row position
     */
    public int getRowPos() {
        return rowPos;
    }

    /**
     * Returns whether the zombie is currently frozen.
     * @return true if frozen, false otherwise
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Gets the armor equipped by the zombie.
     * @return the Armor object, or null if none
     */
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
        if (isFrozen){
            frozenTime -= 0.03;
            if (frozenTime <= 0){
                speed /= 2;
                cooldown /= 2;
            }
        }

        for (Plant p : alivePlants){
            if (p.getRowPos() == rowPos && p.getColumnPos()+0.5 >= columnPos && p.getColumnPos()-0.5 <= columnPos){
                attack(p);
                return;
            }
        }
        move();
    }

    /**
     * Reduces the zombie's health by the specified damage amount, considering armor if present.
     * If health drops to zero or below, marks the zombie as dead.
     * @param d the amount of damage to take
     */
    @Override
    public void takeDamage(int d){
        if (armor != null){
            if (armor.isActive()){
                if (armor.getArmorType() == "flag" || d >= 500){
                    health -= d;
                }
                armor.takeDamage(d);
                if (!armor.isActive()) {
                    removeArmor();
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

    /**
     * Creates an Armor object based on the specified type.
     * @param type the type of armor ("flag", "cone", "bucket")
     * @return the Armor object, or null if type is invalid
     */
    private Armor createArmor(String type) {
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

    /**
     * Removes the zombie's armor and updates its damage and speed accordingly.
     */
    private void removeArmor() {
        damage -= armor.getArmorDamage();
        speed -= armor.getArmorSpeed();
    }

    /**
     * Checks if the zombie currently has armor equipped.
     * @return true if armor is present, false otherwise
     */
    public boolean hasArmor() {
        return armor != null;
    }

    /**
     * Freezes the zombie, doubling its speed and cooldown, and sets frozen time to 5 seconds.
     * Prints a message indicating the zombie has been frozen.
     */
    public void freeze() {
        if (!isFrozen){
            speed *= 2;
            cooldown *= 2;
            isFrozen = true;
            System.out.println("Zombie at (" + rowPos + "," + columnPos + ") has been frozen!");
        }
        frozenTime = 5;
    }
}
import java.util.ArrayList;

/**
 * Represents a zombie entity that moves toward plants and attacks them.
 * Extends Entity and provides zombie-specific behavior including movement, attack patterns, and interaction with plants.
 */
public class Zombie extends Entity {
    private double speed;
    private int damage;
    private Armor armor;
    private boolean isFrozen;
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

    public Zombie(String type) {
        this();
        armor = createArmor(type);
        damage += armor.getArmorDamage();
        speed += armor.getArmorSpeed();
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

    private void removeArmor() {
        damage -= armor.getArmorDamage();
        speed -= armor.getArmorSpeed();
    }

    public boolean hasArmor() {
        return armor != null;
    }

    public void freeze() {
        if (!isFrozen){
            speed *= 2;
            cooldown *= 2;
            isFrozen = true;
        }
        frozenTime = 5;
    }
}
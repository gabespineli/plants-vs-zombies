import java.util.ArrayList;

/**
 * Represents a projectile pea shot by plants in the game.
 * Handles movement of pea, collision detection, and damage dealing to zombies.
 */
 public class Pea {
    protected double columnPos;
    protected int rowPos;
    protected boolean isActive;
    protected int damage;
    protected double speed;

    /**
     * Constructs a new pea with specified position and damage based on the Peashooter.
     * The pea starts as active with a default speed of 1.
     * @param column the initial column position
     * @param row the initial row position
     * @param damage the damage this pea will deal on impact
     */
    public Pea(double column, int row, int damage){
        columnPos = column;
        rowPos = row;
        isActive = true;
        this.damage = damage;
        speed = 0.5;
    }

    /**
     * Gets the row position of the pea.
     * @return the row position
     */
    public int getRowPos() {
        return rowPos;
    }

    /**
     * Gets the column position of the pea.
     * @return the column position
     */
    public double getColumnPos() {
        return columnPos;
    }

    /**
     * Checks if the pea is currently active.
     * @return true if the pea is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Moves the pea forward depending on its speed value.
     * Increases the column position by the speed amount.
     */
    protected void move() {
        columnPos += 1/(speed / 0.03);
    }

    /**
     * Checks if a zombie and pea is in the same tile.
     * If a collision is detected, damages the zombie and turns isActive to false.
     * Prints a message depending on the life status of a zombie after taking damage.
     * @param aliveZombies the list of alive zombies to check for collisions
     */
    protected void checkCollision(ArrayList<Zombie> aliveZombies) {
        for (Zombie z : aliveZombies){
            if (z.getRowPos() == rowPos && z.getColumnPos()+0.2 >= columnPos && z.getColumnPos()-0.2 <= columnPos){
                z.takeDamage(damage);
                if (z.isAlive()){
                    System.out.println("Zombie at (" + z.getRowPos() + "," + z.getColumnPos() + ") has been shot! (HP: " + z.getHealth() + ")");
                } else {
                    System.out.println("Zombie at (" + z.getRowPos() + "," + z.getColumnPos() + ") has been killed!");
                }
                isActive = false;
                break;
            }
        }
    }

    /**
     * Updates the pea's state by checking for collisions and moving if active.
     * Checks for collision before and after movement to ensure if there is a zombie in the current and next tile.
     * @param aliveZombies the list of alive zombies to check for collisions
     */
    public void updatePea(ArrayList<Zombie> aliveZombies){
        checkCollision(aliveZombies);

        if (isActive) {
            move();
            checkCollision(aliveZombies);
        }
    }
}

/**
 * Parent class for all Entity objects such as plants and zombies
 * Helps manage attributes like health, position, and life state.
 */
public class Entity {
    protected int health;
    protected int rowPos;
    protected double columnPos;
    protected boolean isAlive;
    protected double actionCooldown;
    protected double cooldown;


    /**
     * Constructs and entity and sets a boolean isAlive to be true
     */
    public Entity() {
        isAlive = true;
    }

    /**
     * Gets the current health of the entity.
     * @return the health value
     */
    public int getHealth(){ return health; }

    /**
     * Gets the current row position of the entity.
     * @return the row position
     */
    public int getRowPos() { return rowPos; }

    /**
     * Sets the row position of the entity.
     * @param rowPos the new row position
     */
    public void setRowPos(int rowPos) { this.rowPos = rowPos; }

    public double getColumnPos() {
        return columnPos;
    }

    public void setColumnPos(double column) {
        this.columnPos = column;
    }

    /**
     * Used to check the life state of an entity or if it's alive
     * @return true if entity is alive, false if not.
     */
    public boolean isAlive() { return isAlive; }

    /**
     * Subtracts the damage to an entity's health and updates its life state depending on the health.
     * @param d the damage
     */
    public void takeDamage(int d){
        if (isAlive)
            health -= d;

        if (health <= 0) {
            isAlive = false;
        }
    }

    public void reduceActionCooldown(){
        if (actionCooldown > 0)
            actionCooldown -= 0.03;
    }
}

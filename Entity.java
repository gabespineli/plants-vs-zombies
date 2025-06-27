/**
 * Parent class for all Entity objects such as plants and zombies
 * Helps manage attributes like health, position, and life state.
 */
public class Entity {
    private int health;
    private int columnPos;
    private int rowPos;
    private boolean isAlive;


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
     * Sets the health of the entity.
     * @param health the new health value to set
     */
    public void setHealth(int health) { this.health = health; }

    /**
     * Gets the current column position of the entity.
     * @return the column position
     */
    public int getColumnPos() { return columnPos; }

    /**
     * Sets the column position of the entity.
     * @param columnPos the new column position
     */
    public void setColumnPos(int columnPos) { this.columnPos = columnPos; }

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
}

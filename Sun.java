/**
 * Represents a sun object in the game.
 * Sun is dropped in the map and used to buy plants.
 * Has a limited lifespan and can be collected by the player.
 */
 public class Sun {
    private double columnPos;
    private double rowPos;
    private int finalRowPos;
    private int value;
    private boolean isActive;
    private int lifespan;

    /**
     * Constructs a new Sun with default values.
     * Sets active state to true, lifespan to 10 ticks, and value to 50.
     */
    public Sun(){
        isActive = true;
        lifespan = 100;
        value = 50;
    }

    /**
     * Constructs a new Sun at the specified position with default value.
     * @param rowPos the row position where the sun appears
     * @param columnPos the column position where the sun appears
     */
    public Sun(int rowPos, int columnPos) {
        this();
        finalRowPos = rowPos;
        this.rowPos = 0;
        this.columnPos = columnPos;
    }

    /**
     * Constructs a new Sun at the specified position with custom value usually for sunflower.
     * @param rowPos the row position where the sun appears
     * @param columnPos the column position where the sun appears
     * @param value the sun value/worth
     */
    public Sun(int rowPos, double columnPos, int value) {
        this();
        this.rowPos = rowPos;
        this.columnPos = columnPos;
        this.value = value;
    }

    /**
     * Gets the column position of the sun.
     * @return the column position
     */
    public double getColumnPos(){ return columnPos; }

    /**
     * Gets the row position of the sun.
     * @return the row position
     */
    public double getRowPos(){ return rowPos; }

    /**
     * Gets the value/worth of the sun.
     * @return the sun value
     */
    public int getValue(){ return value; }

    /**
     * Checks if the sun is currently active and collectable.
     * @return true if the sun is active, false otherwise
     */
    public boolean isActive(){ return isActive; }

    /**
     * Marks the sun as collected and inactive.
     * Should be called when the player collects the sun.
     */
    public void collected() { isActive = false; }

    /**
     * Updates the sun's state by reducing its lifespan.
     * If lifespan reaches 0, the sun becomes inactive and disappears.
     */
    public void updateSun(){
        if (lifespan > 0){
            fall();
            lifespan--;
        }
        else {
            isActive = false;
        }
    }

    public void fall(){
        if (rowPos != finalRowPos){
            rowPos += 1/(4 / 0.1);
        }
    }
}

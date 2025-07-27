/**
 * Represents a plant entity in the game.
 * Extends Entity and provides plant-specific functionality including cost, cooldown management, and plant type identification.
 */
 public class Plant extends Entity {
    protected int cost;
    protected String plantType;
    private static int plantCount = 0;

    /**
     * Constructs a new Plant.
     * Calls the parent Entity constructor.
     */

    public Plant() {
        super();
    }

    /**
     * Gets the total count of plants created.
     * @return the total plant count
     */
    public static int getPlantCount() { return plantCount; }

    /**
     * Increments the total plant count.
     * Should be called when a new plant is successfully placed.
     */
    public static void incrementPlantCount() { plantCount++; }

    /**
     * Gets the cost required to place this plant.
     * @return the cost of the plant
     */
    public int getCost() { return cost; }

    /**
     * Gets the type of the plant. (Either peashooter or sunflower)
     * @return the plant type as a string
     */
    public String getPlantType() { return plantType; }
}
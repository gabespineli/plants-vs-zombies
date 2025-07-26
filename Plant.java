/**
 * Represents a plant entity in the game.
 * Extends Entity and provides plant-specific functionality including cost, cooldown management, and plant type identification.
 */
 public class Plant extends Entity {
    protected int cost;
    protected int placementCooldown;
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
     * Gets the placement cooldown for this plant type.
     * @return the placement cooldown in ticks
     */
    public int getPlacementCooldown() { return placementCooldown; }

    /**
     * Gets the type of the plant. (Either peashooter or sunflower)
     * @return the plant type as a string
     */
    public String getPlantType() { return plantType; }

    /**
     * Reduces the action cooldown by 1 if it's greater than 0.
     * Should be called each game tick to manage cooldowns.
     */
    public void reduceActionCooldown(){
        if (actionCooldown > 0)
            actionCooldown -= 1;
    }
}
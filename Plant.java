/**
 * Represents a plant entity in the game.
 * Extends Entity and provides plant-specific functionality including cost, cooldown management, and plant type identification.
 */
 public class Plant extends Entity {
    private int cost;
    private int placementCooldown;
    private int actionCooldown;
    private int Cooldown;
    private String plantType;
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
     * Sets the cost required to place this plant.
     * @param cost the cost of the plant
     */
    public void setCost(int cost) { this.cost = cost; }

    /**
     * Gets the placement cooldown for this plant type.
     * @return the placement cooldown in ticks
     */
    public int getPlacementCooldown() { return placementCooldown; }
    /**
     * Sets the placement cooldown for this plant type.
     * @param cd the placement cooldown in ticks
     */
    public void setPlacementCooldown(int cd) { this.placementCooldown = cd;}

    /**
     * Gets the type of the plant. (Either peashooter or sunflower)
     * @return the plant type as a string
     */
    public String getPlantType() { return plantType; }

    /**
     * Sets the type of the plant.
     * @param plantType the plant type as a string
     */
    public void setPlantType(String plantType) { this.plantType = plantType; }

    /**
     * Sets the action cooldown.
     * @param cd the action cooldown in ticks
     */
    public void setActionCooldown(int cd) { this.actionCooldown = cd; }

    /**
     * Gets the base cooldown value for this plant's actions.
     * @return the base cooldown in ticks
     */
    public int getCooldown() { return Cooldown; }

    /**
     * Sets the base cooldown value for this plant's actions.
     * @param Cooldown the base cooldown in ticks
     */
    public void setCooldown(int Cooldown) { this.Cooldown = Cooldown; }

    /**
     * Reduces the action cooldown by 1 if it's greater than 0.
     * Should be called each game tick to manage cooldowns.
     */
    public void reduceActionCooldown(){
        if (actionCooldown > 0)
            actionCooldown--;
    }

    /**
     * Checks if the plant can perform its action.
     * @return true if action cooldown is 0, false otherwise
     */
    public boolean checkActionCooldown(){
        return actionCooldown == 0;
    }

    /**
     * Resets the action cooldown to the specified value.
     * Typically called after performing an action.
     * @param cd the cooldown value to reset to
     */
    public void resetActionCooldown(int cd){
        actionCooldown = cd;
    }
}
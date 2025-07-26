/**
 * Represents a player in the Plants vs Zombies game. Manages sun points and plant purchasing operations.
 */
public class Player {
    private int sunPoints;

    /**
     * Constructs a new Player with 100 initial sun points.
     */
    public Player() {
        this.sunPoints = 999;
    }

    /**
     * Gets the current sun points of this player.
     * @return the current sun points
     */
    public int getSunPoints() {
        return sunPoints;
    }

    /**
     * Adds the specified amount to the player's sun points.
     * @param amount the amount of sun points to add
     */
    public void addSun(int amount) {
        if (amount > 0) {
            sunPoints += amount;
            System.out.println("Player collected " + amount + " sun. Total: " + sunPoints);
        }
    }

    /**
     * Attempts to purchase the specified plant for the player. Checks plant count limits, placement cooldowns, and sufficient sun points.
     * @param p the plant to purchase
     * @param currentTick the current game tick
     * @return true if the plant was successfully purchased, false otherwise
     */
    public boolean buyPlant(Plant p, int currentTick) {
        if (Plant.getPlantCount() >= 45) {
            System.out.println("You have reached the maximum placement of plants!");
            return false;
        }

        if (p instanceof Sunflower) {
            if (currentTick - Sunflower.getLastPlacedSunflower() < p.getPlacementCooldown()) {
                int remaining = p.getPlacementCooldown() - (currentTick - Sunflower.getLastPlacedSunflower());
                System.out.println("Sunflower still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof Peashooter) {
            if (currentTick - Peashooter.getLastPlacedPeashooter() < p.getPlacementCooldown()) {
                int remaining = p.getPlacementCooldown() - (currentTick - Peashooter.getLastPlacedPeashooter());
                System.out.println("Peashooter still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof CherryBomb) {
            if (currentTick - CherryBomb.getLastPlacedCherry() < p.getPlacementCooldown()) {
                int remaining = p.getPlacementCooldown() - (currentTick - CherryBomb.getLastPlacedCherry());
                System.out.println("Cherry still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        }

        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
            if (p instanceof Sunflower) { Sunflower.setLastPlacedSunflower(currentTick); }
            else if (p instanceof Peashooter) { Peashooter.setLastPlacedPeashooter(currentTick); }
            else if (p instanceof CherryBomb) { CherryBomb.setLastPlacedCherry(currentTick); }
            System.out.println("Bought " + p.getPlantType() + " for " + p.getCost() + " sun.");
            return true;
        } else {
            System.out.println("Not enough sun.");
            return false;
        }
    }
}

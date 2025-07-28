/**
 * Represents a player in the Plants vs Zombies game. Manages sun points and plant purchasing operations.
 */
public class Player {
    private int sunPoints;

    /**
     * Constructs a new Player with 100 initial sun points.
     */
    public Player() {
        this.sunPoints = 50;
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
            if (Sunflower.isReady() != 0) {
                double remaining = Sunflower.isReady();
                System.out.println("Sunflower still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof Peashooter) {
            if (Peashooter.isReady() != 0) {
                double remaining = Peashooter.isReady();
                System.out.println("Peashooter still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof CherryBomb) {
            if (CherryBomb.isReady() != 0) {
                double remaining = CherryBomb.isReady();
                System.out.println("Cherry still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof Snowpea) {
            if (Snowpea.isReady() != 0) {
                double remaining = Snowpea.isReady();
                System.out.println("Snowpea still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof Wallnut) {
            if (Wallnut.isReady() != 0) {
                double remaining = Wallnut.isReady();
                System.out.println("Wallnut still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof PotatoMine) {
            if (PotatoMine.isReady() != 0) {
                double remaining = PotatoMine.isReady();
                System.out.println("Potato Mine still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        }

        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
            if (p instanceof Sunflower) { Sunflower.setPlacementTimer(); }
            else if (p instanceof Peashooter) { Peashooter.setPlacementTimer(); }
            else if (p instanceof CherryBomb) { CherryBomb.setPlacementTimer(); }
            else if (p instanceof Snowpea) { Snowpea.setPlacementTimer(); }
            else if (p instanceof Wallnut) { Wallnut.setPlacementTimer(); }
            else if (p instanceof PotatoMine) { PotatoMine.setPlacementTimer(); }
            System.out.println("Bought " + p.getPlantType() + " for " + p.getCost() + " sun.");
            return true;
        } else {
            System.out.println("Not enough sun.");
            return false;
        }
    }
}

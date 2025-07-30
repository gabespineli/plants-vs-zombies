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
     * Resets the sunpoints of the player to 50.
     */
    public void resetSunPoints() {
        sunPoints = 50;
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
            if (Sunflower.checkPlacementCD() > 0) {
                double remaining = Sunflower.checkPlacementCD();
                System.out.println("Sunflower still in cooldown for placement. Wait for " + remaining + " seconds.");
                return false;
            }
        } else if (p instanceof Snowpea) {
            if (Snowpea.checkPlacementCD() > 0) {
                double remaining = Snowpea.checkPlacementCD();
                System.out.println("Snowpea still in cooldown for placement. Wait for " + remaining + " seconds.");
                return false;
            }
        } else if (p instanceof CherryBomb) {
            if (CherryBomb.checkPlacementCD() > 0) {
                double remaining = CherryBomb.checkPlacementCD();
                System.out.println("Cherry still in cooldown for placement. Wait for " + remaining + " seconds.");
                return false;
            }
        } else if (p instanceof Peashooter) {
            if (Peashooter.checkPlacementCD() > 0) {
                double remaining = Peashooter.checkPlacementCD();
                System.out.println("Peashooter still in cooldown for placement. Wait for " + remaining + " seconds.");
                return false;
            }
        } else if (p instanceof Wallnut) {
            if (Wallnut.checkPlacementCD() > 0) {
                double remaining = Wallnut.checkPlacementCD();
                System.out.println("Wallnut still in cooldown for placement. Wait for " + remaining + " seconds.");
                return false;
            }
        } else if (p instanceof PotatoMine) {
            if (PotatoMine.checkPlacementCD() > 0) {
                double remaining = PotatoMine.checkPlacementCD();
                System.out.println("Potato Mine still in cooldown for placement. Wait for " + remaining + " seconds.");
                return false;
            }
        }

        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
            if (p instanceof Sunflower) { Sunflower.setPlacementTimer(); }
            else if (p instanceof Snowpea) { Snowpea.setPlacementTimer(); }
            else if (p instanceof Peashooter) { Peashooter.setPlacementTimer(); }
            else if (p instanceof CherryBomb) { CherryBomb.setPlacementTimer(); }
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

package Program;

public class Player {
    private int sunPoints;

    public Player() {
        this.sunPoints = 100;
    }

    public void addSun(int amount) {
        sunPoints += amount;
        System.out.println("Player collected " + amount + " sun. Total: " + sunPoints);
    }

    //method to display available plants you can buy
    public void displayShop() {

    }

    public boolean buyPlant(Plant p, int currentTick) {
        if (Plant.getPlantCount() >= 45) {
            System.out.println("You have reached the maximum placement of plants!");
            return false;
        }

        if (currentTick - p.getLastPlacedTick() < p.getPlantCD()) {
            int remaining = p.getPlantCD() - (currentTick - p.getLastPlacedTick());
            System.out.println("Plant still in cooldown for placement. Wait for " + remaining + " turns.");
            return false;
        }

        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
            p.setLastPlacedTick(currentTick);
            System.out.println("Bought " + p.getPlantType() + " for " + p.getCost() + " sun.");
            return true;
        } else {
            System.out.println("Not enough sun.");
            return false;
        }
    }

    public int getSunPoints() {
        return sunPoints;
    }
}

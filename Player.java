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
        // check if max plantCount
        // in progress: plant in CD
        if (currentTick - p.lastPlacedTick < p.plantCD) {
            System.out.println("Plant still in cooldown for placement.");
            return false;
        }

        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
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

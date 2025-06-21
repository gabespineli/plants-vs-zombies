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

    public boolean buyPlant(Plant p, int currentTick) {
        if (Plant.getPlantCount() >= 45) {
            System.out.println("You have reached the maximum placement of plants!");
            return false;
        }

        if (p instanceof Sunflower) {
            if (currentTick - Sunflower.getLastPlacedSunflower() < p.getPlantCD()) {
                int remaining = p.getPlantCD() - (currentTick - Sunflower.getLastPlacedSunflower());
                System.out.println("Sunflower still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        } else if (p instanceof Peashooter) {
            if (currentTick - Peashooter.getLastPlacedPeashooter() < p.getPlantCD()) {
                int remaining = p.getPlantCD() - (currentTick - Peashooter.getLastPlacedPeashooter());
                System.out.println("Peashooter still in cooldown for placement. Wait for " + remaining + " turns.");
                return false;
            }
        }

        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
            if (p instanceof Sunflower) { Sunflower.setLastPlacedSunflower(currentTick); }
            else if (p instanceof Peashooter) { Peashooter.setLastPlacedPeashooter(currentTick); }
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

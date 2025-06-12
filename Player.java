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

    public boolean buyPlant(Plant p) {
        // check if max plantCount & plant in CD
        if (sunPoints >= p.getCost()) {
            sunPoints -= p.getCost();
            System.out.println("Bought " + p.getPlantType() + " for " + p.getCost() + " sun.");
            return true;
        } else {
            return false;
        }
    }

    public int getSunPoints() {
        return sunPoints;
    }
}

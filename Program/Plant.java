public class Plant extends Entity {
    private int cost;
    private int plantCD;
    private String plantType;
    private static int lastPlacedTick = -9999;
    private static int plantCount = 0;

    public Plant() {
        super();
        plantCount++;
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public static int getPlantCount() { return plantCount; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public int getPlantCD() { return plantCD; }
    public void setPlantCD(int plantCD) { this.plantCD = plantCD;}

    public static int getLastPlacedTick() { return lastPlacedTick; }
    public static void setLastPlacedTick(int tick) { lastPlacedTick = tick;}

    public String getPlantType() { return plantType; }
    public void setPlantType(String plantType) { this.plantType = plantType; }
}
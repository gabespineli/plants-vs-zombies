package Program;

public class Plant extends Entity {
    private int cost;
    private int plantCD;
    private String plantType;
    private static int plantCount = 0;

    public Plant() {
        super();
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public static int getPlantCount() { return plantCount; }
    public static void incrementPlantCount() { plantCount++; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public int getPlantCD() { return plantCD; }
    public void setPlantCD(int plantCD) { this.plantCD = plantCD;}

    public String getPlantType() { return plantType; }
    public void setPlantType(String plantType) { this.plantType = plantType; }
}
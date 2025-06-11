public class Plant extends Entity {
    private int cost;
    private int plantCd;
    private String plantType;
    private static int plantCount = 0;

    public Plant() {
        super();
        plantCount++;
    }

    public static int getPlantCount() { return plantCount; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public int getPlantCd() { return plantCd; }
    public void setPlantCd(int plantCd) { this.plantCd = plantCd;}

    public String getPlantType() { return plantType; }
    public void setPlantType(String plantType) { this.plantType = plantType; }
}
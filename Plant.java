public class Plant extends Entity {
    private int cost;
    private double plantCd;
    private static int plantCount = 0;

    public Plant() {
        super();
        plantCount++;
    }

    public static int getPlantCount() { return plantCount; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public double getPlantCd() { return plantCd; }
    public void setPlantCd(double plantCd) { this.plantCd = plantCd;}
}
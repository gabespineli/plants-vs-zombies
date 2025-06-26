public class Plant extends Entity {
    private int cost;
    private int placementCooldown;
    private int actionCooldown;
    private int Cooldown;
    private String plantType;
    private static int plantCount = 0;

    public Plant() {
        super();
    }

    public static int getPlantCount() { return plantCount; }
    public static void incrementPlantCount() { plantCount++; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public int getPlacementCooldown() { return placementCooldown; }
    public void setPlacementCooldown(int cd) { this.placementCooldown = cd;}

    public String getPlantType() { return plantType; }
    public void setPlantType(String plantType) { this.plantType = plantType; }

    public void setActionCooldown(int cd) { this.actionCooldown = cd; }

    public int getCooldown() { return Cooldown; }
    public void setCooldown(int Cooldown) { this.Cooldown = Cooldown; }

    public void reduceActionCooldown(){
        if (actionCooldown > 0)
            actionCooldown--;
    }

    public boolean checkActionCooldown(){
        return actionCooldown == 0;
    }

    public void resetActionCooldown(int cd){
        actionCooldown = cd;
    }
}
package Program;

public class Sunflower extends Plant {
    private int lastProducedTick;
    private static int lastPlacedSunflower = -9999;

    public Sunflower() {
        super();
        setHealth(200);
        setCost(50);
        setPlacementCooldown(5);
        setActionCooldown(4);
        setCooldown(4);
        setPlantType("Sunflower");
    }

    public static int getLastPlacedSunflower() { return lastPlacedSunflower; }
    public static void setLastPlacedSunflower(int tick) { lastPlacedSunflower = tick; }

    public Sun updateSunflower(){
        reduceActionCooldown();
        Sun sun;
        if (checkActionCooldown()){
            System.out.println("Sunflower at (" + getRowPos() + "," + getColumnPos() + ") produced suns!");
            sun = new Sun(getRowPos(), getColumnPos());
            resetActionCooldown(getCooldown());
            return sun;
        }
        return null;
    }
}
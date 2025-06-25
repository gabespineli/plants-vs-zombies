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
        if (checkActionCooldown()) {
            sun = new Sun(getRowPos(), getColumnPos(), 25);
            resetActionCooldown(getCooldown());
            System.out.println("Sunflower at (" + getRowPos() + "," + getColumnPos() + ") produced " + sun.getValue() + " sun!");
            return sun;
        }
        return null;
    }
}
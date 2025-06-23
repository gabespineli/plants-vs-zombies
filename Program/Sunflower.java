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

    public void update(Player player){
        reduceActionCooldown();

        if (checkActionCooldown()){
            System.out.println("Sunflower at (" + getRowPos() + "," + getColumnPos() + ") produced 25 sun!");
            player.addSun(25);
            resetActionCooldown(getCooldown());
        }
    }
}
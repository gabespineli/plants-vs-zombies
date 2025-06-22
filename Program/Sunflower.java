package Program;

public class                                                                    Sunflower extends Plant {
    private int lastProducedTick;
    private static int lastPlacedSunflower = -9999;

    public Sunflower() {
        super();
        setHealth(200);
        setCost(50);
        setPlantCD(5);
        setPlantType("Sunflower");
    }

    public static int getLastPlacedSunflower() { return lastPlacedSunflower; }
    public static void setLastPlacedSunflower(int tick) { lastPlacedSunflower = tick; }

    public void produce(int currentTick, Player player) {
        int productionInterval = 4;
        if (currentTick - lastProducedTick >= productionInterval) {
            lastProducedTick = currentTick;
            System.out.println("Sunflower at (" + getRowPos() + "," + getColumnPos() + ") produced 25 sun!");
            player.addSun(25);
        }
    }

    public void update(Player player){
        reduceCD();

        if (checkCD()){
            System.out.println("Sunflower at (" + getRowPos() + "," + getColumnPos() + ") produced 25 sun!");
            player.addSun(25);
        }
    }
}
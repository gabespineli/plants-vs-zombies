public class Sunflower extends Plant {
    private int lastProducedTick;
    private final int productionInterval = 4;

    public Sunflower() {
        super();
        setHealth(200);
        setCost(50);
        setPlantCD(5);
        setPlantType("Sunflower");
    }

    public void produce(int currentTick, Player player) {
        if (currentTick - lastProducedTick >= productionInterval) {
            lastProducedTick = currentTick;
            System.out.println("Sunflower at (" + getRowPos() + "," + getColumnPos() + ") produced 25 sun!");
            player.addSun(25);
        }
    }
}
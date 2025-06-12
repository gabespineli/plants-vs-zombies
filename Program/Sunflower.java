public class Sunflower extends Plant {
    private int lastProducedTick;
    private final int productionInterval = 4;

    public Sunflower() {
        super();
        setHealth(300);
        setCost(50);
        setPlantCd(7);
        setLastPlacedTick(7);
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
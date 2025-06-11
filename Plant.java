public class Plant extends Entity {
    private int cost;
    private int plantCd;
    private static int plantCount = 0;

    public Plant(int c, int h, int x, int y) {
        super(h, x, y);
        cost = c;
    }

    public int getCost(){
        return cost;
    }
}
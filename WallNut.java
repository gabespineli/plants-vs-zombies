public class WallNut extends Plant{
    private static int lastPlacedWallnut = -9999;
    public WallNut(){
        super();
        health = 2666;
        cost = 50;
        placementCooldown = 30;
        plantType = "Wall-nut";
    }

    public static int getLastPlacedWallnut() { return lastPlacedWallnut; }

    public static void setLastPlacedWallnut(int tick) { lastPlacedWallnut = tick; }
}

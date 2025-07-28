import java.util.ArrayList;

public class Wallnut extends Plant {
    private static final int placementCooldown = 250;
    private static int placementTimer;

    public Wallnut(){
        super();
        health = 2666;
        cost = 50;
        plantType = "Wall-nut";
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer--;
        }
    }

    public static int isReady() {
        return placementTimer;
    }

    public static void setPlacementCooldown() {
        placementTimer = placementCooldown;
    }
}

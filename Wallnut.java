import java.util.ArrayList;

public class Wallnut extends Plant {
    private static final double PLACEMENT_COOLDOWN = 30;
    private static double placementTimer;

    public Wallnut(){
        super();
        health = 2666;
        cost = 50;
        plantType = "Wallnut";
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer -= 0.03;
        }
    }

    public static double checkPlacementCD() {
        return placementTimer;
    }

    public static void setPlacementTimer() {
        placementTimer = PLACEMENT_COOLDOWN;
    }
}

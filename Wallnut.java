import java.util.ArrayList;

public class Wallnut extends Plant {
    private static final double PLACEMENT_COOLDOWN = 250;
    private static double placementTimer;

    public Wallnut(){
        super();
        health = 2666;
        cost = 50;
        plantType = "Wallnut";
    }

    public static void reduceCD() {
        if (placementTimer > 0){
            placementTimer--;
        }
    }

    public static double checkPlacementCD() {
        return placementTimer;
    }

    public static void setPlacementTimer() {
        placementTimer = PLACEMENT_COOLDOWN;
    }
}

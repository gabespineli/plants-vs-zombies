public class Sun {
    private static final int SUNPRODUCE = 25;
    private int sunRate;
    private static int sunCount;

    public Sun(int sR){
        sunRate = sR;
    }

    public static void collect(){
        sunCount += SUNPRODUCE;
    }

    public static int getCount(){
        return sunCount;
    }

    public static boolean buyPlant(Plant p){
        if (sunCount > p.getCost()){
            sunCount -= p.getCost();
            return true;
        } else { return false; }
    }
}

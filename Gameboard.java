import java.util.ArrayList;

public class Gameboard {
    private final int ROWS = 5;
    private final int COLUMNS = 9;
    private Plant[][] plantBoard;
    private boolean isOccupied;
    private ArrayList<Zombie> aliveZombies;
    private ArrayList<Plant> alivePlants;

    public Gameboard(){
        plantBoard = new Plant[ROWS][COLUMNS];
        aliveZombies = new ArrayList<>();
        alivePlants = new ArrayList<>();
    }

    public void addZombie() {



        aliveZombies.add(zombie);
        System.out.println("Zombie added to the board.");
    }

    public void addPlant(String name, int row, int column) {
        Sun.buyPlant()


        alivePlants.add(plant);
        System.out.println("Plant added to the board.");
    }
}

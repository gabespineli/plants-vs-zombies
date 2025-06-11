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



        //aliveZombies.add(zombie);
        System.out.println("Zombie added to the board.");
    }

    public void addPlant(String name, int row, int column) {
        Plant tempPlant = switch(name.toLowerCase()) {
            case "sunflower" -> new Sunflower();
            case "peashooter" -> new Peashooter();
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };

        if (Sun.buyPlant(tempPlant)) {
            tempPlant.setRowPos(row);
            tempPlant.setColumnPos(column);
            alivePlants.add(tempPlant);
            plantBoard[row][column] = tempPlant;
            System.out.println("Plant added to the board.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }
}

import java.util.ArrayList;
import java.util.Random;

public class Gameboard {
    private final int ROWS = 5;
    private final int COLUMNS = 9;
    private Plant[][] plantBoard;
    private ArrayList<Zombie> aliveZombies;
    private ArrayList<Plant> alivePlants;

    public Gameboard(){
        plantBoard = new Plant[ROWS][COLUMNS];
        aliveZombies = new ArrayList<>();
        alivePlants = new ArrayList<>();
    }

    public void addZombie() {
        Random random = new Random();
        Zombie tempZomb = new Zombie();

        tempZomb.setRowPos(random.nextInt(5));
        tempZomb.setColumnPos(8);
        aliveZombies.add(tempZomb);
        //aliveZombies.add(zombie);
        System.out.println("Zombie added to the board at row " + tempZomb.getRowPos());
    }

    public void addPlant(String name, int row, int column) {
        Plant tempPlant = switch(name.toLowerCase()) {
            case "sunflower" -> new Sunflower();
            case "peashooter" -> new Peashooter();
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };

        if (Sun.buyPlant(tempPlant)) {
            row--;
            column--;
            if (isValidPos(row, column)){
                tempPlant.setRowPos(row);
                tempPlant.setColumnPos(column);
                alivePlants.add(tempPlant);
                plantBoard[row][column] = tempPlant;
                System.out.println("Plant added to the board.");
            }
            else {
                System.out.println("Position taken.");
            }

        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public boolean isValidPos(int row, int column){
        if (row >= 0 && row < 5 && column >= 0 && column < 9){
            if (plantBoard[row][column] == null){
                return true;
            }
        }
        return false;
    }
}

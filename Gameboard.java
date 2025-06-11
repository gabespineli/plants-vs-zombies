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

    public boolean addPlant(String name, int row, int column, Player player) {
        if (!isValidPosition(row, column)) {
            System.out.println("Invalid position.");
            return false;
        }

        Plant p = switch(name.toLowerCase()) {
            case "sunflower" -> new Sunflower();
            case "peashooter" -> new Peashooter();
            default -> throw new IllegalArgumentException("Invalid plant.");
        };

        if (!player.buyPlant(p)) {
            System.out.println("Not enough sun.");
            return false;
        }

        p.setRowPos(row);
        p.setColumnPos(column);
        alivePlants.add(p);
        plantBoard[row][column] = p;
        System.out.println(name + " planted at inputted position.");
        return true;
    }

    public void updateSunflowers(int currentTick, Player player) { //eventually updatePlants
        for (Plant p : alivePlants) {
            if (p instanceof Sunflower sf) { sf.update(currentTick, player); }
            // else if (p instanceof Peashooter ps) { ps.update(currentTick, player); }
        }
    }

    public boolean isValidPosition(int row, int column){
        if (row >= 0 && row < 5 && column >= 0 && column < 9){
            if (plantBoard[row][column] == null){
                return true;
            }
        }
        return false;
    }

    public String plantAt(int row, int column) {
        if (row >= 0 && row < 5 && column >= 0 && column < 9){
            Plant p = plantBoard[row][column];
            if (p != null){
                if (p instanceof Peashooter) { return "Peashooter"; }
                else if (p instanceof Sunflower) { return "Sunflower"; }
            }
        }
        return "No plant at specified entry.";
    }

    public void addZombie() {
        Random random = new Random();
        Zombie tempZomb = new Zombie();

        tempZomb.setRowPos(random.nextInt(5));
        tempZomb.setColumnPos(8);
        aliveZombies.add(tempZomb);
        System.out.println("Zombie added to the board at Row " + tempZomb.getRowPos());
    }

    public Plant[][] getPlantBoard() { return plantBoard; };
    public ArrayList<Zombie> getAliveZombies() { return aliveZombies; }
    public ArrayList<Plant> getAlivePlants(){ return alivePlants;}
}

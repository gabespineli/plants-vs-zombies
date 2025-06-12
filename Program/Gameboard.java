import java.util.ArrayList;
import java.util.Random;

public class Gameboard {
    private final int ROWS = 5;
    private final int COLUMNS = 9;
    private Plant[][] plantBoard;
    private ArrayList<Zombie> aliveZombies;
    private int lastZombieGeneratedTick = 0;
    private ArrayList<Plant> alivePlants;

    public Gameboard(){
        plantBoard = new Plant[ROWS][COLUMNS];
        aliveZombies = new ArrayList<>();
        alivePlants = new ArrayList<>();
    }

    public void displayBoard() {
        System.out.println("\nCurrent Board:");

        // Print column headers (0 to 8)
        System.out.print("    ");
        for (int col = 0; col <= 8; col++) {
            System.out.print(" " + col + "  ");
        }
        System.out.println();

        // Top border
        System.out.print("   +");
        for (int col = 0; col < 9; col++) {
            System.out.print("---+");
        }
        System.out.println();

        // Rows
        for (int row = 0; row < 5; row++) {
            System.out.print(row + "  |");

            for (int col = 0; col < 9; col++) {
                // Check plant
                String cell = "   ";
                Plant plant = plantBoard[row][col];
                boolean hasZombie = false;

                // Check if any zombie is in this cell
                for (Zombie z : aliveZombies) {
                    if (z.getRowPos() == row && z.getColumnPos() == col) {
                        hasZombie = true;
                        break;
                    }
                }

                if (plant instanceof Sunflower) {
                    cell = hasZombie ? "SZ " : " S ";
                } else if (plant instanceof Peashooter) {
                    cell = hasZombie ? "PZ " : " P ";
                } else {
                    cell = hasZombie ? " Z " : "   ";
                }

                System.out.print(cell + "|");
            }
            System.out.println();

            // Separator
            System.out.print("   +");
            for (int col = 0; col < 9; col++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }



    public void addPlant(String name, int row, int column, Player player, int currentTick) {
        if (!isValidPosition(row, column)) {
            System.out.println("Invalid position.");
            return;
        }

        Plant p = switch(name.toLowerCase()) {
            case "sunflower" -> new Sunflower();
            case "peashooter" -> new Peashooter();
            default -> throw new IllegalArgumentException("Invalid plant.");
        };

        if (!player.buyPlant(p, currentTick)) {
            return;
        }

        p.setRowPos(row);
        p.setColumnPos(column);
        alivePlants.add(p);
        plantBoard[row][column] = p;
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " planted at inputted position.");
    }

    public void updateGame(int currentTick, Player player) {
        for (Plant p : alivePlants) {
            if (p instanceof Sunflower sf) { sf.produce(currentTick, player); }
            // else if (p instanceof Peashooter ps) { ps.update(currentTick, player); }
        }
        for (Zombie z : aliveZombies) {
            z.move(currentTick);
        }
        generateZombie(currentTick);
    }

    public boolean isValidPosition(int row, int column){
        if (row >= 0 && row < 5 && column >= 0 && column < 9){
            return plantBoard[row][column] == null;
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

    public void generateZombie(int currentTick) {
        Random random = new Random();
        int generationInterval = 0;

        if (currentTick <= 29) { return; }

        if (currentTick <= 80) {
            generationInterval = 10;
        } else if (currentTick <= 140) {
            generationInterval = 5;
        } else if (currentTick <= 170) {
            generationInterval = 3;
        } else if (currentTick <= 180) {
            generationInterval = random.nextInt(2) + 1; // generate from 1-2 ticks
        }

        if (currentTick - lastZombieGeneratedTick >= generationInterval) {
            lastZombieGeneratedTick = currentTick;
            Zombie z = new Zombie();
            z.setRowPos(random.nextInt(5));
            z.setColumnPos(8);
            aliveZombies.add(z);
            System.out.println("A zombie appeared at (" + z.getRowPos() + "," + z.getColumnPos() + ").");
        }
    }

    public Plant[][] getPlantBoard() { return plantBoard; };
    public ArrayList<Zombie> getAliveZombies() { return aliveZombies; }
    public ArrayList<Plant> getAlivePlants(){ return alivePlants;}
}

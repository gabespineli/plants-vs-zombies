import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the game board for the Plants vs Zombies game. Manages plants, zombies, projectiles (peas), and sun objects on the game field.
 */
public class Gameboard {
    private Plant[][] plantBoard;
    private ArrayList<Zombie> aliveZombies;
    private ArrayList<Plant> alivePlants;
    private ArrayList<Pea> activePeas;
    private ArrayList<Sun> activeSuns;
    private int lastZombieGeneratedTick;

    /**
     * Constructs a new Gameboard with empty 5x9 grid and initialized collections.
     */
    public Gameboard(){
        plantBoard = new Plant[5][9];
        aliveZombies = new ArrayList<>();
        alivePlants = new ArrayList<>();
        activePeas = new ArrayList<>();
        activeSuns = new ArrayList<>();
        lastZombieGeneratedTick = 0;
    }

    /**
     * Returns the plant board grid.
     * @return the 5x9 plant board array
     */
    public Plant[][] getPlantBoard() { return plantBoard; }

    /**
     * Returns the list of alive zombies.
     * @return the list of alive zombies
     */
    public ArrayList<Zombie> getAliveZombies() { return aliveZombies; }

    /**
     * Returns the list of active pea projectiles.
     * @return the list of active peas
     */
    public ArrayList<Pea> getActivePeas() { return activePeas; }

    /**
     * Returns the list of active sun objects.
     * @return the list of active suns
     */
    public ArrayList<Sun> getActiveSuns() { return activeSuns; }

    /**
     * Updates the game state for one tick. Processes plant actions, zombie movement, projectile updates, and object cleanup.
     * @param currentTick the current game tick
     */
    public void updateGame(int currentTick) {
        // ACTION PHASE
        for (Plant plant : alivePlants) {
            if (plant instanceof Sunflower sf) {
                Sun newSun = sf.updateSunflower();
                if (newSun != null) {
                    activeSuns.add(newSun);
                }
            }
            else if (plant instanceof Peashooter ps) {
                Pea newPea = ps.updatePeashooter(aliveZombies);
                if (newPea != null) {
                    activePeas.add(newPea);
                }
            }

            else if (plant instanceof CherryBomb cb) {
                cb.updateCheery(aliveZombies);
            }
        }
        for (Zombie zombie : aliveZombies) { zombie.updateZombie(alivePlants); }
        for (Pea pea : activePeas) { pea.updatePea(aliveZombies); }
        generateSun(currentTick);

        for (Sun sun : activeSuns) { sun.updateSun(); }

        // REMOVAL PHASE
        for (int i = alivePlants.size() - 1; i >= 0; i--) {
            Plant plant = alivePlants.get(i);
            if (!plant.isAlive()) {
                // Clear from the board grid
                plantBoard[plant.getRowPos()][(int) plant.getColumnPos()] = null;
                alivePlants.remove(i);
            }
        }
        activePeas.removeIf(pea -> pea.getColumnPos() > 8 || !pea.isActive());
        activeSuns.removeIf(sun -> !sun.isActive());


        // ZOMBIE GENERATION
        if (currentTick <= 180) {
            generateZombie(currentTick);
        }
    }

    /**
     * Adds a plant to the game board at the specified position.
     * @param name the name of the plant type to add
     * @param row the row position (0-4)
     * @param column the column position (0-8)
     * @param player the player attempting to place the plant
     * @param currentTick the current game tick
     */
    public boolean addPlant(String name, int row, int column, Player player, int currentTick) {
        if (!isValidPosition(row, column)) {
            System.out.println("Invalid placement position.");
            return false;
        }

        if (isOccupied(row, column)) {
            System.out.println("Placement is occupied.");
            return false;
        }

        Plant p = switch(name.toLowerCase()) {
            case "sunflower" -> new Sunflower();
            case "peashooter" -> new Peashooter();
            case "cherrybomb" -> new CherryBomb();
            default -> null;

        };

        if (p == null) {
            System.out.println("Invalid plant name.");
            return false;
        }

        if (!player.buyPlant(p, currentTick)) {
            return false;
        }

        p.setRowPos(row);
        p.setColumnPos(column+0.5);
        alivePlants.add(p);
        plantBoard[row][column] = p;
        Plant.incrementPlantCount();
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " planted at " + row + "," + column + " position.");
        return true;
    }

    public void removePlant(int row, int col) {
        for (int i = alivePlants.size() - 1; i >= 0; i--) {
            Plant plant = alivePlants.get(i);
            if (plant == plantBoard[row][col]){
                alivePlants.remove(i);
                plantBoard[row][col] = null;
            }
        }
    }

    /**
     * Checks if the specified position is within the valid game board bounds.
     * @param row the row position to check
     * @param column the column position to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int row, int column){
        return (row >= 0 && row < 5 && column >= 0 && column < 9);
    }

    /**
     * Generates sun objects from the sky at regular intervals.
     * @param currentTick the current game tick
     */
    public void generateSun(int currentTick) {
        Random random = new Random();
        if (currentTick % 10 == 0) {
            Sun sun = new Sun(random.nextInt(5), random.nextInt(9));
            activeSuns.add(sun);
            System.out.println("The sky dropped a sun at (" + sun.getRowPos() + ", " + sun.getColumnPos() + ")");
        }
    }

    public boolean isOccupied(int row, int column) { return plantBoard[row][column] != null; }

    /**
     * Collects all sun objects at the specified position and adds their value to the player.
     * @param row the row position to collect from
     * @param column the column position to collect from
     * @param player the player collecting the sun
     */
    public void collectSun(int row, int column, Player player) {
        if (!isValidPosition(row, column)) {
            System.out.println("Invalid collection position.");
            return;
        }

        ArrayList<Sun> sunsToCollect = new ArrayList<>();
        for (Sun sun: activeSuns) {
            if (sun.getRowPos() == row && sun.getColumnPos() == column) {
                sunsToCollect.add(sun);
            }
        }

        if (!sunsToCollect.isEmpty()) {
            int totalSunCollected = 0;
            for (Sun sun : sunsToCollect) {
                sun.collected();
                totalSunCollected += sun.getValue();
            }
            activeSuns.removeIf(s -> !s.isActive());
            System.out.println("Sun collected at (" + row + "," + column + ")!");
            player.addSun(totalSunCollected);
        }
    }

    /**
     * Generates zombies at increasing frequency based on the current game tick.
     * @param currentTick the current game tick
     */
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
}
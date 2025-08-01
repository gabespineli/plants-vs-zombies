import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the game board for the Plants vs Zombies game. Manages plants, zombies, projectiles (peas), and sun objects on the game field.
 */
public class Gameboard {
    private Plant[][] plantBoard;
    private ArrayList<String> availablePlantTypes;
    private ArrayList<Zombie> aliveZombies;
    private ArrayList<Plant> alivePlants;
    private ArrayList<Pea> activePeas;
    private ArrayList<Sun> activeSuns;
    private int lastZombieGeneratedTick;
    private int currentLevel;

    /**
     * Constructs a new Gameboard with empty 5x9 grid and initialized collections.
     */
    public Gameboard(int currentLevel){
        this.currentLevel = currentLevel;
        plantBoard = new Plant[5][9];
        setAvailablePlantTypes();
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
     * Returns the list of alive plants.
     * @return the list of alive plants
     */
    public ArrayList<Plant> getAlivePlants() { return alivePlants; }

    /**
     * Returns Level of the game.
     * @return the Level of the game
     */
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Returns the list of plant types.
     * @return an array list of string that contains plant types.
     */
    public ArrayList<String> getAvailablePlantTypes() { return availablePlantTypes; }

    /**
     * Sets the available plant types depending on the level of the game.
     */
    private void setAvailablePlantTypes() {
        availablePlantTypes = new ArrayList<>();
        if (currentLevel >= 1) {
            availablePlantTypes.add("sunflower");
            availablePlantTypes.add("peashooter");
            availablePlantTypes.add("cherrybomb");
        }
        if (currentLevel >= 2) {
            availablePlantTypes.add("wallnut");
        }
        if (currentLevel >= 3) {
            availablePlantTypes.add("snowpea");
            availablePlantTypes.add("potatomine");
        }
        System.out.println("Available plant types for level " + currentLevel + ": " + availablePlantTypes);
    }

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
                if (ps instanceof Snowpea sp) {
                    Frost frost = sp.updateSnowpea(aliveZombies);
                    if (frost != null) {
                        activePeas.add(frost);
                    }
                } else {
                    Pea newPea = ps.updatePeashooter(aliveZombies);
                    if (newPea != null) {
                        activePeas.add(newPea);
                    }
                }
            }
            else if (plant instanceof ExplosivePlant ep) {
                ep.updateExplosive(aliveZombies);
            }
        }
        for (Zombie zombie : aliveZombies) { zombie.updateZombie(alivePlants); }
        for (Pea pea : activePeas) { pea.updatePea(aliveZombies); }
        generateSun(currentTick);

        for (Sun sun : activeSuns) { sun.updateSun(); }

        // REMOVAL PHASE
        for (int i = alivePlants.size() - 1; i >= 0; i--) {
            Plant plant = alivePlants.get(i);
            if (plant instanceof ExplosivePlant ep) {
                if (ep instanceof PotatoMine pm) {
                    // Remove PotatoMine immediately if not detonated (eaten before explosion)
                    if (!pm.isAlive() && !pm.isDetonated()) {
                        plantBoard[plant.getRowPos()][(int) plant.getColumnPos()] = null;
                        alivePlants.remove(i);
                        continue;
                    }
                    if (!pm.isAlive() && pm.exploded && pm.explosionFinished()) {
                        plantBoard[plant.getRowPos()][(int) plant.getColumnPos()] = null;
                        alivePlants.remove(i);
                        continue;
                    }
                } 
                else if (ep instanceof CherryBomb cb) {
                    if (!cb.isAlive() && cb.exploded && cb.explosionFinished()) {
                        plantBoard[plant.getRowPos()][(int) plant.getColumnPos()] = null;
                        alivePlants.remove(i);
                        continue;
                    }
                }
            } else {
                if (!plant.isAlive()) {
                    plantBoard[plant.getRowPos()][(int) plant.getColumnPos()] = null;
                    alivePlants.remove(i);
                }
            }
        }

        activePeas.removeIf(pea -> pea.getColumnPos() > 8 || !pea.isActive());
        activeSuns.removeIf(sun -> !sun.isActive());
        aliveZombies.removeIf(zombie-> !zombie.isAlive());


        // ZOMBIE GENERATION
        generateZombie(currentTick);

        // REDUCE PLANT COOLDOWN
        reduceCDs();
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
            case "snowpea" -> new Snowpea();
            case "wallnut" -> new Wallnut();
            case "potatomine" -> new PotatoMine();
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

    /**
     * Removes a plant from the board and from the list.
     */
    public void removePlant(int row, int col) {
        for (int i = alivePlants.size() - 1; i >= 0; i--) {
            Plant plant = alivePlants.get(i);
            if (plant == plantBoard[row][col]){
                alivePlants.remove(i);
                plantBoard[row][col] = null;
                System.out.println(plant.getPlantType() + " removed at (" + row + "," + col + ")");
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
        if (currentTick % 267 == 0) {
            Sun sun = new Sun(random.nextInt(5), random.nextInt(9));
            activeSuns.add(sun);
            System.out.println("The sky dropped a sun at (" + sun.getRowPos() + ", " + sun.getColumnPos() + ")");
        }
    }

    /**
     * Checks if there is a plant occupying a location in the grid.
     * @return true or false whether it is occupied or not.
     */
    public boolean isOccupied(int row, int column) { return plantBoard[row][column] != null; }

    /**
     * Collects all sun objects at the specified position and adds their value to the player.
     * @param player the player collecting the sun
     */
    public void collectSun(Sun sun, Player player) {
        player.addSun(sun.getValue());
        sun.collected();
    }

    /**
     * Generates zombies at increasing frequency based on the current game tick.
     * @param currentTick the current game tick
     */
    public void generateZombie(int currentTick) {
        Random random = new Random();
        int nArmorRNG = -1;
        int generationInterval = 0;

        if (currentTick <= 999) { return; }

        if (currentTick <= 2667) {
            generationInterval = 343 - currentLevel *10;
        } else if (currentTick <= 3000) {
            generationInterval = 167 - currentLevel * 10;
        } else if (currentTick <= 3333) {
            if (currentLevel >= 3) {
                generationInterval = 50;
            }
            else {
                generationInterval = 167 - currentLevel * 10;
            }
        } else if (currentTick <= 4667) {
            generationInterval = 167 - currentLevel * 10;
        } else if (currentTick <= 5667) {
            generationInterval = 100 - currentLevel * 10;
        } else if (currentTick <= 6000) {
            generationInterval = random.nextInt(100-66) + 67 - currentLevel * 10;
        } else if(currentTick > 6000) {
            return;
        }

        if (currentTick - lastZombieGeneratedTick >= generationInterval) {
            Zombie z;
            if (currentTick >= 3667){
                nArmorRNG = random.nextInt(100);
            }

            if (nArmorRNG >= 0 && nArmorRNG <= 19 + currentLevel){
                z = new Zombie("cone");
            } else if (nArmorRNG >= 20 + currentLevel && nArmorRNG <= 28 + currentLevel){
                z = new Zombie("bucket");
            } else {
                z = new Zombie();
            }

            lastZombieGeneratedTick = currentTick;
            z.setRowPos(random.nextInt(5));
            z.setColumnPos(10);
            aliveZombies.add(z);
            System.out.println("A zombie appeared at (" + z.getRowPos() + "," + z.getColumnPos() + ").");
        }

        if (currentTick == 5700 || currentTick == 3000 && currentLevel >= 3) {
            Zombie z = new Zombie("flag");
            z.setRowPos(random.nextInt(5));
            z.setColumnPos(10);
            aliveZombies.add(z);
            System.out.println("A flag zombie appeared at (" + z.getRowPos() + "," + z.getColumnPos() + ").");
        }
    }

    /**
     * Reduces the placement cooldown of all plants.
     */
    private void reduceCDs() {
        Sunflower.reduceCD();
        CherryBomb.reduceCD();
        Peashooter.reduceCD();
        Snowpea.reduceCD();
        Wallnut.reduceCD();
        PotatoMine.reduceCD();
    }

    /**
     * Checks if a winning or losing condition is met.
     * @return an integer depending on the state. 2 for lose, 1 for win, 0 for not winning or losing.
     */
    public int checkWinLose(int currentTick) {
        for (Zombie z : aliveZombies) {
            if (z.getColumnPos() < 0) {
                return 2;
            }
        }
        if (currentTick > 6000 && aliveZombies.isEmpty()) {
            return 1;
        }
        return 0;
    }

    /**
     * Resets the board by removing all objects in the gameboard.
     */
    public void resetBoard() {
        for (int i = alivePlants.size() - 1; i >= 0; i--) {
            Plant plant = alivePlants.get(i);
                plantBoard[plant.getRowPos()][(int) plant.getColumnPos()] = null;
                alivePlants.remove(i);
        }
        aliveZombies.clear();
        alivePlants.clear();
        activeSuns.clear();
        activePeas.clear();
    }
}
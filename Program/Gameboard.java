package Program;

import java.util.ArrayList;
import java.util.Random;

public class Gameboard {
    private Plant[][] plantBoard;
    private ArrayList<Zombie> aliveZombies;
    private ArrayList<Plant> alivePlants;
    private ArrayList<Pea> activePeas;
    private int lastZombieGeneratedTick;

    public Gameboard(){
        plantBoard = new Plant[5][9];
        aliveZombies = new ArrayList<>();
        alivePlants = new ArrayList<>();
        activePeas = new ArrayList<>();
        lastZombieGeneratedTick = 0;
    }

    public Plant[][] getPlantBoard() { return plantBoard; }
    public ArrayList<Zombie> getAliveZombies() { return aliveZombies; }
    public ArrayList<Plant> getAlivePlants(){ return alivePlants; }
    public ArrayList<Pea> getActivePeas() { return activePeas; }

    public void updateGame(int currentTick, Player player) {
        // ACTION PHASE
        for (Plant plant : alivePlants) {
            if (plant instanceof Sunflower sf) {
                sf.updateSunflower(player);
            }
            else if (plant instanceof Peashooter ps) {
                Pea newPea = ps.updatePeashooter(aliveZombies);
                if (newPea != null) {
                    activePeas.add(newPea);
                }
            }
        }
        for (Zombie zombie : aliveZombies) { zombie.updateZombie(alivePlants); }
        for (Pea pea : activePeas) { pea.updatePea(aliveZombies); }

        // REMOVAL PHASE
        for (Zombie zombie : aliveZombies) {
            if (!zombie.isAlive()) {
                System.out.println("Zombie at row " + zombie.getRowPos() + " Column " + zombie.getColumnPos() + " has been killed!");
                aliveZombies.remove(zombie);
            }
        }
        for (int i = alivePlants.size() - 1; i >= 0; i--) {
            Plant plant = alivePlants.get(i);
            if (!plant.isAlive()) {
                // Clear from board grid
                plantBoard[plant.getRowPos()][plant.getColumnPos()] = null;
                alivePlants.remove(i);
            }
        }
        activePeas.removeIf(pea -> pea.getColumnPos() > 8 || !pea.isActive());

        // ZOMBIE GENERATION
        if (currentTick <= 180) {
            generateZombie(currentTick);
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
            default -> null;

        };

        if (p == null) {
            System.out.println("Invalid plant name.");
            return;
        }

        if (!player.buyPlant(p, currentTick)) {
            return;
        }

        p.setRowPos(row);
        p.setColumnPos(column);
        alivePlants.add(p);
        plantBoard[row][column] = p;
        Plant.incrementPlantCount();
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " planted at inputted position.");
    }

    public boolean isValidPosition(int row, int column){
        if (row >= 0 && row < 5 && column >= 0 && column < 9){
            return plantBoard[row][column] == null;
        }
        return false;
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
}

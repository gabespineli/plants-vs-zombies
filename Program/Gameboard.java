package Program;

import java.util.ArrayList;
import java.util.Random;

public class Gameboard {
    private Plant[][] plantBoard;
    private ArrayList<Zombie> aliveZombies;
    private ArrayList<Zombie> nearestZombies;
    private int lastZombieGeneratedTick = 0;
    private ArrayList<Plant> alivePlants;

    public Gameboard(){
        plantBoard = new Plant[5][9];
        aliveZombies = new ArrayList<>();
        alivePlants = new ArrayList<>();
    }

    public Plant[][] getPlantBoard() { return plantBoard; }
    public ArrayList<Zombie> getAliveZombies() { return aliveZombies; }
    public ArrayList<Plant> getAlivePlants(){ return alivePlants; }

    public void updateGame(int currentTick, Player player) {
        for (Plant p : alivePlants) {
            if (p instanceof Sunflower sf) { sf.produce(currentTick, player); }
            //else if (p instanceof Peashooter ps) { ps.shoot(z); }
        }
        for (Zombie z : aliveZombies) {
            z.move(currentTick);
        }
        generateZombie(currentTick);
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
        Plant.incrementPlantCount();
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " planted at inputted position.");
    }

    // public void deletePlant()

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

    // public void deleteZombie
}

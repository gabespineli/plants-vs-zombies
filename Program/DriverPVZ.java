package Program;

import java.io.IOException;
import java.util.Scanner;

public class DriverPVZ {
    public void displayBoard(Gameboard board) {
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
                String cell = "   ";
                Plant plant = board.getPlantBoard()[row][col];
                boolean hasZombie = false;
                boolean hasPea = false;

                for (Zombie z : board.getAliveZombies()) {
                    if (z.getRowPos() == row && z.getColumnPos() == col) {
                        hasZombie = true;
                        break;
                    }
                }

                for (Pea pea : board.getActivePeas()) {
                    if (pea.getRowPos() == row && pea.getColumnPos() == col) {
                        hasPea = true;
                        break;
                    }
                }

                if (plant instanceof Sunflower) {
                    if (hasZombie){
                        cell = "SZ ";
                    }
                    else if (hasPea){
                        cell = "So ";
                    }
                    else {
                        cell = " S ";
                    }
                    //cell = hasZombie ? "SZ " : " S ";
                } else if (plant instanceof Peashooter) {
                    if (hasZombie){
                        cell = "PZ ";
                    }
                    else if (hasPea){
                        cell = "Po ";
                    }
                    else {
                        cell = " P ";
                    }
                    //cell = hasZombie ? "PZ " : " P ";
                } else {
                    if (hasZombie){
                        cell = " Z ";
                    }
                    else if (hasPea){
                        cell = " o ";
                    }
                    else {
                        cell = "   ";
                    }
                    //cell = hasZombie ? " Z " : "   ";
                }

                System.out.print(cell + "|");
            }
            System.out.println();

            System.out.print("   +");
            for (int col = 0; col < 9; col++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }

    public void displayShop(Player p, int currentTick) {
        int sun = p.getSunPoints();
        Sunflower sf = new Sunflower();
        int sunflowerCooldown = sf.getPlacementCooldown() - (currentTick - Sunflower.getLastPlacedSunflower());
        Peashooter ps = new Peashooter();
        int peashooterCooldown = ps.getPlacementCooldown() - (currentTick - Peashooter.getLastPlacedPeashooter());


        System.out.println("\nSun: " + p.getSunPoints());
        System.out.println("\n--- SHOP ---");
        if (sun < sf.getCost()) System.out.println("Sunflower - 50 [Not enough sun]");
        else if (sunflowerCooldown > 0) System.out.println("Sunflower - 50 [Plant in cooldown. Wait for " + sunflowerCooldown + " turns.]");
        else System.out.println("Sunflower - 50 [Available]");

        if (sun < ps.getCost()) System.out.println("Peashooter - 100 [Not enough sun]");
        else if (peashooterCooldown > 0) System.out.println("Peashooter - 100 [Plant in cooldown. Wait for " + peashooterCooldown + " turns.]");
        else System.out.println("Peashooter - 100 [Available]");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        System.out.println("Welcome to Plants vs Zombies (Console Edition)!");
        System.out.println("The game runs continuously until you press Enter to input a command.");
        System.out.println("Commands: [PLANT_NAME] [ROW] [COLUMN], or press Enter to resume the game.");
        System.out.println("Prompt Example: Sunflower 2 3");
        System.out.println("Type 'end' to end the game.");
        System.out.println("Press any key to continue...");
        scanner.nextLine();

        game.run();
    }
}

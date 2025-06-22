package Program;

import java.util.ArrayList;
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
                // Check plant
                String cell = "   ";
                Plant plant = board.getPlantBoard()[row][col];
                boolean hasZombie = false;

                // Check if any zombie is in this cell
                for (Zombie z : board.getAliveZombies()) {
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

    public void displayShop(Player p, int currentTick) {
        int sun = p.getSunPoints();
        Sunflower sf = new Sunflower();
        int sunflowerCooldown = sf.getPlantCD() - (currentTick - Sunflower.getLastPlacedSunflower());
        Peashooter ps = new Peashooter();
        int peashooterCooldown = ps.getPlantCD() - (currentTick - Peashooter.getLastPlacedPeashooter());


        System.out.println("\nSun: " + p.getSunPoints());
        System.out.println("\n--- SHOP ---");
        if (sun < sf.getCost()) System.out.println("Sunflower - 50 [Not enough sun]");
        else if (sunflowerCooldown > 0) System.out.println("Sunflower - 50 [Plant in cooldown]");
        else System.out.println("Sunflower - 50 [Available]");

        if (sun < ps.getCost()) System.out.println("Peashooter - 100 [Not enough sun]");
        else if (peashooterCooldown > 0) System.out.println("Peashooter - 100 [Plant in cooldown]");
        else System.out.println("Peashooter - 100 [Available]");
    }

    public boolean WinLoseConditions(ArrayList<Zombie> aliveZombies, int currentTick){
        for (Zombie z : aliveZombies){
            if (z.getColumnPos()<0){
               System.out.println("You lost! A zombie reached your home.");
               return true;
            }
        }
        if (currentTick > 180 && aliveZombies.isEmpty()){
            System.out.println("You win! All zombies eliminated");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DriverPVZ driver = new DriverPVZ();
        Gameboard board = new Gameboard();
        Player player = new Player();

        int currentTick = 0;
        String input;

        System.out.println("Welcome to Plants vs Zombies (Console Edition)!");
        System.out.println("Commands: [PLANT_NAME] [ROW] [COLUMN], or press Enter to skip turn.");
        System.out.println("Prompt Example: Sunflower 2 3");
        System.out.println("Type 'end' to end the game.");
        System.out.println("Press any key to continue...");
        scanner.nextLine();

        do {
            driver.displayBoard(board);
            driver.displayShop(player , currentTick);

            System.out.print("\nTick " + currentTick + " | Command: ");
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                String[] parts = input.split(" ");
                if (parts.length == 3) {
                    String plant = parts[0];
                    try {
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);
                        board.addPlant(plant, row, col, player, currentTick);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid coordinates. Use numbers for row and column.");
                    }
                } else if (parts.length == 1 && input.equalsIgnoreCase("end")) { continue; }
                else {
                    System.out.println("Invalid command. Format: [plantname] [row] [col]");
                }
            }

            if (currentTick % 5 == 0) {
                System.out.println("The sky dropped 50 sun.");
                player.addSun(50);
            }

            board.updateGame(currentTick, player);
            currentTick++;

            if (driver.WinLoseConditions(board.getAliveZombies(), currentTick)){
                input = "end";
            }
        } while (!input.equalsIgnoreCase("end"));
    }
}

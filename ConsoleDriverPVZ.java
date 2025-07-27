import java.io.IOException;
import java.util.Scanner;

public class ConsoleDriverPVZ {
    public void displayBoard(Gameboard board) {
        System.out.println("\nCurrent Board:");

        System.out.print("    ");
        for (int col = 0; col <= 8; col++) {
            System.out.print(" " + col + "  ");
        }
        System.out.println();

        System.out.print("   +");
        for (int col = 0; col < 9; col++) {
            System.out.print("---+");
        }
        System.out.println();

        for (int row = 0; row < 5; row++) {
            System.out.print(row + "  |");

            for (int col = 0; col < 9; col++) {
                String cell = "   ";
                Plant plant = board.getPlantBoard()[row][col];
                boolean hasZombie = false;
                boolean hasPea = false;
                boolean hasSun = false;

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
                for (Sun sun : board.getActiveSuns()) {
                    if (sun.getRowPos() == row && sun.getColumnPos() == col) {
                        hasSun = true;
                        break;
                    }
                }

                if (plant instanceof Sunflower) {
                    if (hasZombie && hasSun){ cell = "SZ*"; }
                    else if (hasPea && hasSun){ cell = "So*"; }
                    else if (hasZombie){ cell = "SZ "; }
                    else if (hasPea){ cell = "So "; }
                    else if (hasSun){ cell = "S* "; }
                    else { cell = " S "; }
                } else if (plant instanceof Peashooter) {
                    if (hasZombie && hasSun){ cell = "PZ*"; }
                    else if (hasPea && hasSun){ cell = "Po*"; }
                    else if (hasZombie){ cell = "PZ "; }
                    else if (hasPea){ cell = "Po "; }
                    else if (hasSun){ cell = "P* "; }
                    else { cell = " P "; }
                } else {
                    if (hasZombie && hasSun){ cell = " Z*"; }
                    else if (hasPea && hasSun){ cell = " o*"; }
                    else if (hasZombie){ cell = " Z "; }
                    else if (hasPea){ cell = " o "; }
                    else if (hasSun){ cell = " * "; }
                    else { cell = "   "; }
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
        /*int sun = p.getSunPoints();
        Sunflower sf = new Sunflower();
        //int sunflowerCooldown = Sunflower.isReady();
        Peashooter ps = new Peashooter();
        //int peashooterCooldown = Peashooter.isReady();
        CherryBomb cb = new CherryBomb();
        //int cherryBombCooldown = CherryBomb.isReady();


        System.out.println("\nSun: " + p.getSunPoints());
        System.out.println("\n--- SHOP ---");
        if (sun < sf.getCost()) System.out.println("Sunflower - 50 [Not enough sun]");
        else if (sunflowerCooldown > 0) System.out.println("Sunflower - 50 [Plant in cooldown. Wait for " + sunflowerCooldown + " turns.]");
        else System.out.println("Sunflower - 50 [Available]");

        if (sun < ps.getCost()) System.out.println("Peashooter - 100 [Not enough sun]");
        else if (peashooterCooldown > 0) System.out.println("Peashooter - 100 [Plant in cooldown. Wait for " + peashooterCooldown + " turns.]");
        else System.out.println("Peashooter - 100 [Available]");

        if (sun < cb.getCost()) System.out.println("Cherry Bomb - 150 [Not enough sun]");
        else if (cherryBombCooldown > 0) System.out.println("Cherry Bomb - 150 [Plant in cooldown. Wait for " + cherryBombCooldown + " turns.]");
        else System.out.println("Cherry Bomb - 150 [Available]");*/
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        System.out.println("Welcome to Plants vs Zombies (Console Edition)!\n");
        System.out.println("The game runs continuously until you press Enter to input a command.");
        System.out.println("COMMANDS (case-insensitive):");
        System.out.println("\t> [PLANT_NAME] [ROW] [COLUMN], places a plant at the given coordinates.");
        System.out.println("\t> " +
                "COLLECT [ROW] [COLUMN], collects sun from the given coordinates.");
        System.out.println("You can also chain commands so long as it's a valid sequence of commands.\n");
        System.out.println("Prompt Examples: \n\t> Sunflower 2 3\n\t> Collect 4 4" +
                                            "\n\t> Peashooter 1 5 Collect 9 9 Sunflower 1 1 Collect 3 2");
        System.out.println("Type 'end' to end the game.");
        System.out.println("Press any key to continue...");
        scanner.nextLine();

        game.run();
    }
}

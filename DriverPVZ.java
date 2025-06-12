import java.util.Scanner;

public class DriverPVZ {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player = new Player();
        Gameboard board = new Gameboard();
        int currentTick = 0;
        String input;

        System.out.println("Welcome to Plants vs Zombies (Console Edition)!");
        System.out.println("Commands: [plantname] [row] [col], or press Enter to skip turn. type 'end' to end the game.");
        System.out.println("Example: sunflower 2 3");


        do {
            board.displayBoard();

            System.out.print("\nTick " + currentTick + " - Sun: " + player.getSunPoints() + " | Command: ");
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

            // Other updates (later): move zombies, shoot peas, etc.

            currentTick++;
        } while (!input.equalsIgnoreCase("end"));
    }
}

package Program;

import java.util.Scanner;

public class Game {
    private Gameboard board;
    private Player player;
    private int currentTick;
    private int lastSkyDropTick;
    private boolean gameEnded;

    public Game() {
        board = new Gameboard();
        player = new Player();
        currentTick = 0;
        lastSkyDropTick = 0;
        gameEnded = false;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        DriverPVZ ui = new DriverPVZ();

        while (!gameEnded) {
            System.out.println("\n==============================");
            System.out.println("Tick: " + currentTick);

            update();
            ui.displayBoard(board);
            ui.displayShop(player, currentTick);

            System.out.print("Command (or press Enter to skip): ");
            long inputStart = System.currentTimeMillis(); // start recording time
            String input = scanner.nextLine().trim();
            long inputEnd = System.currentTimeMillis();

            // Advance time based on how long user took to respond
            long secondsPassed = (inputEnd - inputStart) / 1000;
            if (secondsPassed < 1) secondsPassed = 1;
            currentTick += (int) secondsPassed;

            if (!input.isEmpty()) {
                if (input.equalsIgnoreCase("end")) {
                    gameEnded = true;
                    break;
                }
                processCommand(input);
            }

            if (checkWinLose()) {
                gameEnded = true;
            }
        }
    }


    private void processCommand(String input) {
        if (input.isEmpty()) return;

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
        } else {
            System.out.println("Invalid command. Format: [PLANT_NAME] [ROW] [COLUMN]");
        }
    }

    private void update() {
        board.updateGame(currentTick, player);
        while (currentTick - lastSkyDropTick >= 5) {
            lastSkyDropTick += 5;
            System.out.println("The sky dropped 50 sun.");
            player.addSun(50);
        }
    }

    private boolean checkWinLose() {
        for (Zombie z : board.getAliveZombies()) {
            if (z.getColumnPos() < 0) {
                System.out.println("You lost! A zombie reached your home.");
                return true;
            }
        }

        if (currentTick > 180 && board.getAliveZombies().isEmpty()) {
            System.out.println("You win! All zombies eliminated.");
            return true;
        }

        return false;
    }
}


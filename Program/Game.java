package Program;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    private Gameboard board;
    private Player player;
    private int currentTick;
    private boolean gameEnded;

    public Game() {
        board = new Gameboard();
        player = new Player();
        currentTick = 0;
        gameEnded = false;
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        DriverPVZ ui = new DriverPVZ();
        final int TICK_INTERVAL = 1000;

        long lastTick = System.currentTimeMillis();
        boolean paused = false;

        while (!gameEnded) {
            long now = System.currentTimeMillis();

            if (!paused && now - lastTick >= TICK_INTERVAL) {
                currentTick++;
                System.out.println("\n====================================");
                System.out.println("Tick: " + currentTick + " | Sun: " + player.getSunPoints());

                update();
                ui.displayBoard(board);
                ui.displayShop(player, currentTick);

                if (checkWinLose()) {
                    gameEnded = true;
                    break;
                }

                lastTick += TICK_INTERVAL;
            }

            if (System.in.available() > 0) {
                int key = System.in.read();
                
                // Check if it's the Enter key (ASCII 13 for CR or 10 for LF)
                if (key == 13 || key == 10) {
                    paused = true;
                    System.out.print("\n[PAUSED] Enter command (or just press Enter to resume): ");
                    String input = scanner.nextLine().trim();
                    
                    if (input.equalsIgnoreCase("end")) {
                        gameEnded = true;
                        break;
                    }

                    if (!input.isEmpty()) {
                        processCommand(input);
                    }

                    paused = false;
                    lastTick = System.currentTimeMillis();
                } else {
                    // Consume any other keypress
                    while (System.in.available() > 0) {
                        System.in.read();
                    }
                }
            }

            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
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
        if (currentTick % 5 == 0) {
            System.out.println("The sky dropped 50 sun.");
            player.addSun(50);
        }
        board.updateGame(currentTick, player);
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
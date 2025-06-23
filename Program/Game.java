package Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void run() throws IOException { // IN PROCESS
        DriverPVZ ui = new DriverPVZ();
        long lastTick = System.currentTimeMillis();
        final int TICK_INTERVAL = 1000;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        while (!gameEnded) {
            long now = System.currentTimeMillis();

            // Tick logic
            if (now - lastTick >= TICK_INTERVAL) {
                System.out.println("\n==============================");
                System.out.println("Tick: " + currentTick);
                System.out.println("Sun: " + player.getSunPoints());

                if (currentTick % 5 == 0) {
                    System.out.println("Sky dropped 50 sun.");
                    player.addSun(50);
                }

                board.updateGame(currentTick, player);
                ui.displayBoard(board);
                currentTick++;
                lastTick += TICK_INTERVAL;

                if (checkWinLose()) {
                    gameEnded = true;
                    break;
                }
            }

            // Non-blocking input
            if (input.ready()) {
                String command = input.readLine().trim();
                processCommand(command);
            }
        }
    }
    
    private void processCommand(String input) { // make it into System.millis
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


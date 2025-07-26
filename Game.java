import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.BufferedReader;

/**
 * Main game controller for the Plants vs Zombies game. Manages the game loop, user input, and game state.
 */
public class Game {
    private Gameboard board;
    private final Player player;
    private int currentTick;
    private boolean gameEnded;

    /**
     * Constructs a new Game with default initial state.
     */
    public Game() {
        board = new Gameboard();
        player = new Player();
        currentTick = 0;
        gameEnded = false;
    }

    /**
     * Starts and runs the main game loop. Handles game ticks, user input, and game state updates.
     * @throws IOException if an I/O error occurs during input reading
     */
    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleDriverPVZ ui = new ConsoleDriverPVZ();
        final int TICK_INTERVAL = 100;

        long lastTick = System.currentTimeMillis();
        boolean paused = false;

        while (!gameEnded) {
            long now = System.currentTimeMillis();

            if (!paused && now - lastTick >= TICK_INTERVAL) {
                currentTick++;
                System.out.println("\n=======================================");
                System.out.printf("Time: %02d:%02d | Sun: %d\n", currentTick/60, currentTick%60, player.getSunPoints());

                board.updateGame(currentTick);
                ui.displayBoard(board);
                ui.displayShop(player, currentTick);

                if (checkWinLose()) {
                    gameEnded = true;
                    break;
                }

                lastTick += TICK_INTERVAL;
            }

            if (reader.ready()) {
                int key = reader.read();
                
                if (key == 10) // Detects if key is \n
                {
                    paused = true;
                    System.out.println("\n[PAUSED] Enter command (or just press Enter to resume)");
                    System.out.print("> ");
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
                    while (reader.ready()) {
                        reader.skip(1);
                    }
                }
            }

            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
        scanner.close();
    }

    /**
     * Processes a command string entered by the player. Supports plant placement and sun collection commands.
     * @param input the command string to process
     */
    private void processCommand(String input) {
        if (input.isEmpty()) return;

        String[] parts = input.split(" ");
        if (parts.length % 3 == 0) {
            int row, col, i = 0;
            while (i < parts.length) {
                String command = parts[i];
                if (command.equalsIgnoreCase("collect")) {
                    try {
                        row = Integer.parseInt(parts[i+1]);
                        col = Integer.parseInt(parts[i+2]);
                        board.collectSun(row, col, player);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid coordinates. Use numbers for row and column.");
                    }
                } else {
                    try {
                        row = Integer.parseInt(parts[i+1]);
                        col = Integer.parseInt(parts[i+2]);
                        board.addPlant(command, row, col, player, currentTick);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid coordinates. Use numbers for row and column.");
                    }
                }
                i+=3;
            }
        } else {
            System.out.println("Invalid command. Format: [PLANT_NAME]/COLLECT [ROW] [COLUMN]");
        }
    }

    /**
     * Checks if the game has reached a win or lose condition.
     * @return true if the game should end, false otherwise
     */
    private boolean checkWinLose() {
        for (Zombie z : board.getAliveZombies()) {
            if (z.getColumnPos() <= 0) {
                System.out.println("You lost! A zombie reached your home.");
                return true;
            }
        }

        if (currentTick > 1800 && board.getAliveZombies().isEmpty()) {
            System.out.println("You win! All zombies eliminated.");
            return true;
        }

        return false;
    }
}
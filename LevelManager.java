import java.io.*;

/**
 * Manages the current game level and player name.
 * Handles saving and loading progress from file.
 */
public class LevelManager {
    private int level;
    private String name;

    /**
     * Constructs a LevelManager with the specified level and player name.
     * @param level the current game level
     * @param name the player's name
     */
    public LevelManager(int level, String name){
        this.level = level;
        this.name = name;
    }

    /**
     * Gets the current game level.
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the current game level.
     * @param level the new level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the player's name.
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Saves the current player name and level to a file.
     * @param fn the filename to save to
     */
    public void saveFile(String fn) {
        try {
            FileOutputStream fout = new FileOutputStream(fn);
            PrintWriter writer = new PrintWriter(fout);
            writer.println(name);
            writer.print(level);
            writer.close();
            fout.close();
        }
        catch (IOException io){
        }
    }

    /**
     * Loads the player name and level from a file.
     * @param fn the filename to load from
     * @return a new LevelManager with loaded values, or default if file not found
     */
    public static LevelManager loadFile(String fn) {
        File f = new File(fn);

        try {
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String name = bufferedReader.readLine();
            int level = Integer.parseInt(bufferedReader.readLine());

            return new LevelManager(level, name);
        }
        catch (IOException io){
            return new LevelManager(1, "New Player");
        }
    }
}

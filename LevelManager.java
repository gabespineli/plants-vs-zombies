import java.io.*;

public class LevelManager {
    private int level;
    private String name;

    public LevelManager(int level, String name){
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void saveFile(String fn) {
        try {
            FileOutputStream fout = new FileOutputStream(fn);
            PrintWriter writer = new PrintWriter(fout);
            writer.println(name);
            writer.println(level);
        }
        catch (IOException io){
        }
    }

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

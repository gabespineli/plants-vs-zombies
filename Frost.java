import java.util.ArrayList;

/**
 * Represents a Frost projectile shot by Snowpea, which damages and freezes zombies.
 * Extends Pea and overrides collision logic to apply freeze effect.
 */
public class Frost extends Pea {

    /**
     * Constructs a Frost projectile with specified position and damage.
     * @param column the column position
     * @param row the row position
     * @param damage the damage value
     */
    public Frost(double column, int row, int damage) {
        super(column, row, damage);
    }

    /**
     * Checks for collision with zombies, applies damage and freeze effect.
     * @param aliveZombies the list of alive zombies on the board
     */
    @Override
    protected void checkCollision(ArrayList<Zombie> aliveZombies) {
        for (Zombie z : aliveZombies){
            if (z.getRowPos() == rowPos && z.getColumnPos()+0.2 >= columnPos && z.getColumnPos()-0.2 <= columnPos){
                z.takeDamage(damage);
                z.freeze();
                if (z.isAlive()){
                    System.out.println("Zombie at (" + z.getRowPos() + "," + z.getColumnPos() + ") has been shot! (HP: " + z.getHealth() + ")");
                } else {
                    System.out.println("Zombie at (" + z.getRowPos() + "," + z.getColumnPos() + ") has been killed!");
                }
                isActive = false;
                break;
            }
        }
    }
}

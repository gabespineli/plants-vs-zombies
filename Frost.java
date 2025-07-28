import java.util.ArrayList;

public class Frost extends Pea {

    public Frost(double column, int row, int damage) {
        super(column, row, damage);
    }

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

import java.util.ArrayList;

public class Frostpea extends Pea {

    public Frostpea(int column, int row, int damage){
        super(column, row, damage);
    }

    private void checkCollision(ArrayList<Zombie> aliveZombies) {
        for (Zombie z : aliveZombies){
            if (z.getRowPos() == rowPos && z.getColumnPos() == columnPos){
                z.takeDamage(damage);
                z.slowDown();

                if (z.isAlive()){
                    System.out.println("Zombie at (" + z.getRowPos() + "," + z.getColumnPos() + ") has been shot! (HP: " + z.getHealth() + ")");
                } else {
                    System.out.println("Zombie at (" + z.getRowPos() + "," + z.getColumnPos() + ") has been killed!");
                    aliveZombies.remove(z);
                }
                isActive = false;
                break;
            }
        }
    }

    public void updateFrostpea(ArrayList<Zombie> aliveZombies){
        checkCollision(aliveZombies);

        if (isActive) {
            move();
            checkCollision(aliveZombies);
        }
    }
}

package Program;

import java.util.ArrayList;

public class Pea {
    private int columnPos;
    private int rowPos;
    private boolean isActive;
    private int damage;
    private int speed;

    public Pea(int column, int row, int damage){
        columnPos = column;
        rowPos = row;
        isActive = true;
        this.damage = damage;
        speed = 1;
    }

    public int getRowPos() {
        return rowPos;
    }

    public int getColumnPos() {
        return columnPos;
    }

    public void move() {
        columnPos += speed;
    }

    public void updatePea(ArrayList<Zombie> aliveZombies){
        checkCollision(aliveZombies);

        if (isActive) {
            move();
            checkCollision(aliveZombies);
        }
    }

    private void checkCollision(ArrayList<Zombie> aliveZombies) {
        for (Zombie z : aliveZombies){
            if (z.getRowPos() == rowPos && z.getColumnPos() == columnPos){
                z.takeDamage(damage);
                if (z.isAlive()){
                    System.out.println("Zombie at row " + z.getRowPos() + " Column " + z.getColumnPos() + " has been shot! (HP: " + z.getHealth() + ")");
                } else {
                    System.out.println("Zombie at row " + z.getRowPos() + " Column " + z.getColumnPos() + " has been killed!");
                }
                isActive = false;
                break;
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }


}

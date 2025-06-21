package Program;

public class Entity {
    private int health;
    private int columnPos;
    private int rowPos;
    private boolean isAlive;

    public Entity() {
        isAlive = true;
    }

    public void takeDamage(int d){
        if (isAlive)
            health -= d;
        if (health <= 0) {
            isAlive = false;
            //delete entity
        }
    }

    public int getHealth(){ return health; }
    public void setHealth(int health) { this.health = health; }

    public int getColumnPos() { return columnPos; }
    public void setColumnPos(int columnPos) { this.columnPos = columnPos; }

    public int getRowPos() { return rowPos; }
    public void setRowPos(int rowPos) { this.rowPos = rowPos; }

    public boolean isAlive() { return isAlive; }
}

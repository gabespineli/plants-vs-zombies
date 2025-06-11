public class Entity {
    private int health;
    private int columnPos;
    private int rowPos;
    private boolean isAlive;

    public Entity(int h, int x, int y) {
        health = h;
        columnPos = x;
        rowPos = y;
        isAlive = true;
    }

    public int getHealth(){
        return health;
    }

    public void takeDamage(int d){
        if (isAlive)
            health -= d;
        if (health <= 0) {
            isAlive = false;

        }
    }

}

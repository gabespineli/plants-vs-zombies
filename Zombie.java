public class Zombie extends Entity {
    private int walkSpeed;
    private int damage;

    public Zombie(int ws, int d, int h, int x, int y) {
        super(h, x, y);
        walkSpeed = ws;
        damage = d;
    }

    public void attack(Plant p) {
        if (p.getHealth() > 0){
            p.takeDamage(damage);
        }
    }


}
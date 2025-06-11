public class Zombie extends Entity {
    private int walkSpeed;
    private int damage;

    public Zombie(int ws, int d) {
        super();
        setHealth(70);
        walkSpeed = ws;
        damage = d;
    }

    public void attack(Plant p) {
        if (p.getHealth() > 0){
            p.takeDamage(damage);
        }
    }


}
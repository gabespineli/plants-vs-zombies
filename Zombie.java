public class Zombie extends Entity {
    private int walkSpeed;
    private int damage;
    private static int zombieCount = 0;

    public Zombie() {
        super();
        setHealth(70);
        zombieCount++;
        /**walkSpeed = ws;
        damage = d;*/
    }

    public void attack(Plant p) {
        if (p.getHealth() > 0){
            p.takeDamage(damage);
        }
    }


}
public class Zombie extends Entity {
    private int walkSpeed;
    private int damage;
    private static int zombieCount = 0;

    public Zombie() {
        super();
        setHealth(70);
        zombieCount++;
        walkSpeed = 4;
        damage = 10;
    }

    public void attack(Plant p) {
        if (p.getHealth() > 0){
            p.takeDamage(damage);
        }
    }

    //create move method

    public int getWalkSpeed() { return walkSpeed; }
    public void setWalkSpeed(int walkSpeed) { this.walkSpeed = walkSpeed; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
}
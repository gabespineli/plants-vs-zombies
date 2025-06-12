public class Zombie extends Entity {
    private int walkInterval;
    private int damage;
    private int lastMovementTick;
    private static int zombieCount = 0;

    public Zombie() {
        super();
        setHealth(70);
        zombieCount++;
        walkInterval = 4;
        damage = 10;
    }

    public void attack(Plant p) {
        if (p.getHealth() > 0){
            p.takeDamage(damage);
        }
    }

    public void takeDamage(int damage) {
        setHealth(getHealth - damage);
    }

    public void move(int currentTick) {
        if (currentTick - lastMovementTick >= walkInterval) {
            lastMovementTick = currentTick;
            System.out.print("Zombie previously in (" + getRowPos() + "," + getColumnPos() + "), ");
            setColumnPos(getColumnPos() - 1);
            System.out.println("now moved to (" + getRowPos() + "," + getColumnPos() + ")");
        }
    }

    public int getWalkSpeed() { return walkInterval; }
    public void setWalkSpeed(int walkInterval) { this.walkInterval = walkInterval; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
}
public abstract class Armor {
    protected int armorHP;
    protected int armorSpeed;
    protected int armorDamage;
    protected boolean isActive;
    protected Zombie zombie;
    protected String armorType;

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
        applyArmor();
    }

    public String getArmorType() {
        return armorType;
    }

    public void takeDamage(int d){
        if (isActive)
            armorHP -= d;

        if (armorHP <= 0) {
            System.out.println("Zombie at (" + zombie.getRowPos() + "," + zombie.getColumnPos() + ")'s armor broke!");
            isActive = false;
            removeArmor();
        }
    }

    protected void applyArmor() {
        zombie.setDamage(zombie.getDamage()+armorDamage);
        zombie.setspeed(zombie.getspeed()+armorSpeed);
    }

    protected void removeArmor() {
        zombie.setDamage(zombie.getDamage()-armorDamage);
        zombie.setspeed(zombie.getspeed()-armorSpeed);
    }

    public boolean isActive() {
        return isActive;
    }
}

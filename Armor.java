public abstract class Armor {
    protected int armorHP;
    protected int armorSpeed;
    protected int armorDamage;
    protected boolean isActive;
    protected Zombie zombie;

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
        applyArmor();
    }

    public void takeDamage(int d){
        if (isActive)
            armorHP -= d;

        if (armorHP <= 0) {
            isActive = false;
            removeArmor();
        }
    }

    protected void applyArmor() {
        zombie.setDamage(zombie.getDamage()+armorDamage);
        zombie.setMsPerTile(zombie.getMsPerTile()+armorSpeed);
    }

    protected void removeArmor() {
        zombie.setDamage(zombie.getDamage()-armorDamage);
        zombie.setMsPerTile(zombie.getMsPerTile()-armorSpeed);
    }

    public boolean isActive() {
        return isActive;
    }
}

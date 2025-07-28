public abstract class Armor {
    protected int armorHP;
    protected int armorSpeed;
    protected int armorDamage;
    protected boolean isActive;
    protected String armorType;

    public String getArmorType() {
        return armorType;
    }

    public int getArmorDamage() {
        return armorDamage;
    }

    public int getArmorSpeed() {
        return armorSpeed;
    }

    public void takeDamage(int d){
        if (isActive)
            armorHP -= d;

        if (armorHP <= 0) {
            isActive = false;
        }
    }

    public boolean isActive() {
        return isActive;
    }
}

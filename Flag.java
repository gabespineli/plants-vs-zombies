public class Flag extends Armor {
    public Flag() {
        armorHP = 0;
        armorSpeed = -1;
        armorDamage = 0;
        isActive = true;
        armorType = "Flag";
    }

    @Override
    public void takeDamage(int d){
        zombie.setHealth(zombie.getHealth() - d);
    }
}

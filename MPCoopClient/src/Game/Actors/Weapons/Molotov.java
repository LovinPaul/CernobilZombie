package Game.Actors.Weapons;

import Game.Animations.Explosions.Explosion;
import Game.Animations.MolotovGrenade;

public class Molotov extends Grenade{
    
    public static int shake(){
        return 20;
    }
    
//    public Molotov(float targetX, float targetY) {
//        super.anim_image = new MolotovGrenade();
//        super.explosion = new Explosion();
//        name = "Molotov";
//        super.setFirePower(50);
//        super.fireDistance=130;
//        super.timeUntilExplosion = 50;
//        super.cameraShake = shake();
//    }
//    
//    public Molotov() {
//        super.addAmmo(5);
//        name = "Molotov";
//        super.anim_image = new MolotovGrenade();
//        relativWeaponY = -25;
//        weaponCooldownTime=50;
//    }
//
//    public Molotov(int timeUntilExplosion, float x, float y) {
//        super(timeUntilExplosion, x, y);
//        super.anim_image = new MolotovGrenade();
//        super.explosion = new Explosion();
//        super.dist=10;
////        grenades.add(new Molotov(timeUntilExplosion, x, y));
//    }
//    
//    @Override
//    public void fire(float targetX, float targetY) {
//        if(isCool() && getAmmo()>0){
//            super.fire(targetX, targetY);
//            grenades.add(new Molotov(targetX, targetY));
//       
//        }
//    }
//
//    @Override
//    public void calibrateAnimation(int index) {
//        super.calibrateAnimation(index); //To change body of generated methods, choose Tools | Templates.
//    }

    
}

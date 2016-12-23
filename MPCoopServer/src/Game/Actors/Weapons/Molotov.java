package Game.Actors.Weapons;

import Game.Effects.Explosion;
import Game.Effects.MolotovGrenade;
import Game.Actors.Actor;

public class Molotov extends Grenade{
    
    public Molotov(Actor owner, float targetX, float targetY) {
        super((byte)0, owner, targetX, targetY);
        super.anim_image = new MolotovGrenade();
        super.explosion = new Explosion();
        super.setFirePower(50);
        super.fireDistance=130;
        super.timeUntilExplosion = 100;
        super.cameraShake = 10;
    }
    public Molotov(Actor owner) {
        super(owner);
        super.anim_image = new MolotovGrenade();
        relativWeaponY = -25;
    }
    public Molotov() {
        super.anim_image = new MolotovGrenade();
        relativWeaponY = -25;
    }
    
    
    @Override
    public void fire(float targetX, float targetY) {
        super.fire(targetX, targetY);
        grenades.add(new Molotov(owner, targetX, targetY));
    }
    
}

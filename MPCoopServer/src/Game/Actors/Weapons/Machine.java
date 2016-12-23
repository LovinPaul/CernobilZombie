package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.LoadTextures;


public class Machine extends Gun{

    public Machine(Actor owner) {
        super(owner);
//        super.addAmmo(150);
        clipSize=30;
        firePower=5;
        weaponCooldownTime=2;
        relativWeaponX = 5;
        relativWeaponY = -40;
        relativMuzzleFlashX = +7;
        relativMuzzleFlashY = -50;
        image = LoadTextures.machine;
        muzzle_flash = LoadTextures.gun_muzzle_flash;
        cameraShake=2;
    }
    public Machine() {
//        super.addAmmo(150);
        clipSize=30;
        firePower=5;
        weaponCooldownTime=2;
        relativWeaponX = 5;
        relativWeaponY = -40;
        relativMuzzleFlashX = +7;
        relativMuzzleFlashY = -50;
        image = LoadTextures.machine;
        muzzle_flash = LoadTextures.gun_muzzle_flash;
        cameraShake=2;
    }
    
}

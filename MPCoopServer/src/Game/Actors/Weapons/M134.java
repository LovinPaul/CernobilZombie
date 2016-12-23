package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.LoadTextures;


public class M134 extends Gun{

    public M134(Actor owner) {
        super(owner);
//        super.addAmmo(300);
        clipSize=300;
        firePower=20;
        weaponCooldownTime=4;
        twoHandedWeapon=true;
        relativWeaponX = -11;
        relativWeaponY = -55;
        relativMuzzleFlashX = -6;
        relativMuzzleFlashY = -85;
        image = LoadTextures.M134;
        muzzle_flash = LoadTextures.M134_muzzle_flash;
        cameraShake = 5;
    }
    public M134() {
//        super.addAmmo(300);
        clipSize=300;
        firePower=20;
        weaponCooldownTime=4;
        twoHandedWeapon=true;
        relativWeaponX = -11;
        relativWeaponY = -55;
        relativMuzzleFlashX = -6;
        relativMuzzleFlashY = -85;
        image = LoadTextures.M134;
        muzzle_flash = LoadTextures.M134_muzzle_flash;
        cameraShake = 5;
    }
}

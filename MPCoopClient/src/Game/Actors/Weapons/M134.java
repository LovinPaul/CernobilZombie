package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.Textures.LoadTextures;


public class M134 extends Gun{

    public M134(LoadTextures textures) {
        ID=3;
        name = "M134";
        clipSize=150;
        ammo = clipSize*2;
        firePower=15;
        weaponCooldownTime=4;
        twoHandedWeapon=true;
        relativWeaponX = -11;
        relativWeaponY = -55;
        relativMuzzleFlashX = -6;
        relativMuzzleFlashY = -85;
        image = textures.M134;
        muzzle_flash = textures.M134_muzzle_flash;
        cameraShake = 5;
    }
    
}

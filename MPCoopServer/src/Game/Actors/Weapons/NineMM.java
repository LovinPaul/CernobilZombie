package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.LoadTextures;


public class NineMM extends Gun{

    public NineMM(Actor owner) {
        super(owner);
//        super.addAmmo(50);
        clipSize=7;
        firePower=5;
        weaponCooldownTime=10;
        relativWeaponX = 5;
        relativWeaponY = -30;
        relativMuzzleFlashX = +7;
        relativMuzzleFlashY = -40;
        image = LoadTextures.nineMM;
        muzzle_flash = LoadTextures.gun_muzzle_flash;
        cameraShake=2;
    }
    public NineMM() {
//        super.addAmmo(50);
        clipSize=7;
        firePower=5;
        weaponCooldownTime=10;
        relativWeaponX = 5;
        relativWeaponY = -30;
        relativMuzzleFlashX = +7;
        relativMuzzleFlashY = -40;
        image = LoadTextures.nineMM;
        muzzle_flash = LoadTextures.gun_muzzle_flash;
        cameraShake=2;
    }
}

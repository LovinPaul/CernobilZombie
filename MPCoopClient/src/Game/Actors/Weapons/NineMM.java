package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.Textures.LoadTextures;


public class NineMM extends Gun{

    public NineMM(LoadTextures textures) {
        ID=1;
        name="9mm";
        clipSize=7;
        ammo = clipSize*6;
        firePower=5;
        weaponCooldownTime=10;
        relativWeaponX = 5;
        relativWeaponY = -30;
        relativMuzzleFlashX = +7;
        relativMuzzleFlashY = -40;
        cameraShake=2;
        if(textures!=null){
            image = textures.nineMM;
            muzzle_flash = textures.gun_muzzle_flash;
        }
        
    }
    
}

package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.Textures.LoadTextures;


public class Machine extends Gun{

    public Machine(LoadTextures textures) {
        ID=2;
        name = "Uzzi";
        clipSize=30;
        ammo = clipSize*5;
        firePower=5;
        weaponCooldownTime=2;
        relativWeaponX = 5;
        relativWeaponY = -40;
        relativMuzzleFlashX = +7;
        relativMuzzleFlashY = -50;
        cameraShake=2;
        
        if(textures!=null){
            image = textures.machine;
            muzzle_flash = textures.gun_muzzle_flash;
        }
      
        
    }

    
}


package Game.Actors.Items;

import Game.Actors.Actor;
import Game.Actors.Weapons.Machine;
import Main.Textures.LoadTextures;

public class Machine_Clip extends Item{
    
    int ammo = 60;
    
    public Machine_Clip(float x, float y) {
        super((byte)11, x, y);
    }
    
    public void init(LoadTextures textures){
        image= textures.machineClip;
    }
    
    @Override
    public void pickUp(Actor actor) {
        
        if(actor.getWeapon() instanceof Machine){
            super.pickUp(actor);
            actor.getWeapon().addAmmo(ammo);
        }
    }
    
}

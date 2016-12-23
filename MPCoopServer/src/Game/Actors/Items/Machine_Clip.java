
package Game.Actors.Items;

import Game.Actors.Actor;
import Game.Actors.Weapons.Machine;
import Main.LoadTextures;

public class Machine_Clip extends Item{
    
    int ammo = 30;
    
    public Machine_Clip(float x, float y) {
        super((byte)1, x, y);
        image=LoadTextures.machineClip;
    }
    
    @Override
    public void pickUp(Actor actor) {
        
        if(actor.isAClientWeapon((byte)2)){
            super.pickUp(actor);
            actor.pickUpItem(this, (short) ammo);
        }
    }
    
}

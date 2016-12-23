package Game.Actors.Items;

import Game.Actors.Actor;
import Game.Actors.Weapons.NineMM;
import Main.LoadTextures;

public class NineMM_Clip extends Item{
    
    int ammo = 7;
    
    public NineMM_Clip(float x, float y) {
        super((byte)2, x, y);
        image=LoadTextures.nineMMClip;
    }
    
    @Override
    public void pickUp(Actor actor) {
        
        if(actor.isAClientWeapon((byte)1)){
            super.pickUp(actor);
            actor.pickUpItem(this, (short) ammo);
        }
    }
    
}

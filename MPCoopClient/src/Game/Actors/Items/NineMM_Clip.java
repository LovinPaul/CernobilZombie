package Game.Actors.Items;

import Game.Actors.Actor;
import Game.Actors.Weapons.NineMM;
import Main.Textures.LoadTextures;

public class NineMM_Clip extends Item{
    
    int ammo = 7;
    
    public NineMM_Clip(float x, float y) {
        super((byte)12, x, y);
    }
    
    public void init(LoadTextures textures){
        image= textures.nineMMClip;
    }
    
    @Override
    public void pickUp(Actor actor) {
        
        if(actor.getWeapon() instanceof NineMM){
            super.pickUp(actor);
            actor.getWeapon().addAmmo(ammo);
        }
    }
    
}

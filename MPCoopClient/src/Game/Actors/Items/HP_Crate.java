package Game.Actors.Items;

import Game.Actors.Actor;
import Main.Textures.LoadTextures;

public class HP_Crate extends Item{

    int hp=25;
    
    public HP_Crate(float x, float y) {
        super((byte)10, x, y);
        
    }
    
    public void init(LoadTextures textures){
        image= textures.hpCrate;
    }
    
    @Override
    public void pickUp(Actor actor) {
        super.pickUp(actor);
        if(actor.getHitPoints()+hp>100){
            actor.setHitPoints(100);
        }else{
            actor.setHitPoints(actor.getHitPoints()+hp);
        }
    }
    
}

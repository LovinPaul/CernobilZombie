package Game.Actors.Items;

import Game.Actors.Actor;
import Main.LoadTextures;

public class HP_Crate extends Item{

    int hp=25;
    
    public HP_Crate(float x, float y) {
        super((byte)0, x, y);
        image=LoadTextures.hpCrate;
    }
    
    @Override
    public void pickUp(Actor actor) {
        if(actor.getHitPoints()<100){
            super.pickUp(actor);
            if(actor.getHitPoints()+hp>100){
                actor.setHitPoints(100);
            }else{
                actor.setHitPoints(actor.getHitPoints()+hp);
            }
        }
    }
}

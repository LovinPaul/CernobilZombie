package Game.Maps.Environment.Crates;

import Game.Effects.Explosion1;
import Main.LoadTextures;
import java.awt.Rectangle;

public class TNT extends ExploadingCrate{
    
    public TNT(byte id, float x, float y) {
        super(id, x, y);//(byte)0, 
        super.explosion = new Explosion1();
        super.image = LoadTextures.bigTNTCrate;
        super.firePower=7.5f;
        super.fireDistance=250;
        super.hitPoints=20;
        super.bounds = new Rectangle((int)x-image.getWidth()/2, (int)y-image.getHeight()/2, image.getWidth(), image.getHeight());
    }
    
}

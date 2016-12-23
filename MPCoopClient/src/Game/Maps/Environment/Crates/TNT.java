package Game.Maps.Environment.Crates;

import Game.Animations.Explosions.Debris;
import Game.Animations.Explosions.Explosion1;
import Main.Textures.LoadTextures;
import java.awt.Rectangle;

public class TNT extends ExploadingCrate{
    
    public TNT(byte id, float x, float y, LoadTextures textures) {
        super(id, x, y);//(byte)0, 
        super.explosion = new Explosion1(textures);
        super.image = textures.bigTNTCrate;
        super.hitPoints=100;
        super.debris = new Debris(textures);
        super.bounds = new Rectangle((int)x-image.getWidth()/2, (int)y-image.getHeight()/2, image.getWidth(), image.getHeight());
    }
    
}

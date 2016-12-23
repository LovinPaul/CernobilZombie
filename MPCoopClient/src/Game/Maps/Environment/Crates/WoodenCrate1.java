package Game.Maps.Environment.Crates;

import Game.Animations.Explosions.Debris;
import Main.Textures.LoadTextures;
import java.awt.Rectangle;

public class WoodenCrate1 extends Crate{
    
    public WoodenCrate1(byte ID, float x, float y, LoadTextures textures) {
        super(ID, x, y);
        super.image = textures.woodenCrate1;
        super.hitPoints=100;
        super.debris = new Debris(textures);
        super.bounds = new Rectangle((int)x-image.getWidth()/2, (int)y-image.getHeight()/2, image.getWidth(), image.getHeight());
    }
    
    
    
}

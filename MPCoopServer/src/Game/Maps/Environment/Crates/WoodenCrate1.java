package Game.Maps.Environment.Crates;

import Main.LoadTextures;
import java.awt.Rectangle;

public class WoodenCrate1 extends Crate{
    
    public WoodenCrate1(byte ID, float x, float y) {
        super(ID, x, y);
        super.image = LoadTextures.woodenCrate1;
        super.hitPoints=100;
        super.bounds = new Rectangle((int)x-image.getWidth()/2, (int)y-image.getHeight()/2, image.getWidth(), image.getHeight());
    }
    
    
    
}

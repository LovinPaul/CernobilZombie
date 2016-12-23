package Game.Animations.Explosions;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;
import java.awt.image.BufferedImage;

public class Explosion extends Animation{
    
    public Explosion(LoadTextures textures) {
        super(textures.explosion, 4, 118, 118);
        super.centerTheAnimation = true;
    }
    
    
    
}

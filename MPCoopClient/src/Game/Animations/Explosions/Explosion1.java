package Game.Animations.Explosions;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class Explosion1 extends Animation{
    
    public Explosion1(LoadTextures textures) {
        super(textures.explosion1, 13, 128, 128);
        super.centerTheAnimation = true;
    }
    
    
    
}

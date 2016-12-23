package Game.Animations.UI;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class HpHeart extends Animation{
    
    public HpHeart(LoadTextures textures) {
        super(textures.hpHeart, 30, 32, 32);
        super.rotate=false;
    }
    
}

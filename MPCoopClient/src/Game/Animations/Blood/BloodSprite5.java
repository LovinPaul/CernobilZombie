package Game.Animations.Blood;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class BloodSprite5 extends Animation{
    
    public BloodSprite5(LoadTextures texture) {
        super(texture.blood_sprite_5, 8, 500, 500);
        super.centerTheAnimation=true;
//        super.rotate=false;
    }
    
}

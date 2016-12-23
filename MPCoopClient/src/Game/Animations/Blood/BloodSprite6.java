
package Game.Animations.Blood;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class BloodSprite6 extends Animation{
    
    public BloodSprite6(LoadTextures textures) {
        super(textures.blood_sprite_6, 8, 250, 593);
        super.centerTheAnimation=true;
//        super.rotate=false;
    }
    
}

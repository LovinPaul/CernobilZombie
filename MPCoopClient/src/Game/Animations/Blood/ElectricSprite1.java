package Game.Animations.Blood;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class ElectricSprite1 extends Animation{
    
    public ElectricSprite1(LoadTextures textures) {
        super(textures.electric_sprite_1, 5, 90, 105);
        super.centerTheAnimation=true;
//        super.rotate=false;
    }
    
}

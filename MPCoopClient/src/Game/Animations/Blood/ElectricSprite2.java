package Game.Animations.Blood;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class ElectricSprite2 extends Animation{
    
    public ElectricSprite2(LoadTextures textures) {
        super(textures.electric_sprite_2, 7, 80, 60);
        super.centerTheAnimation=true;
//        super.rotate=false;
    }
    
}

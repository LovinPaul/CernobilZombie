package Game.Animations.Intro;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class GagarinStudio  extends Animation{
    
    public GagarinStudio(LoadTextures texture) {
        super(texture.gagarinStudio, 10, 823, 161);
        super.centerTheAnimation=false;
        super.rotate=false;
    }
    
}

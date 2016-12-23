package Game.Animations.Explosions;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class Debris  extends Animation{
    
    public Debris(LoadTextures textures) {
//        super(textures.debris, 7, 128, 128);
        super(textures.debris1, 5, 63, 60);
        super.centerTheAnimation = true;
    }
    
    public Debris(Debris toCopy){
        super(toCopy);
    }
    
}

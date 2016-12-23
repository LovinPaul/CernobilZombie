package Game.Animations.Explosions;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class Debris1 extends Animation{
    public Debris1(LoadTextures textures) {
        super(textures.debris1, 5, 63, 60);
        super.centerTheAnimation = true;
    }
    
    public Debris1(Debris1 toCopy){
        super(toCopy);
    }
}

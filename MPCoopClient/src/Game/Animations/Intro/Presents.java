package Game.Animations.Intro;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class Presents extends Animation{
    
    public Presents(LoadTextures texture) {
        super(texture.presents, 10, 266, 85);
        super.centerTheAnimation=false;
        super.rotate=false;
    }
    
}

package Game.Animations.Intro;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;

public class LoadingComplete extends Animation{
    
    public LoadingComplete(LoadTextures texture) {
        super(texture.loadingBarAnimation, 67, 500, 216);
//        super.centerTheAnimation = true;
        super.rotate=false;
    }
        
    
}

package Game.Animations.Intro;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;


public class ZombieIntro extends Animation{
    
    public ZombieIntro(LoadTextures texture) {
        super(texture.zombieIntro, 19, 500, 400);
        super.centerTheAnimation=false;
        super.rotate=false;
    }
    
}

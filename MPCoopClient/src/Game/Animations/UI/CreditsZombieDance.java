package Game.Animations.UI;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;
import java.util.ArrayList;

public class CreditsZombieDance extends Animation{
    
    public CreditsZombieDance(LoadTextures texture) {
        super(null, 213, 200, 200);
        super.images = new ArrayList<>();
        images.add(texture.credits1);
        images.add(texture.credits2);
        images.add(texture.credits3);
        super.rotate=false;
    }
    
}

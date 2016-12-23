
package Game.Animations.UI;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;
import java.awt.image.BufferedImage;

public class MainBackground extends Animation{
    
    public MainBackground(LoadTextures texture) {
        super(texture.mainBackground, 8, 801, 481);
        super.rotate=false;
    }
    
}

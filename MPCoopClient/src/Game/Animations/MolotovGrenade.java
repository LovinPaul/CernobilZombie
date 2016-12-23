
package Game.Animations;

import Main.Textures.LoadTextures;

public class MolotovGrenade extends Animation{
    
    public MolotovGrenade(LoadTextures textures) {
        super(textures.molotov, 3, 31, 36);
        super.centerTheAnimation = true;
        super.rotate=false;
    }
    
}

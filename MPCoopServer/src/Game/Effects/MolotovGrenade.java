
package Game.Effects;

import Main.LoadTextures;

public class MolotovGrenade extends Animation{
    
    public MolotovGrenade() {
        super(LoadTextures.molotov, 3, 31, 36);
        super.centerTheAnimation = true;
    }
    
}

package Game.Effects;

import Main.LoadTextures;

public class Explosion extends Animation{
    
    public Explosion() {
        super(LoadTextures.explosion, 4, 118, 118);
        super.centerTheAnimation = true;
    }
    
    
    
}

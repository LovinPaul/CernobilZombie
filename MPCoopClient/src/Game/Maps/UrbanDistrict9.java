package Game.Maps;

import Main.Textures.LoadTextures;

public class UrbanDistrict9 extends Map{

    public UrbanDistrict9(LoadTextures textures) {
        ID=1;
        super.addSpawnPoint(150, 300);
        
        super.setLayer0(textures.urbanDistrict91);
    }
    
}

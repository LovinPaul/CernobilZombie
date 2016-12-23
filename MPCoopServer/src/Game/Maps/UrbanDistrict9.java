package Game.Maps;

import Main.LoadTextures;

public class UrbanDistrict9 extends Map{

    public UrbanDistrict9() {
        ID=1;
        super.addSpawnPoint(150, 300);
        
        super.setLayer0(LoadTextures.urbanDistrict91);
    }
    
}

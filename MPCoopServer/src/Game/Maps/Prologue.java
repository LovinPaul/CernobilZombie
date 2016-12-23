package Game.Maps;

import Main.LoadTextures;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Prologue extends Map{

    public Prologue() {
        ID=0;
        //Spawn
        super.addSpawnPoint(300, 150);
        super.addSpawnPoint(850, 50);
        super.addSpawnPoint(445, 355);
//        super.addSpawnPoint(650, 400);
        super.addSpawnPoint(1450, 600);
        super.addSpawnPoint(2400, 255);
//        super.addSpawnPoint(2000, 1100);
        super.addSpawnPoint(1950, 1150);
        super.addSpawnPoint(2350, 2000);
        super.addSpawnPoint(2530, 2080);
        super.addSpawnPoint(2110, 820);
//        super.addSpawnPoint(2750, 270);
        super.addSpawnPoint(2680, 1000);
        super.addSpawnPoint(2800, 1375);
        super.addSpawnPoint(1450, 780);
//        super.addSpawnPoint(900, 2800);
        super.addSpawnPoint(950, 2500);
//        super.addSpawnPoint(400, 2900);
        super.addSpawnPoint(1510, 2200);
//        super.addSpawnPoint(1650, 2200);
        super.addSpawnPoint(1580, 2225);
//        super.addSpawnPoint(1669, 2260);
//        super.addSpawnPoint(1750, 2225);
        super.addSpawnPoint(2150, 3090);
        super.addSpawnPoint(1890, 3100);
        super.addSpawnPoint(3110, 220);
//        super.addSpawnPoint(3160, 420);
        super.addSpawnPoint(2950, 1320);
        super.addSpawnPoint(3100, 1500);
        
        
        //FinishArea
        super.finishArea = new Rectangle(2560, 2560, 450, 130);
//        
//        //Walls
        super.rectangles = new ArrayList<>();
        super.rectangles.add(new Rectangle(0, 0, 64, 320));
        super.rectangles.add(new Rectangle(0, 0, 64, 320));
        super.rectangles.add(new Rectangle(0, 0, 768, 64));
        super.rectangles.add(new Rectangle(0, 256, 768, 64));
        super.rectangles.add(new Rectangle(704, 0, 64, 128));
        super.rectangles.add(new Rectangle(704, 193, 64, 192));
        super.rectangles.add(new Rectangle(320, 256, 64, 256));
        super.rectangles.add(new Rectangle(320, 449, 449, 64));
        super.rectangles.add(new Rectangle(750, 448, 20, 272));
        super.rectangles.add(new Rectangle(750, 704, 804, 20));
        super.rectangles.add(new Rectangle(1536, 174, 20, 996));
        super.rectangles.add(new Rectangle(1536, 174, 832, 20));
        super.rectangles.add(new Rectangle(2432, 174, 402, 20));////
        super.rectangles.add(new Rectangle(2432, 174, 20, 356));
        super.rectangles.add(new Rectangle(3008, 0, 20, 512));
        super.rectangles.add(new Rectangle(2816, 174, 20, 420));
        super.rectangles.add(new Rectangle(2816, 576, 274, 20));
        super.rectangles.add(new Rectangle(2862, 576, 20, 1555));//466
        super.rectangles.add(new Rectangle(2222, 2112, 658, 20));
        super.rectangles.add(new Rectangle(2222, 1582, 20, 1554));
        super.rectangles.add(new Rectangle(896, 1582, 1344, 20));
        super.rectangles.add(new Rectangle(1472, 2135, 704, 20));
        super.rectangles.add(new Rectangle(1344, 1984, 20, 1216));
        super.rectangles.add(new Rectangle(896, 1582, 20, 868));
        super.rectangles.add(new Rectangle(302, 2432, 612, 20));
        super.rectangles.add(new Rectangle(302, 2432, 20, 594));
        super.rectangles.add(new Rectangle(302, 3007, 483, 20));
        super.rectangles.add(new Rectangle(768, 2542, 320, 20));
        super.rectangles.add(new Rectangle(768, 2542, 20, 484));
        super.rectangles.add(new Rectangle(302, 3008, 484, 20));
        super.rectangles.add(new Rectangle(960, 3118, 402, 20));
        super.rectangles.add(new Rectangle(3072, 576, 20, 704));
        super.rectangles.add(new Rectangle(2944, 1262, 146, 20));
        super.rectangles.add(new Rectangle(2862, 1367, 338, 20));
        super.rectangles.add(new Rectangle(3072, 466, 64, 28));
        super.rectangles.add(new Rectangle(1966, 174, 403, 229));
        super.rectangles.add(new Rectangle(1966, 512, 484, 20));
        super.rectangles.add(new Rectangle(1966, 512, 20, 530));
        super.rectangles.add(new Rectangle(1966, 1024, 850, 20));
        super.rectangles.add(new Rectangle(814, 1152, 740, 20));
        super.rectangles.add(new Rectangle(814, 1152, 20, 466));
        super.rectangles.add(new Rectangle(64, 1600, 768, 20));
        super.rectangles.add(new Rectangle(64, 1024, 20, 594));
        super.rectangles.add(new Rectangle(2496, 2496, 576, 64));
        super.rectangles.add(new Rectangle(3008, 2496, 64, 256));
        super.rectangles.add(new Rectangle(2496, 2688, 576, 64));
        super.rectangles.add(new Rectangle(2496, 2624, 64, 128));
        
        super.rectangles.add(new Rectangle(3182, 0, 20, 3200));
        super.rectangles.add(new Rectangle(0, 3182, 3200, 20));
        super.rectangles.add(new Rectangle(0, 0, 20, 3200));
        super.rectangles.add(new Rectangle(0, 0, 3200, 20));
        
        super.setLayer0(LoadTextures.prologue1);
//        super.setLayer2(LoadTextures.prologue2);
        
    }
    
    
    
    
    
}

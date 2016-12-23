package Game.Maps;

import Game.Maps.Environment.Crates.TNT;
import Game.Maps.Environment.Crates.WoodenCrate1;
import Game.Maps.Environment.Traps.MobileDoubleSpikes;
import Main.Textures.LoadTextures;
import java.awt.Rectangle;
import java.util.ArrayList;

public class cr8  extends Map{

    public cr8(LoadTextures textures) {
        ID=2;
        super.addSpawnPoint(650, 70);
        super.addSpawnPoint(1000, 70);
        super.addSpawnPoint(1200, 70);
        super.addSpawnPoint(1350, 220);
        super.addSpawnPoint(1400, 450);
        super.addSpawnPoint(950, 600);
        super.addSpawnPoint(1250, 800);
        super.addSpawnPoint(1330, 1430);
        super.addSpawnPoint(870, 1370);
        super.addSpawnPoint(750, 1000);
        super.addSpawnPoint(900, 800);
        super.addSpawnPoint(690, 1450);
        super.addSpawnPoint(670, 1300);
        super.addSpawnPoint(670, 1100);
        super.addSpawnPoint(470, 920);
        super.addSpawnPoint(420, 800);
        super.addSpawnPoint(550, 650);
        super.addSpawnPoint(800, 420);
        super.addSpawnPoint(100, 1150);
        
        super.rectangles = new ArrayList<>();
        super.rectangles.add(new Rectangle(0, 0, 243, 371));
        super.rectangles.add(new Rectangle(230, 0, 85, 315));
        super.rectangles.add(new Rectangle(300, 0, 80, 188));
        super.rectangles.add(new Rectangle(365, 0, 134, 115));
        super.rectangles.add(new Rectangle(780, 0, 115, 190));
        super.rectangles.add(new Rectangle(1280, 0, 190, 188));
        super.rectangles.add(new Rectangle(1420, 170, 180, 210));//
        super.rectangles.add(new Rectangle(1475, 365, 125, 147));
        super.rectangles.add(new Rectangle(485, 0, 820, 30));
        super.rectangles.add(new Rectangle(1570, 490, 30, 1110));
        super.rectangles.add(new Rectangle(1410, 1406, 124, 194));
        super.rectangles.add(new Rectangle(1223, 1545, 377, 55));
        super.rectangles.add(new Rectangle(1283, 1475, 251, 125));
        super.rectangles.add(new Rectangle(1100, 1480, 115, 45));
        super.rectangles.add(new Rectangle(1155, 1410, 60, 115));
        super.rectangles.add(new Rectangle(1155, 1410, 115, 50));
        super.rectangles.add(new Rectangle(0, 1570, 1250, 30));
        super.rectangles.add(new Rectangle(0, 1150, 62, 450));
        super.rectangles.add(new Rectangle(0, 1215, 125, 125));
        super.rectangles.add(new Rectangle(0, 1470, 255, 60));
        super.rectangles.add(new Rectangle(61, 1500, 132, 100));
        super.rectangles.add(new Rectangle(142, 1357, 37, 38));
        super.rectangles.add(new Rectangle(0, 320, 30, 896));
        super.rectangles.add(new Rectangle(1158, 456, 120, 120));
        super.rectangles.add(new Rectangle(767, 319, 136, 65));
        super.rectangles.add(new Rectangle(833, 319, 70, 447));
        super.rectangles.add(new Rectangle(641, 704, 509, 62));
        super.rectangles.add(new Rectangle(780, 638, 237, 128));
        super.rectangles.add(new Rectangle(780, 587, 123, 90));
        super.rectangles.add(new Rectangle(524, 579, 179, 50));
        super.rectangles.add(new Rectangle(590, 579, 113, 112));
        super.rectangles.add(new Rectangle(641, 579, 62, 187));
        super.rectangles.add(new Rectangle(641, 720, 123, 167));
        super.rectangles.add(new Rectangle(324, 835, 441, 52));
        super.rectangles.add(new Rectangle(261, 702, 120, 120));
        super.rectangles.add(new Rectangle(323, 702, 58, 185));
        super.rectangles.add(new Rectangle(970, 704, 180, 188));
        super.rectangles.add(new Rectangle(1090, 765, 123, 119));
        super.rectangles.add(new Rectangle(1149, 843, 120, 41));
        super.rectangles.add(new Rectangle(580, 866, 124, 160));
        super.rectangles.add(new Rectangle(510, 866, 90, 95));
        super.rectangles.add(new Rectangle(643, 1025, 188, 62));
        super.rectangles.add(new Rectangle(642, 965, 62, 122));
        super.rectangles.add(new Rectangle(715, 1025, 115, 502));
        super.rectangles.add(new Rectangle(643, 1346, 187, 59));
        super.rectangles.add(new Rectangle(715, 1290, 172, 45));
        super.rectangles.add(new Rectangle(325, 1092, 120, 120));
        super.rectangles.add(new Rectangle(1089, 1090, 190, 125));
        super.rectangles.add(new Rectangle(1027, 1090, 130, 60));
        
//        super.addEnvironment(new TNT((byte)0, 350, 550));
        super.addEnvironment(new TNT((byte)1, 150, 800, textures));
//        super.addEnvironment(new TNT((byte)2, 420, 1350));
        super.addEnvironment(new TNT((byte)3, 1400, 1250, textures));
//        super.addEnvironment(new TNT((byte)4, 1400, 700));
        super.addEnvironment(new TNT((byte)5, 1100, 250, textures));
//        super.addEnvironment(new TNT((byte)6, 500, 200));
        
        super.addEnvironment(new WoodenCrate1((byte)0, 350, 550, textures));
//        super.addEnvironment(new WoodenCrate1((byte)1, 150, 800));
        super.addEnvironment(new WoodenCrate1((byte)2, 420, 1350, textures));
//        super.addEnvironment(new WoodenCrate1((byte)3, 1400, 1250));
        super.addEnvironment(new WoodenCrate1((byte)4, 1400, 700, textures));
//        super.addEnvironment(new WoodenCrate1((byte)5, 1100, 250));
        super.addEnvironment(new WoodenCrate1((byte)6, 500, 200, textures));
        
        super.addEnvironment(new WoodenCrate1((byte)7, 800, 210, textures));
        super.addEnvironment(new WoodenCrate1((byte)8, 800, 260, textures));
        super.addEnvironment(new WoodenCrate1((byte)9, 800, 300, textures));
        
        super.addEnvironment(new MobileDoubleSpikes((byte)10, 1150, 930, textures));
        
        super.setLayer0(textures.cr81);
        super.setLayer2(textures.cr82);
    }
    
    
    
}

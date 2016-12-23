package Game.Maps;

import Game.Maps.Environment.Crates.WoodenCrate1;
import Main.LoadTextures;
import java.awt.Rectangle;
import java.util.ArrayList;

public class In4 extends Map{

    public In4() {
        ID=3;
        super.addSpawnPoint(600, 700);
        super.addSpawnPoint(615, 420);
        super.addSpawnPoint(800, 300);
        super.addSpawnPoint(610, 1050);
        super.addSpawnPoint(830, 830);
        super.addSpawnPoint(1000, 1000);
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
//        super.addSpawnPoint(, );
        


        super.rectangles = new ArrayList<>();
        super.rectangles.add(new Rectangle(0, 0, 1600, 60));
        super.rectangles.add(new Rectangle(0, 0, 30, 1600));
        super.rectangles.add(new Rectangle(0, 1535, 1600, 65));
        super.rectangles.add(new Rectangle(1535, 525, 65, 1075));
        super.rectangles.add(new Rectangle(1580, 0, 20, 610));
        super.rectangles.add(new Rectangle(1087, 0, 65, 320));
        super.rectangles.add(new Rectangle(1087, 385, 65, 190));
        super.rectangles.add(new Rectangle(1087, 511, 512, 65));
        super.rectangles.add(new Rectangle(512, 128, 64, 448));
        super.rectangles.add(new Rectangle(448, 512, 128, 64));
        super.rectangles.add(new Rectangle(0, 512, 384, 64));
        super.rectangles.add(new Rectangle(66, 450, 252, 100));
        super.rectangles.add(new Rectangle(0, 1088, 576, 65));
        super.rectangles.add(new Rectangle(512, 1088, 65, 192));
        super.rectangles.add(new Rectangle(512, 1408, 65, 192));
        super.rectangles.add(new Rectangle(1088, 1152, 30, 450));
        super.rectangles.add(new Rectangle(1216, 1088, 385, 30));
        super.rectangles.add(new Rectangle(1473, 1088, 127, 260));
        super.rectangles.add(new Rectangle(1486, 1421, 114, 120));
        super.rectangles.add(new Rectangle(1284, 1476, 316, 124));
        super.rectangles.add(new Rectangle(1088, 1482, 115, 118));
        
        environment=new ArrayList<>();
        
        super.addEnvironment(new WoodenCrate1((byte)0, 475+22, 575+23));
        super.addEnvironment(new WoodenCrate1((byte)1, 475+22, 620+23));
        super.addEnvironment(new WoodenCrate1((byte)2, 580+22, 790+23));
        
        super.addEnvironment(new WoodenCrate1((byte)3, 70+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)4, 70+22, 630+23));
        super.addEnvironment(new WoodenCrate1((byte)5, 70+22, 675+23));
        super.addEnvironment(new WoodenCrate1((byte)6, 70+22, 720+23));
        super.addEnvironment(new WoodenCrate1((byte)7, 70+22, 765+23));
        super.addEnvironment(new WoodenCrate1((byte)8, 70+22, 810+23));
        super.addEnvironment(new WoodenCrate1((byte)9, 70+22, 855+23));
        super.addEnvironment(new WoodenCrate1((byte)10, 70+22, 900+23));
        super.addEnvironment(new WoodenCrate1((byte)11, 70+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)12, 70+22, 945+23));
        super.addEnvironment(new WoodenCrate1((byte)13, 70+22, 990+23));
        super.addEnvironment(new WoodenCrate1((byte)14, 70+22, 1035+23));
        
        super.addEnvironment(new WoodenCrate1((byte)15, 115+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)16, 115+22, 630+23));
        super.addEnvironment(new WoodenCrate1((byte)17, 115+22, 675+23));
        super.addEnvironment(new WoodenCrate1((byte)18, 115+22, 720+23));
        super.addEnvironment(new WoodenCrate1((byte)19, 115+22, 765+23));
        super.addEnvironment(new WoodenCrate1((byte)20, 115+22, 810+23));
        super.addEnvironment(new WoodenCrate1((byte)21, 115+22, 855+23));
        super.addEnvironment(new WoodenCrate1((byte)22, 115+22, 900+23));
        super.addEnvironment(new WoodenCrate1((byte)23, 115+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)24, 115+22, 945+23));
        super.addEnvironment(new WoodenCrate1((byte)25, 115+22, 990+23));
        super.addEnvironment(new WoodenCrate1((byte)26, 115+22, 1035+23));

        
        super.addEnvironment(new WoodenCrate1((byte)27, 1491+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)28, 1491+22, 630+23));
        super.addEnvironment(new WoodenCrate1((byte)29, 1491+22, 675+23));
        super.addEnvironment(new WoodenCrate1((byte)30, 1491+22, 720+23));
        super.addEnvironment(new WoodenCrate1((byte)31, 1491+22, 765+23));
        super.addEnvironment(new WoodenCrate1((byte)32, 1491+22, 810+23));
        super.addEnvironment(new WoodenCrate1((byte)33, 1491+22, 855+23));
        super.addEnvironment(new WoodenCrate1((byte)34, 1491+22, 900+23));
        super.addEnvironment(new WoodenCrate1((byte)35, 1491+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)36, 1491+22, 945+23));
        super.addEnvironment(new WoodenCrate1((byte)37, 1491+22, 990+23));
        super.addEnvironment(new WoodenCrate1((byte)38, 1491+22, 1035+23));

        super.addEnvironment(new WoodenCrate1((byte)39, 1447+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)40, 1447+22, 630+23));
        super.addEnvironment(new WoodenCrate1((byte)41, 1447+22, 675+23));
        super.addEnvironment(new WoodenCrate1((byte)42, 1447+22, 720+23));
        super.addEnvironment(new WoodenCrate1((byte)43, 1447+22, 765+23));
        super.addEnvironment(new WoodenCrate1((byte)44, 1447+22, 810+23));
        super.addEnvironment(new WoodenCrate1((byte)45, 1447+22, 855+23));
        super.addEnvironment(new WoodenCrate1((byte)46, 1447+22, 900+23));
        super.addEnvironment(new WoodenCrate1((byte)47, 1447+22, 585+23));
        super.addEnvironment(new WoodenCrate1((byte)48, 1447+22, 945+23));
        super.addEnvironment(new WoodenCrate1((byte)49, 1447+22, 990+23));
        super.addEnvironment(new WoodenCrate1((byte)50, 1447+22, 1035+23));
        
        super.addEnvironment(new WoodenCrate1((byte)51, 650+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)52, 694+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)53, 738+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)54, 782+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)55, 826+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)56, 870+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)57, 914+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)58, 958+22, 60+23));
        super.addEnvironment(new WoodenCrate1((byte)59, 1002+22, 60+23));
        
        super.addEnvironment(new WoodenCrate1((byte)60, 694+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)61, 738+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)62, 782+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)63, 826+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)64, 870+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)65, 914+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)66, 958+22, 105+23));
        super.addEnvironment(new WoodenCrate1((byte)67, 1002+22, 105+23));
        
        
        super.addEnvironment(new WoodenCrate1((byte)68, 605+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)69, 650+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)70, 694+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)71, 738+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)72, 782+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)73, 826+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)74, 870+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)75, 914+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)76, 958+22, 1495+23));
        super.addEnvironment(new WoodenCrate1((byte)77, 1002+22, 1495+23));
        
        super.addEnvironment(new WoodenCrate1((byte)78, 255, 740));
        super.addEnvironment(new WoodenCrate1((byte)79, 370, 980));
        super.addEnvironment(new WoodenCrate1((byte)80, 780, 1025));
        super.addEnvironment(new WoodenCrate1((byte)81, 1015, 1230));
        super.addEnvironment(new WoodenCrate1((byte)82, 870, 1325));
        super.addEnvironment(new WoodenCrate1((byte)83, 720, 1400));
        super.addEnvironment(new WoodenCrate1((byte)84, 350, 1230));
        super.addEnvironment(new WoodenCrate1((byte)85, 110, 1460));
        super.addEnvironment(new WoodenCrate1((byte)86, 1340, 1165));
        
        super.addEnvironment(new WoodenCrate1((byte)87, 1140, 950));
        super.addEnvironment(new WoodenCrate1((byte)88, 920, 810));
        super.addEnvironment(new WoodenCrate1((byte)89, 1030, 585));
        super.addEnvironment(new WoodenCrate1((byte)90, 1270, 740));
        super.addEnvironment(new WoodenCrate1((byte)91, 790, 410));
        super.addEnvironment(new WoodenCrate1((byte)92, 1280, 430));
        super.addEnvironment(new WoodenCrate1((byte)93, 430, 180));
        
        
        
        
        super.addEnvironment(new WoodenCrate1((byte)94, 645, 280));
        super.addEnvironment(new WoodenCrate1((byte)95, 1490, 115));
        super.addEnvironment(new WoodenCrate1((byte)96, 85, 400));
        super.addEnvironment(new WoodenCrate1((byte)97, 1020, 245));
        //650 70
        //475 575
        
        
        super.setLayer0(LoadTextures.in41);
        super.setLayer2(LoadTextures.in42);

    }
    
    
    
    
    
}

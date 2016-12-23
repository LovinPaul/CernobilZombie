package Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class LoadTextures {
    
    //UI
    public static BufferedImage cur347;
    public static BufferedImage cur347_lock;
    public static BufferedImage x_icon;
    //Items
    public static BufferedImage hpCrate;
    public static BufferedImage machineClip;
    public static BufferedImage nineMMClip;
    //Enviroment
    public static BufferedImage bigTNTCrate;
    public static BufferedImage woodenCrate1;
    public static BufferedImage spike;
    public static BufferedImage doubleSpike;
    
    //Effects
    public static BufferedImage explosion;
    public static BufferedImage explosion1;
    public static BufferedImage presence;
    public static BufferedImage blood_sprite_1;
    public static BufferedImage blood_sprite_2;
    public static BufferedImage blood_sprite_3;
    public static BufferedImage blood_sprite_4;
    public static BufferedImage M134_muzzle_flash;
    public static BufferedImage gun_muzzle_flash;
    
    
    //Map
    public static BufferedImage prologue1;
    public static BufferedImage prologue2;
    public static BufferedImage urbanDistrict91;
    public static BufferedImage urbanDistrict92;
    public static BufferedImage cr81;
    public static BufferedImage cr82;
    public static BufferedImage in41;
    public static BufferedImage in42;
    
    public static BufferedImage hitman1_hold;
    public static BufferedImage hitman1_stand;
    public static BufferedImage hitman1_left_hand;
    public static BufferedImage hitman1_right_hand;
    
    public static BufferedImage zombie1_hold;
    public static BufferedImage zombie1_stand;
    public static BufferedImage zombie1_left_hand;
    public static BufferedImage zombie1_right_hand;
    
    public static BufferedImage robot1_hold;
    public static BufferedImage robot1_stand;
    public static BufferedImage robot1_left_hand;
    public static BufferedImage robot1_right_hand;
    
    public static BufferedImage molotov;
    public static BufferedImage nineMM;
    public static BufferedImage machine;
    public static BufferedImage silencer;
    public static BufferedImage M134;
    
    public static void init() {
        try {
            
            cur347 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/pointer/cur347.png"));
            cur347_lock = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/pointer/cur347_lock.png"));
            
            hpCrate = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Items/hpCrate.png"));
            machineClip = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Items/machineClip.png"));
            nineMMClip = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Items/9mmClip.png"));
            
            bigTNTCrate = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Crate/ExploadingCrate/TNTCrate/bigTNTCrate.png"));
            woodenCrate1 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Crate/WoodenCrate1/woodenCrate1.png"));
            spike = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Traps/Spike/spike.png"));
            doubleSpike = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Traps/Spike/doubleSpike.png"));
            
            explosion = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Explosion/expl.png"));
            explosion1 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Explosion/expl1.png"));
            presence = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Visuals/presence.png"));
            blood_sprite_1 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Blood/blood_sprite_1.png"));
            blood_sprite_2 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Blood/blood_sprite_2.png"));
            blood_sprite_3 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Blood/blood_sprite_3.png"));
            blood_sprite_4 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Blood/blood_sprite_4.png"));
            M134_muzzle_flash = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Flash/M134_minigun_flash.png"));
            gun_muzzle_flash = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Effects/Flash/gun_flash.png"));
            
            prologue1 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Prologue/layer1.png"));
            prologue2 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Prologue/layer2.png"));
            urbanDistrict91  = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/UrbanDistrict9/layer1.png"));
            urbanDistrict91  = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/UrbanDistrict9/layer1.png"));
            cr81  = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/cr8/layer1.png"));
            cr82  = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/cr8/layer2.png"));
            in41  = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/in4/layer1.png"));
            in42  = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/in4/layer2.png"));
            
            hitman1_hold = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Hitman 1/hitman1_hold.png"));
            hitman1_stand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Hitman 1/hitman1_stand.png"));
            hitman1_left_hand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Hitman 1/hitman1_left_hand.png"));
            hitman1_right_hand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Hitman 1/hitman1_right_hand.png"));
            
            zombie1_hold = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Zombie 1/zombie1_hold.png"));
            zombie1_stand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Zombie 1/zombie1_stand.png"));
            zombie1_left_hand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Zombie 1/zombie1_left_hand.png"));
            zombie1_right_hand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Zombie 1/zombie1_right_hand.png"));
            
            robot1_hold = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Robot 1/robot1_hold.png"));
            robot1_stand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Robot 1/robot1_stand.png"));
            robot1_left_hand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Robot 1/robot1_left_hand.png"));
            robot1_right_hand = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Actors/Robot 1/robot1_right_hand.png"));
            
            molotov = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Wepons/molotov.png"));
            nineMM = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Wepons/nineMM.png"));
            machine = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Wepons/machine.png"));
            silencer = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Wepons/silencer.png"));
            M134 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Wepons/M134.png"));
            
        } catch (IOException ex) {
            System.out.println("LoadTextures :" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}

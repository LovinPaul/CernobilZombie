package Main.Textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public abstract class LoadTextures {
    
    
    
    //UI
    public BufferedImage cur347;
    public BufferedImage cur347_lock;
    public BufferedImage curHand;
    //Credits
    public BufferedImage credits1;
    public BufferedImage credits2;
    public BufferedImage credits3;
    //
    public BufferedImage normalText;
    public BufferedImage hoverText;
    public BufferedImage normalButton;
    public BufferedImage hoverButton;
    public BufferedImage bloodBarOn;
    public BufferedImage bloodBarOff;
    public BufferedImage warning32x32;
    //Items
    public BufferedImage hpCrate;
    public BufferedImage machineClip;
    public BufferedImage nineMMClip;
    //
    public BufferedImage bigTNTCrate;
    public BufferedImage woodenCrate1;
    public BufferedImage doubleSpike;
    //
    public  BufferedImage hpBackground;
    public  BufferedImage hpBar;
    public  BufferedImage hpBarLeft;
    public  BufferedImage hpBarRight;
    public  BufferedImage hpHeart;
    public  BufferedImage clipBackGround;
    public  BufferedImage clipBullets;
    //
    public  BufferedImage zombieUI;
    public  BufferedImage hitmanUI;
    public  BufferedImage robotUI;
    ////
    public  BufferedImage molotovUI;
    public  BufferedImage nineMMUI;
    public  BufferedImage machineUI;
    public  BufferedImage M134UI;
//    public  BufferedImage deploy;
//    public  BufferedImage deployBackground;
    //
//    public  BufferedImage redeploy;
//    public  BufferedImage options;
//    public  BufferedImage quit;
//    ////
//    public  BufferedImage yes;
//    public  BufferedImage no;
    ////
    public  BufferedImage loadingBarAnimation;
    public  BufferedImage mainBackground;
    public  BufferedImage gagarinStudio;
    public  BufferedImage presents;
    public  BufferedImage zombieIntro;
//    public  BufferedImage singlePlayer;
//    public  BufferedImage multiPlayer;
//    public  BufferedImage coop;
//    public  BufferedImage tdm;
//    public  BufferedImage back;
//    public  BufferedImage audio;
//    public  BufferedImage video;
//    public  BufferedImage controls;
//    public  BufferedImage keyboard;
    public  BufferedImage wasd;
    public  BufferedImage upArrow;
    public  BufferedImage downArrow;
    public  BufferedImage leftArrow;
    public  BufferedImage rightArrow;
//    public  BufferedImage mouse;
//    public  BufferedImage player;
//    public  BufferedImage network;
//    public  BufferedImage save;
//    public  BufferedImage easy;
//    public  BufferedImage medium;
//    public  BufferedImage hard;
    
    //Effects
    public  BufferedImage explosion;
    public  BufferedImage explosion1;
    public  BufferedImage debris;
    public  BufferedImage debris1;
    public  BufferedImage presence;
    public  BufferedImage blood_sprite_1;
    public  BufferedImage blood_sprite_2;
    public  BufferedImage blood_sprite_3;
    public  BufferedImage blood_sprite_4;
    public  BufferedImage blood_sprite_5;
    public  BufferedImage blood_sprite_6;
    public  BufferedImage electric_sprite_1;
    public  BufferedImage electric_sprite_2;
    public  BufferedImage M134_muzzle_flash;
    public  BufferedImage gun_muzzle_flash;
    
    
    //Map
    public  BufferedImage prologue1;
    public  BufferedImage prologue2;
    public  BufferedImage urbanDistrict91;
    public  BufferedImage urbanDistrict92;
    public  BufferedImage cr81;
    public  BufferedImage cr82;
    public  BufferedImage in41;
    public  BufferedImage in42;
    
    public  BufferedImage hitman1_hold;
    public  BufferedImage hitman1_stand;
    public  BufferedImage hitman1_left_hand;
    public  BufferedImage hitman1_right_hand;
    
    public  BufferedImage zombie1_hold;
    public  BufferedImage zombie1_stand;
    public  BufferedImage zombie1_left_hand;
    public  BufferedImage zombie1_right_hand;
    
    public BufferedImage robot1_hold;
    public BufferedImage robot1_stand;
    public BufferedImage robot1_left_hand;
    public BufferedImage robot1_right_hand;
    
    public  BufferedImage molotov;
    public  BufferedImage nineMM;
    public  BufferedImage machine;
    public  BufferedImage silencer;
    public  BufferedImage M134;
    
    protected int textureLoaded;
    protected int textureNr=71;
    
    protected boolean loadingComplete;
    
    public abstract void loadTextures(int arg);
    
    public float loadingPercent(){
        return (float)textureLoaded/(float)textureNr;
    }
    public boolean isLoadingComplete(){
        return loadingComplete;
    }
//    public void loadMainMenuTextures(){
//        System.out.println("Load Main Menu Textures.");
//        textureLoaded=0;
//        textureNr=25;
//        try {
//            curHand = ImageIO.read(this.getClass().getResource("/Data/pointer/curHand.png"));textureLoaded++;
//            loadingBarAnimation = ImageIO.read(this.getClass().getResource("/Data/UI/LoadingScreen/loadingBarAnimation.png"));textureLoaded++;
//            mainBackground = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/mainBackground.png"));textureLoaded++;
//            
////            options = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/options.png"));textureLoaded++;
////            quit = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/quit.png"));textureLoaded++;
////            
////            yes = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/yes.png"));textureLoaded++;
////            no = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/no.png"));textureLoaded++;
//            
//            gagarinStudio = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/gagarinStudio.png"));textureLoaded++;
//            presents = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/presents.png"));textureLoaded++;
//            zombieIntro = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/zombie.png"));textureLoaded++;
////            singlePlayer = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/singlePlayer.png"));textureLoaded++;
////            multiPlayer = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/multiPlayer.png"));textureLoaded++;
////            coop = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/coop.png"));textureLoaded++;
////            tdm = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/tdm.png"));textureLoaded++;
////            back = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/back.png"));textureLoaded++;
////            
////            audio = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/audio.png"));textureLoaded++;
////            video = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/video.png"));textureLoaded++;
////            controls = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/controls.png"));textureLoaded++;
////            player = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/player.png"));textureLoaded++;
////            network = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/network.png"));textureLoaded++;
////            save = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/save.png"));textureLoaded++;
////            easy = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/easy.png"));textureLoaded++;
////            medium = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/medium.png"));textureLoaded++;
////            hard = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/hard.png"));textureLoaded++;
//            blood_sprite_5 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_5.png"));textureLoaded++;
//            System.out.println(textureLoaded + " textures loaded successfully.");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//    
//    public void loadLevelTextures(int map){
//        try {
//            cur347 = ImageIO.read(this.getClass().getResource("/Data/pointer/cur347.png"));textureLoaded++;
//            cur347_lock = ImageIO.read(this.getClass().getResource("/Data/pointer/cur347_lock.png"));textureLoaded++;
//            curHand = ImageIO.read(this.getClass().getResource("/Data/pointer/curHand.png"));textureLoaded++;
//            
//            hpCrate = ImageIO.read(this.getClass().getResource("/Data/Items/hpCrate.png"));textureLoaded++;
//            hpBackground = ImageIO.read(this.getClass().getResource("/Data/UI/hpBackground.png"));textureLoaded++;
//            hpBar = ImageIO.read(this.getClass().getResource("/Data/UI/hpBar.png"));textureLoaded++;
//            hpBarLeft = ImageIO.read(this.getClass().getResource("/Data/UI/hpBarLeft.png"));textureLoaded++;
//            hpBarRight = ImageIO.read(this.getClass().getResource("/Data/UI/hpBarRight.png"));textureLoaded++;
//            hpHeart = ImageIO.read(this.getClass().getResource("/Data/UI/hpHeart.png"));textureLoaded++;
//            clipBackGround = ImageIO.read(this.getClass().getResource("/Data/UI/clipBackGround.png"));textureLoaded++;
//            clipBullets = ImageIO.read(this.getClass().getResource("/Data/UI/clipBullets.png"));textureLoaded++;
//            
//            zombieUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/zombie.png"));textureLoaded++;
//            hitmanUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/hitman.png"));textureLoaded++;
//            molotovUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/molotov.png"));textureLoaded++;
//            nineMMUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/nineMM.png"));textureLoaded++;
//            machineUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/machine.png"));textureLoaded++;
//            M134UI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/m134.png"));textureLoaded++;
////            deploy = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/deploy.png"));textureLoaded++;
////            redeploy = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/redeploy.png"));textureLoaded++;
////            options = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/options.png"));textureLoaded++;
////            quit = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/quit.png"));textureLoaded++;
////            
////            yes = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/yes.png"));textureLoaded++;
////            no = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/no.png"));textureLoaded++;
////            
////            audio = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/audio.png"));textureLoaded++;
////            video = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/video.png"));textureLoaded++;
////            controls = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/controls.png"));textureLoaded++;
////            player = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/player.png"));textureLoaded++;
////            network = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/network.png"));textureLoaded++;
////            save = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/save.png"));textureLoaded++;
//            
//            explosion = ImageIO.read(this.getClass().getResource("/Data/Effects/Explosion/expl.png"));textureLoaded++;
//            presence = ImageIO.read(this.getClass().getResource("/Data/Effects/Visuals/presence.png"));textureLoaded++;
//            blood_sprite_1 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_1.png"));textureLoaded++;
//            blood_sprite_2 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_2.png"));textureLoaded++;
//            blood_sprite_3 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_3.png"));textureLoaded++;
//            blood_sprite_4 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_4.png"));textureLoaded++;
//            blood_sprite_5 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_6.png"));textureLoaded++;
//            M134_muzzle_flash = ImageIO.read(this.getClass().getResource("/Data/Effects/Flash/M134_minigun_flash.png"));textureLoaded++;
//            gun_muzzle_flash = ImageIO.read(this.getClass().getResource("/Data/Effects/Flash/gun_flash.png"));textureLoaded++;
//            
//            hitman1_hold = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_hold.png"));textureLoaded++;
//            hitman1_stand = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_stand.png"));textureLoaded++;
//            hitman1_left_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_left_hand.png"));textureLoaded++;
//            hitman1_right_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_right_hand.png"));textureLoaded++;
//            
//            zombie1_hold = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_hold.png"));textureLoaded++;
//            zombie1_stand = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_stand.png"));textureLoaded++;
//            zombie1_left_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_left_hand.png"));textureLoaded++;
//            zombie1_right_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_right_hand.png"));textureLoaded++;
//
//            molotov = ImageIO.read(this.getClass().getResource("/Data/Wepons/molotov.png"));textureLoaded++;
//            nineMM = ImageIO.read(this.getClass().getResource("/Data/Wepons/nineMM.png"));textureLoaded++;
//            machine = ImageIO.read(this.getClass().getResource("/Data/Wepons/machine.png"));textureLoaded++;
//            silencer = ImageIO.read(this.getClass().getResource("/Data/Wepons/silencer.png"));textureLoaded++;
//            M134 = ImageIO.read(this.getClass().getResource("/Data/Wepons/M134.png"));textureLoaded++;
//            
//            switch(map){
//                case 0:
//                    prologue1 = ImageIO.read(this.getClass().getResource("/Data/Map/Prologue/layer1.png"));textureLoaded++;
//                    prologue2 = ImageIO.read(this.getClass().getResource("/Data/Map/Prologue/layer2.png"));textureLoaded++;                    
//                    break;
//                case 1:
//                    urbanDistrict91  = ImageIO.read(this.getClass().getResource("/Data/Map/UrbanDistrict9/layer1.png"));textureLoaded++;
//                    urbanDistrict91  = ImageIO.read(this.getClass().getResource("/Data/Map/UrbanDistrict9/layer1.png"));textureLoaded++;                    
//                    break;
//                case 2:
//                    cr81  = ImageIO.read(this.getClass().getResource("/Data/Map/cr8/layer1.png"));textureLoaded++;
//                    cr82  = ImageIO.read(this.getClass().getResource("/Data/Map/cr8/layer2.png"));textureLoaded++;                    
//                    break;
//            }
//            
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
    
}

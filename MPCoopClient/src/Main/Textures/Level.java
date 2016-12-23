package Main.Textures;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Level extends LoadTextures{

    @Override
    public void loadTextures(int arg) {
        textureLoaded=0;
        textureNr=68;//64
        try {
            
            cur347 = ImageIO.read(this.getClass().getResource("/Data/pointer/cur347.png"));textureLoaded++;
            cur347_lock = ImageIO.read(this.getClass().getResource("/Data/pointer/cur347_lock.png"));textureLoaded++;
            curHand = ImageIO.read(this.getClass().getResource("/Data/pointer/curHand.png"));textureLoaded++;
            
            hpCrate = ImageIO.read(this.getClass().getResource("/Data/Items/hpCrate.png"));textureLoaded++;
            machineClip = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Items/machineClip.png"));textureLoaded++;
            nineMMClip = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Items/9mmClip.png"));textureLoaded++;
            
            bigTNTCrate = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Crate/ExploadingCrate/TNTCrate/bigTNTCrate.png"));textureLoaded++;
            woodenCrate1 = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Crate/WoodenCrate1/woodenCrate1.png"));textureLoaded++;
            doubleSpike = ImageIO.read(LoadTextures.class.getClass().getResource("/Data/Map/Environment/Traps/Spike/doubleSpike.png"));textureLoaded++;
            
            hpBackground = ImageIO.read(this.getClass().getResource("/Data/UI/hpBackground.png"));textureLoaded++;
            hpBar = ImageIO.read(this.getClass().getResource("/Data/UI/hpBar.png"));textureLoaded++;
            hpBarLeft = ImageIO.read(this.getClass().getResource("/Data/UI/hpBarLeft.png"));textureLoaded++;
            hpBarRight = ImageIO.read(this.getClass().getResource("/Data/UI/hpBarRight.png"));textureLoaded++;
            hpHeart = ImageIO.read(this.getClass().getResource("/Data/UI/hpHeart.png"));textureLoaded++;
            clipBackGround = ImageIO.read(this.getClass().getResource("/Data/UI/clipBackGround.png"));textureLoaded++;
            clipBullets = ImageIO.read(this.getClass().getResource("/Data/UI/clipBullets.png"));textureLoaded++;
            
            zombieUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/zombie.png"));textureLoaded++;
            hitmanUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/hitman.png"));textureLoaded++;
            robotUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/robot.png"));textureLoaded++;
            molotovUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/molotov.png"));textureLoaded++;
            nineMMUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/nineMM.png"));textureLoaded++;
            machineUI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/machine.png"));textureLoaded++;
            M134UI = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/m134.png"));textureLoaded++;
//            deploy = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/deploy.png"));textureLoaded++;
//            deployBackground = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/deployBackground.png"));textureLoaded++;
//            redeploy = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/redeploy.png"));textureLoaded++;
//            options = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/options.png"));textureLoaded++;
//            back = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/back.png"));textureLoaded++;
//            quit = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/quit.png"));textureLoaded++;
            
            normalText = ImageIO.read(this.getClass().getResource("/Data/UI/Button/normalTextUI.png"));textureLoaded++;
            hoverText = ImageIO.read(this.getClass().getResource("/Data/UI/Button/hoverTextUI.png"));textureLoaded++;
            normalButton = ImageIO.read(this.getClass().getResource("/Data/UI/Button/normalButton.png"));textureLoaded++;
            hoverButton = ImageIO.read(this.getClass().getResource("/Data/UI/Button/hoverButton.png"));textureLoaded++;
            
            bloodBarOn = ImageIO.read(this.getClass().getResource("/Data/UI/Bars/bloodBarOn.png"));textureLoaded++;
            bloodBarOff = ImageIO.read(this.getClass().getResource("/Data/UI/Bars/bloodBarOff.png"));textureLoaded++;
            
            warning32x32 = ImageIO.read(this.getClass().getResource("/Data/UI/Warnings/warning32x32.png"));textureLoaded++;
            
//            yes = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/yes.png"));textureLoaded++;
//            no = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/no.png"));textureLoaded++;
//            
//            audio = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/audio.png"));textureLoaded++;
//            video = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/video.png"));textureLoaded++;
//            controls = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/controls.png"));textureLoaded++;
//            player = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/player.png"));textureLoaded++;
//            network = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/network.png"));textureLoaded++;
//            save = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/save.png"));textureLoaded++;
            wasd = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/wasd.png"));textureLoaded++;
            upArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/upArrow.png"));textureLoaded++;
            downArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/downArrow.png"));textureLoaded++;
            leftArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/leftArrow.png"));textureLoaded++;
            rightArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/rightArrow.png"));textureLoaded++;
            
            explosion = ImageIO.read(this.getClass().getResource("/Data/Effects/Explosion/expl.png"));textureLoaded++;
            explosion1 = ImageIO.read(this.getClass().getResource("/Data/Effects/Explosion/expl1.png"));textureLoaded++;
            debris = ImageIO.read(this.getClass().getResource("/Data/Effects/Explosion/debris.png"));textureLoaded++;
            debris1 = ImageIO.read(this.getClass().getResource("/Data/Effects/Explosion/debris1.png"));textureLoaded++;
            presence = ImageIO.read(this.getClass().getResource("/Data/Effects/Visuals/presence.png"));textureLoaded++;
            blood_sprite_1 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_1.png"));textureLoaded++;
            blood_sprite_2 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_2.png"));textureLoaded++;
            blood_sprite_3 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_3.png"));textureLoaded++;
            blood_sprite_4 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_4.png"));textureLoaded++;
            blood_sprite_5 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_5.png"));textureLoaded++;
            electric_sprite_1 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/electric_sprite_1.png"));textureLoaded++;
            electric_sprite_2 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/electric_sprite_2.png"));textureLoaded++;
            M134_muzzle_flash = ImageIO.read(this.getClass().getResource("/Data/Effects/Flash/M134_minigun_flash.png"));textureLoaded++;
            gun_muzzle_flash = ImageIO.read(this.getClass().getResource("/Data/Effects/Flash/gun_flash.png"));textureLoaded++;
            
            hitman1_hold = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_hold.png"));textureLoaded++;
            hitman1_stand = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_stand.png"));textureLoaded++;
            hitman1_left_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_left_hand.png"));textureLoaded++;
            hitman1_right_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Hitman 1/hitman1_right_hand.png"));textureLoaded++;
            
            zombie1_hold = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_hold.png"));textureLoaded++;
            zombie1_stand = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_stand.png"));textureLoaded++;
            zombie1_left_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_left_hand.png"));textureLoaded++;
            zombie1_right_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Zombie 1/zombie1_right_hand.png"));textureLoaded++;

            robot1_hold = ImageIO.read(this.getClass().getResource("/Data/Actors/Robot 1/robot1_hold.png"));textureLoaded++;
            robot1_stand = ImageIO.read(this.getClass().getResource("/Data/Actors/Robot 1/robot1_stand.png"));textureLoaded++;
            robot1_left_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Robot 1/robot1_left_hand.png"));textureLoaded++;
            robot1_right_hand = ImageIO.read(this.getClass().getResource("/Data/Actors/Robot 1/robot1_right_hand.png"));textureLoaded++;
            
            molotov = ImageIO.read(this.getClass().getResource("/Data/Wepons/molotov.png"));textureLoaded++;
            nineMM = ImageIO.read(this.getClass().getResource("/Data/Wepons/nineMM.png"));textureLoaded++;
            machine = ImageIO.read(this.getClass().getResource("/Data/Wepons/machine.png"));textureLoaded++;
            silencer = ImageIO.read(this.getClass().getResource("/Data/Wepons/silencer.png"));textureLoaded++;
            M134 = ImageIO.read(this.getClass().getResource("/Data/Wepons/M134.png"));textureLoaded++;
            
            switch(arg){
                case 0:
                    prologue1 = ImageIO.read(this.getClass().getResource("/Data/Map/Prologue/layer1.png"));//textureLoaded++;
                    prologue2 = ImageIO.read(this.getClass().getResource("/Data/Map/Prologue/layer2.png"));//textureLoaded++;                    
                    break;
                case 1:
                    urbanDistrict91  = ImageIO.read(this.getClass().getResource("/Data/Map/UrbanDistrict9/layer1.png"));//textureLoaded++;
                    urbanDistrict91  = ImageIO.read(this.getClass().getResource("/Data/Map/UrbanDistrict9/layer1.png"));//textureLoaded++;                    
                    break;
                case 2:
                    cr81  = ImageIO.read(this.getClass().getResource("/Data/Map/cr8/layer1.png"));
                    cr82  = ImageIO.read(this.getClass().getResource("/Data/Map/cr8/layer2.png"));
                    break;
                case 3:
                    in41  = ImageIO.read(this.getClass().getResource("/Data/Map/in4/layer1.png"));
                    in42  = ImageIO.read(this.getClass().getResource("/Data/Map/in4/layer2.png"));
                    break;
            }

            textureLoaded++;
            textureLoaded++;
            
            System.out.println("Textures loaded in Level = " + textureLoaded);
            loadingComplete=true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

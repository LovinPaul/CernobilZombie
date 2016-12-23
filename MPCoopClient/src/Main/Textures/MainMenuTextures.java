package Main.Textures;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenuTextures extends LoadTextures{

    @Override
    public void loadTextures(int arg) {
        System.out.println("Load Main Menu Textures.");
        textureLoaded=0;
        textureNr=21;//21
        try {
            curHand = ImageIO.read(this.getClass().getResource("/Data/pointer/curHand.png"));textureLoaded++;
            
            credits1 = ImageIO.read(this.getClass().getResource("/Data/UI/Credits/credits1.png"));textureLoaded++;
            credits2 = ImageIO.read(this.getClass().getResource("/Data/UI/Credits/credits2.png"));textureLoaded++;
            credits3 = ImageIO.read(this.getClass().getResource("/Data/UI/Credits/credits3.png"));textureLoaded++;
            
            normalText = ImageIO.read(this.getClass().getResource("/Data/UI/Button/normalTextUI.png"));textureLoaded++;
            hoverText = ImageIO.read(this.getClass().getResource("/Data/UI/Button/hoverTextUI.png"));textureLoaded++;
            normalButton = ImageIO.read(this.getClass().getResource("/Data/UI/Button/normalButton.png"));textureLoaded++;
            hoverButton = ImageIO.read(this.getClass().getResource("/Data/UI/Button/hoverButton.png"));textureLoaded++;
            
            bloodBarOn = ImageIO.read(this.getClass().getResource("/Data/UI/Bars/bloodBarOn.png"));textureLoaded++;
            bloodBarOff = ImageIO.read(this.getClass().getResource("/Data/UI/Bars/bloodBarOff.png"));textureLoaded++;
            
            loadingBarAnimation = ImageIO.read(this.getClass().getResource("/Data/UI/LoadingScreen/loadingBarAnimation.png"));textureLoaded++;
            mainBackground = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/mainBackground.png"));textureLoaded++;
            
//            options = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/options.png"));textureLoaded++;
//            quit = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/quit.png"));textureLoaded++;
//            
//            yes = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/yes.png"));textureLoaded++;
//            no = ImageIO.read(this.getClass().getResource("/Data/UI/Deploy/no.png"));textureLoaded++;
            
            gagarinStudio = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/gagarinStudio.png"));textureLoaded++;
            presents = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/presents.png"));textureLoaded++;
            zombieIntro = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/zombie.png"));textureLoaded++;
//            singlePlayer = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/singlePlayer.png"));textureLoaded++;
//            multiPlayer = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/multiPlayer.png"));textureLoaded++;
//            coop = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/coop.png"));textureLoaded++;
//            tdm = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/tdm.png"));textureLoaded++;
//            back = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/back.png"));textureLoaded++;
//            
//            audio = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/audio.png"));textureLoaded++;
//            video = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/video.png"));textureLoaded++;
//            controls = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/controls.png"));textureLoaded++;
//            keyboard = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/keyboard.png"));textureLoaded++;
            wasd = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/wasd.png"));textureLoaded++;
            upArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/upArrow.png"));textureLoaded++;
            downArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/downArrow.png"));textureLoaded++;
            leftArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/leftArrow.png"));textureLoaded++;
            rightArrow = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/rightArrow.png"));textureLoaded++;
            
//            mouse = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/mouse.png"));textureLoaded++;
//            player = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/player.png"));textureLoaded++;
//            network = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/network.png"));textureLoaded++;
//            save = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/save.png"));textureLoaded++;
//            easy = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/easy.png"));textureLoaded++;
//            medium = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/medium.png"));textureLoaded++;
//            hard = ImageIO.read(this.getClass().getResource("/Data/UI/MainMenu/hard.png"));textureLoaded++;
            blood_sprite_5 = ImageIO.read(this.getClass().getResource("/Data/Effects/Blood/blood_sprite_5.png"));textureLoaded++;
            
            System.out.println("Textures loaded in MainMenu = " + textureLoaded);
            loadingComplete=true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}

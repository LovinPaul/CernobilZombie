package Game.UI.MainUI;

import Game.Animations.Animation;
import Main.Textures.LoadTextures;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Main.Textures.Initilializer;

public class MainUI  extends JPanel implements Initilializer{

    protected JFrame frame;
    protected LoadTextures texture;
    
    protected ArrayList<Animation> bloodEffects;
    
    protected boolean isReady;
//    protected boolean hasTextures;
    
    public MainUI(JFrame frame) {
        this.frame=frame;
        bloodEffects = new ArrayList<>();
    }
    
    public void init(){
//        isReady=true;
    }

    public boolean isReady(){
        return isReady;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.getWidth()!=frame.getWidth() || this.getHeight()!=frame.getHeight()){
            this.setSize(frame.getWidth(), frame.getHeight());
        }
    }

    @Override
    public void setTexture(LoadTextures texture) {
        this.texture = texture;
        init();
    }
    
    
    
    
}

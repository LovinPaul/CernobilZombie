package Game.UI.MainUI;

import Game.Animations.Animation;
import Game.Animations.Intro.LoadingComplete;
import Main.ResourceMonitor;
import Main.Textures.LoadTextures;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoadingScreen extends JPanel{
    
    private BufferedImage loadingBar;
    private boolean loadingComplete;
    private Animation loadingCompleteAnimation;
    private Font loadingFont;
    
    private int animSpeed=2;
    private int animIndex=10;
    
    private float smoothLoadingPercent;
    
    protected JFrame frame;
    protected LoadTextures textures;
    public ResourceMonitor resourceMonitor;
    
    private String loadingText="";
    
    public LoadingScreen(JFrame frame) {
        this.frame=frame;
        setBackground(Color.black);
        loadingFont = new Font("Arial", Font.BOLD, 25);
        try {
            loadingBar = ImageIO.read(this.getClass().getResource("/Data/UI/LoadingScreen/loadingBar.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setLoadingText(String text){
        loadingText = text;
    }
    
    public void initTextures(LoadTextures textures, int arg){
        this.textures = textures;
        
        Thread T = new Thread(new Runnable(){
        
            @Override
            public void run(){
                textures.loadTextures(arg);
            }
        
        });
        T.start();
    }
    public LoadTextures getTextures(){
        return textures;
    }
    public boolean isLoadingComplete(){
        return (textures.isLoadingComplete() && smoothLoadingPercent>0.9);
    }
    public int loadPercent(){
        if(textures!=null){
            return (int)(textures.loadingPercent()*100);
        }else{
            return 0;
        }
        
    }    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = g.create();
        float percent = textures.loadingPercent();
        
        if(smoothLoadingPercent<percent){smoothLoadingPercent+=0.05f;}
        
        if(smoothLoadingPercent>0 && smoothLoadingPercent<0.99){
            g2.drawImage(loadingBar.getSubimage(0, 0, (int) (loadingBar.getWidth()*smoothLoadingPercent), loadingBar.getHeight()), (frame.getWidth()/2)- (loadingBar.getWidth()/2), (frame.getHeight()/2)- (loadingBar.getHeight()/2), null);
            g2.setFont(loadingFont);
            g2.setColor(Color.GREEN);
            g2.drawString("Loading " +(int)(smoothLoadingPercent*100)+"%", (int) ((frame.getWidth()/2)+(Math.random()*3)-1.5), (int) ((frame.getHeight()/2)+(Math.random()*3)-1.5)+loadingBar.getHeight());
            g2.drawString(loadingText, (int) ((frame.getWidth()/2)+(Math.random()*3)-1.5), (int) ((frame.getHeight()/2)+(Math.random()*3)-1.5)+loadingBar.getHeight()+25);
            g2.setColor(Color.darkGray);
            g2.drawString( "Loading " +(int)(smoothLoadingPercent*100)+"%", (frame.getWidth()/2), (frame.getHeight()/2)+loadingBar.getHeight());
            g2.drawString( loadingText, (frame.getWidth()/2), (frame.getHeight()/2)+loadingBar.getHeight()+25);
            
        }
        
        resourceMonitor.drawResource(g, 0, 0);
    }

    
}

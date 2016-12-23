package Game.Effects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Animation {
    
    private final BufferedImage image;

    private final int lenght;
    private final int width;
    private final int height;
    private final float angle;

    private int frame;
    
    protected boolean centerTheAnimation;
    
    public Animation(BufferedImage image, int lenght, int width, int height) {
        this.image = image;
        this.lenght = lenght;
        this.width = width;
        this.height = height;
        angle = (float) (Math.random()*359);
    }

    public int getLenght(){
        return lenght;
    }
    
    public boolean isConsumed(){
        return frame>=lenght;
    }
    public void replay(){
        frame=0;
    }
    public void nextFrame(){
        if(frame<lenght){
            frame++;
        }
    }
    
    public void draw(Graphics g, float x, float y){
        
        if(frame<lenght){
            Graphics2D gBlood = (Graphics2D) g.create();
            gBlood.rotate(Math.toRadians(angle), (int)x, (int)y);
            
            if(centerTheAnimation){
                gBlood.drawImage(image.getSubimage(frame*width, 0, width, height), (int)x-width/2, (int)y-height/2, null);//
            }else{
                gBlood.drawImage(image.getSubimage(frame*width, 0, width, height), (int)x, (int)y, null);//-width/2-height/2
            }
//            gBlood.setColor(Color.red);
//            gBlood.fillOval((int)x-10, (int)y-10, 20, 20);
//            frame++;
        }
        
    }
    
}

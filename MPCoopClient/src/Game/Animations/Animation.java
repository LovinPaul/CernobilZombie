package Game.Animations;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Animation {
    
    private float x;
    private float y;
    
    protected BufferedImage image;
    protected ArrayList<BufferedImage> images;
    protected int imagesIndex;
    protected int imagesFrame;
            
    private int frameWidth;
    private int frameHeight;
    private boolean fixedSize;
    
    private int lenght;
    private int width;
    private int height;
    private float angle;
    protected boolean rotate=true;
    private boolean reverse;
    private float opacity=1.0f;

    private int frame;
    
    protected boolean centerTheAnimation;
    
    public Animation(BufferedImage image, int lenght, int width, int height) {
        this.image = image;
        this.lenght = lenght;
        this.width = width;
        this.height = height;
        angle = (float) (Math.random()*359);
    }

    public Animation(Animation toCopy){
        this.x=toCopy.x;
        this.y=toCopy.y;

        this.image=toCopy.image;
        this.frameWidth=toCopy.frameWidth;
        this.frameHeight=toCopy.frameHeight;
        this.fixedSize=toCopy.fixedSize;

        this.lenght=toCopy.lenght;
        this.width=toCopy.width;
        this.height=toCopy.height;
        this.angle=toCopy.angle;
        this.rotate=toCopy.rotate;
        this.reverse=toCopy.reverse;
        this.opacity=toCopy.opacity;

        this.frame=toCopy.frame;

        this.centerTheAnimation=toCopy.centerTheAnimation;
    }
    
    public int getFrameIndex(){
        return frame;
    }
    public int getLenght(){
        return lenght;
    }
    public int getWidth(){
        if(fixedSize){
            return frameWidth;
        }else{
            return width;
        }
    }
    public int getHeight(){
        if(fixedSize){
            return frameHeight;
        }else{
            return height;
        }
    }
    public float getOpacity(){
        return opacity;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    
    public void setFixedSize(boolean fixedSize){
        this.fixedSize = fixedSize;
    }
    public void setSize(int frameWidth, int frameHeight){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }
    public void setReverse(boolean reverse){
        if(reverse){frame=lenght;}
        this.reverse=reverse;
    }
    public void setOpacity(float opacity){
        if(opacity<0){
            this.opacity=0;
        }else{
            this.opacity = opacity;
        }
    }
    public void setFrameIndex(int frame){
        if(frame<lenght){
            this.frame=frame;
        }else{
            this.frame=0;
        }
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setAngle(float angle){
        this.angle=angle;
    }
    
    public boolean hasNextFrame(){
        return frame<lenght-1;
    }
    public boolean isConsumed(){
        if(reverse){
            return frame<1;
        }else{
            return frame>=lenght;
        }
    }
    public boolean isReversed(){
        return reverse;
    }
    public boolean isOpac(){
        return opacity==1.0f;
    }
    public void replay(){
        if(reverse){
            frame=lenght;
        }else{
            frame=0;
            imagesFrame=0;
            imagesIndex=0;
        }
    }
    public void nextFrame(){
        if(reverse){
            if(frame>0){frame--;}
            if(images!=null)
                if(imagesFrame>0){imagesFrame--;}
        }else{
            if(frame<=lenght){frame++;}
            if(images!=null)
                if(imagesFrame*width<images.get(imagesIndex).getWidth()){imagesFrame++;}
        }

    }
    
    
    public void draw(Graphics g, float x, float y){
        if(image==null){
            drawImageS(g, x, y);
        }else{
            drawImage(g, x, y);
        }
        
    }
    
    private void drawImage(Graphics g, float x, float y){
        Graphics2D gAnim = (Graphics2D) g.create();
        
        if(frame<lenght){
            if(rotate){
                gAnim.rotate(Math.toRadians(angle), (int)x, (int)y);
            }
            
            if(opacity<1.0f){
                gAnim.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            
            if(centerTheAnimation){
                if(fixedSize){
                    gAnim.drawImage(image.getSubimage(frame*width, 0, width, height), (int)x-width/2, (int)y-height/2, frameWidth, frameHeight, null);//
                }else{
                    gAnim.drawImage(image.getSubimage(frame*width, 0, width, height), (int)x-width/2, (int)y-height/2, null);//
                }
            }else{
                if(fixedSize){
                    gAnim.drawImage(image.getSubimage(frame*width, 0, width, height), (int)x, (int)y, frameWidth, frameHeight, null);//-width/2-height/2
                }else{
                    gAnim.drawImage(image.getSubimage(frame*width, 0, width, height), (int)x, (int)y, null);//-width/2-height/2
                }
            }
        }
    }
    private void drawImageS(Graphics g, float x, float y){
        Graphics2D gAnim = (Graphics2D) g.create();
        
        
        
        
        if(frame<lenght){
            if(rotate){
                gAnim.rotate(Math.toRadians(angle), (int)x, (int)y);
            }
            
            if(opacity<1.0f){
                gAnim.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            
            if((imagesFrame+1)*width>images.get(imagesIndex).getWidth()){
                imagesIndex++;
                imagesFrame=0;
            }
            
            if(centerTheAnimation){
                if(fixedSize){
                    gAnim.drawImage(images.get(imagesIndex).getSubimage(imagesFrame*width, 0, width, height), (int)x-width/2, (int)y-height/2, frameWidth, frameHeight, null);//
                }else{
                    gAnim.drawImage(images.get(imagesIndex).getSubimage(imagesFrame*width, 0, width, height), (int)x-width/2, (int)y-height/2, null);//
                }
            }else{
                if(fixedSize){
                    gAnim.drawImage(images.get(imagesIndex).getSubimage(imagesFrame*width, 0, width, height), (int)x, (int)y, frameWidth, frameHeight, null);//-width/2-height/2
                }else{
                    gAnim.drawImage(images.get(imagesIndex).getSubimage(imagesFrame*width, 0, width, height), (int)x, (int)y, null);//-width/2-height/2
                }
            }
        }
    }
}

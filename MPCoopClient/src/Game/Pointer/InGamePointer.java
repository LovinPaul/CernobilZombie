package Game.Pointer;

import Game.Actors.Actor;
import Game.Mods.Game;
import Main.Textures.LoadTextures;
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class InGamePointer {
    BufferedImage cursor;
    BufferedImage cursorLock;
    
    float relativeX;
    float relativeY;
    float x;
    float y;

    boolean lock;
    Actor lockOnActor;
    Actor me;
    Game game;
    Robot robot;
    

    public InGamePointer(Game game, LoadTextures textures) {
        
        this.game = game;
        cursor = textures.cur347;
        cursorLock = textures.cur347_lock;
        try {
            robot = new Robot();
        } catch (AWTException ex) {}
    }
    
    public void setMe(Actor me){
        this.me = me;
    }

    
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void setX(float x){
        relativeX=0;
        this.x = x;
    }
    public void setY(float y){
        relativeY=0;
        this.y = y;
    }

    public void setLockOnActor(Actor actor){
        lockOnActor = actor;
    }

    public boolean isLocked(){
        return lock;
    }
    public void setLock(boolean lock){
        this.lock = lock;
        if(!lock){
            if(me!=null){
                relativeX= lockOnActor.getX()-me.getX();
                relativeY= lockOnActor.getY()-me.getY();                
            }
            lockOnActor=null;
        }
    }
    
    public void robot(){
        if(me==null){return;}
        float dist;
        int maxPointerDistance = (game.getHeight()/2 + game.getWidth()/2)/2;
        
        if(lock){
            dist = (float)(Math.abs(me.getX() - lockOnActor.getX()) + Math.abs(me.getY() - lockOnActor.getY()));
            if(lockOnActor.isAlive() && game.getMap().isInVisualRange(game.getMe(), lockOnActor) && dist<maxPointerDistance){
                x = lockOnActor.getX();
                y = lockOnActor.getY();
            }else{
                setLock(false);
            }
        }else{
            
            float mouseDX =(game.getSettings().mouseSensitivity*(game.getMouseX() - game.getWidth()/2));//Input.mouseX
            float mouseDY =(game.getSettings().mouseSensitivity*(game.getMouseY() - game.getHeight()/2));//Input.mouseY

            dist = (float)(Math.abs(relativeX+mouseDX));
            if(dist<maxPointerDistance){
                relativeX+=mouseDX;
                x = me.getX()+relativeX;
            }else{
//                relativeX-=mouseDX/2;
                x = me.getX()+relativeX;
            }
            
            dist = (float)(Math.abs(relativeY+mouseDY));
            if(dist<maxPointerDistance){
                relativeY+=mouseDY;
                y = me.getY()+relativeY;
            }else{
//                relativeY-=mouseDY/2;
                y = me.getY()+relativeY;
            }
        }
        robot.mouseMove(game.getWidth()/2+game.getLocationOnScreen().x, game.getHeight()/2+game.getLocationOnScreen().y);
    }
    public void draw(Graphics g, float x, float y){
//        if(cursor!=null && cursorLock!=null){
            if(isLocked()){
                g.drawImage(cursorLock, (int)(x-cursorLock.getWidth()/2), (int)(y-cursorLock.getHeight()/2), null);
            }else{
                g.drawImage(cursor, (int)(x-cursorLock.getWidth()/2), (int)(y-cursorLock.getHeight()/2), null);
            }
            
//        }
    }
    
}

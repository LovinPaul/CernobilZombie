package Game;

import Game.Actors.Actor;
import Input.Input;
import Main.LoadTextures;
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class MousePointer {
    BufferedImage cursor;
    BufferedImage cursorLock;
    
    float mouseSensitivity=0.3f;
    float relativeX;
    float relativeY;
    float x;
    float y;

    boolean lock;
    Actor lockOnActor;
    Actor me;
    Game game;
    Robot robot;

    public MousePointer(Actor me, Game game) {
        this.me = me;
        this.game = game;
        cursor = LoadTextures.cur347;
        cursorLock = LoadTextures.cur347_lock;
        try {
            robot = new Robot();
        } catch (AWTException ex) {}
    }

    public float getX(){
        if(Input.esc){return x;}
        if(lock){
            if(lockOnActor.isAlive()){
                x = lockOnActor.getX();
            }else{
                setLock(false);
            }

        }else{
            boolean outOfBounds =false;
//            System.out.println(mouse.mouseX);
            float mouseDX =(mouseSensitivity*(Input.mouseX-8 - game.getWidth()/2));
//                if(me.getX()+relativeX+mouseDX<0){outOfBounds=true;}
//                if(me.getX()+relativeX+mouseDX>(Server.getMap().getLayer0().getWidth())){outOfBounds=true;}

            if(outOfBounds){
                x = me.getX()+relativeX;
            }else{
                relativeX+=mouseDX;
                x = me.getX()+relativeX;
            }
        }
        return x;
    }
    public float getY(){
        if(Input.esc){return y;}
        if(lock){
            if(lockOnActor.isAlive()){
                y = lockOnActor.getY();
            }else{
                setLock(false);
            }
        }else{
            boolean outOfBounds =false;
            float mouseDY =(mouseSensitivity*(Input.mouseY-30 - game.getHeight()/2));
//                if(me.getY()+relativeY+mouseDY<0){outOfBounds=true;}
//                if(me.getY()+relativeY+mouseDY>(Server.getMap().getLayer0().getWidth())){outOfBounds=true;}

            if(outOfBounds){
                y = me.getY()+relativeY;
            }else{
                relativeY+=mouseDY;
                y =  me.getY()+relativeY;
            }                
        }
        return y;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
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
        if(!lock){lockOnActor=null;}
    }

    public void robot(){
        robot.mouseMove(game.getWidth()/2+game.getLocationOnScreen().x, game.getHeight()/2+game.getLocationOnScreen().y);
    }
    public void draw(Graphics g, float x, float y){
        if(cursor!=null && cursorLock!=null){
            if(isLocked()){
                g.drawImage(cursorLock, (int)(x-cursorLock.getWidth()/2), (int)(y-cursorLock.getHeight()/2), null);
            }else{
                g.drawImage(cursor, (int)(x-cursorLock.getWidth()/2), (int)(y-cursorLock.getHeight()/2), null);
            }
            
        }
    }
    
}

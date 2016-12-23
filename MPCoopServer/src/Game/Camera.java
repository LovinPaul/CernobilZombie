package Game;

import Game.Actors.Actor;
import Main.Main;


public class Camera {
    
    public float x;
    public float y;
    public float speedX;
    public float speedY;
    private boolean freeCam;
    private Actor actor;
    private boolean moveY;
    private boolean moveX;
     int agilityX=100;
     int agilityY=100;
     float speedUpRate=1;
     float limitX;
     float limitY;
    
    public void goUp(){
        moveY=true;
        if(isFreeCam()){
            if(Math.abs(speedY) < agilityY){
                speedY-=speedUpRate;
            }
        }else{
            if(Math.abs(speedY) < actor.getAgility()){
                speedY-=actor.getSpeedRate();
            }
        }
    }
    public void goDown(){
        moveY=true;
        if(isFreeCam()){
            if(Math.abs(speedY) < agilityY){
                speedY+=speedUpRate;
            }
        }else{
            if(Math.abs(speedY) < actor.getAgility()){
                speedY+=actor.getSpeedRate();
            }            
        }
    }
    public void goLeft(){
        moveX=true;
        if(isFreeCam()){
            if(Math.abs(speedX) < agilityX){
                speedX-=speedUpRate;
            }
        }else{
            if(Math.abs(speedX) < actor.getAgility()){
                speedX-=actor.getSpeedRate();
            }            
        }
    }
    public void goRight(){
        moveX=true;
        if(isFreeCam()){
            if(Math.abs(speedX) < agilityX){
                speedX+=speedUpRate;
            }
        }else{
            if(Math.abs(speedX) < actor.getAgility()){
                speedX+=actor.getSpeedRate();
            }            
        }
    }
    public void shake(float magnitude){
        setX((float) (getX()+(Math.random()*(2*magnitude)-magnitude)));
        setY((float) (getY()+(Math.random()*(2*magnitude)-magnitude)));
    }
    
    public void setFreeCam(boolean set){
        freeCam = set;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setLimits(float limitX, float limitY){
        this.limitX = limitX;
        this.limitY = limitY;
    }

    public void updateCoordonate(){
        
        //coord
        if(moveY){
        }else{
            if(speedY<0 ){
                speedY+=speedUpRate;///2
            }
            if(speedY>0 ){
                speedY-=speedUpRate;///2
            }
            if(Math.abs(speedY)<speedUpRate){
                speedY = 0;
            }
        }
        //
        if(moveX){
        }else{
            if(speedX<0){
                speedX+=speedUpRate;///10
            }
            if(speedX>0){
                speedX-=speedUpRate;///10
            }
            if(Math.abs(speedX)<speedUpRate){
                speedX = 0;
            }
        }

        moveY=false;
        moveX=false;
        x+=speedX;
        y+=speedY;
        
        if(x<0){x=0;}
        if(y<0){y=0;}
        if(x>(limitX-Main.getWidth())){x=(limitX-Main.getWidth());}
        if(y>(limitY-Main.getHeight())){y=(limitY-Main.getHeight());}
        
    }
    
    public boolean isFreeCam(){
        return freeCam;
    }
    
    public void followPoint(float targetX, float targetY) {
        if(freeCam){return;}
        
        float difX = (float) (this.x+Main.getWidth()/2 -targetX);
        float difY = (float) (this.y+Main.getHeight()/2 -targetY);
        
        x -= difX/10;
        y -= difY/10;

        if(x<0){x=0;}
        if(y<0){y=0;}
        if(x>(limitX-Main.getWidth())){x=(limitX-Main.getWidth());}
        if(y>(limitY-Main.getHeight())){y=(limitY-Main.getHeight());}
         
    
    }
    public void followActor(Actor newActor) {
        if(freeCam){return;}
        if(this.actor != newActor){
            this.actor = newActor;
            this.speedX =0;
            this.speedY =0;
        }
        
        float difX = this.x+Main.getWidth()/2 -actor.getX();
        float difY = this.y+Main.getHeight()/2 -actor.getY();
        
        x -= difX/10;
        y -= difY/10;

        if(x<0){x=0;}
        if(y<0){y=0;}
        if(x>(limitX-Main.getWidth())){x=(limitX-Main.getWidth());}
        if(y>(limitY-Main.getHeight())){y=(limitY-Main.getHeight());}
         
    
    }
}

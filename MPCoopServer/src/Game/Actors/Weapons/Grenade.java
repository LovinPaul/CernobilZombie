package Game.Actors.Weapons;

import Game.Actors.Actor;
import Game.Maps.Map;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Grenade  extends Weapon{
    
    //Statics
    private static Map map;
    
    protected float fireDistance;
    protected boolean impactExplode;
    protected boolean isExploading;
    private boolean isThrown;
    
    private byte type;
    protected float x;
    protected float y;
    
    float targetX;
    float targetY;
    float originX;
    float originY;
    float dist;
    float maxDist=500;
    protected float angle;
    private float drawAngle;
    protected int agility=20;
    private boolean move=true;

    protected int timeUntilExplosion;
//    private static ArrayList<Actor> actors = Actor.getActors();
    protected static ArrayList<Grenade> grenades = new ArrayList<>();
    public Grenade(Actor owner) {
        super(owner);
        
    }
    public Grenade(byte type, Actor owner, float targetX, float targetY) {
        super(owner);
        this.type = type;
        x=owner.getX();
        y=owner.getY();
        originX=owner.getX();
        originY=owner.getY();
        this.targetX = targetX;
        this.targetY = targetY;
        angle = owner.getAngle();
        isThrown=true;
    }
    
    public Grenade(){
    }
    
    public static void setMap(Map map){
        Grenade.map = map;
    }
    public static ArrayList<Grenade> getGrenades(){
        return grenades;
    }

    public byte getType(){
        return type;
    }
    public float getX(){
        return x;//-actor_gun.getWidth()/2
    }
    public float getY(){
        return y;//-actor_gun.getHeight()/2
    }
    public float getOriginX(){
        return originX;
    }
    public float getOriginY(){
        return originY;
    }
    public float getTargetX(){
        return targetX;
    }
    public float getTargetY(){
        return targetY;
    }
    public int getTimeUntilExplosion(){
        return timeUntilExplosion;
    }

    public void setFirePower(float firePower){
        this.firePower=firePower/(explosion.getLenght()+1);
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    
    public void goForward(){
        if(!move){return;}
        
        dist = (float)(Math.abs(x - targetX) + Math.abs(y - targetY));
        if(dist<80){move=false;}
        if(dist>200){dist=200;}
        
        float newX = x;
        float newY = y;
        
        newX-=dist/10*Math.cos(Math.toRadians(angle+90));
        newY-=dist/10*Math.sin(Math.toRadians(angle+90)); 

        handleColision(newX, newY);
        
        if(x<0){x=0;}
        if(y<0){y=0;}
        if(x>(map.getLayer0().getWidth())){x=(map.getLayer0().getWidth());}
        if(y>(map.getLayer0().getHeight())){y=(map.getLayer0().getHeight());}
    }
    

    @Override
    public void fire(float targetX, float targetY) {
            super.fire(targetX, targetY);

    }

    
    private void handleColision(float newX, float newY){
        float dist;
        float newDist;
        
        boolean colisionOnX = false;
        boolean colisionOnY = false;
        
//        for(Actor actor : actors){
//            if(actor!=owner){
//                dist = (float)(Math.abs(actor.getX() - x) + Math.abs(actor.getY() - y)); //Math.sqrt(Math.pow((x - actor.getX()), 2) + Math.pow((y - actor.getY()), 2));
//                if(dist < 30){
//                    newDist = (float) (Math.abs(actor.getX() - newX) + Math.abs(actor.getY() - newY));//Math.sqrt(Math.pow((newX - actor.getX()), 2) + Math.pow((newY - actor.getY()), 2));
//                    if(newDist<dist){
//                        newDist = (float) (Math.abs(actor.getX() - x) + Math.abs(actor.getY() - newY));//Math.sqrt(Math.pow((x - actor.getX()), 2) + Math.pow((newY - actor.getY()), 2));
//                        if(newDist<dist){colisionOnY=true;}
//                        newDist = (float) (Math.abs(actor.getX() - newX) + Math.abs(actor.getY() - y));//Math.sqrt(Math.pow((newX - actor.getX()), 2) + Math.pow((y - actor.getY()), 2));
//                        if(newDist<dist){colisionOnX=true;}
//                    }
//                }
//            }
//        }
        if(map.rectangles!=null){
            for(Rectangle rect : map.rectangles){
                if(rect.contains(newX, newY)){
                    //need clarification
                    if(rect.intersectsLine(x, y, x, newY)){colisionOnY=true;}
                    if(rect.intersectsLine(x, y, newX, y)){colisionOnX=true;}
                    if(rect.contains(x, newY)){colisionOnY=true;}
                    if(rect.contains(newX, y)){colisionOnX=true;}
                }
            }
        }


        if(colisionOnX){move=false;if(impactExplode){timeUntilExplosion=0;}}else{x=newX;}
        if(colisionOnY){move=false;if(impactExplode){timeUntilExplosion=0;}}else{y=newY;}
    }
    protected float calculateNewAngle(float targetX, float targetY){
        float angle = (float) Math.toDegrees(Math.atan2(targetY - y, targetX - x)) + 90;

        if(angle < 0){
            angle += 360;
        }
        return angle;
    }
    
    public boolean isExploading(){
        return isExploading;
    }
    public boolean isExploaded(){
        return explosion.isConsumed();
    }
//    public boolean isThrown(){
//        return isThrown;
//    }
    private boolean isInVisualRange(Actor actor){
        if(map.rectangles!=null){
            for(Rectangle rect : map.rectangles){
                if(rect.intersectsLine(x, y, actor.getX(), actor.getY())){
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public void explode(){
        
        float dist;
//        synchronized(actors){
//            for(Actor actor : actors){
//                if(isInVisualRange(actor)){
//                    dist = (float)(Math.abs(actor.getX() - x) + Math.abs(actor.getY() - y));
//                    if(dist<fireDistance){
//                        float distPercent = 1-(dist * 1/fireDistance);
//                        actor.gotHit(distPercent*firePower);
//                    }
//                }
//            }
//        }
    }
    public void updateMechanics(){
        
        if(timeUntilExplosion>0){
            
            anim_image.nextFrame();
            if(move){goForward();}
        }else{
//            System.out.println(timeUntilExplosion);
//            timeUntilExplosion--;
            isExploading=true;
            explode();
            explosion.nextFrame();
        }
        
        timeUntilExplosion--;
    }
    @Override
    public void draw(Graphics g, float x, float y){
        if(isThrown){
            if(dist>5 && move){
                drawAngle+=dist/10;
                if(drawAngle>359){drawAngle=0;}
            }
            Graphics2D gGrenade = (Graphics2D) g.create();
            gGrenade.rotate(Math.toRadians(drawAngle), (int)x, (int)y);

            if(timeUntilExplosion>0){
                if(anim_image.isConsumed()){anim_image.replay();}
                anim_image.draw(gGrenade, x, y);
            }else{
    //            if(explosion.isConsumed()){explosion.replay();}
                explosion.draw(gGrenade, x, y);
            }
    //            g.fillOval((int)(x-10), (int)(y-10), 20, 20);
//                g.drawOval((int)(x-fireDistance/2), (int)(y-fireDistance/2), (int)fireDistance, (int)fireDistance);
//                g.drawString(timeUntilExplosion+"", (int)x, (int)y);
        }else{
            super.draw(g, x, y);
        }

    }
}

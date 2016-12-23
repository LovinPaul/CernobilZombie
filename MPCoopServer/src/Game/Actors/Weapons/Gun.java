
package Game.Actors.Weapons;

import Game.Actors.Actor;
import Game.Actors.Hitman1;
import Game.Actors.Zombie1;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Gun  extends Weapon{
    protected int reloadTime;
    protected int clipSize;
    private int clip;
    private boolean reloading;
//    private float targetX; 
//    private float targetY;
    
    private boolean drawFire;
    
    
    public Gun(Actor owner) {
        super(owner);
    }
    public Gun() {
    }
    
//    public void reload(){
//        if(reloading){
//            if(clip<clipSize && clip<getAmmo()){
//                clip++;
//            }else{
//                reloading=false;
//            }
//        }
//    }
    public int getClip(){
        return clip;
    }
    public int getClipSize(){
        return clipSize;
    }
    
    public boolean isReloading(){
        return reloading;
    }
    
    private double shortestDistance(float x1,float y1,float x2,float y2,float x3,float y3){
        float px=x2-x1;
        float py=y2-y1;
        float temp=(px*px)+(py*py);
        float u=((x3 - x1) * px + (y3 - y1) * py) / (temp);
        if(u>1){
            u=1;
        }
        else if(u<0){
            u=0;
        }
        float x = x1 + u * px;
        float y = y1 + u * py;

        float dx = x - x3;
        float dy = y - y3;
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist;

    }
    
    @Override
    public void fire(){
        drawFire=true;
    }    
    @Override
    public void fire(float targetX, float targetY){
        if(fireWithCoolDown){if(!isCool()){return;}}
            
        super.fire(0, 0);
        drawFire=true;
        
        this.targetX=targetX;
        this.targetY=targetY;
//        Actor closestActor = null;
//        float dist = 0;
//
//        synchronized(actors){
//            for(Actor actor : actors){
//                if(actor!=owner && owner.isInVisualRange(actor) && shortestDistance(owner.getX(),owner.getY(),targetX,targetY,actor.getX(),actor.getY())<15){
//                    if(!friendlyFire && actor.getTeam()!=owner.getTeam()){
//                        if(closestActor==null){
//                            dist = (float)(Math.abs(actor.getX() - owner.getX()) + Math.abs(actor.getY() - owner.getY()));
//                            closestActor = actor;
//                        }else{
//                            float newDist = (float)(Math.abs(actor.getX() - owner.getX()) + Math.abs(actor.getY() - owner.getY()));
//                            if(newDist<dist){
//                                dist = newDist;
//                                closestActor = actor;
//                            }
//                        }
//                    }
//                }
//            }
//            if(closestActor!=null){
//                closestActor.gotHit(firePower);
//                if(!closestActor.isAlive()){
//                    if(closestActor instanceof Zombie1){
//                        owner.incrementZombiesKilled();
//                    }else if(closestActor instanceof Hitman1){
//                        owner.incrementHitmansKilled();
//                    }
//                }
//            }
//        }
    }

    
    @Override
    public void draw(Graphics g, float x, float y){
    
        Graphics2D gGun = (Graphics2D) g.create();
        if(drawFire){//muzzle_flash!=null && weaponCooldown==weaponCooldownTime-1
            drawFire=false;
//            gWeapon.setColor(bulletTrajectory);
//            gWeapon.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                          RenderingHints.VALUE_ANTIALIAS_ON);
//            gGun.drawLine(
//                        (int)(x-(100*Math.cos(Math.toRadians(owner.getAngle()+90)))),
//                        (int)(y-(100*Math.sin(Math.toRadians(owner.getAngle()+90)))),
//                        (int)(targetX),
//                        (int)(targetY)
//            );
            
            gGun.rotate(Math.toRadians(owner.getAngle()), (int)x, (int)y);
            gGun.drawImage(image, (int)(x+relativWeaponX+(Math.random()*3-1.5)), (int)(y+relativWeaponY+5), null);
            gGun.drawImage(muzzle_flash, (int)(x+relativMuzzleFlashX), (int)(y+relativMuzzleFlashY), null);
        }else{
            super.draw(gGun, x, y);
        }
        
    }
}

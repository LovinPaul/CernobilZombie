
package Game.Actors.Weapons;

import Game.Actors.Actor;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Gun  extends Weapon{
    protected int reloadTime;
    protected int clipSize;
    private int clip;
    private boolean reloading;
    
    private boolean drawFire;
    
    public void reload(){
        if(clip<clipSize){
            clip=0;
            reloading=true;
        }

    }
    public void reloading(){
        if(reloading){
            if(clip<clipSize && clip<getAmmo()){
                clip++;
            }else{
                reloading=false;
            }
        }
    }
    public int getClip(){
        return clip;
    }
    public int getClipSize(){
        return clipSize;
    }
    
    public boolean isReloading(){
        return reloading;
    }

    
    @Override
    public void fire(){
        super.fire();
        if(clip>0){
            clip--;
        }else{
            reloading=true;
        }
        drawFire=true;
    }    
    @Override
    public void fire(float targetX, float targetY){
        if(isCool() && getAmmo()>0 && !reloading){
            if(clip>0){
                clip--;
            }else{
                reloading=true;
                return;
            }
            
            super.fire(0, 0);
            drawFire=true;

            this.targetX=targetX;
            this.targetY=targetY;
            

//            Actor closestActor = null;
//            float dist = 0;
//            
//            synchronized(actors){
//                for(Actor actor : actors){
//                    if(actor!=owner && owner.isInVisualRange(actor) && shortestDistance(owner.getX(),owner.getY(),targetX,targetY,actor.getX(),actor.getY())<15){
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
//                if(closestActor!=null){
//                    closestActor.gotHit(firePower);
//                }
//            }
        }
    }

    @Override
    public boolean silentFire(){
        if(isCool() && getAmmo()>0 && !reloading){
            if(clip>0){
//                clip--;
            }else{
                reloading=true;
                return false;
            }
            
            super.silentFire();
            return true;
        }
        return false;
    }

    
    @Override
    public void draw(Graphics g, float x, float y){
    
        Graphics2D gGun = (Graphics2D) g.create();
        
        if(muzzle_flash!=null && drawFire){
            drawFire=false;
            
            gGun.rotate(Math.toRadians(owner.getAngle()), (int)x, (int)y);
            gGun.drawImage(image, (int)(x+relativWeaponX+(Math.random()*3-1.5)), (int)(y+relativWeaponY+5), null);
            gGun.drawImage(muzzle_flash, (int)(x+relativMuzzleFlashX), (int)(y+relativMuzzleFlashY), null);
        }else{
            super.draw(gGun, x, y);
        }
        
    }
}

package Game.Maps.Environment.Crates;

import Game.Maps.Environment.Environment;
import Game.Animations.Animation;
import Game.Animations.Explosions.Debris;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Crate implements Environment{
    
    private byte ID;
    protected float x;
    protected float y;
    public int hitPoints;
    protected boolean isExploading;
    protected int timeUntilExplosion;
    
    protected BufferedImage image;
    protected Animation debris;
    protected Animation anim_image;
    protected Animation explosion;
    
    
    protected Rectangle bounds;
    private boolean isUpdated;
    
    ArrayList<Animation> debrisEffects;
    
    public Crate(byte ID, float x, float y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        debrisEffects = new ArrayList<>();
    }

    @Override
    public byte getID(){
        return ID;
    }
    @Override
    public float getX(){
        return x;
    }
    @Override
    public float getY(){
        return y;
    }
    @Override
    public Rectangle getBounds(){
        return bounds;
    }
    
    public int getTimeUntilExplosion(){
        return timeUntilExplosion;
    }
    public int getHitPoints(){
        return hitPoints;
    }
    
    @Override
    public void setState(byte state){
        if(state<=0 && explosion!=null){
            isExploading=true;
            for(int i=0; i<Math.abs(state); i++){
                explosion.nextFrame();
            }
        }
        
        if(state>0 && state<hitPoints){
            Debris copyOf = new Debris((Debris) debris);
            copyOf.setAngle((float) (Math.random()*359));
            debrisEffects.add(copyOf);
        }
        
        hitPoints = state;
        
        
    }
    @Override
    public void setState(int state){
        setState((byte)state);
    }
    @Override
    public void setIsUpdated(boolean isUpdated){
        this.isUpdated = isUpdated;
    }
    
    @Override
    public void updateMechanics(){
//        if(hitPoints<=0){
//            isExploading=true;
//            if(explosion!=null){
//                explosion.nextFrame();
//                timeUntilExplosion--;
//            }
//        }
        
        
        if(!debrisEffects.isEmpty()){
            Iterator<Animation> debrisIterator = debrisEffects.listIterator();
            while ( debrisIterator.hasNext() ) {
                
                Animation debris = debrisIterator.next();
                if(debris.isConsumed()){
                    debrisIterator.remove();
                }else{
                    debris.nextFrame();
                }
            }
        }
        
    }
    
    @Override
    public boolean isUpdated(){
        return isUpdated;
    }
    public boolean isDestroyed(){
        if(explosion==null){
            return hitPoints<=0;
        }else{
            return explosion.isConsumed();
        }
    }
    
    @Override
    public void gotHit(float damage){
        hitPoints-=damage;
//        switch((int)(Math.random()*3)){//
//            case 0:
//                bloodEffects.add(new BloodSprite1());
//                break;
//            case 1:
//                bloodEffects.add(new BloodSprite2());
//                break;
//            case 2:
//                bloodEffects.add(new BloodSprite3());
//                break;
//            case 3:
//                bloodEffects.add(new BloodSprite4());
//                break;
//        }
    }

    @Override
    public void draw(Graphics g, float x, float y){
        Graphics2D gExplCrate = (Graphics2D) g.create();
//        gWeapon.rotate(Math.toRadians(owner.getAngle()), (int)x, (int)y);

        if(isExploading){
            if(explosion!=null){
                explosion.draw(gExplCrate, (int)(x), (int)(y));
            }
        }else{
            gExplCrate.drawImage(image, (int)(x)-image.getWidth()/2, (int)(y)-image.getHeight()/2, null);
        }
        
        for(Animation debris : debrisEffects){
            debris.draw(g, (int)x, (int)y);
        }
//        gExplCrate.drawRect((int)(x), (int)(y), bounds.width, bounds.height);
//        gExplCrate.drawString(hitPoints+" HP", (int)x, (int)y);
    }
    
}

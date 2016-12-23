package Game.Maps.Environment.Crates;

import java.awt.Graphics;
import java.awt.Graphics2D;


public abstract class ExploadingCrate extends Crate{
    
    protected float fireDistance;
    public float firePower;

    protected boolean isExploading;
    protected int timeUntilExplosion;


    public ExploadingCrate(byte ID, float x, float y) {
        super(ID, x, y);
    }
    
    public int getTimeUntilExplosion(){
        return timeUntilExplosion;
    }
    
    public float getFireDistance(){
        return fireDistance;
    }
    public float getFirePower(){
        return firePower;
    }
    
    @Override
    public void setState(byte state){
        if(state<=0){
            isExploading=true;
            for(int i=0; i<Math.abs(state); i++){
                explosion.nextFrame();
            }
        }
        hitPoints = state;
    }
    
    public boolean isExploading(){
        return isExploading;
    }
    
    @Override
    public byte getState() {
        return (byte) (hitPoints>0? hitPoints : getTimeUntilExplosion());
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
    public void updateMechanics(){
        if(hitPoints<=0){
            isExploading=true;
            if(explosion!=null){
                explosion.nextFrame();
                timeUntilExplosion--;
            }

        }
    }
    
    @Override
    public void draw(Graphics g, float x, float y){
        Graphics2D gExplCrate = (Graphics2D) g.create();
        if(isExploading){
            if(explosion!=null){
                explosion.draw(gExplCrate, (int)(x), (int)(y));
            }
        }else{
            super.draw(g, x, y);
        }
        
        
        
        g.drawOval((int)(x-fireDistance/2), (int)(y-fireDistance/2), (int)fireDistance, (int)fireDistance);
    }
    
}

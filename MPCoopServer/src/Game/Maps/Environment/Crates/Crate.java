package Game.Maps.Environment.Crates;

import Game.Maps.Environment.Environment;
import Game.Effects.Animation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Crate implements Environment{
    
    private final byte ID;
    protected float x;
    protected float y;
    public int hitPoints;

    
    protected BufferedImage image;
    protected Animation anim_image;
    protected Animation explosion;
    
    protected Rectangle bounds;
//    private boolean isUpdated;
    
    public Crate(byte ID, float x, float y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
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
    

    @Override
    public int getHitPoints(){
        return hitPoints;
    }

    @Override
    public byte getState() {
        return (byte) hitPoints;
    }
    
    
    
    @Override
    public void setState(byte state){
        hitPoints = state;
    }
    
    @Override
    public void updateMechanics(){}

    @Override
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

        gExplCrate.drawImage(image, (int)(x)-image.getWidth()/2, (int)(y)-image.getHeight()/2, null);
//        gExplCrate.drawRect((int)(x), (int)(y), bounds.width, bounds.height);
        gExplCrate.drawString(hitPoints+" HP", (int)x, (int)y);
    }
    
}

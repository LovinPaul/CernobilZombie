package Game.Maps.Environment.Traps;

import Game.Actors.Actor;
import Game.Animations.Animation;
import Game.Maps.Environment.Environment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Trap implements Environment{
    private final byte ID;
    protected float x;
    protected float y;
    public int hitPoints;
    protected float position;
    
    private boolean isUpdated;
    
    protected BufferedImage image;
    protected Animation anim_image;
    protected Rectangle bounds;
    protected Rectangle hitBox;

    public Trap(byte ID, float x, float y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
    public Rectangle getHitBounds() {
        return bounds;
    }

    @Override
    public void setState(byte state) {
        position=state;
    }
    @Override
    public void setState(int state) {
        position=state;
    }

    @Override
    public void updateMechanics() {
//        position+=1;
//        if(position>175){position=0;}
//        bounds.setLocation((int)x-image.getWidth()/2, (int) (y+position-image.getHeight()/2));
//        bounds.setSize(image.getWidth(), (int) (position+image.getHeight()));
//        bounds = new Rectangle((int)x-image.getWidth()/2, (int) (y+position-image.getHeight()/2), image.getWidth(), image.getHeight());
    }

    @Override
    public void gotHit(float damage) {
    }

    public void catchPray(Actor actor){}
    
    @Override
    public void draw(Graphics g, float x, float y){
        Graphics2D gExplCrate = (Graphics2D) g.create();
//        gWeapon.rotate(Math.toRadians(owner.getAngle()), (int)x, (int)y);

        gExplCrate.drawImage(image, (int)(x)-image.getWidth()/2, (int)(y+position-image.getHeight()/2), null);
//        gExplCrate.drawRect((int)(x), (int)(y), bounds.width, bounds.height);
        gExplCrate.drawString(position+" Position", (int)x, (int)y);
    }

    @Override
    public boolean isUpdated() {
        return isUpdated;
    }

    @Override
    public void setIsUpdated(boolean isUpdated){
        this.isUpdated = isUpdated;
    }
}

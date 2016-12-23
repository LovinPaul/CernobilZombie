
package Game.Maps.Environment;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Environment {
    
    public boolean isDestroyed();
    public int getHitPoints();
//    public boolean isUpdated();
    public byte getID();
    public float getX();
    public float getY();
    public Rectangle getBounds();
    public void setState(byte state);
    public byte getState();
//    public void setIsUpdated(boolean isUpdated);
    public void updateMechanics();
    public void gotHit(float damage);
    public void draw(Graphics g, float x, float y);
}

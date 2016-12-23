package Game.Maps.Environment.Traps;

import Game.Actors.Actor;
import Main.LoadTextures;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MobileDoubleSpikes extends Trap{
    
    private boolean pozitivMotion;
    private final int hitPower = 1;
    private final int squezePower = 90;
    private final float pozitivSpeed = 6.5f;
    private final float negativSpeed = 3.5f;
    
    private final float maxDepls=120;
    
    public MobileDoubleSpikes(byte ID, float x, float y) {
        super(ID, x, y);
        super.image = LoadTextures.doubleSpike;
        super.bounds = new Rectangle((int) (x+22-image.getWidth()/2), (int) (y+20-image.getHeight()/2), image.getWidth()-44, image.getHeight()-40);
        hitBox = new Rectangle((int) (x+22-image.getWidth()/2), (int) (y+5-image.getHeight()/2), image.getWidth()-44, image.getHeight()-10);
//        hitBox=new Rectangle(0, 0);
    }
    
    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public void catchPray(Actor actor){
        if(pozitivMotion){
            actor.gotHit(hitPower*pozitivSpeed);
            if(position>maxDepls-20){
                actor.gotHit(squezePower);
            }
            if(bounds.contains(actor.getX(), actor.getY())){
                actor.setY(actor.getY()+4*pozitivSpeed);
            }else{
                actor.setY(actor.getY()+pozitivSpeed);
            }
        }else{
            actor.gotHit(hitPower*negativSpeed);
            if(position<5){
                actor.gotHit(squezePower);
            }
            if(bounds.contains(actor.getX(), actor.getY())){
                actor.setY(actor.getY()-(4*negativSpeed));
            }else{
                actor.setY(actor.getY()-negativSpeed);
            }
        }
    }
    @Override
    public void updateMechanics() {
        if(pozitivMotion){
            position+=pozitivSpeed;
            if(position>maxDepls){pozitivMotion=false;}
        }else{
            position-=negativSpeed;
            if(position<negativSpeed){pozitivMotion=true;}
        }
        
        bounds.setLocation((int)x+20-image.getWidth()/2, (int) (y+20+position-image.getHeight()/2));
        hitBox.setLocation((int)x+22-image.getWidth()/2, (int) (y+5+position-image.getHeight()/2));
    }
    @Override
    public void draw(Graphics g, float x, float y){
        Graphics2D gMobileDouleSpike = (Graphics2D) g.create();
//        gMobileDouleSpike.rotate(Math.toRadians(90), (int)x, (int)y);
        
        
        gMobileDouleSpike.drawImage(image, (int)(x-image.getWidth()/2), (int)(y+position-image.getHeight()/2), null);
        gMobileDouleSpike.drawString(position+" Position", (int)x, (int)y);
        gMobileDouleSpike.drawRect((int)(x-hitBox.width/2), (int)(y+position-hitBox.getHeight()/2), hitBox.width, hitBox.height);
    }
}

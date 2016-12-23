package Game.Maps.Environment.Traps;

import Game.Actors.Actor;
import Main.Textures.LoadTextures;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MobileDoubleSpikes extends Trap{
    
    private boolean pozitivMotion;
    private final int hitPower = 1;
    private final int squezePower = 25;
    private final float pozitivSpeed = 2.5f;
    private final float negativSpeed = 0.5f;
    
    public MobileDoubleSpikes(byte ID, float x, float y, LoadTextures textures) {
        super(ID, x, y);
        super.image = textures.doubleSpike;
        super.bounds = new Rectangle((int) (x+22-image.getWidth()/2), (int) (y+25-image.getHeight()/2), image.getWidth()-44, image.getHeight()-50);
//        hitBox = new Rectangle((int) (x+15-image.getWidth()/2), (int) (y+10-image.getHeight()/2), image.getWidth()-30, image.getHeight()-20);
    }
    
    @Override
    public void setState(byte state) {
        position=state;
    }
    @Override
    public void setState(int state) {
        position=state;
        bounds.setLocation((int)x+22-image.getWidth()/2, (int) (y+25+position-image.getHeight()/2));
    }
    @Override
    public void catchPray(Actor actor){
        if(pozitivMotion){
            actor.setY(actor.getY()+pozitivSpeed);
            actor.gotHit(hitPower*pozitivSpeed);
            if(position>100){
                actor.gotHit(squezePower);
            }
        }else{
            actor.setY(actor.getY()-negativSpeed);
            actor.gotHit(hitPower*negativSpeed);
            if(position<5){
                actor.gotHit(squezePower);
            }
        }
    }
    @Override
    public void updateMechanics() {
        if(hitBox==null){return;}
        if(pozitivMotion){
            position+=pozitivSpeed;
            if(position>140){pozitivMotion=false;}
        }else{
            position-=negativSpeed;
            if(position<1){pozitivMotion=true;}
        }
        
        bounds.setLocation((int)x+20-image.getWidth()/2, (int) (y+25+position-image.getHeight()/2));
        hitBox.setLocation((int)x+15-image.getWidth()/2, (int) (y+10+position-image.getHeight()/2));
    }
    @Override
    public void draw(Graphics g, float x, float y){
        Graphics2D gMobileDouleSpike = (Graphics2D) g.create();
//        gMobileDouleSpike.rotate(Math.toRadians(90), (int)x, (int)y);

        gMobileDouleSpike.drawImage(image, (int)(x-image.getWidth()/2), (int)(y+position-image.getHeight()/2), null);
//        gMobileDouleSpike.drawString(position+" Position", (int)x, (int)y);
    }
}

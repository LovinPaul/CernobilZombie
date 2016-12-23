package Game.Actors.Items;

import Game.Actors.Actor;
import Game.Animations.Animation;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Item {
    
//    public static ArrayList<Item> items = new ArrayList();
    
    protected boolean isPickedUp;
    private byte type;
    float x;
    float y;
    
    protected BufferedImage image;
    protected Animation anim_image;

//    public static ArrayList<Item> getItems(){
//        return items;
//    }
    
    public Item(byte type, float x, float y) {
        this.type=type;
        this.x = x;
        this.y = y;
    }
    
    public float getX(){
        return x;//-actor_gun.getWidth()/2
    }
    public float getY(){
        return y;//-actor_gun.getHeight()/2
    }
    
    public byte getType(){
        return type;
    }
    
    public boolean isPickedUp(){
        return isPickedUp;
    }
    
    public void pickUp(Actor actor){
        isPickedUp=true;
    }
    
    public void draw(Graphics g, float x, float y){
        if(image!=null){
            g.drawImage(image, (int) x, (int)y, null);
        }
        
    }
    
}

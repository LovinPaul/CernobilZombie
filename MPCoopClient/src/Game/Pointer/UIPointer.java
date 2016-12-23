package Game.Pointer;

import Input.Mouse;
import Main.Textures.LoadTextures;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIPointer {
    BufferedImage cursor;
    Mouse mouse;

    float x;
    float y;

    
    
    public UIPointer(Mouse mouse, LoadTextures texture) {
        
        this.mouse = mouse;
        cursor = texture.curHand;

    }
    public float getX(){
        return mouse.mouseX;
    }
    public float getY(){
        return mouse.mouseY;
    }
    
    public void setTexture(BufferedImage texture){
        cursor=texture;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public void draw(Graphics g){
        g.drawImage(cursor, (int)(mouse.mouseX), (int)(mouse.mouseY), null);
    }
    
}

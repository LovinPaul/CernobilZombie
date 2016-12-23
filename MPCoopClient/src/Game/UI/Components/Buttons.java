package Game.UI.Components;

import Input.Mouse;
import Main.Textures.LoadTextures;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Buttons extends Component{
    
    String title;
    
    BufferedImage normalText;
    BufferedImage hoverText;
    
    BufferedImage normalButton;
    BufferedImage hoverButton;

    Mouse mouse;
    

    
    private float opacity=0.0f;
    private float opacityChangeRate=0.075f;
    
    private boolean hover;
    private boolean radio;
    private boolean selected;
    private boolean smoothHover=true;//
    
    public Buttons(String title, LoadTextures textures) {
        super(textures);
        this.title = title.toUpperCase();
        normalText = textures.normalText;
        hoverText = textures.hoverText;
        normalButton = textures.normalButton;
        hoverButton = textures.hoverButton;
    }
    
    public void setMouse(Mouse mouse){
        this.mouse=mouse;
    }
    public void setRadioMode(boolean radio){
        this.radio=radio;
    }
    public void setSelected(boolean selected){
        this.selected=selected;
    }
    

    
    public void hover(){
        hover=true;
    }
    
    public boolean isB1Released(){
        Rectangle rect = new Rectangle(x, y, width, height);
        if(rect.contains(mouse.mouseX, mouse.mouseY)){
            return mouse.b1Released;
        }else{
            return false;
        }
        
    }
    
    public void draw(Graphics g){
        
        Graphics2D gButton = (Graphics2D) g.create();
        
        BufferedImage text;
        BufferedImage button;
        
        if(mouse!=null){
            Rectangle rect = new Rectangle(x, y, width, height);
            hover = rect.contains(mouse.mouseX, mouse.mouseY);
        }
        
        if(radio && selected){hover=true;}
        

        
        if(hover){
            text=hoverText;
            button=hoverButton;
        }else{
            text=normalText;
            button=normalButton;
        }
        
        if(smoothHover){
            gButton.drawImage(normalButton, x, y, null);
            drawText(gButton, width/2, height/2, normalText, title);//
            
            if(hover){
                if(opacity<=1-opacityChangeRate){opacity+=opacityChangeRate;}
            }else{
                if(opacity-opacityChangeRate>=0){opacity-=opacityChangeRate;}
            }
            
            gButton.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            gButton.drawImage(hoverButton, x, y, null);
            drawText(gButton, width/2, height/2, hoverText, title);
        }else{
            gButton.drawImage(button, x, y, null);
            drawText(gButton, width/2, height/2, text, title);
        }
    }
    
}

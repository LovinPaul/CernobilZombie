package Game.UI.Components;

import Main.Textures.LoadTextures;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Lable extends Component{
    private BufferedImage normalText;
    private BufferedImage hoverText;
    
    private BufferedImage drawText;
    
    private String text;
    
    public Lable(LoadTextures textures) {
        super(textures);
        normalText = textures.normalText;
        hoverText = textures.hoverText;
        centerText=true;
        
        drawText = normalText;
    }
    
    public void setText(String text){
        this.text=text;
    }

    public void setHoverText(boolean hover){
        if(hover){
            drawText = hoverText;
        }else{
            drawText = normalText;
        }
    }
    
    public void draw(Graphics g) {
        drawText(g, 0, 0, drawText, text);
    }
    
    
}

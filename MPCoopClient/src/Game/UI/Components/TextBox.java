package Game.UI.Components;

import Input.Keyboard;
import Input.Mouse;
import Main.Textures.LoadTextures;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TextBox extends Component{
    
    BufferedImage normalText;
    BufferedImage hoverText;
    
    private String text;
    private int carIndicatorTime;
    private int carIndicator;
    private int carPosition;
    
    private boolean edit=true;
    
    Keyboard key;
    Mouse mouse;
    
    public TextBox(Keyboard key, Mouse mouse, LoadTextures textures) {
        super(textures);
        this.key=key;
        this.mouse=mouse;
        normalText = textures.normalText;
        hoverText = textures.hoverText;
        centerText=false;
    }
    
    public void setText(String text){
        this.text=text;
        carPosition = text.length();
    }
    public void setEdit(boolean edit){
        this.edit=edit;
    }
    
    public String getText(){
        return text;
    }
    
    public static boolean isPunctuation(char c) {
        return c == ','
            || c == '.'
            || c == '<'
            || c == '>'
            || c == '?'
            || c == '/'
            || c == ';'
            || c == ':'
            || c == '"'
            || c == ']'
            || c == '}'
            || c == '['
            || c == '{'
            || c == '='
            || c == '+'
            || c == '-'
            || c == '_'
            || c == ')'
            || c == '('
            || c == '`'
            || c == '~'
            || c == '!'
            || c == '@'
            || c == '#'
            || c == '$'
            || c == '%'
            || c == '^'
            || c == '&'
            || c == '*'
            ;
    }
    
    public void draw(Graphics g){
        
        int newIntChar = key.getCharCodeConsume();
        
        if(edit){
            String textBeforeCar=text.substring(0, carPosition);
            String textAfterCar=text.substring(carPosition, text.length());
            
            char newChar = (char) newIntChar;
            switch(newIntChar){
                case -1:
                    break;
                case 8:
                    if(carPosition>0){
                        textBeforeCar = textBeforeCar.substring(0, textBeforeCar.length()-1);
                        carPosition--;
                    }
                    break;
                case 37:
                    if(carPosition>0){carPosition--;}
                    break;
                case 39:
                    if(carPosition<text.length()){carPosition++;}
                    break;
                case 127:
                    if(textAfterCar.length()>0){
                        textAfterCar = textAfterCar.substring(1, textAfterCar.length());
                    }
                    break;
                default:
                    if(Character.isAlphabetic(newChar) || Character.isSpaceChar(newChar) || Character.isDigit(newChar) || isPunctuation(newChar)){
                        textBeforeCar += newChar;
                        carPosition++;
                    }
                    break;
            }
            text = textBeforeCar+textAfterCar;
            
            if(carIndicator>20){carIndicator=0;}
            if(carIndicator>10){
                g.setColor(Color.red);
                g.drawLine(x+(16*carPosition)-1, y, x+(16*carPosition)-1, y+16);
            }
            carIndicator++;
            
            
            
        }

        drawText(g, 0, 0, normalText, text);
        
    }
    
    
}

package Game.UI.Components;

import Main.Textures.LoadTextures;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Component {
    
    LoadTextures textures;
    
    
    int x;
    int y;
    int width=290;
    int height=40;
    
    float charSize = 1f;
    
    protected boolean centerText=true;

    public Component(LoadTextures textures) {
        this.textures = textures;
    }
    
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    
    
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public void setCharSize(float size){
        charSize = size;
    }
    
    
    
    protected void drawText(Graphics g, int x, int y, BufferedImage text, String textString){
        Graphics2D gText = (Graphics2D) g.create();
//        textString=textString.toUpperCase();
        
        int textPositionY;
        int textPositionX;
        if(centerText){
            textPositionY=this.y+y-text.getHeight()/2;
            textPositionX=this.x+x-textString.length()*16/2;//+x
        }else{
            textPositionY=this.y+y;
            textPositionX=this.x+x;
        }
        
        
        
        int charPosition = 0;
        for(char ch : textString.toCharArray()){
            switch(ch){
                case ' ':
                    charPosition++;
                    break;
                case 'A':
                    gText.drawImage(text.getSubimage(0*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'B':
                    gText.drawImage(text.getSubimage(1*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'C':
                    gText.drawImage(text.getSubimage(2*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'D':
                    gText.drawImage(text.getSubimage(3*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'E':
                    gText.drawImage(text.getSubimage(4*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'F':
                    gText.drawImage(text.getSubimage(5*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'G':
                    gText.drawImage(text.getSubimage(6*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'H':
                    gText.drawImage(text.getSubimage(7*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'I':
                    gText.drawImage(text.getSubimage(8*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'J':
                    gText.drawImage(text.getSubimage(9*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'K':
                    gText.drawImage(text.getSubimage(10*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'L':
                    gText.drawImage(text.getSubimage(11*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'M':
                    gText.drawImage(text.getSubimage(12*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'N':
                    gText.drawImage(text.getSubimage(13*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'O':
                    gText.drawImage(text.getSubimage(14*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'P':
                    gText.drawImage(text.getSubimage(15*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'Q':
                    gText.drawImage(text.getSubimage(16*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'R':
                    gText.drawImage(text.getSubimage(17*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'S':
                    gText.drawImage(text.getSubimage(18*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'T':
                    gText.drawImage(text.getSubimage(19*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'U':
                    gText.drawImage(text.getSubimage(20*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'V':
                    gText.drawImage(text.getSubimage(21*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'W':
                    gText.drawImage(text.getSubimage(22*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'X':
                    gText.drawImage(text.getSubimage(23*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'Y':
                    gText.drawImage(text.getSubimage(24*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'Z':
                    gText.drawImage(text.getSubimage(25*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '1':
                    gText.drawImage(text.getSubimage(26*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '2':
                    gText.drawImage(text.getSubimage(27*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '3':
                    gText.drawImage(text.getSubimage(28*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '4':
                    gText.drawImage(text.getSubimage(29*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;                    
                case '5':
                    gText.drawImage(text.getSubimage(30*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;                    
                case '6':
                    gText.drawImage(text.getSubimage(31*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;                    
                case '7':
                    gText.drawImage(text.getSubimage(32*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;                    
                case '8':
                    gText.drawImage(text.getSubimage(33*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;                    
                case '9':
                    gText.drawImage(text.getSubimage(34*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;                    
                case '0':
                    gText.drawImage(text.getSubimage(35*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'a':
                    gText.drawImage(text.getSubimage(36*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'b':
                    gText.drawImage(text.getSubimage(37*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'c':
                    gText.drawImage(text.getSubimage(38*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'd':
                    gText.drawImage(text.getSubimage(39*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'e':
                    gText.drawImage(text.getSubimage(40*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'f':
                    gText.drawImage(text.getSubimage(41*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'g':
                    gText.drawImage(text.getSubimage(42*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'h':
                    gText.drawImage(text.getSubimage(43*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'i':
                    gText.drawImage(text.getSubimage(44*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'j':
                    gText.drawImage(text.getSubimage(45*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'k':
                    gText.drawImage(text.getSubimage(46*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'l':
                    gText.drawImage(text.getSubimage(47*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'm':
                    gText.drawImage(text.getSubimage(48*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'n':
                    gText.drawImage(text.getSubimage(49*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'o':
                    gText.drawImage(text.getSubimage(50*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'p':
                    gText.drawImage(text.getSubimage(51*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'q':
                    gText.drawImage(text.getSubimage(52*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'r':
                    gText.drawImage(text.getSubimage(53*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 's':
                    gText.drawImage(text.getSubimage(54*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 't':
                    gText.drawImage(text.getSubimage(55*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'u':
                    gText.drawImage(text.getSubimage(56*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'v':
                    gText.drawImage(text.getSubimage(57*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'w':
                    gText.drawImage(text.getSubimage(58*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'x':
                    gText.drawImage(text.getSubimage(59*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'y':
                    gText.drawImage(text.getSubimage(60*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case 'z':
                    gText.drawImage(text.getSubimage(61*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                    
                case '!':
                    gText.drawImage(text.getSubimage(62*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '"':
                    gText.drawImage(text.getSubimage(63*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '#':
                    gText.drawImage(text.getSubimage(64*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '$':
                    gText.drawImage(text.getSubimage(65*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '%':
                    gText.drawImage(text.getSubimage(66*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '&':
                    gText.drawImage(text.getSubimage(67*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '\'':
                    gText.drawImage(text.getSubimage(68*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '(':
                    gText.drawImage(text.getSubimage(69*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case ')':
                    gText.drawImage(text.getSubimage(70*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '*':
                    gText.drawImage(text.getSubimage(71*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '+':
                    gText.drawImage(text.getSubimage(72*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case ',':
                    gText.drawImage(text.getSubimage(73*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '-':
                    gText.drawImage(text.getSubimage(74*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '.':
                    gText.drawImage(text.getSubimage(75*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '/':
                    gText.drawImage(text.getSubimage(76*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case ':':
                    gText.drawImage(text.getSubimage(77*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case ';':
                    gText.drawImage(text.getSubimage(78*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '<':
                    gText.drawImage(text.getSubimage(79*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '=':
                    gText.drawImage(text.getSubimage(80*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '>':
                    gText.drawImage(text.getSubimage(81*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '?':
                    gText.drawImage(text.getSubimage(82*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
                case '\\':
                    gText.drawImage(text.getSubimage(83*16, 0, 12, 16), textPositionX+charPosition*16, textPositionY, (int)(charSize*12), (int)(charSize*16), null);
                    charPosition++;
                    break;
            }
        }
    }
    
}

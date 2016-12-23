package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{//extends Input 

    //Keys
//    public boolean A;
//    public boolean D;
//    public boolean W;
//    public boolean S;
//    public boolean g;
//    public boolean R;
//    public boolean P;
    public boolean t;
    public boolean c=true;
    public boolean resourceMonitor;
    public boolean esc;
    public boolean cons;
    
    private int goUpKey=87;
    private int goDownKey=83;
    private int goLeftKey=65;
    private int goRightKey=68;
    private int reloadKey=82;
    private int statsKey=80;
    
    private int chatAreaKey=72;
    private int chatInputKey=80;
    
    private boolean goUp;
    private boolean goDown;
    private boolean goLeft;
    private boolean goRight;
    private boolean reload;
    private boolean stats;
    
    public boolean chatArea;
    private boolean chatInput;
    private boolean chatArea_typed;
    private boolean chatInput_typed;
    
    public int typedCharCode;
    public char typedChar;
    public boolean newChar;
    public boolean backSpace;
    
    //38 up
    //37 left
    //39 right
    //40 down
    
    public int getCharCodeConsume(){
        int returnChar = typedCharCode;
        typedCharCode=-1;
        return returnChar;
    }
    public int getCharCode(){
        newChar=false;
        backSpace=false;
        return typedCharCode;
    }
    public char getChar(){
        newChar=false;
        backSpace=false;
        return typedChar;
    }
    public void setKeys(int goUpKey, int goDownKey, int goLeftKey, int goRightKey, int reloadKey, int statsKey){
        this.goUpKey = goUpKey;
        this.goDownKey = goDownKey;
        this.goLeftKey = goLeftKey;
        this.goRightKey = goRightKey;
        this.reloadKey = reloadKey;
        this.statsKey = statsKey;
        
    }
    
    public boolean goUp(){
        return goUp;
    }
    public boolean goDown(){
        return goDown;
    }
    public boolean goLeft(){
        return goLeft;
    }
    public boolean goRight(){
        return goRight;
    }
    public boolean reload(){
        return reload;
    }
    public boolean stats(){
        return stats;
    }
    public boolean chatArea(){
        return chatArea;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        typedCharCode = e.getKeyCode();
        typedChar = e.getKeyChar();
        newChar=true;
        chatArea=false;
//        System.out.println(e.getKeyCode());
        
        
        
        if(e.getKeyCode()==8){backSpace=true;}
        
        if(e.getKeyCode()==goUpKey){goUp=true;}
        if(e.getKeyCode()==goDownKey){goDown=true;}
        if(e.getKeyCode()==goLeftKey){goLeft=true;}
        if(e.getKeyCode()==goRightKey){goRight=true;}
        if(e.getKeyCode()==reloadKey){reload=true;}
        if(e.getKeyCode()==statsKey){stats=true;}

        if(e.getKeyCode()==chatAreaKey && !chatArea_typed){chatArea=true;chatArea_typed=true;}//else{chatArea=false;}
        
        if(e.getKeyChar()=='t'){t=true;}
//        if(e.getKeyChar()=='c'){c=true;}
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){esc=!esc;}
        if(e.getKeyCode() == 192){cons=!cons;}
        
        if(e.getKeyCode()==goUpKey){goUp=false;}
        if(e.getKeyCode()==goDownKey){goDown=false;}
        if(e.getKeyCode()==goLeftKey){goLeft=false;}
        if(e.getKeyCode()==goRightKey){goRight=false;}
        if(e.getKeyCode()==reloadKey){reload=false;}
        if(e.getKeyCode()==statsKey){stats=false;}
        
        if(e.getKeyCode()==chatAreaKey){chatArea_typed=false; chatArea=false;}
//        if(e.getKeyCode()==chatInputKey){chatInput_typed=!chatInput_typed;}
        
        if(e.getKeyChar()=='t'){t=false;}
        if(e.getKeyChar()=='c'){c=!c;}
        if(e.getKeyChar()=='x'){resourceMonitor=!resourceMonitor;}
        
    }
    
}

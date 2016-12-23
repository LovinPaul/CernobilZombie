package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard extends Input implements KeyListener{

    
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        if(e.getKeyCode()==38){up=true;}
//        if(e.getKeyCode()==40){down=true;}

        if(e.getKeyChar() == 'w'){W=true;}
        if(e.getKeyChar() == 's'){S=true;}
        if(e.getKeyChar() == 'a'){A=true;}
        if(e.getKeyChar() == 'd'){D=true;}
        if(e.getKeyChar() == 'x'){specialKey=true;}
//        if(e.getKeyChar() == 'g'){G=true;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
//            if(e.getKeyCode()==38){up=false;}
//            if(e.getKeyCode()==40){down=false;}
            
            if(e.getKeyChar() == 'w'){W=false;}
            if(e.getKeyChar() == 's'){S=false;}
            if(e.getKeyChar() == 'a'){A=false;}
            if(e.getKeyChar() == 'd'){D=false;}
            if(e.getKeyChar() == 'x'){specialKey=false;}
            
            if(e.getKeyChar() == 'g'){G=true;}
            
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                esc=!esc;
            }
    }
    
}

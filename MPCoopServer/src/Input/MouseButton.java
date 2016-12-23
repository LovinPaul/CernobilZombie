package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseButton extends Input implements MouseListener{// 
    //Buttons
//    public boolean button1;
//    public boolean button2;
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1){button1=true;}
        if(e.getButton()==3){button2=true;}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==1){button1=false;}
        if(e.getButton()==3){button2=false;}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}

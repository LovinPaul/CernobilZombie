package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse  implements MouseListener, MouseMotionListener, MouseWheelListener{// extends Input
    //MouseButtons
    public boolean b1Down;
    public boolean b2Down;
    
    public boolean b1Pressed;
    public boolean b2Pressed;
    public boolean b1Released;
    public boolean b2Released;
    
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1){b1Down=true;}
        if(e.getButton()==3){b2Down=true;}
        if(e.getButton()==1){b1Pressed=true;}
        if(e.getButton()==3){b2Pressed=true;}        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==1){b1Down=false;}
        if(e.getButton()==3){b2Down=false;}
        if(e.getButton()==1){b1Released=true;}
        if(e.getButton()==3){b2Released=true;}
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    //MouseMovement
    public float mouseX;
    public float mouseY;
    @Override
    public void mouseDragged(MouseEvent e) {
        
        mouseX=e.getX();
        mouseY=e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
    }
    
    //MouseWhell
    public int mouseWhell;
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWhell = e.getWheelRotation();
    }
}

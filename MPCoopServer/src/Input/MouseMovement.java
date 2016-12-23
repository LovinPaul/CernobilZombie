package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMovement extends Input implements MouseMotionListener{ //
//    public float mouseX;
//    public float mouseY;
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
    
}

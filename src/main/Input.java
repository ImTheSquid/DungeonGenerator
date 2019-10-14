package main;

import java.awt.*;
import java.awt.event.*;

public class Input implements MouseWheelListener, MouseListener, MouseMotionListener, KeyListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        Plane.changeBoxSize(mouseWheelEvent.getWheelRotation()<0?1:-1);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Plane.setSingleSelect(mouseEvent.getButton()==MouseEvent.BUTTON3);
        int pointXOff=0,pointYOff=0;
        if(mouseEvent.getX()-Plane.getCamX()<0)pointXOff=-1;
        if(mouseEvent.getY()-Plane.getCamY()<0)pointYOff=-1;
        Plane.setSelectedPoint(new Point((mouseEvent.getX()-Plane.getCamX())/Plane.getBoxSize()+pointXOff,(mouseEvent.getY()-Plane.getCamY())/Plane.getBoxSize()+pointYOff));
    }

    private int x1,y1;
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        x1=mouseEvent.getX();
        y1=mouseEvent.getY();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Plane.doDrag(new Point(mouseEvent.getX()-x1,mouseEvent.getY()-y1));
        x1=mouseEvent.getX();
        y1=mouseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==KeyEvent.VK_BACK_SPACE)Plane.removeSelectedCell();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

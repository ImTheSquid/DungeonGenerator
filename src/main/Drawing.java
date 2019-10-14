package main;

import java.awt.*;

public class Drawing {
    public static void fillRect(Point pos, int x, int y, int percentWidth, int percentHeight){
        MainFrame.g.fillRect((int)pos.getX()*Plane.getBoxSize()+x,(int)pos.getY()*Plane.getBoxSize()+y,percentWidth* Plane.getBoxSize(),percentHeight* Plane.getBoxSize());
    }

    public static void drawRect(Point pos, int x, int y, int percentWidth, int percentHeight){
        MainFrame.g.drawRect((int)pos.getX()*Plane.getBoxSize()+x,(int)pos.getY()*Plane.getBoxSize()+y,percentWidth* Plane.getBoxSize(),percentHeight* Plane.getBoxSize());
    }
}

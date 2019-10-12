package main;

import java.awt.*;

public class Drawing {
    public static void fillRect(Point pos, int x, int y, int percentWidth, int percentHeight){
        MainFrame.g.fillRect((int)pos.getX()*25+x,y,percentWidth* Plane.getBoxSize(),percentHeight* Plane.getBoxSize());
    }
}

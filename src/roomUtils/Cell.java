package roomUtils;

import java.awt.*;

public abstract class Cell{
    Point pos;

    Cell(Point pos){
        this.pos=pos;
    }

    public abstract void update();

    public abstract void render();
}

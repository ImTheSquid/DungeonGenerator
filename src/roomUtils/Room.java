package roomUtils;

import main.Drawing;
import main.MainFrame;

import java.awt.*;

public class Room extends Cell{
    public Room(Point pos, Color c, int width, int height){super(pos, c, width, height);}

    @Override
    public void update() {
    }

    @Override
    public void render() {
        MainFrame.g.setColor(c);
        for(int w=0;w<width;w++)
            for(int h=0;h<height;h++)
                Drawing.fillRect(new Point((int)pos.getX()+w,(int)pos.getY()+h), 0, 0, 1, 1);
    }

    @Override
    public void renderOutline() {
        MainFrame.g.setColor(Color.RED);
        MainFrame.g.setStroke(new BasicStroke(2));
        Drawing.drawRect(pos, 0,0,width,height);
        MainFrame.g.setStroke(new BasicStroke(1));
    }
}

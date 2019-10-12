package roomUtils;

import main.Drawing;
import main.MainFrame;

import java.awt.*;

public class Room extends Cell{
    public Room(Point pos) {
        super(pos);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        MainFrame.g.setColor(Color.WHITE);
        Drawing.fillRect(pos, 0, 0, 1, 1);
    }
}

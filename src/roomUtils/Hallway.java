package roomUtils;

import main.Drawing;
import main.MainFrame;

import java.awt.*;

public class Hallway extends Cell{
    private Orientation orientation;
    Hallway(Point pos, Orientation o) {
        super(pos);
        orientation=o;
    }

    public Hallway(Point pos, Color c, Orientation o) {
        super(pos, c);
        orientation=o;
    }

    @Override
    public void update() {
        if(orientation==Orientation.HORIZONTAL){
            width=9;
            height=5;
        }else{
            width=5;
            height=9;
        }
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

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Edge getEdgeConnection(Direction d) {
        if(orientation==Orientation.VERTICAL){
            if(d==Direction.EAST||d==Direction.WEST) return null;
        }else{
            if(d==Direction.NORTH||d==Direction.SOUTH) return null;
        }
        return super.getEdgeConnection(d);
    }

    public static int[] getDims(Orientation o){
        return o==Orientation.HORIZONTAL?new int[]{9,5}:new int[]{5,9};
    }
}

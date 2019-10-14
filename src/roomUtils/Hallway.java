package roomUtils;

import java.awt.*;

public class Hallway extends Cell{
    private Orientation orientation;
    Hallway(Point pos, Orientation o) {
        super(pos);
        orientation=o;
    }

    Hallway(Point pos, Color c, Orientation o) {
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

    }

    @Override
    public void renderOutline() {

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
}

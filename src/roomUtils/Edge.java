package roomUtils;

import java.awt.*;

//Defines an edge that can be used to connect to another Cell
public class Edge {
    private Point start, end;
    private Direction direction;

    Edge(Point start, Point end, Direction d){
        this.start=start;
        this.end=end;
        this.direction =d;
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public int getXLength(){
        return (int) Math.abs(start.getX()-end.getX());
    }

    public int getYLength(){
        return (int) Math.abs(start.getY()-end.getY());
    }

    public boolean canConnect(Edge e){
        return false;
    }

    public Orientation getOrientation() {
        return direction ==Direction.NORTH|| direction ==Direction.SOUTH?Orientation.HORIZONTAL:Orientation.VERTICAL;
    }

    public Direction getDirection() {
        return direction;
    }
}

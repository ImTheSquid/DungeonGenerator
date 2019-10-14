package roomUtils;

import java.awt.*;

enum Orientation{
    VERTICAL, HORIZONTAL
}

public abstract class Cell{
    Point pos;
    Color c;
    int width=1,height=1;

    Cell(Point pos){
        this.pos=pos;
        c=Color.WHITE;
    }

    Cell(Point pos, Color c){
        this.pos=pos;
        this.c=c;
    }

    Cell(Point pos, Color c, int width, int height){
        this.pos=pos;
        this.c=c;
        this.width=width;
        this.height=height;
    }

    public abstract void update();

    public abstract void render();

    public abstract void renderOutline();

    //Gets where another cell could connect
    public Edge getEdgeConnection(Direction d){
        switch(d){
            case NORTH:
                return new Edge(pos, new Point((int)pos.getX()+width-1,(int)pos.getY()), Direction.NORTH);
            case SOUTH:
                return new Edge(new Point((int)pos.getX(), (int)pos.getY()+height-1), new Point((int)pos.getX()+width-1, (int)pos.getY()+height-1), Direction.SOUTH);
            case EAST:
                return new Edge(new Point((int)pos.getX()+width-1, (int)pos.getY()), new Point((int)pos.getX()+width-1, (int)pos.getY()+height-1), Direction.EAST);
            case WEST:
                return new Edge(pos, new Point((int)pos.getX(), (int)pos.getY()+height-1), Direction.WEST);
        }
        return null;
    }

    //Finds where to put Point to make a cell with a specified width, height, and Direction that connects to a target Edge
    public static Point convertToPlot(Edge target, int width, int height, Direction d){
        if(getOpposite(target.getDirection())!=d)return null;
        switch(d){
            case NORTH: return new Point((int)target.getStart().getX(), (int)target.getStart().getY()+1);
            case EAST:
            case SOUTH:
            case WEST:
        }
        return null;
    }

    public boolean contains(Point p){
        boolean x=p.getX()>=pos.getX()&&p.getX()<pos.getX()+width;
        boolean y=p.getY()>=pos.getY()&&p.getY()<pos.getY()+height;
        return x&&y;
    }

    private static Direction getOpposite(Direction d){
        switch(d){
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case EAST: return Direction.WEST;
            case WEST: return Direction.EAST;
        }
        return null;
    }
}

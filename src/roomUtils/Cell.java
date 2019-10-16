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

    //Finds where to put Point to make a cell with a specified width and height that faces a Direction that connects to a target Edge
    public static Point convertToPlot(Edge target, int width, int height, Direction d){
        if(getOpposite(target.getDirection())!=d)return null;
        int targetX,targetY;
        if(target.getOrientation()==Orientation.HORIZONTAL){
            if(width>target.getXLength())return null;
            targetX=(target.getXLength()-width)/2;
            if(d==Direction.NORTH){
                targetY=(int)target.getStart().getY()+1;
            }else{
                targetY=(int)target.getStart().getY()-height;
            }
        }else{
            if(height>target.getYLength())return null;
            targetY=(target.getYLength()-height)/2;
            if(d==Direction.EAST){
                targetX=(int)target.getStart().getX()-width;
            }else{
                targetX=(int)target.getStart().getX()+1;
            }
        }
        return new Point(targetX, targetY);
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

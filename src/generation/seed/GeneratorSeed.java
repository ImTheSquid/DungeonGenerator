package generation.seed;

import main.Plane;
import roomUtils.Cell;
import roomUtils.Direction;
import roomUtils.Hallway;
import roomUtils.Room;

import java.awt.*;
import java.util.ArrayList;

/*
This class uses recursive generation to create a dungeon.
Ex: Start with 1000 rooms to go, then generate one, leaving you with 999. If the random generator chooses 3 hallways
from that room, each hallway will generate 333 rooms, using the same strategy
 */
public class GeneratorSeed {
    //Total rooms, rooms left to generate on branch, number of remainder rooms to generate due to integer division
    private int totalRoomsToGenerate, branchRoomsLeft, branchOverflow, minHalls, maxHalls, minBossroom, maxBossroom, minRoomHalls, maxRoomHalls, roomsGenerated=0;
    //Defines the current set of rooms on the current recursive branch

    GeneratorSeed(int numRooms, int minHalls, int maxHalls, int minBoss, int maxBoss, int minRoomHalls, int maxRoomHalls){
        totalRoomsToGenerate=numRooms;
        this.maxBossroom=maxBoss;
        this.minBossroom=minBoss;
        this.maxHalls=maxHalls;
        this.minHalls=minHalls;
        this.minRoomHalls=minRoomHalls;
        this.maxRoomHalls=maxRoomHalls;
    }

    public void stepGenerator(){
        Room r=new Room(new Point(0, 0), Color.RED, 15, 9);
        Plane.getCells().add(r);
        generateRooms(totalRoomsToGenerate, r, Direction.NORTH);
    }

    private void generateRooms(int roomsToGenerate, Cell srcCell, Direction srcDir){
        if(roomsToGenerate==0)return;
        int hallsToGenerate= randomVal(minRoomHalls, maxRoomHalls);
        Direction[] dirsToGo=generateDirections(srcDir, hallsToGenerate);
        for(Direction d:dirsToGo){
            Cell c=generateHallways(randomVal(minHalls, maxHalls),srcCell,d);
            Room r=addNewStandardRoom(c, d);
            generateRooms(roomsToGenerate-1, r, d);
        }
    }

    private static Room addNewStandardRoom(Cell ref, Direction srcDir){
        Room r=new Room(Cell.convertToPlot(ref.getEdgeConnection(srcDir), 15, 9), Color.WHITE, 15, 9);
        Plane.getCells().add(r);
        return r;
    }

    private static int randomVal(int min, int max){
        return (int)(Math.random()*(max-min))+min;
    }

    private static Direction[] generateDirections(Direction src, int numToGenerate){
        if(numToGenerate>3)return null;
        Direction[] dirs=new Direction[numToGenerate];
        for(int i=0;i<numToGenerate;i++){
            Direction d=chooseRandomDirection();
            while(checkContain(d, dirs)&&d!=src)d=chooseRandomDirection();
            dirs[i]=d;
        }
        return dirs;
    }

    private static boolean checkContain(Direction d, Direction[] arr){
        for(Direction dir:arr){
            if(d==dir)return true;
        }
        return false;
    }

    private static Direction chooseRandomDirection(){
        switch((int)(Math.random()*4)){
            case 0:return Direction.NORTH;
            case 1:return Direction.SOUTH;
            case 2:return Direction.EAST;
            default:
            case 3:return Direction.WEST;
        }
    }

    private static Cell generateHallways(int num, Cell source, Direction dest){
        if(num==0)return source;
        int[] dims=Hallway.getDims(Cell.dirToOri(dest));
        Hallway h=new Hallway(Cell.convertToPlot(source.getEdgeConnection(dest), dims[0], dims[1]), Color.LIGHT_GRAY, Cell.dirToOri(dest));
        Plane.getCells().add(h);
        return generateHallways(num-1, h, dest);
    }

    public boolean isFinished(){
        return false;
    }
}

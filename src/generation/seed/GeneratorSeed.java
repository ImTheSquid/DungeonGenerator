package generation.seed;

import main.Plane;
import roomUtils.Cell;
import roomUtils.Direction;
import roomUtils.Hallway;
import roomUtils.Orientation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
This class uses recursive generation to create a dungeon.
Ex: Start with 1000 rooms to go, then generate one, leaving you with 999. If the random generator chooses 3 hallways
from that room, each hallway will generate 333 rooms, using the same strategy
 */
public class GeneratorSeed {
    //Total rooms, rooms left to generate on branch, number of remainder rooms to generate due to integer division
    private int totalRoomsToGenerate, branchRoomsLeft, branchOverflow, minHalls, maxHalls, minBossroom, maxBossroom, minRoomHalls, maxRoomHalls, roomsGenerated=0;
    //Defines the current set of rooms on the current recursive branch
    private ArrayList<Cell> currentBranch=new ArrayList<>();

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

    }

    private void generateRooms(int roomsToGenerate){
        if(roomsToGenerate==0)return;
        int hallsToGenerate=(int)(Math.random()*(maxRoomHalls-minRoomHalls))+minRoomHalls;
        generateRooms(roomsToGenerate/hallsToGenerate);
    }

    private static void generateHallways(int num, Cell source, Direction dest){
        if(num==0)return;
        int[] dims=Hallway.getDims(Cell.dirToOri(dest));
        Hallway h=new Hallway(Cell.convertToPlot(source.getEdgeConnection(dest), dims[0], dims[1]), Color.WHITE, Cell.dirToOri(dest));
        Plane.getCells().add(h);
        generateHallways(num-1, h, dest);
    }

    public boolean isFinished(){
        return false;
    }
}

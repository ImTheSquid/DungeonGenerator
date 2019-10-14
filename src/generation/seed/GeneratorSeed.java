package generation.seed;

import roomUtils.Cell;
import roomUtils.Direction;

import java.util.ArrayList;
import java.util.Random;

/*
This class uses recursive generation to create a dungeon.
Ex: Start with 1000 rooms to go, then generate one, leaving you with 999. If the random generator chooses 3 hallways
from that room, each hallway will generate 333 rooms, using the same strategy
 */
public class GeneratorSeed {
    //Total rooms, rooms left to generate on branch, number of remainder rooms to generate due to integer division
    private int totalRoomsToGenerate, branchRoomsLeft, branchOverflow, minHalls, maxHalls, minBossroom, maxBossroom, minRoomHalls, maxRoomHalls;
    private Direction[] restrictedDirections;
    //Defines the current set of rooms on the current recursive branch
    private ArrayList<Cell> currentBranch=new ArrayList<>();

    GeneratorSeed(int numRooms, int minHalls, int maxHalls, int minBoss, int maxBoss, int minRoomHalls, int maxRoomHalls, Direction[] restrictedDirections){
        totalRoomsToGenerate=numRooms;
        this.restrictedDirections=restrictedDirections;
        this.maxBossroom=maxBoss;
        this.minBossroom=minBoss;
        this.maxHalls=maxHalls;
        this.minHalls=minHalls;
        this.minRoomHalls=minRoomHalls;
        this.maxRoomHalls=maxRoomHalls;
    }

    public void stepGenerator(){

    }

    public boolean isFinished(){
        return false;
    }
}

package main;

import roomUtils.Cell;

import java.awt.*;
import java.util.ArrayList;

class Plane {
    private static int camX=0,camY=0;
    private static int boxSize=25;
    private static ArrayList<Cell> cells=new ArrayList<>();
    static void render(){
        renderMap();
    }

    private static void renderMap(){
        MainFrame.g.setColor(new Color(46, 46, 46));
        int xOff=-camX+camX%boxSize-boxSize;
        int yOff=-camY+camY%boxSize-boxSize;
        int endXOff=Main.f.getWidth()-camX;
        int endYOff=Main.f.getHeight()-camY;
        for(int i=xOff;i<endXOff;i+=boxSize)
            MainFrame.g.drawLine(i,yOff,i,endYOff);
        for(int i=yOff;i<endYOff;i+=boxSize)
            MainFrame.g.drawLine(xOff,i,endXOff,i);
    }

    static void changeBoxSize(int val){
        if(val+boxSize>0)
        boxSize+=val;
    }

    static int getCamX() {
        return camX;
    }

    static int getCamY() {
        return camY;
    }

    static void doDrag(Point dist){
        camX+=dist.getX();
        camY+=dist.getY();
    }

    static int getBoxSize() {
        return boxSize;
    }

    static ArrayList<Cell> getCells() {
        return cells;
    }
}

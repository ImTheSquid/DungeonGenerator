package main;

import roomUtils.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Plane {
    private static int camX=0,camY=0;
    private static int boxSize=25;
    private static ConcurrentLinkedQueue<Cell> cells=new ConcurrentLinkedQueue<>();
    private static Point selectedPoint=new Point(0,0);
    private static Cell selectedCell=null;
    //Whether or not to auto-select multiple cells at once
    private static boolean singleSelect=false;
    static void render(){
        renderMap();
    }

    static void postRender(){
        renderSelectedPoint();
    }

    private static void renderMap(){
        MainFrame.g.setColor(new Color(46, 46, 46));
        int xOff=-camX+camX%boxSize-boxSize;
        int yOff=-camY+camY%boxSize-boxSize;
        int endXOff=Main.mainFrame.getWidth()-camX;
        int endYOff=Main.mainFrame.getHeight()-camY;
        for(int i=xOff;i<endXOff;i+=boxSize)
            MainFrame.g.drawLine(i,yOff,i,endYOff);
        for(int i=yOff;i<endYOff;i+=boxSize)
            MainFrame.g.drawLine(xOff,i,endXOff,i);
    }

    private static void renderSelectedPoint(){
        MainFrame.g.setColor(Color.RED);
        MainFrame.g.setStroke(new BasicStroke(2));
        selectedCell=null;
        for(Cell c:cells){
            if(c.contains(selectedPoint)){
                selectedCell=c;
                if(!singleSelect) {
                    c.renderOutline();
                    MainFrame.g.setStroke(new BasicStroke(1));
                    return;
                }else break;
            }
        }
        MainFrame.g.drawRect((int)selectedPoint.getX()*boxSize,(int)selectedPoint.getY()*boxSize,boxSize,boxSize);
        MainFrame.g.setStroke(new BasicStroke(1));
    }

    static void changeBoxSize(int val){
        if(val+boxSize>0)
        boxSize+=val;
    }

    static int getCamX() {
        return camX;
    }

    static void setCamX(int camX) {
        Plane.camX = camX;
    }

    static int getCamY() {
        return camY;
    }

    static void setCamY(int camY) {
        Plane.camY = camY;
    }

    static void doDrag(Point dist){
        camX+=dist.getX();
        camY+=dist.getY();
    }

    static int getBoxSize() {
        return boxSize;
    }

    public static ConcurrentLinkedQueue<Cell> getCells() {
        return cells;
    }

    static void setSelectedPoint(Point selectedPoint) {
        Plane.selectedPoint = selectedPoint;
    }

    static Point getSelectedPoint() {
        return selectedPoint;
    }

    static void removeSelectedCell(){
        if(selectedCell==null)return;
        cells.remove(selectedCell);
        selectedCell=null;
    }

    public static void setSingleSelect(boolean singleSelect) {
        Plane.singleSelect = singleSelect;
    }
}

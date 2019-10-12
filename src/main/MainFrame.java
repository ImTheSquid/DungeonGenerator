package main;

import roomUtils.Cell;
import roomUtils.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class MainFrame extends Canvas implements Runnable, MouseWheelListener, MouseListener, MouseMotionListener {
    private boolean isRunning=true,updateScreen=true;
    private int updates=0,currentFPS=0;
    private final int MAX_UPDATES=5;

    private long lastUpdateTime=0;

    private static int targetFPS=60,targetTime=1000000000/targetFPS;
    public static Graphics2D g=null;
    private BufferStrategy bs;

    MainFrame(){
        JFrame window=new JFrame("Generator");
        window.setLocationRelativeTo(null);
        //window.setMenuBar(generateMenus());
        window.setVisible(true);
        JPanel panel=(JPanel)window.getContentPane();
        panel.add(this);
        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(500,500));
        setIgnoreRepaint(true);

        createBufferStrategy(2);
        bs=getBufferStrategy();
        //Start game thread
        Thread t=new Thread(this);
        t.start();
        window.pack();

        //Test code
        Plane.getCells().add(new Room(new Point(0,0)));
    }

    public void run(){
        lastUpdateTime=System.nanoTime();
        int fps=0;
        long lastFpsCheck=System.nanoTime();
        while(isRunning) {
            long currentTime = System.nanoTime();
            //Lag protection
            updates = 0;
            while (currentTime - lastUpdateTime >= targetTime) {
                update();
                lastUpdateTime += targetTime;
                updates++;
                if (updates > MAX_UPDATES) break;
            }

            long startTime = System.nanoTime();
            render();

            //FPS counter
            fps++;
            if (System.nanoTime() >= lastFpsCheck + 1000000000) {
                currentFPS = fps;
                fps = 0;
                lastFpsCheck = System.nanoTime();
            }

            long timeTaken = System.nanoTime() - startTime;
            if (timeTaken < targetTime) {
                try {
                    Thread.sleep((targetTime - timeTaken) / 1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update(){
        for(Cell c:Plane.getCells()){
            c.update();
        }
        System.out.println("Current camera pos: "+Plane.getCamX()+","+Plane.getCamY());
    }

    private void render(){
        if(!updateScreen)return;
        g=(Graphics2D) bs.getDrawGraphics();
        if(g==null)return;
        initBackground();
        g.translate(Plane.getCamX(),Plane.getCamY());
        Plane.render();

        for(Cell c:Plane.getCells()){
            c.render();
        }


        bs.show();
    }

    private void initBackground(){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        Plane.changeBoxSize(mouseWheelEvent.getWheelRotation()<0?1:-1);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    private int x1,y1;
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        x1=mouseEvent.getX();
        y1=mouseEvent.getY();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Plane.doDrag(new Point(mouseEvent.getX()-x1,mouseEvent.getY()-y1));
        x1=mouseEvent.getX();
        y1=mouseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Frame extends Canvas implements Runnable{
    private boolean isRunning=true;
    private int updates=0,currentFPS=0;
    private final int MAX_UPDATES=5;

    private long lastUpdateTime=0;

    private static int targetFPS=60,targetTime=1000000000/targetFPS;
    private Graphics2D g=null;

    Frame(){
        setPreferredSize(new Dimension(500,500));
        Thread t=new Thread(this);
        t.start();
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

    }

    private void render(){
        BufferStrategy bs=getBufferStrategy();
        if(bs==null){
            createBufferStrategy(1);
            return;
        }
        g=(Graphics2D) bs.getDrawGraphics();
        initBackground();

        bs.show();
    }

    private void initBackground(){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
}

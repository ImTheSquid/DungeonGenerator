package main;

import generation.Generator;
import roomUtils.Cell;
import roomUtils.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class MainFrame extends Canvas implements Runnable{
    public static Input input=new Input();
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
        window.setJMenuBar(generateMenus());
        window.setVisible(true);
        JPanel panel=(JPanel)window.getContentPane();
        panel.add(this);
        addMouseWheelListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addKeyListener(input);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Component c=e.getComponent();
                resizePlane(c.getWidth(), c.getHeight());
            }
        });
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(500,500));
        setIgnoreRepaint(true);

        createBufferStrategy(2);
        bs=getBufferStrategy();
        //Start game thread
        Thread t=new Thread(this);
        t.start();
        window.pack();

        //Center
        resizePlane(getWidth(), getHeight());

        //Test code
        Plane.getCells().add(new Room(new Point(0,0),Color.BLUE,16,9));
    }

    private JMenuBar generateMenus(){
        JMenuBar bar= new JMenuBar();

        JMenu generation= new JMenu("Generation");
        generation.setMnemonic(KeyEvent.VK_G);
        bar.add(generation);

        JMenu cells=new JMenu("Cells");
        cells.setMnemonic(KeyEvent.VK_C);
        bar.add(cells);

        JMenuItem delete=new JMenuItem("Delete selected cell");
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Plane.removeSelectedCell();
            }
        });
        cells.add(delete);

        JMenu plane=new JMenu("Plane");
        plane.setMnemonic(KeyEvent.VK_P);
        bar.add(plane);

        JMenuItem resetPos=new JMenuItem("Go to origin");
        resetPos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        resetPos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Plane.setSelectedPoint(new Point(0,0));
                resizePlane(getWidth(), getHeight());
            }
        });
        plane.add(resetPos);

        return bar;
    }

    //Centers selected point on screen
    private void resizePlane(int width, int height){
        Plane.setCamX(width/2-(int)Plane.getSelectedPoint().getX()*Plane.getBoxSize());
        Plane.setCamY(height/2-(int)Plane.getSelectedPoint().getY()*Plane.getBoxSize());
    }

    //Game loop
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
        Generator.generateNext();
        for(Cell c:Plane.getCells()){
            c.update();
        }
        //System.out.println("Current camera pos: "+Plane.getCamX()+","+Plane.getCamY());
    }

    private void render(){
        if(!updateScreen)return;
        g=(Graphics2D) bs.getDrawGraphics();
        if(g==null)return;
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
        initBackground();
        g.translate(Plane.getCamX(),Plane.getCamY());
        Plane.render();

        for(Cell c:Plane.getCells()){
            c.render();
        }

        Plane.postRender();

        bs.show();
    }

    private void initBackground(){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
}

package Main;

import Game.*;
import Input.*;
import Game.Network.*;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Main implements Runnable{
    
    private Thread thread;
    private boolean running;
    private final int FPS = 30;
    private final long targetTime = 1000 / FPS;
    private boolean inGame;
    
    private static int width = 1366;
    private static int height = 600;
    
    public Input key;
//    public Input mouseMove;
//    public Input mouseClick;
    
    public JFrame frame;
    Game game;
//    Console console;
    
    public static void main(String args[]){
        
        Main app = new Main();
        app.init();
        
    }
    public static int getWidth(){
        return width;
    }
    public static int getHeight(){
        return height;
    }
    public void hideMousePointer(){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
        frame.getContentPane().setCursor(blankCursor);
    }
    
    
    private void init(){
        System.out.println("init()");
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        LoadTextures.init();
//        console = new Console(this);
//        console.setLocation(0, 0);
//        console.setSize(width, 125);
//        console.setVisible(true);
//        frame.add(console);
        
        key = new KeyBoard();
//        mouseMove = new MouseMovement();
//        mouseClick = new MouseButton();
        frame.addKeyListener((KeyListener) key);//
//        frame.addMouseMotionListener((MouseMotionListener) mouseMove);//(MouseMotionListener)
//        frame.addMouseListener((MouseListener) mouseClick);//(MouseListener)
        
        thread = new Thread(this);
        thread.start();
        running = true;
        frame.repaint();
        
        
    }
    
    public void initTDM(){
        System.out.println("initTeamDeathMatch()");
        
        game = new MPTDM();
        frame.add(game);
        TDM_Connection.addGame(game);
        TDM_Connection.listenForNewConnections();
        inGame=true;
    }

    @Override
    public void run() {
        initTDM();
        
        long start;
        long elapsed;
        long wait;
        while (running) {
            start = System.nanoTime();

            game.gameLoop();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {wait = 5;}
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
    
}

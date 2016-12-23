package Main;

import Game.UI.MainUI.MainMenu;
import Game.UI.MainUI.MainUI;
import Game.UI.MainUI.LoadingScreen;
import Main.Textures.LoadTextures;
import Game.Mods.*;
import Game.Network.Connection.Connection;
import Game.Network.Connection.TCPConnection;
import Game.UI.Components.ServerList;
import Main.Textures.Level;
import Main.Textures.MainMenuTextures;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import Main.Textures.Initilializer;


public class Main implements Runnable{
    
    private Thread thread;
    private boolean running;
    private final int FPS = 30;
    private final long targetTime = 1000 / FPS;
    private static int width = 1366;
    private static int height = 600;

//    private String tempName;
    
    public JFrame frame;
    Game game;
    Connection multiplayerGame;
    Console console;
    public SettingsManager settings;
    
    MainUI sparta;
    LoadingScreen loadingScreen;
    Initilializer texturetInitializer;
    public ResourceMonitor resourceMonitor;
    boolean tempBool;
    
    private int mainPage;
    private int nextMainPage;
    
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
        

        settings = new SettingsManager();
        settings.read();
        
        resourceMonitor = new ResourceMonitor();
        resourceMonitor.startMonitoring();
        
        frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(settings.width, settings.height);//
        if(settings.maximized){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        
        frame.setVisible(true);
        frame.setLayout(null);
        

//        console.setLocation(0, 0);
//        console.setSize(width, 125);
//        console.setVisible(true);
//        frame.add(console);
        
//        sparta = new LoadingScreen(frame);
//        sparta.initTextures(new MainMenuTextures(), -1);
//        sparta.setLocation(0, 0);
//        sparta.setSize(width,height);
//        frame.add(sparta);
        
        initMainMenu(0);
        
        thread = new Thread(this);
        thread.start();
        running=true;
        
                
        
        console = new Console(this);
        Thread consoleinput = new Thread(new Runnable(){
            @Override
            public void run() {
                while(running){
                    console.getInput();
                }
            }
        });
        consoleinput.start();
        
        
        hideMousePointer();
//        LoadTextures.init();
    }
    public void initMainMenu(int page){
        
        
        loadingScreen = new LoadingScreen(frame);
        loadingScreen.resourceMonitor = resourceMonitor;
        
        loadingScreen.setLocation(0, 0);
        loadingScreen.setSize(frame.getWidth(),frame.getHeight());
        loadingScreen.initTextures(new MainMenuTextures(), -1);
        frame.add(loadingScreen);
        
        mainPage=0;
        nextMainPage=1;
        
        sparta = new MainMenu(frame, settings);
        ((MainMenu)sparta).resourceMonitor = resourceMonitor;
        ((MainMenu)sparta).setMain(this);
        ((MainMenu)sparta).setMenuPage(page);
        
        sparta.setLocation(0, 0);
        sparta.setSize(width,height);
        frame.add(sparta);
        sparta.grabFocus();
        
        texturetInitializer = sparta;

    }
    public void initSP(int difficulty, int mapID){
        System.out.println("initSP()");
        

        
        game = new SP(settings, difficulty, (byte)mapID);

        

        loadingScreen = new LoadingScreen(frame);
        loadingScreen.resourceMonitor = resourceMonitor;
        loadingScreen.setLocation(0, 0);
        loadingScreen.setSize(width,height);
        loadingScreen.initTextures(new Level(), mapID);//game.getMapID()

        mainPage=0;
        nextMainPage=2;
        
        frame.remove(sparta);
        sparta=null;
        frame.add(loadingScreen);
        frame.add(game);
        game.grabFocus();
        texturetInitializer = game;
        ((SP)game).resourceMonitor = resourceMonitor;
        
    }
    
    public void launchGame(String ip, int tcpPort, int udpInPort, int udpOutPort){
        Thread th = new Thread(new Runnable(){
            @Override
            public void run(){
                ((MainMenu)sparta).setMessage("Connecting...");
                ((MainMenu)sparta).clickToReturn(false);
                ((MainMenu)sparta).setMenuPage(6);
                sparta.repaint();

                multiplayerGame = new Connection();
                multiplayerGame.setIp(ip);
                multiplayerGame.setTCPPort(tcpPort);
                multiplayerGame.setUDPInPort(udpInPort);
                multiplayerGame.setUDPOutPort(udpOutPort);
                
                if(multiplayerGame.tryToConnect()){
                    texturetInitializer = multiplayerGame;
                    
                    ((MainMenu)sparta).setMessage("Waiting for server");
                    ((MainMenu)sparta).clickToReturn(false);
                    sparta.repaint();
                    
                    do{
                        multiplayerGame.readParseTCP();
                    }while(!multiplayerGame.isServertInitReceived());
                    
                    multiplayerGame.setSettings(settings);
                    
                    String gameModeUI="Mode: ";
                    String gameMapUI="Map: ";

                    switch(multiplayerGame.getGameMode()){
                        case 0:
                            gameModeUI += "TDM";
                            break;
                        default:
                            gameModeUI += "Uknown(" + multiplayerGame.getGameMode()+")";
                            break;
                    }
                    switch(multiplayerGame.getGameMap()){
                        case 0:
                            gameMapUI += "Prologue";
                            break;
                        case 1:
                            gameMapUI += "UrbanDistrict9";
                            break;
                        case 2:
                            gameMapUI += "CR8";
                            break;
                        default:
                            gameMapUI += "Uknown(" + multiplayerGame.getGameMap()+")";
                            break;
                    }
                    
                    
                    loadingScreen = new LoadingScreen(frame);
                    loadingScreen.setLoadingText(gameModeUI + "   " + gameMapUI);
                    loadingScreen.resourceMonitor = resourceMonitor;
                    loadingScreen.setLocation(0, 0);
                    loadingScreen.setSize(width,height);
                    loadingScreen.initTextures(new Level(), multiplayerGame.getGameMap());//game.getMapID()

                    mainPage=0;
                    nextMainPage=3;

                    frame.remove(sparta);
                    sparta=null;
                    frame.add(loadingScreen);
                    frame.add(multiplayerGame.getGame());
                    multiplayerGame.getGame().grabFocus();
                    multiplayerGame.getGame().resourceMonitor = resourceMonitor;
                    resourceMonitor.monitorNetwork(multiplayerGame);
                    
//                    ((MPTDM)game).resourceMonitor = resourceMonitor;
//                    resourceMonitor.monitorNetwork(((MPTDM)game).getConnectionTemporary());
                    
                }else{
                    ((MainMenu)sparta).setMessage("Unable to connect.");
                    ((MainMenu)sparta).clickToReturn(true);
                    ((MainMenu)sparta).setMenuPage(6);
                }
            }
        });
        th.start();
    }
    public void launchGame(ServerList.ListElement server) {
        launchGame(server.getIP(), 4242, 4244, 4243);
    }

    
    @Override
    public void run() {
//        initGame();

        long start;
        long elapsed;
        long wait;
        while (running) {
            start = System.nanoTime();
            
            width = frame.getWidth();
            height = frame.getHeight();
            
            
            switch(mainPage){
                case -1:
                    break;
                case 0: //Loading Screen
                    if(loadingScreen.isLoadingComplete()){
                        texturetInitializer.setTexture(loadingScreen.getTextures());
                        texturetInitializer=null;
                        frame.remove(loadingScreen);

                        loadingScreen=null;
                        

                        frame.setTitle("Cernobil Zombie BETA");
                        System.gc();
                        mainPage=nextMainPage;
                    }else{
                        frame.setTitle("Loading " + loadingScreen.loadPercent() + "% ( " + 
                                Runtime.getRuntime().totalMemory()/1048576 + "  MB )");
                        loadingScreen.repaint();
                    }
                    break;
                case 1: //Sparta
                    sparta.repaint();
                    break;
                case 2: //SinglePlayer
                    if(game.isDisposed()){
                        resourceMonitor.disposeNetworkMonitor();
                        frame.remove(game);
                        game = null;
                        

                        initMainMenu(1);
                    }else{
                        game.gameLoop();
                    }
                    break;
                case 3: //MultiPlayer
                    
                    if(multiplayerGame.isGameDisposed()){
                        resourceMonitor.disposeNetworkMonitor();
                        frame.remove(multiplayerGame.getGame());
                        multiplayerGame.dispose();
                        multiplayerGame = null;
                        

                        initMainMenu(1);
                    }else{
                        multiplayerGame.gameLoop();
                    }
                    
                    break;
                case 4:
                    break;
                default:
                    break;
            }
            
            
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

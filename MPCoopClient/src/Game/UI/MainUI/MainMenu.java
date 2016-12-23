package Game.UI.MainUI;

import Game.Animations.UI.MainBackground;
import Game.Animations.Intro.Presents;
import Game.Animations.Intro.GagarinStudio;
import Game.Animations.Intro.ZombieIntro;
import Game.Animations.Intro.LoadingComplete;
import Game.Animations.Blood.BloodSprite5;
import Game.Animations.*;
import Game.Animations.UI.CreditsZombieDance;
import Game.Network.Broadcast.BroadcastListen;
import Game.Pointer.UIPointer;
import Game.UI.Components.*;
import Input.Keyboard;
import Input.Mouse;
import Main.Main;
import Main.ResourceMonitor;
import Main.SettingsManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JFrame;

public class MainMenu extends MainUI{
    
    BroadcastListen broadcastListen;
    byte[] serverUp;
    
    private Animation background;
    private Animation intro;
    
    private Animation creditsZombieDance;

    private int animSpeed=2;
    private int animIndex=10;
    
    private boolean refreshInternetServers;
    private boolean refreshLocalServers;
    
    private int menuPage;
    private int introPage;
    private int multiPlayerPage;
    private int optionsPage=-1;
    private int optionsControlsPage=-1;
    
    private final Keyboard key;
    private final Mouse mouse;
    private Main main;
    private UIPointer cursor;
    
    protected ArrayList<Buttons> mainMenuButtons;
    protected ArrayList<Buttons> singlePlayerButtons;
    protected ArrayList<Buttons> multiPlayerButtons;
    protected ArrayList<Buttons> multiPlayerInternetButtons;
    protected ArrayList<Buttons> multiPlayerLANButtons;
    protected ArrayList<Buttons> multiPlayerManualConnectButtons;
    protected ArrayList<Buttons> optionsButtons;
    protected ArrayList<Buttons> optionsControlsButtons;
    protected ArrayList<Buttons> yesNoButtons;
    
    protected Buttons serverBrowserUpButton;
    protected Buttons saveButton;
    protected Buttons button;
    
    protected TextBox ipText;
    protected TextBox tcpPortText;
    protected TextBox udpPortInText;
    protected TextBox udpPortOutText;
    
    protected Lable messageLable;
    protected Lable addressLable;
    protected String addressString;
    
    protected BufferedImage bloodBarOn;
    protected BufferedImage bloodBarOff;
    
    ServerBrowser serverBrowser;
    
    private String message;
    private boolean clickToReturn;
    
    protected Color hoverClass;
    protected Color optionsColor;
    protected Font exitGame;
    protected Font optionsFont;

    private String tmpName;

    private String tmpIP;
    private String tmpTCPPort;
    private String tmpUdpOutPort;
    private String tmpUdpInPort;

    private int tmpGoUp;
    private int tmpGoDown;
    private int tmpGoLeft;
    private int tmpGoRight;
    private int tmpReloadKey;
    private int tmpStatsKey;
    private int tmpChatAreaKey;
    private int tmpChatInputKey;
    public float tmpMouseSensitivity;
    
    SettingsManager settings;
    public ResourceMonitor resourceMonitor;
    
    
    private float lastMouseX=-1.0f;
    
    public MainMenu(JFrame frame, SettingsManager settings) {
        super(frame);
        this.settings = settings;
        
        mouse = new Mouse();
        key = new Keyboard();
        
        super.addMouseMotionListener(mouse);
        super.addMouseWheelListener(mouse);
        super.addMouseListener(mouse);
        super.addKeyListener(key);
        
        broadcastListen = new BroadcastListen();
        
    }

    @Override
    public void init() {
        
//        test_text = new TextBox(key, mouse, textures);
        
        ipText = new TextBox(key, mouse, texture);
        tcpPortText = new TextBox(key, mouse, texture);
        udpPortInText = new TextBox(key, mouse, texture);
        udpPortOutText = new TextBox(key, mouse, texture);
        
        addressLable = new Lable(texture);
        addressString="";
        messageLable = new Lable(texture);
        messageLable.setHoverText(true);
        messageLable.setCharSize(2);
//        test_text.setX(50);
//        test_text.setY(50);
//        test_text.setText("123HAHA");
        
        saveButton =new Buttons("Save", texture);
        saveButton.setMouse(mouse);
        
        mainMenuButtons = new ArrayList<>();
        button =new Buttons("Single Player", texture);
        button.setMouse(mouse);
        mainMenuButtons.add(button);
        button =new Buttons("Multi Player", texture);
        button.setMouse(mouse);
        mainMenuButtons.add(button);
        button =new Buttons("Options", texture);
        button.setMouse(mouse);
        mainMenuButtons.add(button);
        button =new Buttons("Quit", texture);
        button.setMouse(mouse);
        mainMenuButtons.add(button);
        
        
        singlePlayerButtons = new ArrayList<>();
        button =new Buttons("Easy", texture);
        button.setMouse(mouse);
        singlePlayerButtons.add(button);
        button =new Buttons("Medium", texture);
        button.setMouse(mouse);
        singlePlayerButtons.add(button);
        button =new Buttons("Hard", texture);
        button.setMouse(mouse);
        singlePlayerButtons.add(button);
        button =new Buttons("Back", texture);
        button.setMouse(mouse);
        singlePlayerButtons.add(button);
        
        multiPlayerButtons = new ArrayList<>();
        button =new Buttons("Internet", texture);
        button.setMouse(mouse);
        multiPlayerButtons.add(button);
        button =new Buttons("LAN", texture);
        button.setMouse(mouse);
        multiPlayerButtons.add(button);
        button =new Buttons("Back", texture);
        button.setMouse(mouse);
        multiPlayerButtons.add(button);
        
        serverBrowserUpButton =new Buttons("up", texture);
        serverBrowserUpButton.setMouse(mouse);
        
        multiPlayerInternetButtons = new ArrayList<>();
        button =new Buttons("down", texture);
        button.setMouse(mouse);
        multiPlayerInternetButtons.add(button);
        button =new Buttons("Manual Connect", texture);
        button.setMouse(mouse);
        multiPlayerInternetButtons.add(button);
        button =new Buttons("Refresh", texture);
        button.setMouse(mouse);
        multiPlayerInternetButtons.add(button);
        button =new Buttons("Back", texture);
        button.setMouse(mouse);
        multiPlayerInternetButtons.add(button);
        
        multiPlayerLANButtons = new ArrayList<>();
        button =new Buttons("Refresh", texture);
        button.setMouse(mouse);
        multiPlayerLANButtons.add(button);
        button =new Buttons("Back", texture);
        button.setMouse(mouse);
        multiPlayerLANButtons.add(button);
        
        multiPlayerManualConnectButtons = new ArrayList<>();
        button =new Buttons("Connect", texture);
        button.setMouse(mouse);
        multiPlayerManualConnectButtons.add(button);
        button =new Buttons("Back", texture);
        button.setMouse(mouse);
        multiPlayerManualConnectButtons.add(button);
        
        optionsButtons = new ArrayList<>();
        button =new Buttons("Audio", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Video", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Controls", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Player", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Credits", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Back", texture);
        button.setMouse(mouse);
        optionsButtons.add(button);
        
        optionsControlsButtons = new ArrayList<>();
        button =new Buttons("Keyboard", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsControlsButtons.add(button);
        button =new Buttons("Mouse", texture);
        button.setMouse(mouse);
        button.setRadioMode(true);
        optionsControlsButtons.add(button);

        yesNoButtons = new ArrayList<>();
        button =new Buttons("Yes", texture);
        button.setMouse(mouse);
        yesNoButtons.add(button);
        button =new Buttons("No", texture);
        button.setMouse(mouse);
        yesNoButtons.add(button);
        
        bloodBarOn = texture.bloodBarOn;
        bloodBarOff = texture.bloodBarOff;
        
        cursor = new UIPointer(mouse, texture);
        cursor.setTexture(texture.curHand);
        cursor.setX(frame.getWidth()/2);
        cursor.setY(frame.getHeight()/2);
        

        
        hoverClass = new Color(100, 0, 0, 100);
        optionsColor = new Color(100, 0, 0, 200);
        exitGame = new Font("Arial", Font.BOLD, 25);
        optionsFont = new Font("Arial", Font.BOLD, 20);
        
        serverBrowser = new ServerBrowser(mouse, texture);
        serverBrowser.setX(50);
        serverBrowser.setY(50);
        creditsZombieDance = new CreditsZombieDance(texture);
        intro = new LoadingComplete(texture);
        background = new MainBackground(texture);
        background.setSize(frame.getWidth(), frame.getHeight());
        background.setFixedSize(true);
        super.setBackground(Color.black);
        isReady=true;
    }

    
    public void setMessage(String message){
        this.message=message;
    }
    public void clickToReturn(boolean click){
        clickToReturn=click;
    }
    
    public void setMenuPage(int page){
        menuPage=page;
    }
    public void setMain(Main main){
        this.main = main;
    }
    public void setControls(char up, char down, char left, char right){
//        goUp=up;
//        goDown=down;
//        goLeft=left;
//        goRight=right;
    }
    public void setNetwork(String ip, String tcpPort, String udpInPort,String udpOutPort){
//        this.ip=ip;
//        this.tcpPort=tcpPort;
//        this.udpInPort=udpInPort;
//        this.udpOutPort = udpOutPort;
    }
    public void setPlayer(String name){
//        this.name=name;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = g.create();
        
        
        
        if(menuPage!=0){
            background.setSize(frame.getWidth(), frame.getHeight());
            background.draw(g2, 0, 0);
        }
        settings.showResourceMonitor=key.resourceMonitor;
        

        
        switch(menuPage){
            case 0:
                drawIntro(g2);
                break;
            case 1:
                addressString="";
                drawMainMenu(g2);
                break;
            case 2:
                addressString="Singleplayer";
                drawSinglePlayer(g);
                break;
            case 3:
                addressString="Multiplayer";
                drawMultiPlayer(g2);
                break;
            case 4:
                addressString="Options";
                drawOptions(g);
                break;
            case 5:
                addressString="Question";
                drawYesNo(g);
                break;
            case 6:
                addressString="Message";
                drawMessage(g);
                break;
        }
        
        addressLable.setX(main.frame.getWidth()/2);
        addressLable.setY(50);
        addressLable.setText(addressString);
        addressLable.draw(g);
        
        if(mouse.b1Released){
            Animation bloodsprite = new BloodSprite5(texture);
//            bloodsprite.setOpacity(0.5f);
            bloodsprite.setX(mouse.mouseX);
            bloodsprite.setY(mouse.mouseY);
            bloodEffects.add(bloodsprite);
        }
        if(!bloodEffects.isEmpty()){
            Iterator<Animation> bloodIterator = bloodEffects.listIterator();
            while ( bloodIterator.hasNext() ) {
                Animation blood = bloodIterator.next();
                if(blood.getOpacity()<0.05f){
                    bloodIterator.remove();
                }else{
                    blood.setOpacity(blood.getOpacity()-0.05f);
                    blood.draw(g2, blood.getX(), blood.getY());
                    if(blood.hasNextFrame()){
                        blood.nextFrame();
                    }
                    
                }
            }
        }
        
        cursor.draw(g2);
        mouse.b1Released=false;
        
        if(settings.showResourceMonitor){
            resourceMonitor.drawResource(g, 0, 0);
        }
//        test_text.draw(g);
    }

    private void drawIntro(Graphics g2){ 
        if(animIndex<=0){
            intro.nextFrame();
            animIndex=animSpeed;
        }
        animIndex--;   

        switch(introPage){
            case 0:
                intro.draw(g2, (frame.getWidth()/2)- (intro.getWidth()/2), (frame.getHeight()/2)- (intro.getHeight()/2));
                if(intro.getFrameIndex()>47){
                    g2.setColor(new Color(0, 0, 0, (intro.getFrameIndex()-47)*12));
                    g2.fillRect(0, 0, getWidth(), getHeight());                    
                }
                if(intro.isConsumed() || mouse.b1Released){
                    intro = null;
                    intro = new GagarinStudio(texture);
                    intro.setReverse(true);
                    animSpeed=3;
                    animIndex=10;
                    introPage=1;
                }
                break;
            case 1:
                intro.draw(g2, (frame.getWidth()/2)- (intro.getWidth()/2), (frame.getHeight()/2)- (intro.getHeight()/2));
                if(intro.isConsumed() || mouse.b1Released){
                    if(!intro.isReversed() || mouse.b1Released){
//                        mouse.b1Released=false;
                        intro = null;
                        intro = new Presents(texture);
                        intro.setReverse(true);
                        animSpeed=3;
                        animIndex=10;
                        introPage=2;
                        break;
                    }
                    intro.setReverse(false);
                    animIndex=50;
                }
                break;
            case 2:
                intro.draw(g2, (frame.getWidth()/2)- (intro.getWidth()/2), (frame.getHeight()/2)- (intro.getHeight()/2));
                if(intro.isConsumed() || mouse.b1Released){
                    if(!intro.isReversed() || mouse.b1Released){
//                        mouse.b1Released=false;
                        intro = null;
                        intro = new ZombieIntro(texture);
                        intro.setReverse(true);
                        this.setBackground(new Color(46,14,12));
                        animSpeed=5;
                        animIndex=10;
                        introPage=3;
                        break;
                    }
                    intro.setReverse(false);
                    animIndex=50;
                }
                break;
            case 3:
                g2.drawString("(A VERY ORIGINAL NAME)", 50, 50);
                intro.draw(g2, (frame.getWidth()/2)- (intro.getWidth()/2), (frame.getHeight()/2)- (intro.getHeight()/2));
                if(intro.getFrameIndex()>14){
                    g2.setColor(new Color(0, 0, 0, (intro.getFrameIndex()-14)*51));
                    g2.fillRect(0, 0, getWidth(), getHeight());                    
                }
                if(intro.isConsumed() || mouse.b1Released){
                    if(!intro.isReversed() || mouse.b1Released){
//                        mouse.b1Released=false;
                        animSpeed=3;
                        animIndex=10;
                        menuPage=1;
                        break;
                    }
                    intro.setReverse(false);
                    animIndex=50;
                }
                break;
        }
    }
    
    private void drawMainMenu(Graphics g2){
        if(background.hasNextFrame()){
            if(animIndex<=0){
                background.nextFrame();
                animIndex=animSpeed;
            }
            animIndex--;
            g2.setColor(new Color(0, 0, 0, (7-background.getFrameIndex())*32));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }else{
            
            int i=0;
            int x=(getWidth()/2)-150;
            int y;
            for(Buttons pauseButton : mainMenuButtons){

                y=(getHeight()/2)+(100*(i-mainMenuButtons.size()/2));
                pauseButton.setX(x);
                pauseButton.setY(y);
                pauseButton.draw(g2);
                if(pauseButton.isB1Released()){
                    switch(i){
                        case 0:
                            System.out.println("Single player");
                            menuPage=2;
                            break;
                        case 1:
                            System.out.println("Multi player");
                            menuPage=3;
                            break;
                        case 2:
                            System.out.println("Options");
                            menuPage=4;
                            break;
                        case 3:
                            System.out.println("Quit");
                            messageLable.setText("Exit the Game ?");
                            menuPage=5;
                            break;
                    }
                }
                i++;
            }
        }
    }
    
    private void drawSinglePlayer(Graphics g2){
        int i=0;
        int x=(getWidth()/2)-150;
        int y;
        for(Buttons singleplayerButton : singlePlayerButtons){

            y=(getHeight()/2)+(100*(i-singlePlayerButtons.size()/2));
            singleplayerButton.setX(x);
            singleplayerButton.setY(y);
            singleplayerButton.draw(g2);
            if(singleplayerButton.isB1Released()){
                switch(i){
                    case 0:
                        main.initSP(0,0);
                        break;
                    case 1:
                        main.initSP(1,0);
                        break;
                    case 2:
                        main.initSP(2,0);
                        break;
                    case 3:
                        System.out.println("Back");
                        menuPage=1;
                        break;
                }
            }
            i++;
        }
    }
    
    private void drawMultiPlayer(Graphics g2){
        //draw option submenu
        switch(multiPlayerPage){
            case 0:
                drawMultiPlayerMenu(g2);
                break;
            case 1:
                addressString+="/Internet Servers";
                drawInternetServers(g2);
                break;
            case 2:
                addressString+="/Lan Servers";
                drawLanServers(g2);
                break;
            case 3:
                drawManulConnect(g2);
                break;
        }
        key.getCharCode();
//        main.initMPTDM();
    }
    private void drawMultiPlayerMenu(Graphics g2){
        int i=0;
        int x=(getWidth()/2)-150;
        int y;
        for(Buttons multiplayerButton : multiPlayerButtons){

            y=(getHeight()/2)+(100*(i-multiPlayerButtons.size()/2));
            multiplayerButton.setX(x);
            multiplayerButton.setY(y);
            multiplayerButton.draw(g2);
            if(multiplayerButton.isB1Released()){
                switch(i){
                    case 0:
                        System.out.println("Internet");
                        serverBrowser.clear();
                        broadcastListen.clear();
                        broadcastListen.setLocal(false);
                        broadcastListen.init();
//                        broadcastListen.requestInternetData();
//                        refreshInternetServers=true;
                        multiPlayerPage=1;
                        break;
                    case 1:
                        System.out.println("LAN");
                        serverBrowser.clear();
                        broadcastListen.clear();
                        broadcastListen.setLocal(true);
                        broadcastListen.init();
                        broadcastListen.requestLocalData();
                        
                        refreshLocalServers=true;
                        multiPlayerPage=2;
                        break;
                    case 2:
                        System.out.println("Back");
//                        broadcastListen.close();
                        menuPage=1;
                        break;
                        
                }
            }
            i++;
        }
    }
    private void drawLanServers(Graphics g2){

        int i=-1;
        int x=(getWidth()/2)-150;
        int y=100;

        serverBrowserUpButton.setX(x);
        serverBrowserUpButton.setY(y);
        serverBrowserUpButton.draw(g2);

        if(serverBrowserUpButton.isB1Released()){
            serverBrowser.scrollUp();
        }

        y=150;
        serverBrowser.setX(frame.getWidth()/2 - serverBrowser.getWidth()/2);
        serverBrowser.setY(y);
        serverBrowser.nrOfDrawnServers(getHeight());
        serverBrowser.draw(g2);

        if(serverBrowser.joinServer()){
//                message="Connecting";
//                drawMessage(g2);
            main.launchGame(serverBrowser.getServerToJoin());
            return;
        }

        for(Buttons multiplayerTDMButton : multiPlayerInternetButtons){

            y=getHeight() + 50*(i-multiPlayerInternetButtons.size());
            multiplayerTDMButton.setX(x);
            multiplayerTDMButton.setY(y);
            multiplayerTDMButton.draw(g2);
            if(multiplayerTDMButton.isB1Released()){
                switch(i+1){
                    case 0:
                        serverBrowser.scrollDown();
                        break;
                    case 1:
                        System.out.println("ManualConnect");
                        tmpIP = settings.ip;
                        tmpTCPPort = settings.tcpPort+"";
                        tmpUdpInPort = settings.udpInPort+"";
                        tmpUdpOutPort = settings.udpOutPort+"";
                        multiPlayerPage=3;
                        break;
                    case 2:
                        System.out.println("Refresh");
                        broadcastListen.requestLocalData();
                        serverBrowser.refresh();
                        refreshLocalServers=true;
                        break;
                    case 3:
                        System.out.println("Back");
                        broadcastListen.close();
                        serverBrowser.clear();
                        multiPlayerPage=0;
                        break;
                }
            }
            i++;
        }
            
            
            
        if(refreshLocalServers){
//            serverBrowser.clear();
            if(broadcastListen.getServerListSize()>0){
                serverBrowser.updateServerList(broadcastListen.getServers());
            }
        }
    }
    private void drawInternetServers(Graphics g2){
        
        if(refreshInternetServers){
            switch(broadcastListen.getBytesIn()){
                case -1:
                    message="Unreachable ServerCenter.";
                    menuPage=6;
                    clickToReturn=true;
                    refreshInternetServers=false;
                    break;
                case 0:
                    message = "Refreshing...";
                    clickToReturn=false;
                    drawMessage(g2);
                    break;
                default:
                    serverBrowser.updateServerList(broadcastListen.getServers());
                    refreshInternetServers=false;
                    break;
            }
        }else{

            int i=-1;
            int x=(getWidth()/2)-150;
            int y=100;
            
            serverBrowserUpButton.setX(x);
            serverBrowserUpButton.setY(y);
            serverBrowserUpButton.draw(g2);
            if(serverBrowserUpButton.isB1Released()){
                serverBrowser.scrollUp();
            }
            
            y=150;
            serverBrowser.setX(frame.getWidth()/2 - serverBrowser.getWidth()/2);
            serverBrowser.setY(y);
            serverBrowser.nrOfDrawnServers(getHeight());
            serverBrowser.draw(g2);
            
            if(serverBrowser.joinServer()){
                main.launchGame(serverBrowser.getServerToJoin());
                return;
            }
            
            for(Buttons multiplayerTDMButton : multiPlayerInternetButtons){
                y=getHeight() + 50*(i-multiPlayerInternetButtons.size());
                        
                multiplayerTDMButton.setX(x);
                multiplayerTDMButton.setY(y);
                multiplayerTDMButton.draw(g2);
                if(multiplayerTDMButton.isB1Released()){
                    switch(i+1){
                        case 0:
                            serverBrowser.scrollDown();
                            break;
                        case 1:
                            System.out.println("ManualConnect");
                            tmpIP = settings.ip;
                            tmpTCPPort = settings.tcpPort+"";
                            tmpUdpInPort = settings.udpInPort+"";
                            tmpUdpOutPort = settings.udpOutPort+"";
                            multiPlayerPage=3;
                            break;
                        case 2:
                            System.out.println("Refresh");
                            broadcastListen.requestInternetData();
                            serverBrowser.refresh();
                            refreshInternetServers=true;
                            break;
                        case 3:
                            System.out.println("Back");
                            broadcastListen.close();
                            serverBrowser.clear();
                            multiPlayerPage=0;
                            break;
                    }
                }
                i++;
            }
        }
    }
    private void drawManulConnect(Graphics g2){
        
        int textHoverWidth = 290;

        boolean wrongChar=false;
        int x=(getWidth()/2)-100;
        int y=(int) ((getHeight()/2)+(75*(-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+textHoverWidth && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, textHoverWidth, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                    if(tmpIP.length()>0){
                        tmpIP = tmpIP.substring(0, tmpIP.length()-1);
                    }
                }else{
                    char newChar = (char)key.getCharCode();
                    if(Character.isDigit(newChar) || newChar=='.'){
                        tmpIP += newChar;
                    }else{
                        wrongChar=true;
                    }
                }
            }
        }
        y=(int) ((getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        if(wrongChar){g2.setColor(Color.red);}else{g2.setColor(Color.black);}
        g2.setFont(optionsFont);
        g2.drawString("IP: " + tmpIP, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("IP: " + tmpIP, x+24, y+1);
        
        //Set TCP Port
        ////Hover
        wrongChar=false;
        y=(int) ((getHeight()/2)+(75*(1-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+textHoverWidth && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, textHoverWidth, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                    if(tmpTCPPort.length()>0){
                        tmpTCPPort = tmpTCPPort.substring(0, tmpTCPPort.length()-1);
                    }
                }else{
                    char newChar = (char)key.getCharCode();
                    if(Character.isDigit(newChar)){
                        tmpTCPPort += newChar;
                    }else{
                        wrongChar=true;
                    }
                }
            }
        }
        y=(int) ((getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(wrongChar){g2.setColor(Color.red);}else{g2.setColor(Color.black);}
        g2.setFont(optionsFont);
        g2.drawString("TCP Port: " + tmpTCPPort, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("TCP Port: " + tmpTCPPort, x+24, y+1);
        
        //Set UDP-Out Port
        ////Hover
        wrongChar=false;
        y=(int) ((getHeight()/2)+(75*(2-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+textHoverWidth && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, textHoverWidth, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                    if(tmpUdpOutPort.length()>0){
                        tmpUdpOutPort = tmpUdpOutPort.substring(0, tmpUdpOutPort.length()-1);
                    }
                }else{
                    char newChar = (char)key.getCharCode();
                    if(Character.isDigit(newChar)){
                        tmpUdpOutPort += newChar;
                    }else{
                        wrongChar=true;
                    }
                }
            }
        }
        y=(int) ((getHeight()/2)+(75*(2.5f-optionsButtons.size()/2)));
        if(wrongChar){g2.setColor(Color.red);}else{g2.setColor(Color.black);}
        g2.setFont(optionsFont);
        g2.drawString("UDP-Out Port: " + tmpUdpOutPort, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("UDP-Out Port: " + tmpUdpOutPort, x+24, y+1);
        
        //Set UDP-In Port
        ////Hover
        wrongChar=false;
        y=(int) ((getHeight()/2)+(75*(3-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+textHoverWidth && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, textHoverWidth, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                    if(tmpUdpInPort.length()>0){
                        tmpUdpInPort = tmpUdpInPort.substring(0, tmpUdpInPort.length()-1);
                    }
                }else{
                    char newChar = (char)key.getCharCode();
                    if(Character.isDigit(newChar)){
                        tmpUdpInPort += newChar;
                    }else{
                        wrongChar=true;
                    }
                }
            }
        }
        y=(int) ((getHeight()/2)+(75*(3.5f-optionsButtons.size()/2)));
        if(wrongChar){g2.setColor(Color.red);}else{g2.setColor(Color.black);}
        g2.setFont(optionsFont);
        g2.drawString("UDP-In Port: " + tmpUdpInPort, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("UDP-In Port: " + tmpUdpInPort, x+24, y+1);
        


        
        int i=2;
        for(Buttons multiplayerManualConnButton : multiPlayerManualConnectButtons){

            y=(getHeight()/2)+(100*(i-multiPlayerManualConnectButtons.size()/2));
            multiplayerManualConnButton.setX(x);
            multiplayerManualConnButton.setY(y);
            multiplayerManualConnButton.draw(g2);
            if(multiplayerManualConnButton.isB1Released()){
                switch(i){
                    case 2:
                        main.launchGame(tmpIP, Integer.parseInt(tmpTCPPort), Integer.parseInt(tmpUdpInPort), Integer.parseInt(tmpUdpOutPort));
                        break;
                    case 3:
                        System.out.println("Back");
                        multiPlayerPage=0;
                        break;
                }
            }
            i++;
        }
    }
    
    private void drawOptions(Graphics g2){
        
        int i=0;
        int subMenu=0;
        int x;
        int y;
        for(Buttons optionButton : optionsButtons){

            if(optionsPage==-1){
                x=(getWidth()/2)-150;
            }else{
                x=150;
            }
            y=(getHeight()/2)+(50*(i-optionsButtons.size()/2));
            optionButton.setX(x);
            optionButton.setY(y);
            optionButton.draw(g2);

            //SelectedOption
            if(optionsPage==i){
                optionButton.setSelected(true);
                
                switch(i){
                    case 2:
                        i+=2;
                        subMenu=2;
//                        y=(getHeight()/2)+(75*(i-optionsButtons.size()/2));
                        break;
                    default:
                        subMenu=0;
                        //option Tab
                        float tabHeight= ((getHeight()/2)+(75*(6-optionsButtons.size()/2)))-((getHeight()/2)+(75*(-optionsButtons.size()/2)));
                        float tabWidth = getWidth()-500-100;
                        g2.setColor(optionsColor);
                        g2.fillRoundRect(500, (getHeight()/2)+(75*(-optionsButtons.size()/2)), (int) tabWidth, (int) tabHeight, 60, 60);  
                        break;
                }
            }else{
                optionButton.setSelected(false);
            }
            

            if(optionButton.isB1Released()){
                    switch(i-subMenu){
                        case 0:
                            if(i==optionsPage){
                                optionsPage=-1;
                            }else{
                                optionsPage=0;
                            }
                            break;
                        case 1:
                            if(i==optionsPage){
                                optionsPage=-1;
                            }else{
                                optionsPage=1;
                            }
                            break;
                        case 2:
                            if(i==optionsPage){
                                optionsPage=-1;
                            }else{
                                optionsPage=2;
                            }
                            break;
                        case 3:
                            if(i==optionsPage){
                                optionsPage=-1;
                            }else{
                                optionsPage=3;
                                tmpName=settings.name;
                            }
                            break;
                        case 4:
                            if(i==optionsPage){
                                optionsPage=-1;
                            }else{
                                optionsPage=4;
                                tmpIP = settings.ip;
                                tmpTCPPort = settings.tcpPort+"";
                                tmpUdpInPort = settings.udpInPort+"";
                                tmpUdpOutPort = settings.udpOutPort+"";
                            }

                            break;
                        case 5:
                            System.out.println("Back");
                            optionsPage=-1;
                            menuPage=1;
                            break;
                    }
                }
            i++;
        }
        //draw option submenu
        switch(optionsPage){
            case 2:
                drawOptionsSubMenuColtrols(g2);
                break;
            case 3:
                drawOptionsSubMenuPlayer(g2);
                break;
            case 4:
                drawCredits(g2);
                break;
        }
        key.getCharCode();
    }
    private void drawOptionsSubMenuColtrols(Graphics g2){
        int i=3;
        int x=175;
        int y;
        for(Buttons opContKeyButton : optionsControlsButtons){

            y=(int) ((getHeight()/2)+(45*(i-optionsButtons.size()/2)));
            opContKeyButton.setX(x);
            opContKeyButton.setY(y);
            opContKeyButton.draw(g2);
            //SelectedOption
            if(optionsControlsPage==i-3){
                opContKeyButton.setSelected(true);

//                option Tab
                float tabHeight= ((getHeight()/2)+(75*(6-optionsButtons.size()/2)))-((getHeight()/2)+(75*(-optionsButtons.size()/2)));
                float tabWidth = getWidth()-550-100;
                g2.setColor(optionsColor);
                g2.fillRoundRect(550, (getHeight()/2)+(75*(-optionsButtons.size()/2)), (int) tabWidth, (int) tabHeight, 60, 60);
                
            }else{
                opContKeyButton.setSelected(false);
            }

            if(opContKeyButton.isB1Released()){
                switch(i-3){
                    case 0:
                        if(i-3==optionsControlsPage){
                            optionsControlsPage=-1;
                        }else{
                            optionsControlsPage=0;

                            tmpGoUp=settings.goUp;
                            tmpGoDown=settings.goDown;
                            tmpGoLeft=settings.goLeft;
                            tmpGoRight=settings.goRight;
                            tmpReloadKey=settings.reloadKey;
                            tmpChatAreaKey=settings.chatAreaKey;
                            tmpChatInputKey=settings.chatInputKey;
                        }
                        break;
                    case 1:
                        if(i-3==optionsControlsPage){
                            optionsControlsPage=-1;
                        }else{
                            optionsControlsPage=1;

                            tmpMouseSensitivity=settings.mouseSensitivity;
                        }
                        break;
                }
            }
            i++;
        }

        //draw option submenu
        switch(optionsControlsPage){
            case 0:
                drawOptionsSubMenuColtrolsKeyboard(g2);
                break;
            case 1:
                drawOptionsSubMenuColtrolsMouse(g2);
                break;
        }
    }
    private void drawOptionsSubMenuColtrolsKeyboard(Graphics g2){
        
        int x=200+375;
        int y=(int) ((getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        g2.drawImage(texture.wasd, x, y, null);
        
        //Set goUp
        ////Hover
        x=200+433;
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpGoUp = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(1-optionsButtons.size()/2));
        if(Character.isLetterOrDigit(tmpGoUp)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString(convertKeyCode(tmpGoUp), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString(convertKeyCode(tmpGoUp), x+24, y+1);
        }else{
            g2.drawImage(convertKeyCodeToImage(tmpGoUp), x+25, y-25, null);
        }
        
        //GoLeft
        ////Hover
        x=200+375;
        y=(int) ((getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpGoLeft = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(2-optionsButtons.size()/2));
        if(Character.isLetterOrDigit(tmpGoLeft)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString(convertKeyCode(tmpGoLeft), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString(convertKeyCode(tmpGoLeft), x+24, y+1);
        }else{
            g2.drawImage(convertKeyCodeToImage(tmpGoLeft), x+25, y-25, null);
        }
        
//        
//        //GoDown
//        ////Hover
        x=200+450;
        y=(int) ((getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpGoDown = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(2-optionsButtons.size()/2));
        if(Character.isLetterOrDigit(tmpGoDown)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString(convertKeyCode(tmpGoDown), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString(convertKeyCode(tmpGoDown), x+24, y+1);
        }else{
            g2.drawImage(convertKeyCodeToImage(tmpGoDown), x+25, y-25, null);
        }
//        
//        //GoRight
//        ////Hover
        x=200+525;
        y=(int) ((getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpGoRight = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(2-optionsButtons.size()/2));
        
        if(Character.isLetterOrDigit(tmpGoRight)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString(convertKeyCode(tmpGoRight), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString(convertKeyCode(tmpGoRight), x+24, y+1);
        }else{
            g2.drawImage(convertKeyCodeToImage(tmpGoRight), x+25, y-25, null);
        }
        
        
//        //reload
//        ////Hover
        x=200+375;
        y=(int) ((getHeight()/2)+(75*(2.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+150 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 150, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpReloadKey = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(3-optionsButtons.size()/2));
        
        if(Character.isLetterOrDigit(tmpReloadKey)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString("Reload: " + convertKeyCode(tmpReloadKey), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString("Reload: " + convertKeyCode(tmpReloadKey), x+24, y+1);
        }else{
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString("Reload: ", x+25, y);
            g2.setColor(Color.gray);
            g2.drawString("Reload: ", x+24, y+1);
            g2.drawImage(convertKeyCodeToImage(tmpReloadKey), x+100, y-25, null);
        }
        
//        //ChatArea
//        ////Hover
        x=200+675;
        y=(int) ((getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+175 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 175, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpChatAreaKey = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(1-optionsButtons.size()/2));
        
        if(Character.isLetterOrDigit(tmpChatAreaKey)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString("Chat Area: " + convertKeyCode(tmpChatAreaKey), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString("Chat Area: " + convertKeyCode(tmpChatAreaKey), x+24, y+1);
        }else{
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString("Chat Area: ", x+25, y);
            g2.setColor(Color.gray);
            g2.drawString("Chat Area: ", x+24, y+1);
            g2.drawImage(convertKeyCodeToImage(tmpChatAreaKey), x+130, y-25, null);
        }
        
//        //InputArea
//        ////Hover
        x=200+675;
        y=(int) ((getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+175 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 175, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                }else{
                    tmpChatInputKey = Character.toUpperCase(key.getCharCode());
                }
            }
        }
        y=(getHeight()/2)+(75*(2-optionsButtons.size()/2));
        
        if(Character.isLetterOrDigit(tmpChatInputKey)){
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString("Chat Input: " + convertKeyCode(tmpChatInputKey), x+25, y);
            g2.setColor(Color.gray);
            g2.drawString("Chat Input: " + convertKeyCode(tmpChatInputKey), x+24, y+1);
        }else{
            g2.setColor(Color.black);
            g2.setFont(optionsFont);
            g2.drawString("Chat Input: ", x+25, y);
            g2.setColor(Color.gray);
            g2.drawString("Chat Input: ", x+24, y+1);
            g2.drawImage(convertKeyCodeToImage(tmpChatInputKey), x+130, y-25, null);
        }
        
        //Save
        if(settings.goUp!=tmpGoUp || settings.goDown!=tmpGoDown || settings.goLeft!=tmpGoLeft || 
                settings.goRight!=tmpGoRight || settings.reloadKey!=tmpReloadKey || 
                settings.chatAreaKey!=tmpChatAreaKey || settings.chatInputKey!=tmpChatInputKey){
            x=200+375;
            y=(getHeight()/2)+(75*(5-optionsButtons.size()/2));
            saveButton.setX(x);
            saveButton.setY(y);
            saveButton.draw(g2);
            if(saveButton.isB1Released()){
                optionsControlsPage=-1;

                settings.goUp=tmpGoUp;
                settings.goDown=tmpGoDown;
                settings.goLeft=tmpGoLeft;
                settings.goRight=tmpGoRight;
                settings.reloadKey=tmpReloadKey;
                settings.chatAreaKey=tmpChatAreaKey;
                settings.chatInputKey=tmpChatInputKey;
                settings.write();
            }
        }
    }
    private void drawOptionsSubMenuColtrolsMouse(Graphics g2){
        //Set goUp
        ////Hover
        int x=200+375;
        int y=(int) ((getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.setFont(optionsFont);
        g2.drawString("Sensitivity: " + tmpMouseSensitivity, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("Sensitivity: " + tmpMouseSensitivity, x+24, y+1);
        
        y=(int) ((getHeight()/2)+(75*(0.6f-optionsButtons.size()/2)));
        g2.drawImage(bloodBarOff, x, y, null);
        if(cursor.getX()>x && cursor.getX()<x+bloodBarOff.getWidth() && 
                cursor.getY()>y && cursor.getY()<y+bloodBarOff.getHeight()){
            if(mouse.b1Down && tmpMouseSensitivity<=2.0 && tmpMouseSensitivity>=0.1f){
                if(lastMouseX==-1){lastMouseX=mouse.mouseX;}
                tmpMouseSensitivity+=(mouse.mouseX-lastMouseX)/150;
                if(tmpMouseSensitivity>2.0f){tmpMouseSensitivity=2.0f;}
                if(tmpMouseSensitivity<0.1f){tmpMouseSensitivity=0.1f;}
                lastMouseX=mouse.mouseX;
            }else{
                lastMouseX=-1;
            }
        }
        g2.drawImage(bloodBarOn.getSubimage(0, 0, (int) ((tmpMouseSensitivity/2)*bloodBarOn.getWidth()), bloodBarOn.getHeight()), x, y, null);
        

        
        
        
        //Save
        if(settings.mouseSensitivity!=tmpMouseSensitivity){
            y=(getHeight()/2)+(75*(5-optionsButtons.size()/2));
            saveButton.setX(x);
            saveButton.setY(y);
            saveButton.draw(g2);
            if(saveButton.isB1Released()){
                optionsControlsPage=-1;

                settings.mouseSensitivity=tmpMouseSensitivity;
                settings.write();
            }
        }
    }
    private void drawOptionsSubMenuPlayer(Graphics g2){
        
        int x=300+225;
        
        //Avatar
        int y=(int) ((getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+200 && 
                cursor.getY()>y && cursor.getY()<y+200){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 200, 200, 60, 60);
        }
        g2.setColor(Color.white);
        g2.fillRoundRect(x, y, 200, 200, 60, 60);
        
        //SetName
        ////Hover
        y=(int) ((getHeight()/2)+(75*(3.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+500 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 500, 60, 60, 60);
            if(key.newChar){
                if(key.backSpace){
                    if(tmpName.length()>0){
                        tmpName = tmpName.substring(0, tmpName.length()-1);
                    }
                }else{
                    char newChar = key.getChar();
                    if(Character.isLetterOrDigit(newChar)){
                        tmpName += newChar;
                    }
                    
                }
            }
        }
        y=(getHeight()/2)+(75*(4-optionsButtons.size()/2));
        g2.setColor(Color.black);
        g2.setFont(optionsFont);
        g2.drawString("Name: " + tmpName, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("Name: " + tmpName, x+24, y+1);
        

        
        
        //Save
        if(settings.name == null ? tmpName != null : !settings.name.equals(tmpName)){
            y=(getHeight()/2)+(75*(5-optionsButtons.size()/2));
            saveButton.setX(x);
            saveButton.setY(y);
            saveButton.draw(g2);
            if(saveButton.isB1Released()){
                optionsPage=-1;
                settings.name=tmpName;
                settings.write();
            }
        }
    }    

    private void drawCredits(Graphics g2){
        g2.setFont(optionsFont);
        
        int x=300+225;
        int y=(int) ((getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("Developer, Producer, Director, Regizor, etc.. :", x, y);
        g2.setColor(Color.gray);
        g2.drawString("Developer, Producer, Director, Regizor, etc.. :", x, y+1);
        
        x=400+225;
        y=(int) ((getHeight()/2)+(75*(1-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("- Lovin Paul", x, y);
        g2.setColor(Color.gray);
        g2.drawString("- Lovin Paul", x, y+1);
        
        x=300+225;
        y=(int) ((getHeight()/2)+(75*(2.0f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("Testers:", x, y);
        g2.setColor(Color.gray);
        g2.drawString("Testers:", x, y+1);
        
        x=400+225;
        y=(int) ((getHeight()/2)+(75*(2.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("- Sprincenatu Alexandra", x, y);
        g2.setColor(Color.gray);
        g2.drawString("- Sprincenatu Alexandra", x, y+1);
        
        y=(int) ((getHeight()/2)+(75*(3.0f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("- Nicolae Diaconu", x, y);
        g2.setColor(Color.gray);
        g2.drawString("- Nicolae Diaconu", x, y+1);
        
        y=(int) ((getHeight()/2)+(75*(3.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("- Andrei Gheorghe", x, y);
        g2.setColor(Color.gray);
        g2.drawString("- Andrei Gheorghe", x, y+1);
        
        y=(int) ((getHeight()/2)+(75*(4.0f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("- Gidei Stefan", x, y);
        g2.setColor(Color.gray);
        g2.drawString("- Gidei Stefan", x, y+1);
        
        y=(int) ((getHeight()/2)+(75*(4.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("- Gabriel Musat", x, y);
        g2.setColor(Color.gray);
        g2.drawString("- Gabriel Musat", x, y+1);
        
        y=(int) ((getHeight()/2)+(75*(5.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.drawString("Zombie Dance... For you guys and girls", x, y);
        g2.setColor(Color.gray);
        g2.drawString("Zombie Dance... For you guys and girls", x, y+1);
        
        x=500+225;
        y=(int) ((getHeight()/2)+(75*(2.5f-optionsButtons.size()/2)));
        if(creditsZombieDance.isConsumed()){creditsZombieDance.replay();}
        creditsZombieDance.draw(g2, x, y);
        creditsZombieDance.nextFrame();
    }
    
    private void drawYesNo(Graphics g2){
        
//        g2.setFont(exitGame);
//        g2.drawString("Exit the Game ? (chiar vrei asta !?)", (getWidth()/2)-224, (getHeight()/2)-49);
//        g2.setColor(Color.BLACK);
//        g2.drawString("Exit the Game ? (chiar vrei asta !?)", (getWidth()/2)-225, (getHeight()/2)-50);
        
        messageLable.setX(getWidth()/2);
        messageLable.setY(getHeight()/2 - 50);
        messageLable.draw(g2);

        int i=1;
        int x=(getWidth()/2)-150;
        int y;
        for(Buttons yesNoButton : yesNoButtons){

            y=(getHeight()/2)+(50*(i-yesNoButtons.size()/2));
            
            yesNoButton.setX(x);
            yesNoButton.setY(y);
            yesNoButton.draw(g2);
            
            if(yesNoButton.isB1Released()){
                switch(i){
                    case 1:
                        System.exit(0);
                        break;
                    case 2:
                        menuPage=1;
                        break;
                }
            }
            i++;
        }
    }
    private void drawMessage(Graphics g2){
//        if(errorMessage!=null){
//            g2.setFont(exitGame);
//            g2.drawString("Server is unreachable.", (getWidth()/2)-224, (getHeight()/2)-49);
//            g2.drawString(errorMessage, (getWidth()/2)-224, (getHeight()/2)-29);
//            g2.setColor(Color.BLACK);
//            g2.drawString("Server is unreachable.", (getWidth()/2)-225, (getHeight()/2)-50);
//            g2.drawString(errorMessage, (getWidth()/2)-225, (getHeight()/2)-30);
//            if(mouse.b1Released){
//                errorMessage=null;
//                menuPage=3;
//            }            
//        }else{
            if(message==null){menuPage=3;return;}
            
            g2.setFont(exitGame);
            g2.drawString(message, (getWidth()/2)-224, (getHeight()/2)-49);
            g2.setColor(Color.BLACK);
            g2.drawString(message, (getWidth()/2)-225, (getHeight()/2)-50);
            if(clickToReturn && mouse.b1Released){
                message=null;
                clickToReturn=false;
                menuPage=3;
            }
//        }

    }
    
    private String convertKeyCode(int keyCode){
        switch(keyCode){
            case 37:
                return "Left Arrow";
            case 38:
                return "Up Arrow";
            case 39:
                return "Right Arrow";
            case 40:
                return "Down Arrow";
            default:
                return ""+(char)keyCode;
                
        }
    }
    private BufferedImage convertKeyCodeToImage(int keyCode){
        switch(keyCode){
            case 37:
                return texture.leftArrow;
            case 38:
                return texture.upArrow;
            case 39:
                return texture.rightArrow;
            case 40:
                return texture.downArrow;
            default:
                return null;
                
        }
    }
}

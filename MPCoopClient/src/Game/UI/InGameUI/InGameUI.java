
package Game.UI.InGameUI;

import Game.Pointer.UIPointer;
import Game.*;
import Game.Animations.Animation;
import Game.Animations.Blood.BloodSprite5;
import Game.Mods.*;
import Game.UI.Components.Buttons;
import Main.Textures.LoadTextures;
import Main.SettingsManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class InGameUI {
    
    protected final Game game;
    
    protected final Color background;
    protected final Color statsBackground;
    protected Color hoverClass;
    protected Color selectedClass;
    protected Color hoverWeapon;
    protected Color selectedWeapon;
    protected Color optionsColor;
    
    protected Font exitGame;
    protected Font optionsFont;
    
    protected final UIPointer cursor;
    
    protected ArrayList<BufferedImage> actorsImages;
    protected ArrayList<BufferedImage> weaponsImages;
//    protected ArrayList<BufferedImage> pauseImages;
//    protected ArrayList<BufferedImage> yesNoImages;
//    protected ArrayList<BufferedImage> optionsImages;
    
    
    protected ArrayList<Buttons> pauseButtons;
    protected ArrayList<Buttons> optionsButtons;
    protected ArrayList<Buttons> optionsControlsButtons;
    protected ArrayList<Buttons> yesNoButtons;
    public Buttons button;
    protected Buttons saveButton;
    protected BufferedImage bloodBarOn;
    protected BufferedImage bloodBarOff;
    protected BufferedImage warning32x32;
    
    protected ArrayList<Animation> bloodEffects;
    
    protected int menuPage;
    private int optionsPage=-1;
    private int optionsControlsPage=-1;
    protected boolean quitConfirmation;
    
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
    
    protected SettingsManager settings;
    protected LoadTextures textures;
    
    protected boolean nameChnaged;
    private float lastMouseX=-1.0f;
    
    public InGameUI(Game game, SettingsManager settings, LoadTextures textures) {
        this.game=game;
        this.settings = settings;
        this.textures=textures;
        
        bloodEffects = new ArrayList<>();
        
        saveButton =new Buttons("Save", textures);
        saveButton.setMouse(game.getMouse());
        
        optionsButtons = new ArrayList<>();
        button =new Buttons("Audio", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Video", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Controls", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Player", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Network", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsButtons.add(button);
        button =new Buttons("Back", textures);
        button.setMouse(game.getMouse());
        optionsButtons.add(button);

        optionsControlsButtons = new ArrayList<>();
        button =new Buttons("Keyboard", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsControlsButtons.add(button);
        button =new Buttons("Mouse", textures);
        button.setMouse(game.getMouse());
        button.setRadioMode(true);
        optionsControlsButtons.add(button);

        yesNoButtons = new ArrayList<>();
        button =new Buttons("Yes", textures);
        button.setMouse(game.getMouse());
        yesNoButtons.add(button);
        button =new Buttons("No", textures);
        button.setMouse(game.getMouse());
        yesNoButtons.add(button);

        bloodBarOn = textures.bloodBarOn;
        bloodBarOff = textures.bloodBarOff;
        warning32x32 = textures.warning32x32;

        hoverClass = new Color(100, 0, 0, 100);
        optionsColor = new Color(100, 0, 0, 200);
        exitGame = new Font("Arial", Font.BOLD, 25);
        optionsFont = new Font("Arial", Font.BOLD, 20);
        
        cursor =new UIPointer(game.getMouse(), textures);
        background=new Color(0, 0, 0, 200);
        statsBackground = new Color(195,195,195,195);
        
        tmpName = settings.name;
        tmpGoUp = settings.goUp;
        tmpGoDown = settings.goDown;
        tmpGoLeft = settings.goLeft;
        tmpGoRight = settings.goRight;
        tmpIP = settings.ip;
        tmpTCPPort = settings.tcpPort+"";
        tmpUdpInPort = settings.udpInPort+"";
        tmpUdpOutPort = settings.udpOutPort+"";
    }
    
    public void drawStats(Graphics g){
        Graphics g2 = g.create();
        g2.drawLine(725, 60, 725, 340);
        g2.setColor(statsBackground);
        g2.fillRect(400, 50, 700, 300);
    }
    
    public void drawHP(Graphics g){}
    
    public void drawAmmo(Graphics g){}
    
    public void drawDeployScreen(Graphics g){
        Graphics2D gUI = (Graphics2D) g.create();
        gUI.setColor(background);
        gUI.fillRect(0, 0, game.getWidth(), game.getHeight());
    }
    
    
    public void drawPauseMenu(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(background);
        g2.fillRect(0, 0, game.getWidth(), game.getHeight());
        

    }
    

    
    protected void drawOptions(Graphics g2){

        int i=0;
        int subMenu=0;
        int x;
        int y;
        for(Buttons optionButton : optionsButtons){

            if(optionsPage==-1){
                x=(game.getWidth()/2)-150;
            }else{
                x=150;
            }
            y=(game.getHeight()/2)+(50*(i-optionsButtons.size()/2));
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
                        float tabHeight= ((game.getHeight()/2)+(75*(6-optionsButtons.size()/2)))-((game.getHeight()/2)+(75*(-optionsButtons.size()/2)));
                        float tabWidth = game.getWidth()-500-100;
                        g2.setColor(optionsColor);
                        g2.fillRoundRect(500, (game.getHeight()/2)+(75*(-optionsButtons.size()/2)), (int) tabWidth, (int) tabHeight, 60, 60);  
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
                            menuPage=0;
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
                drawOptionsSubMenuServer(g2);
                break;
        }
        game.getKeyboard().getCharCode();
    }
    protected void drawOptionsSubMenuColtrols(Graphics g2){
        int i=3;
        int x=175;
        int y;
        for(Buttons opContKeyButton : optionsControlsButtons){

            y=(int) ((game.getHeight()/2)+(45*(i-optionsButtons.size()/2)));
            opContKeyButton.setX(x);
            opContKeyButton.setY(y);
            opContKeyButton.draw(g2);
            //SelectedOption
            if(optionsControlsPage==i-3){
                opContKeyButton.setSelected(true);

//                option Tab
                float tabHeight= ((game.getHeight()/2)+(75*(6-optionsButtons.size()/2)))-((game.getHeight()/2)+(75*(-optionsButtons.size()/2)));
                float tabWidth = game.getWidth()-550-100;
                g2.setColor(optionsColor);
                g2.fillRoundRect(550, (game.getHeight()/2)+(75*(-optionsButtons.size()/2)), (int) tabWidth, (int) tabHeight, 60, 60);
                
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
    protected void drawOptionsSubMenuColtrolsKeyboard(Graphics g2){
        
        int x=200+375;
        int y=(int) ((game.getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        g2.drawImage(textures.wasd, x, y, null);
        
        //Set goUp
        ////Hover
        x=200+433;
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpGoUp = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(1-optionsButtons.size()/2));
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
        y=(int) ((game.getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpGoLeft = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(2-optionsButtons.size()/2));
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
        y=(int) ((game.getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpGoDown = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(2-optionsButtons.size()/2));
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
        y=(int) ((game.getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+72 && 
                cursor.getY()>y && cursor.getY()<y+69){
            g2.setColor(hoverClass);
            g2.fillRoundRect(x, y, 72, 69, 25, 25);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpGoRight = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(2-optionsButtons.size()/2));
        
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
        y=(int) ((game.getHeight()/2)+(75*(2.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+150 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 150, 60, 60, 60);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpReloadKey = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(3-optionsButtons.size()/2));
        
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
        y=(int) ((game.getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+175 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 175, 60, 60, 60);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpChatAreaKey = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(1-optionsButtons.size()/2));
        
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
        y=(int) ((game.getHeight()/2)+(75*(1.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+175 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 175, 60, 60, 60);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                }else{
                    tmpChatInputKey = Character.toUpperCase(game.getKeyboard().getCharCode());
                }
            }
        }
        y=(game.getHeight()/2)+(75*(2-optionsButtons.size()/2));
        
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
            y=(game.getHeight()/2)+(75*(5-optionsButtons.size()/2));
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
                game.setControls(tmpGoUp, tmpGoDown, tmpGoLeft, tmpGoRight, tmpReloadKey, tmpStatsKey);
                settings.write();
            }
        }
    }
    protected void drawOptionsSubMenuColtrolsMouse(Graphics g2){
        //Set goUp
        ////Hover
        int x=200+375;
        int y=(int) ((game.getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        g2.setColor(Color.black);
        g2.setFont(optionsFont);
        g2.drawString("Sensitivity: " + tmpMouseSensitivity, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("Sensitivity: " + tmpMouseSensitivity, x+24, y+1);
        
        y=(int) ((game.getHeight()/2)+(75*(0.6f-optionsButtons.size()/2)));
        g2.drawImage(bloodBarOff, x, y, null);
        if(cursor.getX()>x && cursor.getX()<x+bloodBarOff.getWidth() && 
                cursor.getY()>y && cursor.getY()<y+bloodBarOff.getHeight()){
            if(game.getMouse().b1Down && tmpMouseSensitivity<=2.0 && tmpMouseSensitivity>=0.1f){
                if(lastMouseX==-1){lastMouseX=game.getMouse().mouseX;}
                tmpMouseSensitivity+=(game.getMouse().mouseX-lastMouseX)/150;
                if(tmpMouseSensitivity>2.0f){tmpMouseSensitivity=2.0f;}
                if(tmpMouseSensitivity<0.1f){tmpMouseSensitivity=0.1f;}
                lastMouseX=game.getMouse().mouseX;
            }else{
                lastMouseX=-1;
            }
        }
        g2.drawImage(bloodBarOn.getSubimage(0, 0, (int) ((tmpMouseSensitivity/2)*bloodBarOn.getWidth()), bloodBarOn.getHeight()), x, y, null);
        

        
        
        
        //Save
        if(settings.mouseSensitivity!=tmpMouseSensitivity){
            y=(game.getHeight()/2)+(75*(5-optionsButtons.size()/2));
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
    protected void drawOptionsSubMenuPlayer(Graphics g2){
        
        int x=300+225;
        
        //Avatar
        int y=(int) ((game.getHeight()/2)+(75*(0.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+200 && 
                cursor.getY()>y && cursor.getY()<y+200){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 200, 200, 60, 60);
        }
        g2.setColor(Color.white);
        g2.fillRoundRect(x, y, 200, 200, 60, 60);
        
        //SetName
        ////Hover
        y=(int) ((game.getHeight()/2)+(75*(3.5f-optionsButtons.size()/2)));
        if(cursor.getX()>x && cursor.getX()<x+500 && 
                cursor.getY()>y && cursor.getY()<y+60){
            g2.setColor(Color.white);
            g2.fillRoundRect(x, y, 500, 60, 60, 60);
            if(game.getKeyboard().newChar){
                if(game.getKeyboard().backSpace){
                    if(tmpName.length()>0){
                        tmpName = tmpName.substring(0, tmpName.length()-1);
                    }
                }else{
                    char newChar = game.getKeyboard().getChar();
                    if(Character.isLetterOrDigit(newChar)){
                        tmpName += newChar;
                    }
                    
                }
            }
        }
        y=(game.getHeight()/2)+(75*(4-optionsButtons.size()/2));
        g2.setColor(Color.black);
        g2.setFont(optionsFont);
        g2.drawString("Name: " + tmpName, x+25, y);
        g2.setColor(Color.gray);
        g2.drawString("Name: " + tmpName, x+24, y+1);
        

        
        
        //Save
        if(settings.name == null ? tmpName != null : !settings.name.equals(tmpName)){
            y=(game.getHeight()/2)+(75*(5-optionsButtons.size()/2));
            g2.drawImage(warning32x32, x+saveButton.getWidth() , y, null);
            
            g2.setColor(Color.black);
            g2.drawString("Changes will take effect on next deploy", x+saveButton.getWidth()+warning32x32.getWidth(), y+warning32x32.getHeight()/2);
            g2.setColor(Color.gray);
            g2.drawString("Changes will take effect on next deploy", x+saveButton.getWidth()+warning32x32.getWidth(), y+warning32x32.getHeight()/2+1);
        
            saveButton.setX(x);
            saveButton.setY(y);
            saveButton.draw(g2);
            if(saveButton.isB1Released()){
                optionsPage=-1;
                settings.name=tmpName;
//                game.setUserName(tmpName);
                settings.write();
            }
        }
    }    
    protected void drawOptionsSubMenuServer(Graphics g2){
        
    }
    
    protected void drawYesNo(Graphics g2){
//        background.setSize(frame.getWidth(), frame.getHeight());
//        background.draw(g2, 0, 0);
        g2.setFont(exitGame);
        g2.drawString("Return to main menu ? (chiar vrei asta !?)", (game.getWidth()/2)-224, (game.getHeight()/2)-49);
        g2.setColor(Color.BLACK);
        g2.drawString("Return to main menu ? (chiar vrei asta !?)", (game.getWidth()/2)-225, (game.getHeight()/2)-50);
        
        int i=1;
        int x=(game.getWidth()/2)-150;
        int y;
        for(Buttons yesNoButton : yesNoButtons){

            y=(game.getHeight()/2)+(50*(i-yesNoButtons.size()/2));
            
            yesNoButton.setX(x);
            yesNoButton.setY(y);
            yesNoButton.draw(g2);
            
            if(yesNoButton.isB1Released()){
                switch(i){
                    case 1:
                        game.dispose();
                        break;
                    case 2:
                        menuPage=0;
                        break;
                }
            }
            i++;
        }
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
                return textures.leftArrow;
            case 38:
                return textures.upArrow;
            case 39:
                return textures.rightArrow;
            case 40:
                return textures.downArrow;
            default:
                return null;
                
        }
    }
}

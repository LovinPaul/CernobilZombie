package Game.Mods;

import Game.Actors.Actor;
import Game.Actors.Hitman1;
import Game.Actors.Zombie1;
import Game.Maps.Map;
import Game.Network.Connection.Connection;
import Main.SettingsManager;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MPCoop extends Game{
    
    private Connection conn;
    private boolean isMapReady;
    private int respawnTime=500;
    private int respawn;

    
    private JPanel thisPanel = this;
    private Color chatColor;
    
    public MPCoop(SettingsManager settings) {
//        init();
        initChat();
    }
    
    @Override
    public void init(){
        
        
//        conn = new Connection(this);
        if(conn.tryToConnect()){
//            conn.parseTCP();
        }
    }
    private void initChat(){
        
        chatColor = new Color(255, 127, 39, 100);
        
        chatArea = new JTextArea();
        chatArea.setLocation(50, 50);
        chatArea.setSize(300,400);
        chatArea.setEditable(false);
        chatArea.setOpaque(false);
        chatArea.setVisible(true);
        chatArea.setFocusable(false);
        
        jsp = new JScrollPane(chatArea);
        jsp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_NEVER );
        jsp.setLocation(50, 50);
        jsp.setSize(300, 300);
        jsp.setEnabled(true);
        jsp.setVisible(true);
        jsp.getViewport().setOpaque(false);
        jsp.setBorder(null);
        jsp.setOpaque(false);
        
        chatInput = new JTextField();
        chatInput.setSize(400, 25);
        chatInput.setLocation(getWidth()/2-chatInput.getWidth()/2, getHeight()/2-chatInput.getHeight()/2);
        chatInput.setVisible(false);
        chatInput.setOpaque(false);
        chatInput.setBorder(null);

        chatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisPanel.grabFocus();
                chatInput.setVisible(false);
                if(!"".equals(chatInput.getText())){
//                    conn.writeChatTCP(chatInput.getText().getBytes());
                    conn.flushTCP();
                    
                    chatStatusMessage="Sending...";
                    if(getMe()!=null && getMe().isAlive()){
                        chatArea.append(getMe().getName() + " > " + chatInput.getText() + "\n");
                    }else{
                        chatArea.append("(Dead)" + getMe().getName() + " > " + chatInput.getText() + "\n");
                    }
                    
                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
                    chatInput.setText("");
                }

            }
        });
        
        this.setLayout(null);
        this.add(chatInput);
        this.add(jsp);
    }
    
    @Override
    public void setMap(Map map){
        super.setMap(map);
        isMapReady=true;
    }
    
    
    @Override
    public void selectMe(Game.ActorClasses actorClass, String name){
        byte ac = 0;
//        if(!"".equals(name)){
//            this.name=name;
//        }
        
        switch(actorClass){
            case Zombie:
                ac=0;
                break;
            case Hitman:
                ac=1;
                break;
        }
//        conn.writeInitMeTCP(ac, name);
        conn.flushTCP();
    }
//    @Override
//    public void setMyName(String name){
//        this.name = name;
//    }
    
    @Override
    public void getUserInput(){
//        if(key.W){}
//        if(key.S){}
//        if(key.D){}
//        if(key.A){}
//        if(key.g){}
//        if(key.R){getMe().reloadWeapon();}
//        if(key.t){
//            key.t=false;
//            jsp.setVisible(true);
//            chatInput.setVisible(true);
//            chatInput.grabFocus();
//        }
//        if(!chatInput.isVisible()){
//            jsp.setVisible(key.c);
//        }
        
        if(mouse.b2Pressed){
            mouse.b2Pressed=false;
            lockOnTarget();
        }else if(mouse.b2Released){
            mouse.b2Released=false;
            if(cursor.isLocked()){cursor.setLock(false);}
        }

//        if(cursor!=null){cursor.robot();}
    }
    @Override
    public void updateGameMechanics() {
        super.updateGameMechanics();
//        conn.parseTCP();
//        conn.readUDP();
        
        if(getMap()==null){return;}
        
        if(camera.isFreeCam()){
        }else{
            if(getMe()!=null && !key.esc){
                camera.followPoint(cursor.getX(), cursor.getY());
                camera.followActor(getMe());
                getMe().setAngle(cursor.getX(), cursor.getY());
            }
        }
        
        
//        if(conn.isMeKilled()){
//            if(respawn>0){respawn--;}
//        }else{
//            respawn=respawnTime;
//        }
        
        synchronized(actors){
            if(!actors.isEmpty()){

    //            addZombie();
    //            addHitman1();
                nrOfZombies=0;
                nrOfHitmans=0;
                Iterator<Actor> actorsIterator = actors.iterator();
                while ( actorsIterator.hasNext() ) {
                    Actor actor = actorsIterator.next();

                    if(actor instanceof Zombie1){
                        nrOfZombies++;
                    }else if(actor instanceof Hitman1){
                        nrOfHitmans++;
                    }
                    actor.updateActorMechanics();
    //                if(actor.getWeapon()!=null){
    //                    actor.getWeapon().coolDown();
    //                }
                }
            }
        }
//        conn.writeActorUDP();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = g.create();
        
        if(chatInput.isVisible()){
            g2.setColor(chatColor);
            g2.fillRect(chatInput.getX(), chatInput.getY(), chatInput.getWidth(), chatInput.getHeight());
        }
        if(jsp.isVisible()){
            g2.setColor(chatColor);
            g2.fillRect(jsp.getX(), jsp.getY(), jsp.getWidth(), jsp.getHeight());
        }
        
        if("Received...".equals(chatStatusMessage) && messageStatusDisplay>0){
            messageStatusDisplay--;
            g2.setColor(Color.BLUE);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
        }else if("Sending...".equals(chatStatusMessage)){
            g2.setColor(Color.red);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
        }
        

        
        g2.setColor(Color.yellow);
        g2.fillRect(600, 90, conn.getLatencyUDP(), 20);
        g.drawString("Latency : " + conn.getLatencyUDP(), 600, 110);
        g2.fillRect(600, 110, conn.getBytesInUDP(), 20);
        g.drawString("BytesIn : " + conn.getBytesInUDP(), 600, 130);
        g2.fillRect(600, 130, conn.getBytesOutUDP(), 20);
        g.drawString("BytesOut : " + conn.getBytesOutUDP(), 600, 150);
        
//        if(respawn>0){
//            g.drawString("Respawn in : " + respawn, 300, 170);
//        }
//        if(conn.isMeKilled()){
//            g.drawString("You Got Killed", this.getWidth()/2, this.getHeight()/2);
//        }
    }
    
    @Override
    public void gameLoop(){
        if(!key.esc){
            getUserInput();
        }
        updateGameMechanics();
        renderGame();
    }
    
}

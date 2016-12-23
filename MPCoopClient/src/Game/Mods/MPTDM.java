package Game.Mods;

import Game.Network.ModProtocol.TDM_Protocol;
import Game.Network.Connection.Connection;
import Game.UI.InGameUI.TDM_Rime;
import Game.UI.InGameUI.InGameUI;
import Game.Actors.*;
import Game.Maps.*;
import Game.Maps.Environment.Environment;
import Game.Network.*;
import Game.Network.ModProtocol.Protocol;
import Game.Network.ModProtocol.Stats.ActorStats;
import Main.ResourceMonitor;
import Main.SettingsManager;
import Main.Textures.LoadTextures;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MPTDM extends Game{
    
    private TDM_Protocol protocol;
    private boolean isMapReady;
    private int respawnTime=250;
    private int respawn;
    
    private JPanel thisPanel = this;
    
    
    private Color chatColor;
    private Color friendly;
    private Color enemy;
    
    private TDM_Rime gui;
    
    protected boolean chatButton;
    
    public MPTDM(TDM_Protocol protocol) {
        this.protocol = protocol;

    }

    public Iterator<ActorStats.Stats> getProtocolStats(){
        return protocol.getActorStats();
    }
    
    @Override
    public void init() {
        initChat();
        super.init();
        key.setKeys(settings.goUp, settings.goDown, settings.goLeft, settings.goRight, 82, 80);
        
       
        
        friendly = new Color(0, 250, 0, 50);
        enemy = new Color(255, 0, 0, 50);
        
        
        gui = new TDM_Rime(this, settings, textures);
        isReady=true;
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
                    protocol.writeChatTCP(chatInput.getText().getBytes());
                    protocol.flushTCP();
                    
                    chatStatusMessage="Sending...";
//                    if(getMe()!=null && getMe().isAlive()){
//                        chatArea.append(conn.getName() + " > " + chatInput.getText() + "\n");
//                    }else{
//                        chatArea.append("(Dead)" + conn.getName() + " > " + chatInput.getText() + "\n");
//                    }
//                    
//                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
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
    public void dispose(){
        super.dispose();
    }
    @Override
    public void redeploy(){
        protocol.writeRedeployTCP();
    }
//    public boolean isMeKilled(){
//        return conn.isMeKilled();
//    }
    
    @Override
    public void selectMe(Game.ActorClasses actorClass, String name){
        byte ac = 0;
        if(name==null){name="";}
        
        switch(actorClass){
            case Zombie:
                ac=0;
                break;
            case Hitman:
                ac=1;
                break;
            case Robot:
                ac=2;
                break;
        }
        
        protocol.writeInitMeTCP(ac, name);
        protocol.flushTCP();
    }
    public void selectMe(Game.ActorClasses actorClass){
        selectMe(actorClass, settings.name);
    }
    @Override
    public void getUserInput(){
        if(!isReady){return;}
        if(camera.isFreeCam()){
            if(key.goUp()){camera.goUp();}
            if(key.goDown()){camera.goDown();}
            if(key.goRight()){camera.goRight();}
            if(key.goLeft()){camera.goLeft();}
        }
        
        if(key.t){
            key.t=false;
            chatInput.setVisible(true);
            chatInput.grabFocus();
        }
        
        settings.showResourceMonitor=key.resourceMonitor;
        
        if(key.chatArea()){
            key.chatArea=false;
            chatVisibility++;
            if(chatVisibility==3){chatVisibility=0;}
            switch(chatVisibility){
                case 0:
                    jsp.setVisible(true);
                    chatStatusMessage="Show";
                    messageStatusDisplay=messageStatusDisplayTime;
                    break;
                case 1:
                    jsp.setVisible(false);
                    chatStatusMessage="Hide";
                    messageStatusDisplay=messageStatusDisplayTime;
                    break;
                case 2:
                    jsp.setVisible(false);
                    chatStatusMessage="Show on receive";
                    messageStatusDisplay=messageStatusDisplayTime;
                    break;
            }
        }
        
        
        
        if(mouse.b2Pressed){
            mouse.b2Pressed=false;
            lockOnTarget();
        }else if(mouse.b2Released){
            mouse.b2Released=false;
//            if(me()!=null){
                if(cursor.isLocked()){cursor.setLock(false);}
//            }
            
        }
        
        if(getMe()!=null){
            if(key.reload()){getMe().reloadWeapon();}
            if(mouse.mouseWhell>0){
                getMe().nextWeapon();
                mouse.mouseWhell=0;
            }else if(mouse.mouseWhell<0){
                mouse.mouseWhell=0;
                getMe().prevWeapon();
            }
        }
    }
    @Override
    public void updateGameMechanics() {
        if(!isReady){return;}

        
        if(camera.isFreeCam()){
            camera.updateCoordonate();
        }else{
            if(getMe()!=null && !key.esc){
                camera.followPoint(cursor.getX(), cursor.getY());
                camera.followActor(getMe());
                getMe().setAngle(cursor.getX(), cursor.getY());
            }
        }
        
        
        if(protocol.isMeKilled()){
            if(respawn>0){respawn--;}
        }else{
            respawn=respawnTime;
        }
        
        synchronized(actors){
            if(!actors.isEmpty()){
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
        Iterator<Environment> envIterator = getMap().getEnvironment().iterator();
        while(envIterator.hasNext()){
            Environment env = envIterator.next();
            env.updateMechanics();
        }
        if(!key.esc){
            cursor.robot();
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if(!isReady){return;}
        super.paintComponent(g);
        
        Graphics g2 = g.create();
        
        if(getMe()!=null){
            Iterator<Actor> actorIterator = actors.iterator();
            while(actorIterator.hasNext()){
                Actor actor = actorIterator.next();
                if(actor!=getMe() && actor.isInTheFrame() && getMap().isInVisualRange(getMe(), actor)){
                    if(actor.getTeam()!=protocol.getTeam()){
                        g2.setColor(enemy);
                    }else{
                        g2.setColor(friendly);
                    }
                    g2.fillOval((int)(actor.getX()-30-camera.getX()), (int)(actor.getY()-30-camera.getY()), 60, 60);
        //            g2.setColor(Color.black);
    //                g2.drawString(actor.getTeam()+"", (int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()));
                }                
            }
        }


        
        if(chatInput.isVisible()){
            g2.setColor(chatColor);
            g2.fillRoundRect(chatInput.getX()-5, chatInput.getY(), chatInput.getWidth()+10, chatInput.getHeight(), 25, 25);
            chatInput.setLocation(getWidth()/2-chatInput.getWidth()/2, getHeight()/2-chatInput.getHeight()/2);
        }
        if(jsp.isVisible()){
            g2.setColor(chatColor);
            g2.fillRoundRect(jsp.getX()-10, jsp.getY()-10, jsp.getWidth()+20, jsp.getHeight()+20, 25, 25);
        }
        
        
        
        if(chatVisibility==0){jsp.setVisible(true);}
        if("Show on receive".equals(chatStatusMessage) && messageStatusDisplay>0){
            messageStatusDisplay--;
            g2.setColor(Color.black);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
            if(messageStatusDisplay<2){
                jsp.setVisible(false);
            }else{
                jsp.setVisible(true);
            }
        }else if("Hide".equals(chatStatusMessage) && messageStatusDisplay>0){
            messageStatusDisplay--;
            g2.setColor(Color.black);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
        }else if("Show".equals(chatStatusMessage) && messageStatusDisplay>0){
            messageStatusDisplay--;
            g2.setColor(Color.black);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
        }else if("Received...".equals(chatStatusMessage) && messageStatusDisplay>0){
            messageStatusDisplay--;
            g2.setColor(Color.BLUE);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
            if(chatVisibility==2){
                if(messageStatusDisplay<2){
                    jsp.setVisible(false);
                }else{
                    jsp.setVisible(true);
                }
            }

        }else if("Sending...".equals(chatStatusMessage)){
            g2.setColor(Color.red);
            g2.drawString(chatStatusMessage, jsp.getX(), jsp.getY()+jsp.getHeight());
        }
        

        
//        g2.setColor(Color.yellow);
//        g.drawString("Network", 50, 200);
//        g2.fillRect(50, 200, conn.getLatencyUDP(), 20);
//        g.drawString("Latency : " + conn.getLatencyUDP() + " ms", 50, 220);
//        g2.fillRect(50, 220, conn.getBytesInUDP(), 20);
//        g.drawString("BytesIn : " + conn.getBytesInUDP()+ " MB", 50, 240);
//        g2.fillRect(50, 240, conn.getBytesOutUDP(), 20);
//        g.drawString("BytesOut : " + conn.getBytesOutUDP()+ " MB", 50, 260);
        
//        if(respawn>0){
//            g.drawString("Respawn in : " + respawn, 300, 170);
//        }
        if(protocol.isMeKilled()){
            if(respawn>0){
                g.drawString("You Got Killed. Redeploy in " + respawn/30, this.getWidth()/2, this.getHeight()/2);
                camera.setFreeCam(true);
            }else{
                camera.setFreeCam(false);
                jsp.setVisible(false);
                gui.drawDeployScreen(g);                
            }
        }
        if(getMe()!=null && getMe().isAlive()){
            gui.drawHP(g);
            if(getMe().getWeapon()!=null){
                gui.drawAmmo(g);
            }
        }
        if(key.stats()){
            gui.drawStats(g);
        }
        if(key.esc){
            jsp.setVisible(false);
            gui.drawPauseMenu(g);
        }
        mouse.b1Released=false;
        
        if(settings.showResourceMonitor){
            resourceMonitor.drawResource(g, 0, 0);
        }
        
    }

    
}

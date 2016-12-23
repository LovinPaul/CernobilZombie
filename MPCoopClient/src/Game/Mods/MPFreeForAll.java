package Game.Mods;

import Game.Actors.*;
import Game.Actors.Weapons.Grenade;
import Game.Maps.*;
import Game.Network.Connection.Connection;
import Main.SettingsManager;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class MPFreeForAll extends Game{
    
    Connection conn;
    private boolean isMapReady;
    private int respawnTime=500;
    private int respawn;
    
    public MPFreeForAll(SettingsManager settings) {
        init();
    }
    
    public void init(){
        
//        conn = new Connection(this);
        if(conn.tryToConnect()){
//            conn.parseTCP();
        }
        
    }

    @Override
    public void setMap(Map map){
        super.setMap(map);
        isMapReady=true;
    }
    
    @Override
    public void selectMe(Game.ActorClasses actorClass, String name){
        byte ac = 0;

        switch(actorClass){
            case Zombie:
                ac=0;
                break;
            case Hitman:
                ac=1;
                break;
        }
        
//        conn.writeInitMeTCP(ac, name);
//        conn.flushTCP();
    }
    @Override
    public void getUserInput(){
//        if(key.W){}
//        if(key.S){}
//        if(key.D){}
//        if(key.A){}
//        if(key.g){}
//        if(key.R){
//            getMe().reloadWeapon();
//        }
        if(mouse.mouseWhell>0){
            getMe().nextWeapon();
            mouse.mouseWhell=0;
        }else if(mouse.mouseWhell<0){
            mouse.mouseWhell=0;
            getMe().prevWeapon();
        }
//        if(key.button2){
//            super.lockOnTarget();
//        }else{
//            if(cursor.isLocked()){cursor.setLock(false);}
//        }
//        if(cursor!=null){cursor.robot();}
    }
    @Override
    public void updateGameMechanics() {
//        super.updateGameMechanics();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){

//        conn.();
//        conn.readUDP();
//                }
//            }
//        
//        
//        });
//        t.start();
        
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
        
        g2.setColor(Color.yellow);
        g2.fillRect(300, 90, conn.getLatencyUDP(), 20);
        g.drawString("Latency : " + conn.getLatencyUDP(), 300, 110);
        g2.fillRect(300, 110, conn.getBytesInUDP(), 20);
        g.drawString("BytesIn : " + conn.getBytesInUDP(), 300, 130);
        
        if(respawn>0){
            g.drawString("Respawn in : " + respawn, 300, 170);
        }
//        if(conn.isMeKilled()){
//            g.drawString("You Got Killed", this.getWidth()/2, this.getHeight()/2);
//        }
        
//        g.drawString("BytesOut : " + bytesOut, 300, 150);
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

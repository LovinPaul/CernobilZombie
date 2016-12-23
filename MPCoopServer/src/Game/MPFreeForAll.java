package Game;

import Game.Actors.Hitman1;
import Game.Actors.Zombie1;
import Game.Actors.Actor;
import Game.Maps.*;
import Game.Network.Connection;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class MPFreeForAll extends Game{


    public MPFreeForAll() {
        init();
//        cursor = new MousePointer(me, this);
//        cursor.setX(me.getX());
//        cursor.setY(me.getY());
    }

    private void init(){
        setMap(new UrbanDistrict9());
        camera.setFreeCam(true);
    }
    
    
    @Override
    public void getUserInput(){
        super.getUserInput();
    }
    @Override
    public void updateGameMechanics() {
        super.updateGameMechanics();
//        Connection.readAllTCP();
//        Connection.readAllUDP();
        
        if(camera!=null){camera.updateCoordonate();}
        synchronized(actors){
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

                if(actor.isAlive()){
//                    if(actor!=me && actor instanceof Zombie1){((Zombie1)actor).searchForPrey();}
//                    if(actor!=me && actor instanceof Hitman1){((Hitman1)actor).searchForVictim();}
                    actor.updateActorMechanics();                    
                }else{
                    actorsIterator.remove();
                }
            }

        }
//        Connection.writeActorsAllUDP();
//        if(!actors.isEmpty()){
//            Connection.writeAllUDP();
//        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = g.create();
        for(Actor actor : actors){
            if(actor.isHit()){
                g2.setColor(Color.red);
                g2.fillRect((int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-25), 100/2, 5);
                g2.setColor(Color.green);
                g2.fillRect((int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-25), (int)actor.getHitPoints()/2, 5);                
            }
        }
        
        
//        g2.setColor(Color.yellow);
//        g2.fillRect(300, 90, conn.getLatencyUDP(), 20);
//        g.drawString("Latency : " + conn.getLatencyUDP(), 300, 110);
//        g2.fillRect(300, 110, conn.getBytesInUDP(), 20);
//        g.drawString("BytesIn : " + conn.getBytesInUDP(), 300, 130);
        
//        g.drawString("BytesOut : " + bytesOut, 300, 150);
    }
}

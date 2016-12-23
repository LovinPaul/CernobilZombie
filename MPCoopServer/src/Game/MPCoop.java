package Game;

import Game.Actors.Hitman1;
import Game.Actors.Zombie1;
import Game.Actors.Actor;
import Game.Maps.*;
import Game.Network.Connection;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

public class MPCoop extends Game{

    private boolean isFinished;
    private boolean noEnemies;
    private boolean easy=true;
    private boolean medium;
    private boolean hard;


    public MPCoop() {
        init();
    }

    private void init(){
        setMap(new Prologue());
        camera.setFreeCam(true);
        
        
        for(Point point : getMap().getSpawnPoints()){
            if(noEnemies){break;}
            if(point!=getMap().getSpawnPoints().get(0)){
                if(easy){
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
                    Actor newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                }
                if(medium){
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
                    Actor newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                    newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                }
                if(hard){
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
    //                    actors.add(new Zombie1((byte)0, point.x, point.y, (float) (Math.random()*359)));
                    Actor newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                    newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                    newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                    newActor = new Zombie1(assignID(ActorClasses.Zombie), point.x, point.y, (float) (Math.random()*359));
    //                    newActor.init();
                    newActor.setAgility(4);
                    newActor.setName("");
                    add(newActor);
                }
            }
        }
        
    }
    
    public boolean isFinished(){
        return isFinished;
    }
    
    @Override
    public void getUserInput(){
        super.getUserInput();
    }
    
    @Override
    public void updateGameMechanics() {
        super.updateGameMechanics();
//        Connection.verifyConnection();
        
//        Connection.readAllTCP();
//        Connection.readAllUDP();
        
        if(camera!=null){camera.updateCoordonate();}
        synchronized(actors){
            
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
                    if(actor instanceof Zombie1){((Zombie1)actor).searchForPrey();}
                    actor.updateActorMechanics();
                }else{
                    actorsIterator.remove();
                }
            }

        }
//        Connection.writeActorsAllUDP();
//        Connection.writeAllTCP();
//        Connection.writeAllUDP();
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


        if(isFinished){
            g.drawString("You are safe... and alive... for now.", (int) (2700-camera.getX()), (int) (2600-camera.getY()));
        }
    }
}

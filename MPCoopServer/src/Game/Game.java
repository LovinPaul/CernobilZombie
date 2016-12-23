package Game;

import Game.Maps.Environment.Crates.ExploadingCrate;
import Game.Maps.Environment.Environment;
import Game.Actors.Hitman1;
import Game.Actors.Zombie1;
import Game.Actors.Actor;
import Game.Actors.Items.Item;
import Game.Actors.Robot1;
import Game.Actors.Weapons.Grenade;
import Game.Actors.Weapons.Weapon;
import Game.Maps.Environment.Traps.Trap;
import Input.*;
import Game.Maps.Map;
import Main.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

public abstract class Game extends JPanel{
    public enum ActorClasses {
        Zombie, Hitman, Robot
    }
    
//    protected static ArrayList<Actor> actors = Actor.getActors();
//    protected static ArrayList<Grenade> grenades = Grenade.getGrenades();
    
    protected ArrayList<Actor> actors;
    protected ArrayList<Item> items;
    protected ArrayList<Grenade> grenades;
    
    protected int nrMaxOfZombies=19;
    protected int nrMaxOfHitmans=5;
    protected int nrOfZombies=0;
    protected int nrOfHitmans=0;
    protected int nrOfRobots=0;
    
    private Map map;
    protected Actor me;
    public Camera camera;
    protected MousePointer cursor;
    
    protected byte gameMode;
    
    public Game() {
        System.out.println(this.getClass());
        init();
    }
    
    private void init(){
        setLocation(0, 0);
        setSize(Main.getWidth(), Main.getHeight());
        setVisible(true);
        
//        bloodEffects = new ArrayList<>();
        actors = new ArrayList<>();
        items = new ArrayList<>();
        grenades = new ArrayList<>();
        
        camera = new Camera();
        camera.setFreeCam(true);
    }
    
    public Map getMap(){
        return map;
    }
    public Actor getMe(){
        return me;
    }
    public Iterator<Actor> getActorsIterator(){
        return actors.iterator();
    }
    public int getNrOfActors(){
        return actors.size();
    }
    public void setMap(Map map){
        this.map = map;
        camera.setLimits(map.getLayer0().getWidth(), map.getLayer0().getHeight());
        Actor.setMap(map);
        Grenade.setMap(map);
    }
    public byte getGameMode(){
        return gameMode;
    }
    
    public void add(Actor actor){
//        actor.init();
        actors.add(actor);
    }
    public void remove(Actor actor){
        actors.remove(actor);
    }
    public byte assignID(Game.ActorClasses actorClass){
        byte availableID = 0;
        synchronized(actors){
            switch(actorClass){
                case Zombie:
                    availableID = 1;
                    for(int i=0; i<actors.size(); i++){
                        if(actors.get(i) instanceof Zombie1 && actors.get(i).getID()==availableID){
                            i=-1;
                            availableID++;
                        }
                    }
                    break;
                case Hitman:
                    availableID = 20;
                    for(int i=0; i<actors.size(); i++){
                        if(actors.get(i) instanceof Hitman1 && actors.get(i).getID()==availableID){
                            i=-1;
                            availableID++;
                        }
                    }
                    break;
                case Robot:
                    availableID = 40;
                    for(int i=0; i<actors.size(); i++){
                        if(actors.get(i) instanceof Robot1 && actors.get(i).getID()==availableID){
                            i=-1;
                            availableID++;
                        }
                    }
                    break;
            }
        }
        return availableID;
    }
    protected void handleColision(Actor actor){
        float dist;
        float newDist;
        
        boolean colisionOnX = false;
        boolean colisionOnY = false;
        
        for(Actor otherActor : actors){
            dist = (float)(Math.abs(otherActor.getX() - actor.getX()) + Math.abs(otherActor.getY() - actor.getY())); //Math.sqrt(Math.pow((x - actor.getX()), 2) + Math.pow((y - actor.getY()), 2));
            if(dist < 30){
                newDist = (float) (Math.abs(otherActor.getX() - actor.getNewX()) + Math.abs(otherActor.getY() - actor.getNewY()));//Math.sqrt(Math.pow((newX - actor.getX()), 2) + Math.pow((newY - actor.getY()), 2));
                if(newDist<dist){
                    newDist = (float) (Math.abs(otherActor.getX() - actor.getX()) + Math.abs(otherActor.getY() - actor.getNewY()));//Math.sqrt(Math.pow((x - actor.getX()), 2) + Math.pow((newY - actor.getY()), 2));
                    if(newDist<dist){colisionOnY=true;}
                    newDist = (float) (Math.abs(otherActor.getX() - actor.getNewX()) + Math.abs(otherActor.getY() - actor.getY()));//Math.sqrt(Math.pow((newX - actor.getX()), 2) + Math.pow((y - actor.getY()), 2));
                    if(newDist<dist){colisionOnX=true;}
                }
            }
        }
        if( map.rectangles!=null){
            for(Rectangle rect : map.rectangles){
                if(rect.contains(actor.getNewX(), actor.getNewY())){
                    if(rect.intersectsLine(actor.getX(), actor.getY(), actor.getX(), actor.getNewY())){colisionOnY=true;}
                    if(rect.intersectsLine(actor.getX(), actor.getY(), actor.getNewX(), actor.getY())){colisionOnX=true;}
    //                if(rect.intersects(x-10, y-10, 20, 20)){colisionOnX=true;colisionOnY=true;}
    //                if(rect.intersectsLine(x, y, newX, y)){colisionOnY=true;}

    //                if(rect.contains(newX, newY)){colisionOnY=true;}
                    if(rect.contains(actor.getX(), actor.getNewY())){colisionOnY=true;}
                    if(rect.contains(actor.getNewX(), actor.getY())){colisionOnX=true;}
                }
            }
        }

        if(!colisionOnX){actor.setX(actor.getNewX());}
        if(!colisionOnY){actor.setY(actor.getNewY());}
    }
    protected void bulletColision(Actor owner){
        Weapon weapon = owner.getWeapon();
        if(weapon==null || !weapon.isFired()){return;}
        
        Actor closestActor = null;
        float actorDist = 0;

        synchronized(actors){
            for (Actor actor : actors) {
                if(actor!=owner && isInVisualRange(owner, actor) && shortestDistance(owner.getX(),owner.getY(),weapon.getTargetX(), weapon.getTargetY(),actor.getX(),actor.getY())<15){
                    if(closestActor==null){
                        actorDist = (float)(Math.abs(actor.getX() - owner.getX()) + Math.abs(actor.getY() - owner.getY()));
                        closestActor = actor;
                    }else{
                        float newDist = (float)(Math.abs(actor.getX() - owner.getX()) + Math.abs(actor.getY() - owner.getY()));
                        if(newDist<actorDist){
                            actorDist = newDist;
                            closestActor = actor;
                        }
                    }
                }
            }
//            if(closestActor!=null){
//                closestActor.gotHit(weapon.getFirePower());
//            }
        }

        Environment closestEnv = null;
        float envDist = 0;
        Iterator<Environment> environmentIterator = map.getEnvironmentIterator();
        while(environmentIterator.hasNext()){
            Environment env = environmentIterator.next();

            if(env.getBounds().intersectsLine(owner.getX(), owner.getY(), weapon.getTargetX(), weapon.getTargetY())){
                if(closestEnv==null){
                    envDist = (float)(Math.abs(env.getX() - owner.getX()) + Math.abs(env.getY() - owner.getY()));
                    closestEnv = env;
                }else{
                    float newDist = (float)(Math.abs(env.getX() - owner.getX()) + Math.abs(env.getY() - owner.getY()));
                    if(newDist<envDist){
                        envDist = newDist;
                        closestEnv = env;
                    }
                }
            }
        }
        
        if(closestActor!=null && closestEnv!=null){
            if(actorDist<envDist){
                closestActor.gotHit(weapon.getFirePower());
                if(!closestActor.isAlive()){owner.incrementKills(closestActor);}
            }else{
                closestEnv.gotHit(weapon.getFirePower());
            }
        }else if(closestActor!=null){
            closestActor.gotHit(weapon.getFirePower());
            if(!closestActor.isAlive()){owner.incrementKills(closestActor);}
            
        }else if(closestEnv!=null){
            closestEnv.gotHit(weapon.getFirePower());
        }
        

        
//        weapon.setIsFired(false);
        
    }
    protected void trapColision(Actor owner){
        for(Environment env : map.getEnvironment()){
            if(env instanceof Trap){
                if(((Trap)env).getHitBounds().contains(owner.getX(), owner.getY())){
                    ((Trap)env).catchPray(owner);
                }                
            }
        }
    }
    private double shortestDistance(float x1,float y1,float x2,float y2,float x3,float y3){
        float px=x2-x1;
        float py=y2-y1;
        float temp=(px*px)+(py*py);
        float u=((x3 - x1) * px + (y3 - y1) * py) / (temp);
        if(u>1){
            u=1;
        }
        else if(u<0){
            u=0;
        }
        float x = x1 + u * px;
        float y = y1 + u * py;

        float dx = x - x3;
        float dy = y - y3;
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist;

    }
    public boolean isInVisualRange(Actor actor1, Actor actor2){
        return isInVisualRange(actor1.getX(), actor1.getY(), actor2.getX(), actor2.getY());
    }
    public boolean isInVisualRange(float x1, float y1, float x2, float y2){
        if(map.rectangles!=null){
            for(Rectangle rect : map.rectangles){
                if(rect.intersectsLine(x1, y1, x2, y2)){
                    return false;
                }
            }
//            for(Environment env : map.getEnvironment()){
//                if(env.getBounds().intersectsLine(x1, y1, x2, y2)){
//                    return false;
//                }
//            }
            
        }
        return true;
    }
    public boolean isInVisualRange(Actor actor1, float x2, float y2){
        return isInVisualRange(actor1.getX(), actor1.getY(), x2, y2);
    }
    public boolean isInVisualRange(Actor actor1, Environment env2){
        return isInVisualRange(actor1.getX(), actor1.getY(), env2.getX(), env2.getY());
    }
    
    
    public void getUserInput(){
//        if(Input.esc){return;} 
        if(Input.W){
            camera.goUp();
        }
        if(Input.S){
            camera.goDown();
//            Actor.getActors().get(0).goDown();
        }
        if(Input.D){
            camera.goRight();
//            Actor.getActors().get(0).goRight();
        }
        if(Input.A){
            camera.goLeft();
        }
//        if(Input.G){
//            me.throwGrenade(cursor.getX(), cursor.getY());
//            Input.G=false;
//        }
//        if(Input.button1){
//            me.fire(cursor.getX(), cursor.getY());
//            camera.shake(me.getWeapon().getCameraChake());
//        }
//        if(Input.button2){
//            lockOnTarget();
//        }else{
//            if(cursor.isLocked()){cursor.setLock(false);}
//        }
//        
//        cursor.robot();
    }
    public void updateGameMechanics(){
        
        Iterator<Item> itemIterator = Item.items.iterator();
        while(itemIterator.hasNext()){
            Item item = itemIterator.next();
            for(Actor actor : actors){
                if((Math.abs(actor.getX() - item.getX()) + Math.abs(actor.getY() - item.getY()))<40){
                    item.pickUp(actor);
                    break;
                }
            }
            if(item.isPickedUp()){
                itemIterator.remove();
            }
        }
        
        Iterator<Grenade> grenadeIterator = grenades.iterator();
        while ( grenadeIterator.hasNext() ) {
            Grenade grenade = grenadeIterator.next();
            if(grenade.isExploaded()){
                grenadeIterator.remove();
            }else{
                grenade.updateMechanics();
                if(grenade.isExploading()){
                    camera.shake(grenade.getCameraShake());
                }
            }
        }
        
        
        Iterator<Environment> environmentIterator = map.getEnvironmentIterator();
        while(environmentIterator.hasNext()){
            Environment env = environmentIterator.next();
            
            env.updateMechanics();
            if(env instanceof ExploadingCrate){
                if(((ExploadingCrate)env).isExploading()){
                    float dist;
                    for(Actor actor : actors){
                        if(isInVisualRange(actor, env)){
                            dist = (float)(Math.abs(actor.getX() - env.getX()) + Math.abs(actor.getY() - env.getY()));
                            float fireDist = ((ExploadingCrate)env).getFireDistance();
                            if(dist<fireDist){
                                float distPercent = 1-(dist/fireDist);
                                actor.gotHit(distPercent*((ExploadingCrate)env).getFirePower());
                            }
                        }
                    }
                }
            }
            if(env.isDestroyed()){
                map.removeRectangle(env.getBounds());
                environmentIterator.remove();
            }
        }
        
    }
    public void renderGame(){
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(Main.getWidth(), Main.getHeight());
        
        if(map==null || camera==null){return;}
        
        map.drawLayer1(g, camera.getX(), camera.getY());
        
        for(Item item : Item.items){
            item.draw(g, item.getX()-camera.getX(), item.getY()-camera.getY());
        }
        
        synchronized(grenades){
            for(Grenade grenade : grenades){
                grenade.draw(g, grenade.getX()-camera.getX(), grenade.getY()-camera.getY());
            }
        }
        
        nrOfZombies=0;
        nrOfHitmans=0;
        nrOfRobots=0;
        int i=0;
        synchronized(actors){
            for(Actor actor : actors){
                
                for(Actor actor2 : actors){
                    if(actor!=actor2 && actor.getID()==actor2.getID()){
                        g.setColor(Color.red);
                    }else{
                        g.setColor(Color.black);
                    }
                }
                
                g.drawString(actor.getID() + "     - " + actor.getName(), 50, 50+(i*10));
                i++;
                
                if(actor instanceof Zombie1){
                    nrOfZombies++;
                }else if(actor instanceof Hitman1){
                    nrOfHitmans++;
                }else if(actor instanceof Robot1){
                    nrOfRobots++;
                }

                if(me!=null && actor!=me){
                    if(me.isInVisualRange(actor)){
                        actor.draw(g, actor.getX()-camera.getX(), actor.getY()-camera.getY());
                    }else{
                        actor.drawVisuals(g, actor.getX()-camera.getX(), actor.getY()-camera.getY());
                    }
                }else{
                    actor.draw(g, actor.getX()-camera.getX(), actor.getY()-camera.getY());
                }
            }
        }

//        map.drawLayer2(g, camera.getX(), camera.getY());

        //UI
        ////Cursor
        if(cursor!=null){
            cursor.draw(g, cursor.getX()-camera.getX(), cursor.getY()-camera.getY());
        }
        ////Infection
        if(me!=null){
            if(me.getInfectionPoints()<50){
                g.fillRect((int)(getWidth()/2 - me.getInfectionPoints()), 20, (int)(2*me.getInfectionPoints()), 20);
            }else{
                g.setColor(Color.red);
                g.fillRect((int)(getWidth()/2 - 100), 20, (int)(2*100), 20);
                g.setColor(Color.darkGray);
                g.fillRect((int)(getWidth()/2 - me.getInfectionPoints()), 20, (int)(2*me.getInfectionPoints()), 20);
            }
        
    ////        g.drawLine((int)(me.getX()-camera.getX()), (int)(me.getY()-camera.getY()), 
    ////                (int)(me.newXX-camera.getX()), (int)(me.newYY-camera.getY()));
    //        
    //        g.drawLine((int)(me.getX()-camera.getX()), (int)(me.getY()-camera.getY()), 
    //                (int)(me.newXX-camera.getX()), (int)(me.getY()-camera.getY()));
    //        g.setColor(Color.red);
    //        g.drawLine((int)(me.getX()-camera.getX()), (int)(me.getY()-camera.getY()), 
    //                (int)(me.getX()-camera.getX()), (int)(me.newYY-camera.getY()));
    ////        g.fillOval((int)(me.newXX-camera.getX()-10), (int)(me.newYY-camera.getY()-10), 20, 20);
    //        g.fillOval((int)(me.getX()-camera.getX()-10), (int)(me.newYY-camera.getY()-10), 20, 20);
    //        g.setColor(Color.black);
    //        g.fillOval((int)(me.newXX-camera.getX()-10), (int)(me.getY()-camera.getY()-10), 20, 20);



            g.drawString("" +me, 50, 50);
            g.drawString("MeX: "+ me.getX(), 50, 70);
            g.drawString("MeY: " +me.getY(), 50, 90);

            g.drawString("HP: " + me.getHitPoints(), 50, 120);
            g.drawString("IP: " + me.getInfectionPoints(), 50, 140);
            g.drawString("AG: " + me.getAgility(), 50, 160);

            if(me.getWeapon()!=null){
                g.drawString("Weapon : " + me.getWeapon().getClass(), 50, 170);
                g.drawString("  clip : " + "unlimited", 50, 190);
                g.drawString("   amo : " + "unlimited", 50, 210);
            }
        }

        g.drawString("Zombie : " + nrOfZombies, 300, 50);
        g.drawString("Hitman : " + nrOfHitmans, 300, 70);

//        if(Connection.connections!=null && !Connection.connections.isEmpty()){
//            g.drawString("K : " + Connection.connections.get(0).kills, 300, 110);
//            g.drawString("D : " + Connection.connections.get(0).deaths, 300, 130);
//        }
        
//            g.drawString("Latency : " + latency, 300, 110);
//            g.drawString("BytesIn : " + bytesIn, 300, 130);
//            g.drawString("BytesOut : " + bytesOut, 300, 150);

//        g.drawString("CameraX: " +camera.getX(), 600, 50);
//        g.drawString("CameraY: " +camera.getY(), 600, 70);
//        g.drawString("CursorX: " +cursor.getX(), 600, 90);
//        g.drawString("CursorY: " +cursor.getY(), 600, 110);
//        g.drawString("mouseY: " +Input.mouseY, 600, 130);
        
        

    }
    
    public void gameLoop(){
        getUserInput();
        updateGameMechanics();
        renderGame();
    }
    
}

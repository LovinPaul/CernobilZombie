package Game.Mods;

import Game.Actors.AI.SP_AI;
import Game.Actors.Zombie1;
import Game.Actors.Actor;
import Game.Actors.Hitman1;
import Game.Actors.Weapons.Machine;
import Game.Maps.*;
import Game.UI.InGameUI.SP_Rime;
import Main.ResourceMonitor;
import Main.SettingsManager;
import Main.Textures.LoadTextures;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class SP extends Game{
    
    

    protected boolean noEnemies;
    protected boolean easy=true;
    protected boolean mediu;
    protected boolean hard;
    
    private SP_AI ai;
    private SP_Rime gui;
    
    private boolean objectiveReached;
    
    public ResourceMonitor resourceMonitor;
    
    public SP(SettingsManager settings, int difficulty, byte mapID) {
        this.settings=settings;
        this.mapID=mapID;
        
        ai = new SP_AI();
        
        switch(difficulty){
            case -1:
                noEnemies=true;
                easy=false;
                mediu=false;
                hard=false;
                break;
            case 0:
                noEnemies=false;
                easy=true;
                mediu=false;
                hard=false;
                break;
            case 1:
                noEnemies=false;
                easy=false;
                mediu=true;
                hard=false;
                break;
            case 2:
                noEnemies=false;
                easy=false;
                mediu=false;
                hard=true;
                break;
        }
    }

    @Override
    public void setTexture(LoadTextures textures) {
        super.setTexture(textures);
        init();
    }
    
    @Override
    public void init(){
        super.init();
        
        gui = new SP_Rime(this, settings, textures);
        key.setKeys(settings.goUp, settings.goDown, settings.goLeft, settings.goRight, 82, 80);
//        setMap(new Prologue(textures));
        ArrayList<Point> points = getMap().getSpawnPoints();
        
        Actor newMe =new Hitman1((byte)1, points.get(0).x, points.get(0).y, 10);
        newMe.setFollowAngle(false);
        newMe.addWeapon(new Machine(textures));
        newMe.init(textures);
        setMe(newMe);
        newMe.setIsInTheFrame(true);
        actors.add(newMe);
        
        for(int i=1; i<points.size();i++){
            if(noEnemies){break;}
            if(easy){
                Actor newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
            }
            if(mediu){
                Actor newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
                newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
            }
            if(hard){
                Actor newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
                newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
                newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
                newActor = new Zombie1((byte)0, points.get(i).x, points.get(i).y, (float) (Math.random()*359));
                newActor.init(textures);
                actors.add(newActor);
            }
        }
        isReady=true;
    }

    @Override
    public void redeploy() {
        actors.clear();
        init();
        gameOver=false;
        objectiveReached=false;
        
    }
    
    public boolean isObjectiveReached(){
        return objectiveReached;
    }
    
    @Override
    public void getUserInput(){
        if(!isReady){return;}
        if(key.goUp()){
            getMe().goUp();
        }
        if(key.goDown()){
            getMe().goDown();
        }
        if(key.goRight()){
            getMe().goRight();
        }
        if(key.goLeft()){
            getMe().goLeft();
        }
        if(key.reload()){
            getMe().reloadWeapon();
        }
        if(mouse.b1Down){
            getMe().fire(cursor.getX(), cursor.getY());
//            camera.shake(me.getWeapon().getCameraShake());
        }
        if(mouse.b2Pressed){
            mouse.b2Pressed=false;
            lockOnTarget();
        }else if(mouse.b2Released){
            mouse.b2Released=false;
            if(cursor.isLocked()){cursor.setLock(false);}
        }
    }
    @Override
    public void updateGameMechanics() {
        if(!isReady){return;}
        super.updateGameMechanics();
        
        if(super.getMap().isInFinishArea(getMe())){gameOver=true;objectiveReached=true;}
        if(getMe().isInfected()){
//            Actor.remove(me);
//            float x =me().getX();
//            float y =me().getY();
//            float angle =me().getAngle();
////            Weapon weapon = me.getWeapon();
//            me=new Zombie1((byte)0, x, y, angle);
//            me.init();
//            Actor.add(me);
//            me.setWeapon(weapon);
//            actors.add(me);
            gameOver=true;
            objectiveReached=false;
        }
        if(getMe().getHitPoints()<=0){gameOver=true;objectiveReached=false;}
        
        if(camera.isFreeCam()){
        }else{
            if(getMe()!=null && !key.esc){
                getMe().setAngle(cursor.getX(), cursor.getY());
                camera.followPoint(cursor.getX(), cursor.getY());
                camera.followActor(getMe());                
            }
        }
        
        
        synchronized(actors){
//            addZombie();
//            addHitman1();
            nrOfZombies=0;
            nrOfHitmans=0;
            Iterator<Actor> actorsIterator = actors.iterator();
            while ( actorsIterator.hasNext() ) {
                Actor actor = actorsIterator.next();

                if(actor instanceof Zombie1){
                    ai.searchForPrey(actor, this);
                    
                    nrOfZombies++;
                }else if(actor instanceof Hitman1){
                    nrOfHitmans++;
                }
                
                
                actor.updateActorMechanics();
                handleColision(actor);
                bulletColision(actor);                
                
                
                if(actor.isAlive()){
//                    if(actor!=getMe() && actor instanceof Zombie1){((Zombie1)actor).searchForPrey();}
                    if(actor!=getMe() && actor instanceof Hitman1){((Hitman1)actor).searchForVictim();}

                }else{
                    actorsIterator.remove();
                }
            }

        }
        
    }
    @Override
    protected void paintComponent(Graphics g) {
        if(!isReady){return;}
        super.paintComponent(g);
        ////Infection
        if(getMe()!=null){
            if(getMe().getInfectionPoints()<50){
                g.fillRect((int)(getWidth()/2 - getMe().getInfectionPoints()), 20, (int)(2*getMe().getInfectionPoints()), 20);
            }else if(getMe().getInfectionPoints()>50 && getMe().getInfectionPoints()<75){
                g.setColor(Color.red);
                g.fillRect((int)(getWidth()/2 - 100), 20, (int)(2*100), 20);
                g.setColor(Color.darkGray);
                g.fillRect((int)(getWidth()/2 - getMe().getInfectionPoints()), 20, (int)(2*getMe().getInfectionPoints()), 20);
            }else if(getMe().getInfectionPoints()>75){
                if(g.getColor()!=g.getColor()){
                    g.setColor(Color.magenta);
                }else{
                    g.setColor(Color.red);
                }
                
                g.fillRect((int)(getWidth()/2 - 100), 20, (int)(2*100), 20);
                g.setColor(Color.darkGray);
                g.fillRect((int)(getWidth()/2 - getMe().getInfectionPoints()), 20, (int)(2*getMe().getInfectionPoints()), 20);
            }

            gui.drawHP(g);
            if(getMe().getWeapon()!=null){
                gui.drawAmmo(g);
            }
    
        }
        
        synchronized(actors){
            for(Actor actor : actors){
                if(actor.getX()>camera.getX() && actor.getX()<(camera.getX()+this.getWidth()) && 
                        actor.getY()>camera.getY() && actor.getY()<(camera.getY()+this.getHeight())){
                    actor.setIsInTheFrame(true);
                }else{
                    actor.setIsInTheFrame(false);
                }
            }
        }

        
        if(isDisposed()){
            g.drawString("You are safe... and alive... for now.", (int) (2700-camera.getX()), (int) (2600-camera.getY()));
        }
        
        if(gameOver){
            gui.drawGameOver(g);
        }else{
            if(key.esc){
                gui.drawPauseMenu(g);
            }            
        }

        mouse.b1Released=false;
        
        if(settings.showResourceMonitor){
            resourceMonitor.drawResource(g, 0, 0);
        }
    }
    
    @Override
    public void gameLoop(){
        if(gameOver){key.esc=true;}
        if(!key.esc){
            getUserInput();
            updateGameMechanics();
        }
        renderGame();
    }
    
}

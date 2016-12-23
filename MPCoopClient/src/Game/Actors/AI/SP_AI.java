package Game.Actors.AI;

import Game.Actors.*;
import Game.Mods.Game;
import java.util.Iterator;

public class SP_AI extends AI{
    
    public void searchForPrey(Actor thisActor, Game game){
        if(!(thisActor instanceof Zombie1)){System.out.println("SP_AI searchForPrey - Need a Zombie as first argument");return;}
        
        Actor closestActor = null;
        float dist = 0;
        
        Iterator<Actor> actors = game.getActorsIterator();
        while(actors.hasNext()){
            Actor actor = actors.next();
            if(!(actor instanceof Zombie1) && !actor.isInfected()){
                if(game.getMap().isInVisualRange(thisActor, actor)){
                    if(closestActor==null){
                        dist = (float)(Math.abs(actor.getX() - thisActor.getX()) + Math.abs(actor.getY() - thisActor.getY()));
                        closestActor = actor;
                    }else{
                        float newDist = (float)(Math.abs(actor.getX() - thisActor.getX()) + Math.abs(actor.getY() - thisActor.getY()));
                        if(newDist<dist){
                            dist = newDist;
                            closestActor = actor;
                        }
                    }
                }
            }
        }
        
        if(closestActor==null){
            if((Math.abs(thisActor.getX()-thisActor.getTargetX())<=thisActor.getAgility() && Math.abs(thisActor.getY()-thisActor.getTargetY())<=thisActor.getAgility())){
                thisActor.setX(thisActor.getTargetX());
                thisActor.setY(thisActor.getTargetY());
            }else{
                if(aimAt(thisActor, thisActor.getTargetX(), thisActor.getTargetY())){
                    thisActor.goForward();
                }
            }
            
        }else{
            thisActor.setTarget(closestActor.getX(), closestActor.getY());
            aimAt(thisActor, closestActor.getX(), closestActor.getY());
            if(dist<((Zombie1)thisActor).getInfectionRange()){closestActor.infect((float) (Math.random()*((Zombie1)thisActor).getInfectionPower()));}
            if(dist<35){closestActor.gotHit(((Zombie1)thisActor).getBitePower());}
            if(aimAt(thisActor, thisActor.getTargetX(), thisActor.getTargetY())){
                thisActor.goForward();
            }
        }
    }
    
}

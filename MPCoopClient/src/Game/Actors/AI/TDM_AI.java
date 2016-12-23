package Game.Actors.AI;

import Game.Actors.Actor;
import Game.Mods.Game;
import java.util.Iterator;

public class TDM_AI extends AI{
    
    public void searchForVictim(Actor thisActor, Game game){
        if(!thisActor.getName().equals("Bot")){return;}
        
        Actor closestActor = null;
        float dist = 0;
        
        Iterator<Actor> actors = game.getActorsIterator();
        while(actors.hasNext()){
            Actor actor = actors.next();
            if(thisActor.getTeam()!=actor.getTeam()){
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
            if(!(Math.abs(thisActor.getX()-thisActor.getTargetX())<50 && Math.abs(thisActor.getY()-thisActor.getTargetY())<50)){
                thisActor.setAngle(thisActor.getTargetX(), thisActor.getTargetY());
                thisActor.goForward();
            }
//            return false;
        }else{
            thisActor.setTarget(closestActor.getX(), closestActor.getY());
            aimAt(thisActor, closestActor.getX(), closestActor.getY());
            if(thisActor.getWeapon()!=null){
                if(dist<=500){thisActor.getWeapon().fire(closestActor.getX(), closestActor.getY());}
            }
            if(dist>500){thisActor.goForward();}
            if(dist<250){thisActor.goBackward();}
//            return true;
        }
    }
    

    


}

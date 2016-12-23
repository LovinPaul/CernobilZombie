package Game.Actors.AI;

import Game.Actors.Actor;
import Game.Actors.Items.HP_Crate;
import Game.Actors.Items.Item;
import Game.Game;
import java.awt.Point;
import java.util.Iterator;

public class TDM_AI {
    
    private Game game;

    public TDM_AI(Game game) {
        this.game = game;
    }
    
    
    
    private Item searchForMedKit(Actor thisActor){
        Item closestItem = null;
        float dist = 0;
        
        for(Item item : Item.items){
            if(item instanceof HP_Crate){
                if(thisActor.isInVisualRange(item.getX(), item.getY())){
                    if(closestItem==null){
                        dist = (float)(Math.abs(item.getX() - thisActor.getX()) + Math.abs(item.getY() - thisActor.getY()));
                        closestItem = item;
                    }else{
                        float newDist = (float)(Math.abs(item.getX() - thisActor.getX()) + Math.abs(item.getY() - thisActor.getY()));
                        if(newDist<dist){
                            dist = newDist;
                            closestItem = item;
                        }
                    }
                }
            }
        }
        return closestItem;
    }
    
    private Point randomPoint(Actor thisActor){
//        if(newTargetX==0 && newTargetY==0){
           float newTargetX = (float) (thisActor.getX() + (Math.random()*2000 -1000));
           float newTargetY = (float) (thisActor.getY() + (Math.random()*2000 -1000));
           int i=0;//Temporary
           do{
               newTargetX = (float) (thisActor.getX() + (Math.random()*2000 -1000));
               newTargetY = (float) (thisActor.getY() + (Math.random()*2000 -1000));
               i++;
               if(i>15){return null;}
           }while(!thisActor.isInVisualRange(newTargetX, newTargetY));
           
//            if(){
//                thisActor.setTarget(newTargetX, newTargetY);
                return new Point((int)newTargetX, (int)newTargetY);
//            }
//        }
//        return null;
    }
    
    private Actor searchForEnemyTeam(Actor thisActor){
        Actor closestEnemy = null;
        float dist = 0;
        
        Iterator<Actor> actors = game.getActorsIterator();
        while(actors.hasNext()){
            Actor actor = actors.next();
            if(thisActor.getTeam()!=actor.getTeam()){
                if(thisActor.isInVisualRange(actor)){
                    if(closestEnemy==null){
                        dist = (float)(Math.abs(actor.getX() - thisActor.getX()) + Math.abs(actor.getY() - thisActor.getY()));
                        closestEnemy = actor;
                    }else{
                        float newDist = (float)(Math.abs(actor.getX() - thisActor.getX()) + Math.abs(actor.getY() - thisActor.getY()));
                        if(newDist<dist){
                            dist = newDist;
                            closestEnemy = actor;
                        }
                    }
                }
            }
        }
        return closestEnemy;
    }
    
    public void control(Actor thisActor){
        if(!thisActor.getName().equals("Bot")){return;}
        
        thisActor.setHitPoints(thisActor.getHitPoints()-0.1f);
        
        Actor closestEnemy = searchForEnemyTeam(thisActor);
        float distToEnemy;
        Item medKit = searchForMedKit(thisActor);
        float distToMedKit;

        
        if(thisActor.getHitPoints()<35 && medKit!=null){
            thisActor.setTarget(medKit.getX(), medKit.getY());
        }else{

        }
        
        
        
        if(closestEnemy!=null && medKit!=null){
            
            distToMedKit = (float)(Math.abs(medKit.getX() - thisActor.getX()) + Math.abs(medKit.getY() - thisActor.getY()));
            distToEnemy = (float)(Math.abs(closestEnemy.getX() - thisActor.getX()) + Math.abs(closestEnemy.getY() - thisActor.getY()));
            
            if(thisActor.getHitPoints()<50 && distToMedKit<distToEnemy){
                if(thisActor.getTargetX()!=medKit.getX() && thisActor.getTargetY()!=medKit.getY()){
                    thisActor.setTarget(medKit.getX(), medKit.getY());
                }
            }else if(thisActor.getHitPoints()<80 && 3*distToMedKit<distToEnemy){
                if(thisActor.getTargetX()!=medKit.getX() && thisActor.getTargetY()!=medKit.getY()){
                    thisActor.setTarget(medKit.getX(), medKit.getY());
                }
            }else{
                thisActor.setTarget(closestEnemy.getX(), closestEnemy.getY());
                if(aimAt(thisActor, closestEnemy.getX(), closestEnemy.getY())){
                    if(thisActor.getWeapon()!=null){
                        if(distToEnemy<=500){
                            thisActor.getWeapon().fire(closestEnemy.getX(), closestEnemy.getY());
                        }
                    }
                }
                if(distToEnemy>500){thisActor.goForward();}
                if(distToEnemy<250){thisActor.goBackward();}
                return;
            }
            
        }else if(closestEnemy!=null){
            distToEnemy = (float)(Math.abs(closestEnemy.getX() - thisActor.getX()) + Math.abs(closestEnemy.getY() - thisActor.getY()));
            thisActor.setTarget(closestEnemy.getX(), closestEnemy.getY());
            if(aimAt(thisActor, closestEnemy.getX(), closestEnemy.getY())){
                if(thisActor.getWeapon()!=null){
                    if(distToEnemy<=500){
                        thisActor.getWeapon().fire(closestEnemy.getX(), closestEnemy.getY());
                    }
                }
            }
            if(distToEnemy>500){thisActor.goForward();}
            if(distToEnemy<250){thisActor.goBackward();}
            return;
            
        }else if(medKit!=null){
            if(thisActor.getHitPoints()<80){
                if(thisActor.getTargetX()!=medKit.getX() && thisActor.getTargetY()!=medKit.getY()){
                    thisActor.setTarget(medKit.getX(), medKit.getY());
                }
//                if(aimAt(thisActor, medKit.getX(), medKit.getY())){
//                    thisActor.goForward();
//                }
            }
        }
        
        if((thisActor.getX()!=thisActor.getTargetX() && thisActor.getY()!=thisActor.getTargetY()) & !thisActor.isStuck()){
            if((Math.abs(thisActor.getX()-thisActor.getTargetX())<=thisActor.getAgility() && Math.abs(thisActor.getY()-thisActor.getTargetY())<=thisActor.getAgility())){
                thisActor.setX(thisActor.getTargetX());
                thisActor.setY(thisActor.getTargetY());
            }else{
                if(aimAt(thisActor, thisActor.getTargetX(), thisActor.getTargetY())){
                    thisActor.goForward();
                }
            }

        }else{
            Point walkTo = randomPoint(thisActor);
            if(walkTo!=null){
                thisActor.setTarget(walkTo.x, walkTo.y);
            }
            
        }
        
//        if(closestActor==null){
//            if((Math.abs(thisActor.getX()-thisActor.getTargetX())>=thisActor.getAgility() && Math.abs(thisActor.getY()-thisActor.getTargetY())>=thisActor.getAgility()) & !thisActor.isStuck()){
////                thisActor.setAngle(thisActor.getTargetX(), thisActor.getTargetY());
//                if(aimAt(thisActor, thisActor.getTargetX(), thisActor.getTargetY())){
//                    thisActor.goForward();
//                }
//            }else{
//                walkAround(thisActor);
//            }
////            return false;
//        }else{
//            thisActor.setTarget(closestActor.getX(), closestActor.getY());
//            if(aimAt(thisActor, closestActor.getX(), closestActor.getY())){
//                if(thisActor.getWeapon()!=null){
//                    if(dist<=500){
//                        thisActor.getWeapon().fire(closestActor.getX(), closestActor.getY());
//                    }
//                }
//            }
//            if(dist>500){thisActor.goForward();}
//            if(dist<250){thisActor.goBackward();}
////            return true;
//        }
    }
    
    private boolean aimAt(Actor thisActor, float targetX, float targetY){
        float newAngle = (float) Math.toDegrees(Math.atan2(targetY - thisActor.getY(), targetX - thisActor.getX())) + 90;
        if(newAngle < 0){newAngle += 359;}

        if(Math.abs(thisActor.getAngle() - newAngle)>thisActor.getAgility()){
            if(aimRotateRight(thisActor.getAngle(), newAngle)){
                thisActor.setAngle(thisActor.getAngle()-thisActor.getAgility());
                if(thisActor.getAngle()<0){thisActor.setAngle(thisActor.getAngle()+359);}
            }else{
                thisActor.setAngle(thisActor.getAngle()+thisActor.getAgility());
                if(thisActor.getAngle()>359){thisActor.setAngle(thisActor.getAngle()-360);}
            }
            return false;
        }else{
            thisActor.setAngle(newAngle);
            return true;
        }
    }
    
    private boolean aimRotateRight(float angle, float newAngle){
        float leftSide;
        float rightSide;
        
        if(angle>=newAngle){
            leftSide = newAngle + 359-angle;
            rightSide = angle - newAngle;
        }else{
            leftSide = newAngle-angle;
            rightSide = 359-newAngle + angle;
        }
        
        return leftSide>rightSide;
    }
}


package Game.Actors.AI;

import Game.Actors.Actor;


public abstract class AI {
    
    protected boolean aimAt(Actor thisActor, float targetX, float targetY){
//        if(thisActor.getWeapon()!=null){
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
//        }else{
//            return false;
//        }
    }
    protected boolean aimRotateRight(float angle, float newAngle){
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

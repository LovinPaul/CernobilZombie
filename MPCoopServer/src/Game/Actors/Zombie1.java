
package Game.Actors;

import Main.LoadTextures;

public class Zombie1 extends Actor{
    
    private float targetX=x;
    private float targetY=y;
    
    private float infectionRange=200;
    private float infectionPower=1;
    private float bitePower=1;
    
    public Zombie1(byte ID, float x, float y, float angle) {
        super(ID, x, y, angle);
        super.setAgility(7);
        super.setInfected(true);
        super.setImages(LoadTextures.zombie1_hold, LoadTextures.zombie1_stand, LoadTextures.zombie1_left_hand, LoadTextures.zombie1_right_hand);
    }
    
    public float getInfectionRange(){
        return infectionRange;
    }
    public float getInfectionPower(){
        return infectionPower;
    }
    public float getBitePower(){
        return bitePower;
    }

    public void setInfectionRange(float infectionRange){
        this.infectionRange=infectionRange;
    }    
    public void setInfectionPower(float infectionPower){
        this.infectionPower=infectionPower;
    }
    public void setBitePower(float bitePower){
        this.bitePower = bitePower;
    }
    
    public boolean searchForPrey(){
        Actor closestActor = null;
        float dist = 0;
        
//        for(Actor actor : actors){
//            if(!(actor instanceof Zombie1) && !actor.isInfected()){
//                if(isInVisualRange(actor)){
//                    
//                    if(closestActor==null){
//                        dist = (float)(Math.abs(actor.getX() - x) + Math.abs(actor.getY() - y));
//                        closestActor = actor;
//                    }else{
//                        float newDist = (float)(Math.abs(actor.getX() - x) + Math.abs(actor.getY() - y));
//                        if(newDist<dist){
//                            dist = newDist;
//                            closestActor = actor;
//                        }
//                    }
//                }
//            }
//        }
        
        if(closestActor==null){
            if(!(x==targetX && y==targetY)){
                setAngle(targetX, targetY);
                goForward();
            }
            return false;
        }else{
            targetX = closestActor.getX();
            targetY = closestActor.getY();
            aimForPrey(targetX, targetY);
//            if(dist<infectionRange){closestActor.infect((float) (Math.random()*infectionPower));}
            if(dist<35){closestActor.gotHit(bitePower);}
//            goForward();
            return true;
        }
    }
    
    private boolean aimForPrey(float targetX, float targetY){
        float newAngle = (float) Math.toDegrees(Math.atan2(targetY - y, targetX - x)) + 90;
        if(newAngle < 0){newAngle += 359;}

        if(Math.abs(angle - newAngle)<90){goForward();}
        if(Math.abs(angle - newAngle)>agility){
            if(aimRotateRight(newAngle)){
                angle-=agility;
                if(angle<0){angle+=359;}
            }else{
                angle+=agility;
                if(angle>359){angle-=360;}
            }
            return false;
        }else{
            angle = newAngle;
            return true;

        }
    }
    private boolean aimRotateRight(float newAngle){
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

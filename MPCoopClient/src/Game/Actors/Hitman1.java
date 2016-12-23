package Game.Actors;

import Main.Textures.LoadTextures;

public class Hitman1 extends Actor{

    private float targetX=x;
    private float targetY=y;
    
    private float invisibilityPower=2;
    
    
    public Hitman1(byte ID, float x, float y, float angle) {
        super(ID, x, y, angle);
    }
    public Hitman1(byte ID) {
        super(ID);

    }

    @Override
    public void init(LoadTextures textures) {
        super.init(textures);
        super.setAgility(7);
        super.setImages(textures.hitman1_hold, textures.hitman1_stand, textures.hitman1_left_hand, textures.hitman1_right_hand);         
    }
    
    
    
    public float getInvisibilityPower(){
        return invisibilityPower;
    }
    
    public void setInvisibilityPower(float invisibilitiPower){
        if(invisibilitiPower>100){invisibilitiPower=100;}
        if(invisibilitiPower<0){invisibilitiPower=0;}
        this.invisibilityPower = invisibilitiPower;
    }
    
    public boolean searchForVictim(){
//        Actor closestActor = null;
//        float dist = 0;
//        
//        for(Actor actor : actors){
//            if(!(actor instanceof Hitman1) && actor.isInfected()){
//                if(isInVisualRange(actor)){
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
//        
//        if(closestActor==null){
//            if(!(Math.abs(x-targetX)<50 && Math.abs(y-targetY)<50)){
//                setAngle(targetX, targetY);
//                goForward();
//            }
//            return false;
//        }else{
//            targetX = closestActor.getX();
//            targetY = closestActor.getY();
//            aimAt(targetX, targetY);
//            if(getWeapon()!=null){
//                if(dist<=500){getWeapon().fire(targetX, targetY);}
//            }
//            if(dist>500){goForward();}
//            if(dist<250){goBackward();}
//            return true;
//        }

        return false;
    }
    
    private boolean aimAt(float targetX, float targetY){
        if(getWeapon()!=null){
            float newAngle = (float) Math.toDegrees(Math.atan2(targetY - y, targetX - x)) + 90;
            if(newAngle < 0){newAngle += 359;}
            
            if(this.getID()==99){
                System.out.println(angle + "     -     " + newAngle);
            }
            
            
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
        }else{
            return false;
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

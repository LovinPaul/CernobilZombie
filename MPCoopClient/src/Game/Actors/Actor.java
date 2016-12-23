
package Game.Actors;

import Game.Animations.Blood.BloodSprite4;
import Game.Animations.Blood.BloodSprite2;
import Game.Animations.Blood.BloodSprite3;
import Game.Animations.Blood.BloodSprite1;
import Game.Animations.Presence;
import Game.Animations.Animation;
import Game.Actors.Weapons.*;
import Main.Textures.LoadTextures;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

public abstract class Actor {
    //statics
//    protected static ArrayList<Actor> actors = new ArrayList<>();
//    private Map map; /// E STATIC!!!!!!!!!!!!!!!!
    
    private BufferedImage actor_hold;
    private BufferedImage actor_stand;
    private BufferedImage actor_left_hand;
    private BufferedImage actor_right_hand;
    
    private String name="anonim";
    private byte team;
    private byte ID;
    public byte kills;
    private byte deaths;
    private byte accuracy;
    protected LoadTextures textures;
    private Weapon weapon;
    private ArrayList<Weapon> weapons;
    private int weaponIndex;
    private boolean isInfected;
    private boolean isUpdated;
    private boolean isInTheFrame;
    private boolean isHit;
    
    private float hitPoints = 100;
    private float infectionPoints;
    
    private boolean followMouse;
    private boolean followAngle=true;
    private boolean showName=true;
    private boolean showID;
    private boolean showTeam;
    
    protected float x;
    protected float y;
    protected float newX;
    protected float newY;
    
    private float speedX;
    private float speedY;
    private float speedUpRate=1;
    protected float angle;
    private boolean moveY;
    private boolean moveX;
    protected int agility;
    
    //cheats
    public boolean isInvincible;
    public boolean isImune;
    public boolean autoAim=true;
    
    protected ListIterator<Animation> bloodIterator;
    private ArrayList<Animation> bloodEffects;
    private Animation presence;
    private int lastShownPresance;
    
    //AI
    private float targetX=x;
    private float targetY=y;
    
    public Actor(byte ID, float x, float y, float angle) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        targetX=x;
        targetY=y;
        newX=x;
        newY=y;
        this.angle = angle;
        
    }
    public Actor(byte ID){
        this.ID = ID;
        isInTheFrame=false;

    }
    
    public void init(LoadTextures textures){
        this.textures = textures;
        presence = new Presence(textures);
        bloodEffects = new ArrayList<>();
        bloodIterator = bloodEffects.listIterator();    
    }
    
    public Weapon getWeapon(){
        if(weapons==null || weapons.isEmpty()){
            return null;
        }else{
            return weapons.get(weaponIndex);
        }
    }
    public byte getWeaponByte(){
        if(weapons==null && weapon ==null){return 0;}
        if(weapons!=null){
            if(weapons.isEmpty()){return 0;}
            if(weapons.get(weaponIndex) instanceof NineMM){
                return 1;
            }else if(weapons.get(weaponIndex) instanceof Machine){
                return 2;
            }else if(weapons.get(weaponIndex) instanceof M134){
                return 3;
            }else if(weapons.get(weaponIndex) instanceof Molotov){
                return 4;
            }else{
                return 0;
            }
        }else{
            if(weapon instanceof NineMM){
                return 1;
            }else if(weapon instanceof Machine){
                return 2;
            }else if(weapon instanceof M134){
                return 3;
            }else if(weapon instanceof Molotov){
                return 4;
            }else{
                return 0;
            }
        }

    }
    public byte getNrOfWeapons(){
        return weapons!=null?(byte) weapons.size():0;
    }
    public byte[] getWeaponsByte(){
        byte[] weaponsByte = new byte[getNrOfWeapons()];
        
        int i=0;
        for(Weapon weap : weapons){
            weaponsByte[i]= weap.getID();
            i++;
        }
        return weaponsByte;
    }
    public byte getTeam(){
        return team;
    }
    public byte getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
    public float getX(){
        return x;//-actor_gun.getWidth()/2
    }
    public float getY(){
        return y;//-actor_gun.getHeight()/2
    }
    public float getNewX(){
        return newX;
    }
    public float getNewY(){
        return newY;
    }
    public float getAgility() {
        return agility;
    }
    public float getSpeedRate() {
        return speedUpRate;
    }
    public float getAngle(){
        return angle;
    }
    public float getHitPoints(){
        return hitPoints;
    }
    public float getInfectionPoints(){
        return infectionPoints;
    }
    public Rectangle getRectangle(){
        return new Rectangle(
                (int)(x-actor_hold.getWidth()), 
                (int)(y-actor_hold.getHeight()), 
                actor_hold.getWidth(), 
                actor_hold.getHeight());
    }
    public float getTargetX(){
        return targetX;
    }
    public float getTargetY(){
        return targetY;
    }
    
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setWeapon(byte byteWeapon){
        if(weapons==null){
            switch(byteWeapon){
                case -4:
                    if(!(weapon instanceof Molotov)){weapon= new Molotov();}
                    break;                
                case -3:
                    if(!(weapon instanceof M134)){weapon= new M134(textures);}
                    break;
                case -2:
                    if(!(weapon instanceof Machine)){weapon= new Machine(textures);}
                    break;
                case -1:
                    if(!(weapon instanceof NineMM)){weapon= new NineMM(textures);}
                    break;
                case 0:
                    if(weapon!=null){weapon=null;}
                    break;
                case 1:
                    if(!(weapon instanceof NineMM)){weapon= new NineMM(textures);}
                    break;
                case 2:
                    if(!(weapon instanceof Machine)){weapon= new Machine(textures);}
                    break;
                case 3:
                    if(!(weapon instanceof M134)){weapon= new M134(textures);}
                    break;
                case 4:
                    if(!(weapon instanceof Molotov)){weapon= new Molotov();}
                    break;
            }
            if(weapon!=null){weapon.setOwner(this);}
        }

    }
    public void setTeam(byte team){
        this.team=team;
    }
    public void setID(byte id){
        ID = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setImages(BufferedImage actor_hold, BufferedImage actor_stand, BufferedImage actor_left_hand, BufferedImage actor_right_hand){
        this.actor_hold = actor_hold;
        this.actor_stand = actor_stand;
        this.actor_left_hand = actor_left_hand;
        this.actor_right_hand = actor_right_hand;
    }
    public void setAngle(float angle){
        this.angle = angle;
    }
    public void setAngle(float targetX, float targetY) {
        angle = calculateNewAngle(targetX, targetY);
    }
    public void setHitPoints(float hp){
        if(hp < hitPoints){
            addBloodEffect();
            isHit=true;
        }
        hitPoints = hp;
    }
    public void setAgility(int agility){
        this.agility = agility;
    }
    public void setInfected(boolean infected){
        this.isInfected=infected;
        if(isInfected){infectionPoints=100;}
    }
    public void setIsUpdated(boolean updated){
        isUpdated = updated;
    }
    public void setIsInTheFrame(boolean isInTheFrame){
        this.isInTheFrame = isInTheFrame;
    }
    public void setShowName(boolean show){
        showName=show;
    }
    public void setShowID(boolean show){
        showID=show;
    }
    public void setStats(byte kills, byte deaths, byte accuracy){
        this.kills=kills;
        this.deaths=deaths;
        this.accuracy=accuracy;
    }
    public void setTarget(float x, float y){
        targetX=x;
        targetY=y;
    }
    public void setFollowAngle(boolean follow){
        followAngle=follow;
    }
    
    public void goUp(){
        if(!followMouse){
            moveY=true;
            if(Math.abs(speedY) < agility){
                speedY-=speedUpRate;
            }else{
                speedY=-agility;
            }
        }else{
            x-=agility*Math.cos(Math.toRadians(angle+90));
            y-=agility*Math.sin(Math.toRadians(angle+90));
        }
    }
    public void goDown(){
        if(!followMouse){
            moveY=true;
            if(Math.abs(speedY) < agility){
                speedY+=speedUpRate;
            }else{
                speedY=agility;
            }
        }else{
            x+=agility*Math.cos(Math.toRadians(angle+90));
            y+=agility*Math.sin(Math.toRadians(angle+90));            
        }

    }
    public void goLeft(){
        if(!followMouse){
            moveX=true;
            if(speedX > -agility){
                speedX-=speedUpRate;
                if(speedX<-agility){speedX=-agility;}
            }
        }else{
            x-=agility*Math.cos(Math.toRadians(angle));
            y-=agility*Math.sin(Math.toRadians(angle));            
        }

        
    }
    public void goRight(){
        if(!followMouse){
            moveX=true;
            if(speedX < agility){
                speedX+=speedUpRate;
                if(speedX>agility){speedX=agility;}
            }
        }else{
            x+=agility*Math.cos(Math.toRadians(angle));
            y+=agility*Math.sin(Math.toRadians(angle));
        }
    }
    public void goForward(){
        
        newX = (float) (x - agility*Math.cos(Math.toRadians(angle+90)));
        newY = (float) (y - agility*Math.sin(Math.toRadians(angle+90)));
        
        lastShownPresance=0;
    }
    public void goBackward(){
        newX = x;//float
        newY = y;//float
        
        newX+=agility*Math.cos(Math.toRadians(angle+90));
        newY+=agility*Math.sin(Math.toRadians(angle+90)); 

//        handleColision(newX, newY);
        
//        if(x<0){x=0;}
//        if(y<0){y=0;}
//        if(x>(map.getLayer0().getWidth())){x=(map.getLayer0().getWidth());}
//        if(y>(map.getLayer0().getHeight())){y=(map.getLayer0().getHeight());}
        
        lastShownPresance=0;
    }

    public void addWeapon(Weapon newWeapon){
        if(newWeapon==null){return;}
        if(weapons==null){weapons = new ArrayList<>();}
        for(Weapon wep : weapons){
            if(wep.getClass().equals(newWeapon.getClass())){
                wep.addAmmo(newWeapon.getAmmo());
                return;
            }
        }
        newWeapon.setOwner(this);
        weapons.add(newWeapon);
    }
    public void removeWeapon(int index){
        if(index<weapons.size()-1){
            weapons.remove(index);
        }
    }
    public void nextWeapon(){
        if(weapons==null){return;}
        if(weaponIndex<weapons.size()-1){
            weaponIndex++;
        }else{
            weaponIndex=0;
        }
    }
    public void prevWeapon(){
        if(weapons==null){return;}
        if(weaponIndex>0){
            weaponIndex--;
        }else{
            weaponIndex=weapons.size()-1;
        }
    }
    public void reloadWeapon(){
        if(weapons!=null && !weapons.isEmpty() && weapons.get(weaponIndex) instanceof Gun){
            ((Gun)weapons.get(weaponIndex)).reload();
        }else if(weapon!=null){
            ((Gun)weapon).reload();
        }
    }
    public void fire(){
        if(weapons!=null && !weapons.isEmpty()){
            weapons.get(weaponIndex).fire();
        }else if(weapon!=null){
            weapon.fire();
        }
    }
    public void fire(float targetX, float targetY){
        if(weapons!=null && !weapons.isEmpty()){
            if(weapons.get(weaponIndex).getAmmo()>0){
                weapons.get(weaponIndex).fire(targetX, targetY);
//                if(weapons.get(weaponIndex) instanceof Grenade){
//                    if(weapons.get(weaponIndex).getAmmo()==0){
//                        weapons.remove(weaponIndex);
//                        prevWeapon();
//                    }
//                }
            }else{
                nextWeapon();
            }
        }else if(weapon!=null){
            weapon.fire(targetX, targetY);
            if(weapon.getAmmo()==0){
                weapon = null;
            }
        }
    }
    public void gotHit(float damage){
        isHit=true;
        addBloodEffect();
        if(isInvincible){return;}
        hitPoints-=damage;
    }
    protected void addBloodEffect(){
        switch((int)(Math.random()*3)){//
            case 0:
                bloodIterator.add(new BloodSprite1(textures));
//                bloodEffects.add();
                break;
            case 1:
                bloodIterator.add(new BloodSprite2(textures));
//                bloodEffects.add(new BloodSprite2());
                break;
            case 2:
                bloodIterator.add(new BloodSprite3(textures));
//                bloodEffects.add(new BloodSprite3());
                break;
            case 3:
                bloodIterator.add(new BloodSprite4(textures));
//                bloodEffects.add(new BloodSprite4());
                break;
        }
    }
    public void infect(float infectPoints){
        if(isImune){return;}
        this.infectionPoints += infectPoints;
        if(this.infectionPoints>=100){isInfected=true;}
    }
    
    public boolean isAlive(){
        return hitPoints>0;
    }
    public boolean isInfected(){
        return isInfected;
    }
    public boolean isUpdated(){
        return isUpdated;
    }
    public boolean isHit(){
        return (bloodEffects!=null && !bloodEffects.isEmpty());
    }
    public boolean isHitAcurate(){
        boolean returnVal = isHit;
        isHit=false;
        return returnVal;
    }
    public boolean isInTheFrame(){
        return isInTheFrame;
    }
    public boolean isInMyTeam(byte team){
        return this.team==team;
    }

    protected float calculateNewAngle(float targetX, float targetY){
        float angle = (float) Math.toDegrees(Math.atan2(targetY - y, targetX - x)) + 90;

        if(angle < 0){
            angle += 360;
        }
        return angle;
    }
    public void updateActorMechanics(){
        
        if(infectionPoints<100 && infectionPoints>0){infectionPoints-=(float)(Math.random()*0.5);}
        if(weapons!=null){
            if(!weapons.isEmpty()){
                if(weapons.get(weaponIndex) instanceof Gun){
                    ((Gun)weapons.get(weaponIndex)).reloading();
                }                
            }

            for(Weapon weapon : weapons){
                weapon.coolDown();
            }
        }else if(weapon!=null){
            weapon.coolDown();
        }
        
        

        if(!bloodEffects.isEmpty()){
            bloodIterator = bloodEffects.listIterator();
            while ( bloodIterator.hasNext() ) {
                
                Animation blood = bloodIterator.next();
                if(blood.isConsumed()){
                    bloodIterator.remove();
                }else{
                    blood.nextFrame();
                }
            }
        }
        
        if(followAngle){
            
        }else{
        
            //coord
            if(moveY){
                lastShownPresance=0;
            }else{
                if(speedY<0 ){
                    speedY+=speedUpRate;///2
                }
                if(speedY>0 ){
                    speedY-=speedUpRate;///2
                }
                if(Math.abs(speedY)<speedUpRate){
                    speedY = 0;
                }
            }
            //
            if(moveX){
                lastShownPresance=0;
            }else{
                if(speedX<0){
                    speedX+=speedUpRate;///10
                }
                if(speedX>0){
                    speedX-=speedUpRate;///10
                }
                if(Math.abs(speedX)<speedUpRate){
                    speedX = 0;
                }
            }

            moveY=false;
            moveX=false;
            newX = x+speedX;
            newY = y+speedY;
        }
//        handleColision(x+speedX, y+speedY);
        
//        if(x<0){x=0;}
//        if(y<0){y=0;}
//        if(x>(map.getLayer0().getWidth())){x=(map.getLayer0().getWidth());}
//        if(y>(map.getLayer0().getHeight())){y=(map.getLayer0().getHeight());}
        
    }
    
    public void draw(Graphics g, float x, float y){
        if(!isInTheFrame){return;}
        
        if(actor_hold==null || actor_stand==null || actor_left_hand==null || actor_right_hand==null){return;}

        
        Graphics2D gActor = (Graphics2D) g.create();
        gActor.rotate(Math.toRadians(angle), (int)x, (int)y);
        
        Weapon curentWeapon;
        if(weapons==null){
            curentWeapon = weapon;
        }else{
            if(!weapons.isEmpty()){
                curentWeapon = weapons.get(weaponIndex);
            }else{
                curentWeapon=null;
            }
        }
        
        if(isInfected && curentWeapon==null){
            gActor.drawImage(actor_hold, (int)x-actor_hold.getWidth()/2, (int)y-actor_hold.getHeight()/2, null);
        }else{
            if(curentWeapon==null){
                gActor.drawImage(actor_stand, (int)x-actor_stand.getWidth()/2, (int)y-actor_stand.getHeight()/2, null);
            }else{
                if(curentWeapon.isTwoHandedWeapon()){
                    gActor.drawImage(actor_hold, (int)x-actor_hold.getWidth()/2, (int)y-actor_hold.getHeight()/2, null);
                }else{
                    if(curentWeapon.isLeftHandedWeapon()){
                        gActor.drawImage(actor_left_hand, (int)x-actor_left_hand.getWidth()/2, (int)y-actor_left_hand.getHeight()/2, null);
                    }else if(curentWeapon.isRightHandedWeapon()){
                        gActor.drawImage(actor_right_hand, (int)x-actor_right_hand.getWidth()/2, (int)y-actor_right_hand.getHeight()/2, null);
                    }
                }
                curentWeapon.draw(g, x, y);
            }
        }

        for(Animation blood : bloodEffects){
            blood.draw(g, (int)x, (int)y);
        }

//        gActor.fillOval((int)x-5, (int)y-5, 10, 10);
        if(showID){g.drawString(ID+"", (int)x, (int)y);}
        if(showTeam){g.drawString(team+"", (int)x, (int)y);}
        if(showName){g.drawString(name, (int)x, (int)y+25);}

    }
    public void drawVisuals(Graphics g, float x, float y){
        if(!isInTheFrame){return;}
        if(lastShownPresance<=0 && presence.isConsumed()){
            presence=new Presence(textures);
            lastShownPresance=(int) (Math.random()*300);
        }else{
            lastShownPresance--;
            presence.draw(g, x, y);
            presence.nextFrame();
        }
    }
    
}


package Game.Actors;

import Game.Actors.Items.Item;
import Game.Actors.Weapons.Weapon;
import Game.Actors.Weapons.M134;
import Game.Actors.Weapons.NineMM;
import Game.Actors.Weapons.Machine;
import Game.Actors.Weapons.Molotov;
import Game.Actors.Weapons.Gun;
import Game.Effects.BloodSprite3;
import Game.Effects.BloodSprite4;
import Game.Effects.Animation;
import Game.Effects.BloodSprite2;
import Game.Effects.BloodSprite1;
import Game.Effects.Presence;
import Game.Maps.Map;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Actor {
    //statics
//    protected static ArrayList<Actor> actors = new ArrayList<>();
    private static Map map;
    
    public BufferedImage actor_hold;
    public BufferedImage actor_stand;
    public BufferedImage actor_left_hand;
    public BufferedImage actor_right_hand;
    
    private int zombiesKilled;
    private int hitmansKilled;
    private int robotsKilled;
//    private boolean statsChanged;
    
    private String name="anonim";
    private byte team;
    private byte ID;
    protected Weapon weapon;
    protected byte[] weaponsOnClient;
    private boolean reload;
    private boolean isInfected;
    private boolean isUpdated;
    
    private float hitPoints = 100;
    private float infectionPoints;
    
    private boolean followAngle;
    private boolean showName=true;//=true
    private boolean showID=true;//
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
    
    private ArrayList<ItemPickUp> pickedUpItems;
    private ArrayList<Animation> bloodEffects;
    private Animation presence = new Presence();
    private int lastShownPresance;
    
    //AI
    private float targetX;
    private float targetY;
    private int aproxTimeToTravel;
    private float stuckX;
    private float stuckY;
    private float stuckAngle;
    private int stuckTime;
    
    public Actor(byte ID, float x, float y, float angle) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        targetX=x;
        targetY=y;
        newX=x;
        newY=y;
        this.angle = angle;
        bloodEffects = new ArrayList<>();
        pickedUpItems = new ArrayList<>();
//        actors.add(this);
    }

    public static void setMap(Map map){
        Actor.map = map;
    }
    public void setWeaponsByte(byte[] weapons){
        weaponsOnClient = weapons;
    }
    
    
    public Weapon getWeapon(){
        return weapon;
    }
    public byte getWeaponByte(){
        if(weapon instanceof NineMM){
            return (((Gun)weapon).isFired() ? (byte) -1: (byte) 1);
        }else if(weapon instanceof Machine){
            return (((Gun)weapon).isFired() ? (byte) -2: (byte) 2);
        }else if(weapon instanceof M134){
            return (((Gun)weapon).isFired() ? (byte) -3: (byte) 3);
        }else if(weapon instanceof Molotov){
            return 4;//(((Gun)weapon).isFired() ? (byte) -4: (byte) 4);
        }else{
            return 0;
        }
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
        return newX;//-actor_gun.getWidth()/2
    }
    public float getNewY(){
        return newY;//-actor_gun.getHeight()/2
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
//    public int getZombiesKilled(){
//        return zombiesKilled;
//    }
//    public int getHitmansKilled(){
//        return hitmansKilled;
//    }
    public int getKills(){
        return zombiesKilled+hitmansKilled+robotsKilled;
    }
    public float getTargetX(){
        return targetX;
    }
    public float getTargetY(){
        return targetY;
    }
    public ArrayList<ItemPickUp> getPickedUpItems(){
        return pickedUpItems;
    }
    
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }
    public void setWeapon(byte byteWeapon){
        switch(byteWeapon){
            case 0:
                if(weapon!=null){weapon=null;}
                break;
            case 1:
                if(!(weapon instanceof NineMM)){weapon= new NineMM(this);}
                break;
            case 2:
                if(!(weapon instanceof Machine)){weapon= new Machine(this);}
                break;
            case 3:
                if(!(weapon instanceof M134)){weapon= new M134(this);}
                break;
            case 4:
                if(!(weapon instanceof Molotov)){weapon= new Molotov(this);}
                break;
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
    public void setTarget(float x, float y){
        targetX=x;
        targetY=y;
        
//        float dist = (float)(Math.abs(targetX - this.x) + Math.abs(targetY - this.y));
//        aproxTimeToTravel = (int) ((dist/agility)*1.5f);

            stuckX=this.x;
            stuckY=this.y;
            stuckAngle=this.angle;
            stuckTime=0;
        
    }
    public void setFollowAngle(boolean follow){
        followAngle = follow;
    }
    
    public void goUp(){
        if(!followAngle){
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
        if(!followAngle){
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
        if(!followAngle){
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
        if(!followAngle){
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
        newX = (float) (x - agility*Math.cos(Math.toRadians(angle+90)));//float
        newY = (float) (y - agility*Math.sin(Math.toRadians(angle+90)));//float

        lastShownPresance=0;
    }
    public void goBackward(){
        newX = (float) (x + agility*Math.cos(Math.toRadians(angle+90)));//float
        newY = (float) (y + agility*Math.sin(Math.toRadians(angle+90)));//float
        lastShownPresance=0;
    }

    public void fire(float targetX, float targetY){
        
        if(weapon!=null){
//            weaponFired = !weaponFired;
//                      float x = this.x;
//                      float y = this.y;
//                      x -= (float) (75*Math.cos(Math.toRadians(angle+90)));
//                      y -= (float) (75*Math.sin(Math.toRadians(angle+90)));

                weapon.fire(targetX, targetY);
//                if(weapon instanceof Grenade){weapon=null;}
        }
    }
    public void stopFire(){
        if(weapon!=null){
            weapon.stopFire();
        }
    }
    public void gotHit(float damage){
        if(isInvincible){return;}
        hitPoints-=damage;
        
        switch((int)(Math.random()*3)){//
            case 0:
                bloodEffects.add(new BloodSprite1());
                break;
            case 1:
                bloodEffects.add(new BloodSprite2());
                break;
            case 2:
                bloodEffects.add(new BloodSprite3());
                break;
            case 3:
                bloodEffects.add(new BloodSprite4());
                break;
        }
        
    }
    public void infect(float infectPoints){
        if(isImune){return;}
        this.infectionPoints += infectPoints;
        if(this.infectionPoints>=100){isInfected=true;}
    }
    public void incrementKills(Actor actor){
        if(actor instanceof Zombie1){
            zombiesKilled++;
        }else if(actor instanceof Hitman1){
            zombiesKilled++;
        }else if(actor instanceof Robot1){
            robotsKilled++;
        }
                
    }
    public void resetKills(){
        zombiesKilled=0;
        hitmansKilled=0;
    }
    public void pickUpItem(Item item, short arg0){
        pickedUpItems.add(new ItemPickUp(item, arg0));
    }
    
    
    public boolean isAClientWeapon(byte weaponID){
        if(weaponsOnClient!=null){
            for(byte b : weaponsOnClient){
                if(b==weaponID){
                    return true;
                }
            }
        }

        return false;
    }
    public boolean isAlive(){
        return hitPoints>0;
    }
    public boolean isInfected(){
        return isInfected;
    }
    public boolean isInVisualRange(Actor actor){
        if(actor instanceof Hitman1){
            if((Math.random()*100) < ((Hitman1)actor).getInvisibilityPower()){
                return false;
            }
        }
        if(map.rectangles!=null){
            for(Rectangle rect : map.rectangles){
                if(rect.intersectsLine(x, y, actor.getX(), actor.getY())){
                    return false;
                }
            }
        }

        return true;
    }
    public boolean isInVisualRange(float targetX, float targetY){
        if(map.rectangles!=null){
            for(Rectangle rect : map.rectangles){
                if(rect.intersectsLine(x, y, targetX, targetY)){
                    return false;
                }
            }
        }

        return true;
    }
    public boolean isUpdated(){
        return isUpdated;
    }
    public boolean isHit(){
        return (bloodEffects!=null && !bloodEffects.isEmpty());
    }
    public boolean isInMyTeam(byte team){
        return this.team==team;
    }
    public boolean isStuck(){
//        aproxTimeToTravel--;
//        return aproxTimeToTravel<0;
        
        if(this.x!=stuckX || this.y!=stuckY || this.angle!=stuckAngle){
            stuckX=this.x;
            stuckY=this.y;
            stuckAngle=this.angle;
            stuckTime=0;
        }else{
            stuckTime++;
        }
        return stuckTime>5;
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
//        if(weapon!=null){weapon.coolDown();}
        
        if(!bloodEffects.isEmpty()){
            Iterator<Animation> bloodIterator = bloodEffects.iterator();
            while ( bloodIterator.hasNext() ) {
                Animation blood = bloodIterator.next();
                if(blood.isConsumed()){
                    bloodIterator.remove();
                }else{
                    blood.nextFrame();
                }
            }
        }
        if(!pickedUpItems.isEmpty()){
            Iterator<ItemPickUp> pickUpItemsIterator = pickedUpItems.iterator();
            while ( pickUpItemsIterator.hasNext() ) {
                ItemPickUp pickItem = pickUpItemsIterator.next();
                if(pickItem.isConsumed()){
                    pickUpItemsIterator.remove();
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
//        
//        if(x<0){x=0;}
//        if(y<0){y=0;}
//        if(x>(map.getLayer0().getWidth())){x=(map.getLayer0().getWidth());}
//        if(y>(map.getLayer0().getHeight())){y=(map.getLayer0().getHeight());}
        
    }
    
    public void draw(Graphics g, float x, float y){
        
        Graphics2D gActor = (Graphics2D) g.create();
        gActor.rotate(Math.toRadians(angle), (int)x, (int)y);
        
        if(isInfected && weapon==null){
            gActor.drawImage(actor_hold, (int)x-actor_hold.getWidth()/2, (int)y-actor_hold.getHeight()/2, null);
        }else{
            if(weapon!=null){
                if(weapon.isTwoHandedWeapon()){
                    gActor.drawImage(actor_hold, (int)x-actor_hold.getWidth()/2, (int)y-actor_hold.getHeight()/2, null);
                }else{
                    if(weapon.isLeftHandedWeapon()){
                        gActor.drawImage(actor_left_hand, (int)x-actor_left_hand.getWidth()/2, (int)y-actor_left_hand.getHeight()/2, null);
                    }else if(weapon.isRightHandedWeapon()){
                        gActor.drawImage(actor_right_hand, (int)x-actor_right_hand.getWidth()/2, (int)y-actor_right_hand.getHeight()/2, null);
                    }
                }
                weapon.draw(g, x, y);
            }else{
                gActor.drawImage(actor_stand, (int)x-actor_stand.getWidth()/2, (int)y-actor_stand.getHeight()/2, null);
            }
        }

        if(!bloodEffects.isEmpty()){
            Iterator<Animation> bloodIterator = bloodEffects.iterator();
            while ( bloodIterator.hasNext() ) {
                Animation blood = bloodIterator.next();
                if(blood.isConsumed()){
                    bloodIterator.remove();
                }else{
                    blood.draw(g, (int)x, (int)y);
                    
                }
            }
        }
//        gActor.fillOval((int)x-5, (int)y-5, 10, 10);
//        g.drawString(ID+"", (int)x, (int)y);
        if(showID){g.drawString(ID+"", (int)x, (int)y);}
        if(showTeam){g.drawString(team+"", (int)x, (int)y);}
        if(showName){g.drawString(name, (int)x, (int)y+25);}
        
//        gActor.setColor(Color.red);
//        gActor.drawString(hitPoints+"", (int)x, (int)y+25);
//        gActor.drawLine((int)x, (int)y, (int)tmpX, (int)tmpY);
        
    }
    public void drawVisuals(Graphics g, float x, float y){
        
        if(lastShownPresance<=0 && presence.isConsumed()){
            presence=new Presence();
            lastShownPresance=(int) (Math.random()*300);
        }else{
            lastShownPresance--;
            presence.draw(g, x, y);
            presence.nextFrame();
        }
    }
    
    public class ItemPickUp {
        
        Item item;
        short arg0;

        boolean isConsumed;
        boolean isSend;
        
        public ItemPickUp(Item item, short arg0) {
            this.item = item;
            this.arg0 = arg0;
        }
        
        public Item getItem(){
            return item;
        }
        public float getArg0(){
            return arg0;
        }
        
        public boolean isConsumed(){
            return isConsumed;
        }
        public void consume(){
            isConsumed=true;
        }
        public void markAsSend(){
            isSend=true;
        }
    }
}

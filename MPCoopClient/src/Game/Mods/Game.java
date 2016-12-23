package Game.Mods;

import Game.Pointer.InGamePointer;
import Game.Actors.Zombie1;
import Game.Actors.Actor;
import Game.Actors.Hitman1;
import Game.Actors.Items.Item;
import Game.Actors.Robot1;
import Game.Actors.Weapons.Grenade;
import Game.Actors.Weapons.Gun;
import Game.Actors.Weapons.Weapon;
import Game.Animations.Animation;
import Game.Camera;
import Game.Maps.*;
import Game.Maps.Environment.Environment;
import Game.UI.InGameUI.InGameUI;
import Input.*;
import Main.Main;
import Main.ResourceMonitor;
import Main.SettingsManager;
import Main.Textures.LoadTextures;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Main.Textures.Initilializer;
import java.awt.Graphics2D;

public abstract class Game extends JPanel implements Initilializer{
    public enum ActorClasses {
        Zombie, Hitman, Robot
    }
    public enum Weapons{
        NineMM, Machine, M134, Molotov, Default
    }
    
//    protected static ArrayList<Actor> actors = Actor.getActors();
//    protected static ArrayList<Grenade> grenades = Grenade.getGrenades();
    
    protected ArrayList<Actor> actors;
    protected ArrayList<Item> items;
    protected ArrayList<Grenade> grenades;
    
    
    protected int nrMaxOfZombies=20;
    protected int nrMaxOfHitmans=5;
    protected int nrOfZombies=0;
    protected int nrOfHitmans=0;

    protected Mouse mouse;
    protected Keyboard key;
    
    protected boolean goUp;
    protected boolean goDown;
    protected boolean goLeft;
    protected boolean goRight;  
    
    private Map map;
    private Actor me;
    protected Camera camera;
    protected InGamePointer cursor;
    protected SettingsManager settings;
    protected LoadTextures textures;
    public ResourceMonitor resourceMonitor;
    private boolean dispose;
    protected boolean gameOver;
    protected boolean isReady;
    protected boolean isTextureReady;
    protected byte mapID=-1;
    
    protected int messageStatusDisplayTime=150;
    protected int messageStatusDisplay;
    protected String chatStatusMessage="";
    protected JTextField chatInput;
    protected JTextArea chatArea;
    protected JScrollPane jsp;
    protected byte chatVisibility;

    protected ArrayList<Animation> bloodEffects;
    private Color hitColor;
    
    public Game() {
        System.out.println(this.getClass());
    }
    
    public void add(Actor actor){
//        actor.init();
        actors.add(actor);
    }
    public void remove(Actor actor){
        actors.remove(actor);
    }
    public Iterator<Actor> getActorsIterator(){
        return actors.iterator();
    }
    public ArrayList<Actor> getActors(){
        return actors;
    }
    public int getNrOfActors(){
        return actors.size();
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
                    availableID = 30;
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
    public ArrayList<Environment> getEnvironment(){
        return map.getEnvironment();
    }
    
    public ArrayList<Item> getItems(){
        return items;
    }
    public ArrayList<Grenade> getGrenades(){
        return grenades;
    }
    
    public void init(){
        
        hitColor = new Color(255, 0, 0, 50);
        
        mouse = new Mouse();
        key = new Keyboard();
        
        this.addMouseMotionListener(mouse);//(MouseMotionListener)
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);
        this.addKeyListener(key);
        
        
        setLocation(0, 0);
        setSize(Main.getWidth(), Main.getHeight());
        setVisible(true);
        
        cursor = new InGamePointer(this, textures);
        camera = new Camera();
        
        bloodEffects = new ArrayList<>();
        actors = new ArrayList<>();
        items = new ArrayList<>();
        grenades = new ArrayList<>();
        
//        Load Map
        switch(mapID){
            case 0:
                setMap(new Prologue(textures));
                System.out.println("Map loaded : Prologue");
                break;
            case 1:
                setMap(new UrbanDistrict9(textures));
                System.out.println("Map loaded : UrbanDistrict9");
                break;
            case 2:
                setMap(new cr8(textures));
                System.out.println("Map loaded : cr8");
                break;
            case 3:
                setMap(new In4(textures));
                System.out.println("Map loaded : cr8");
                break;
            default:
                //No map ?
                System.out.println("Uknown map Error " + mapID);
                break;
        }
        
    }
    
    public void selectMe(Game.ActorClasses actorClass, String name){}

    
    public float getCameraX(){
        return camera.getX();
    }
    public float getCameraY(){
        return camera.getY();
    }
    public Mouse getMouse(){
        return mouse;
    }
    public Keyboard getKeyboard(){
        return key;
    }
    public float getMouseX(){
        return mouse.mouseX;
    }
    public float getMouseY(){
        return mouse.mouseY;
    }
    public boolean getMouseButton1(){
        return key.esc ? false : mouse.b1Down;
    }
    public boolean getMouseButton2(){
        return key.esc ? false : mouse.b2Down;
    }
    public Map getMap(){
        return map;
    }
    public byte getMapID(){
        return mapID;
    }
    public Camera getCamera(){
        return camera;
    }
    public InGamePointer getMousePointer(){
        return cursor;
    }
    public Actor getMe(){
        return me;
    }
    public LoadTextures getTextures(){
        return textures;
    }
    public SettingsManager getSettings(){
        return settings;
    }
    
    public void setControls(int goUpKey, int goDownKey, int goLeftKey, int goRightKey, int reloadKey, int statsKey){
        key.setKeys(goUpKey, goDownKey, goLeftKey, goRightKey, reloadKey, statsKey);
    }
    public void setMe(Actor me){
        if(this.me!=me){
            this.me = me;
            cursor.setMe(me);
            if(me!=null){
                cursor.setX(me.getX());
                cursor.setY(me.getY());                
            }
        }
    }
    public void setMap(Map map){
        this.map = map;
        this.map.init();
    }
    public void setMapID(byte id){
        mapID=id;
    }
    @Override
    public void setTexture(LoadTextures textures){
        System.out.println("Texture set");
        this.textures=textures;
        isTextureReady=true;
    }
    public void setSettings(SettingsManager settingsManager){
        settings = settingsManager;
    }
    
    public void chatRoomAppend(String message){
        if(chatArea==null){return;}
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        
        
        
    }
    public void setMessageStatus(String message){
        if("Received...".equals(message)){messageStatusDisplay=messageStatusDisplayTime;}
        chatStatusMessage = message;
    }
    
    protected void lockOnTarget(){
        if(me==null || !me.autoAim){return;}
        if(mouse.b2Down){
            if(!cursor.isLocked()){
                synchronized(actors){
                    for(Actor actor : actors){
                        if(actor!=me && map.isInVisualRange(me, actor) && (float)(Math.abs(actor.getX() - cursor.getX()) + Math.abs(actor.getY() - cursor.getY()))<150){
                            cursor.setLock(true);
                            cursor.setLockOnActor(actor);
                        }
                    }
                }
            }
        }
    }
    public void cameraShake(int magnitude){
        camera.shake(magnitude);
    }
    public void dispose(){
        this.dispose = true;
//        actors.clear();
//        grenades.clear();
//        Item.getItems().clear();
    }
    public void redeploy(){
        if(me!=null){
            me.setHitPoints(0);
        }
    }

    
    public boolean isDisposed(){
        return dispose;
    }
    public boolean isReady(){
        return isReady;
    }
    public boolean isTextureReady(){
        
        return isTextureReady;
    }
    public boolean isGameOver(){
        return gameOver;
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

//        if(actor.getX() != actor.getNewX()){
//            System.out.println(actor);
//        }
        
        if(!colisionOnX){actor.setX(actor.getNewX());}
        if(!colisionOnY){actor.setY(actor.getNewY());}
    }
    protected void bulletColision(Actor owner){
        Weapon weapon = owner.getWeapon();
        if(weapon==null || !weapon.isFired()){return;}
        
        Actor closestActor = null;
        float dist = 0;

        synchronized(actors){
            for (Actor actor : actors) {
                if(actor!=owner && map.isInVisualRange(owner, actor) && shortestDistance(owner.getX(),owner.getY(),weapon.getTargetX(), weapon.getTargetY(),actor.getX(),actor.getY())<15){
                    if(closestActor==null){
                        dist = (float)(Math.abs(actor.getX() - owner.getX()) + Math.abs(actor.getY() - owner.getY()));
                        closestActor = actor;
                    }else{
                        float newDist = (float)(Math.abs(actor.getX() - owner.getX()) + Math.abs(actor.getY() - owner.getY()));
                        if(newDist<dist){
                            dist = newDist;
                            closestActor = actor;
                        }
                    }
                }
            }
            if(closestActor!=null){
                closestActor.gotHit(weapon.getFirePower());
            }
        }
        weapon.setIsFired(false);
        
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

    
    public void getUserInput(){
//        if(key.W){
//            getMe().goUp();
//        }
//        if(key.S){
//            getMe().goDown();
//        }
//        if(key.D){
//            getMe().goRight();
//        }
//        if(key.A){
//            getMe().goLeft();
//        }
    }
    public void updateGameMechanics(){
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
        
        Iterator<Item> itemIterator = items.iterator();
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
        
        if(map.getEnvironment()!=null){
            Iterator<Environment> envIterator = map.getEnvironment().iterator();
            while(envIterator.hasNext()){
                Environment env = envIterator.next();
                env.updateMechanics();
            }  
        }

        cursor.robot();
    }
    public void renderGame(){
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setSize(Main.getWidth(), Main.getHeight());
        Graphics2D rimeUI = (Graphics2D) g.create();
        
        map.drawLayer1(g, camera.getX(), camera.getY());
        map.drawEnvironment(g, camera.getX(), camera.getY());
        
        synchronized(items){
            for(Item item : items){
                if(me!=null){
                    if(map.isInVisualRange(me, item.getX(), item.getY())){
                        item.draw(g, item.getX()-camera.getX(), item.getY()-camera.getY());
                    }                
                }else{
                    item.draw(g, item.getX()-camera.getX(), item.getY()-camera.getY());
                }
            }
        }
        
        nrOfZombies=0;
        nrOfHitmans=0;
        synchronized(actors){
            Iterator<Actor> actorIterator = actors.iterator();
            while(actorIterator.hasNext()){
                Actor actor = actorIterator.next();
                
                if(actor instanceof Zombie1){
                    nrOfZombies++;
                }else if(actor instanceof Hitman1){
                    nrOfHitmans++;
                }                    

                
                if(me!=null){// && actor!=me
                    if(map.isInVisualRange(me, actor)){
                        actor.draw(g, actor.getX()-camera.getX(), actor.getY()-camera.getY());
                        if(actor.isHit()){
                            rimeUI.setColor(Color.darkGray);
                            rimeUI.drawString("HP ", (int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-30));
                            rimeUI.setColor(Color.red);
                            rimeUI.fillRect((int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-25), 100/2, 5);
                            rimeUI.setColor(Color.green);
                            rimeUI.fillRect((int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-25), (int)actor.getHitPoints()/2, 5);
                        }                        
                    }else{
                        actor.drawVisuals(g, actor.getX()-camera.getX(), actor.getY()-camera.getY());
                    }
                }else{
                    actor.draw(g, actor.getX()-camera.getX(), actor.getY()-camera.getY());
                }
//                actor.setIsUpdated(false);
            }
        }

        map.drawLayer2(g, camera.getX(), camera.getY());

        //UI
        ////Cursor
        if(cursor!=null && me!=null && !key.esc){//
            cursor.draw(g, cursor.getX()-camera.getX(), cursor.getY()-camera.getY());
        }
        if(me!=null){
//            if(me.getHitPoints()<60){
//                int hitFlash=0;
//                if(me.isHit()){
//                    hitFlash=(int)(Math.random()*60);
//                }
//                Composite originalComposite = rimeUI.getComposite();
//                rimeUI.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)((hitFlash+60-me.getHitPoints())/300)));
//                rimeUI.drawImage(textures.deployBackground, getWidth()/2 - textures.deployBackground.getWidth()/2, getHeight()/2 - textures.deployBackground.getHeight()/2, null);
//                rimeUI.setComposite(originalComposite);
//            }
//            g.setColor(new Color( 250, 0, 0, (int)(100-me.getHitPoints())));//
//            g.fillRect(0, 0, getWidth(), getHeight());
//            g.setColor(Color.BLACK);              
            


//            if(me.isHitAcurate()){

//                Animation bloodsprite;
//                if(Math.random()*10>5){
//                    bloodsprite = new BloodSprite6();
//                    bloodsprite.setFrameIndex((int) (Math.random()*4));
//                }else{
//                    bloodsprite = new BloodSprite5();
//                }
//                bloodsprite.setOpacity(0.5f);
//                bloodsprite.setX((float) (Math.random()*getWidth()));
//                bloodsprite.setY((float) (Math.random()*getHeight()));
//                
//                bloodEffects.add(bloodsprite);
//            }

            if((me.getWeapon() instanceof Gun)){
                if(((Gun)me.getWeapon()).isReloading()){
                        byte spacing=0;
                        if(me.isHit()){
                            spacing=20;
                        }
                        rimeUI.setColor(Color.darkGray);
                        rimeUI.drawString("Reloading ... ", (int)(me.getX()-camera.getX()), (int)(me.getY()-camera.getY()-30-spacing));
                        rimeUI.setColor(Color.blue);
                        rimeUI.fillRect((int)(me.getX()-camera.getX()), (int)(me.getY()-camera.getY()-25-spacing), 100/2, 5);
                        rimeUI.setColor(Color.green);
                        rimeUI.fillRect((int)(me.getX()-camera.getX()), (int)(me.getY()-camera.getY()-25-spacing), (100*((Gun)me.getWeapon()).getClip()/((Gun)me.getWeapon()).getClipSize()) /2, 5);
                }
            }
            
            
//            g.drawString("" +me, 350, 50);
//            g.drawString("MeX: "+ me.getX(), 350, 70);
//            g.drawString("MeY: " +me.getY(), 350, 90);
//
//            g.drawString("HP: " + me.getHitPoints(), 350, 120);
//            g.drawString("IP: " + me.getInfectionPoints(), 350, 140);

//            if(me.getWeapon()!=null){
////                g.drawString("Weapon : " + me.getWeapon().getClass(), 350, 170);
//                g.drawString("ammo : " + me.getWeapon().getAmmo(), 350, 190);
//                if(me.getWeapon() instanceof Gun){
//                    g.drawString("clip : " + ((Gun)me.getWeapon()).getClip(), 350, 210);
//                    if(((Gun)me.getWeapon()).isReloading()){
//                        g.drawString("Reloading ...", 350, 230);
//                    }
//                }
//            }
        }

//        g.drawString("Zombie : " + nrOfZombies, 600, 50);
//        g.drawString("Hitman : " + nrOfHitmans, 600, 70);
//
//
//
//        g.drawString("CameraX: " +camera.getX(), 900, 50);
//        g.drawString("CameraY: " +camera.getY(), 900, 70);
//        if(cursor!=null && me!=null && !key.esc){// 
//            g.drawString("CursorX: " +cursor.getX(), 900, 90);
//            g.drawString("CursorY: " +cursor.getY(), 900, 110);
//        }
//        mouse.b1Released=false;


//        if(!bloodEffects.isEmpty()){
//            Iterator<Animation> bloodIterator = bloodEffects.listIterator();
//            while ( bloodIterator.hasNext() ) {
//                Animation blood = bloodIterator.next();
//                
//                if(blood instanceof BloodSprite5){
//                    if(blood.getOpacity()<0.05f){
//                        bloodIterator.remove();
//                    }else{
//                        blood.setOpacity(blood.getOpacity()-0.05f);
//                        blood.draw(g, blood.getX(), blood.getY());
//                        if(blood.hasNextFrame()){
//                            blood.nextFrame();
//                        }
//
//                    }
//                }
//                
//                if(blood instanceof BloodSprite6){
//                    if(blood.getOpacity()<0.05f){
//                        bloodIterator.remove();
//                    }else{
//                        blood.setOpacity(blood.getOpacity()-0.05f);
//                        blood.draw(g, blood.getX(), blood.getY());
//                    }
//                }
//            }
//        }

    }
    
    public void gameLoop(){
        if(!key.esc){
            getUserInput();
            updateGameMechanics();
        }
        renderGame();
    }
    
}

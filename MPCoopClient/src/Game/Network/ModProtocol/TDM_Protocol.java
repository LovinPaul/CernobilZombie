package Game.Network.ModProtocol;

import Game.Network.Connection.Connection;
import Game.Actors.*;
import Game.Actors.Items.*;
import Game.Actors.Weapons.*;
import Game.Mods.*;
import Game.Network.ModProtocol.Stats.ActorStats;
import Game.Network.ModProtocol.Stats.ActorStats.Stats;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TDM_Protocol extends Protocol{// extends Connection
    
    private ActorStats actorStats;
    
    public TDM_Protocol(Connection conn) {
        super(conn);
        actorStats = new ActorStats();
        game = new MPTDM(this);
        game.setMapID(conn.getGameMap());
    }
    
    public Iterator<Stats> getActorStats(){
        return actorStats.getStats();
    }
    
    @Override
    public void readTCP() {
        byte type = conn.readParseTCP();
        
        switch(type){
            case 2:
                id = conn.readTCP();
                team = conn.readTCP();
//                if("anonim".equals(name)){name="Player"+id;}
                isKilled=false;

                System.out.println("ID assigned : " + id);
                System.out.println("Team assigned : " + team);
                break;
            case 6:
                byte statsLenght=conn.readTCP();
                for(int i=0; i<statsLenght;i++){
                    byte connID = conn.readTCP();
                    byte actrID = conn.readTCP();
                    byte actrKills = conn.readTCP();
                    byte actrDeaths = conn.readTCP();
                    byte actrAccuracy = conn.readTCP();
                    actorStats.updateStats(connID, actrID, actrKills, actrDeaths, actrAccuracy);
                }
                actorStats.finalUpdateStats(game.getActors());
                break;
            default:
                super.parseTCP(type);
                break;
        }
        
    }

    public void writeRedeployTCP(){
        conn.writeTCP((byte)7);
        conn.flushTCP();
    }
    @Override
    public void readUDP(){
        byte[] data = conn.readUDP();
        if(data!=null){
            
            int tempTimeMillis = data[0] << 24 | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
            if(tempTimeMillis==0){
                conn.writeTCP((byte)9);
                conn.flushTCP();
                return;
            }
            
            Rectangle frame = new Rectangle((int)game.getCameraX(), (int)game.getCameraY(), game.getWidth(), game.getHeight());
            
            Iterator<Actor> actors = game.getActorsIterator();
//            ArrayList<Actor> actors = game.getActors();
            ArrayList<Item> items = game.getItems();
            ArrayList<Grenade> grenades = game.getGrenades();
//            ArrayList<Environment> environment = game.getEnvironment();
            
            
            while(actors.hasNext()){
                Actor actor = actors.next();
                actor.setIsUpdated(false);
            }
            game.getMap().setEnvironmentIsUpdated(false);

            boolean isNewActor;
            int i=4;
            
            grenades.clear();
            items.clear();
            
            do{
                
                //<editor-fold defaultstate="collapsed" desc="New method">
                switch(data[i++]){
                    case 0: //Actors
                        isNewActor=true;
                        byte actorID = data[i++];
                        byte isVisible = data[i++];
                        byte actorTeam = data[i++];
                        
                        int serverActorX = 0;
                        int serverActorY = 0;
                        int serverActorAngle = 0;
                        byte serverActorWeapon = 0;
                        byte serverActorHP = 0;
                        
                        if(isVisible==1){
                            serverActorX = (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
                            serverActorY = (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
                            serverActorAngle = (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
                            serverActorWeapon = data[i++];
                            serverActorHP = data[i++];
                        }
                        
                        
                        //<editor-fold defaultstate="collapsed" desc="Update actor">

                        actors = game.getActorsIterator();
                        while(actors.hasNext()){
                            Actor actor = actors.next();
                            if(!actor.isUpdated() && actor.getID()==actorID){
//                                System.out.println("actor Updated : " + data[i]);
                                isNewActor=false;
                                actor.setIsUpdated(true);
                                actor.setTeam(actorTeam);
                                
                                if(isVisible==0){
                                    actor.setIsInTheFrame(false);
                                }else{
                                    
                                    if(actor.isInTheFrame()){
                                        if(actor.getX()!=serverActorX){actor.setX((actor.getX()+serverActorX)/2);}
                                        if(actor.getY()!=serverActorY){actor.setY((actor.getY()+serverActorY)/2);}
                                    }else{
                                        actor.setX(serverActorX);
                                        actor.setY(serverActorY);
                                        actor.setIsInTheFrame(true);
                                    }
                                    
                                    if(actor.getAngle()!=serverActorAngle){actor.setAngle((actor.getAngle()+serverActorAngle)/2);}
                                    //weapon
                                    actor.setWeapon(serverActorWeapon);
                                    if(serverActorWeapon<0){
                                        actor.fire();
                                        if(actor.getID()==id){
                                            game.cameraShake(5);
                                        }
                                    }
                                    //HP
                                    actor.setHitPoints(serverActorHP);
                                }
                                break;
                            }
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="Add new actor">
                        //add new actor
                        if(isNewActor){
//                            System.out.println("new actor added : " + data[i]);
                            Actor actor;
                            actor = makeActor(actorID);
                            actor.setTeam(actorTeam);

                            if(isVisible==1){
                                actor.setX(serverActorX);
                                actor.setY(serverActorY);
                                actor.setIsInTheFrame(true);
                                actor.setAngle(serverActorAngle);
                                actor.setWeapon(serverActorWeapon);
                                if(serverActorWeapon<0){actor.fire();}
                                actor.setHitPoints(serverActorHP);
                            }
                            //                            actor.init(game.getTextures());
                            actor.setIsUpdated(true);
                            game.add(actor);

                            if(actorID==id){
                                actor.setName(settings.name);
                            }else{
                                conn.writeTCP(new byte[]{5, actorID});
                            }
                        }
                        //</editor-fold>
                        break;
                    case 1: //Grenades, Missiles
                        switch(data[i++]){
                            case 0:
                                byte timeUntilExplosion = data[i++];
                                i+=4;

    //                            Molotov molotov = new Molotov(timeUntilExplosion, (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF)), (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF)));
    //                            molotov.calibrateAnimation(timeUntilExplosion);
    //                            if(timeUntilExplosion<=0){game.cameraShake(Molotov.shake());}
    //                            grenades.add(molotov);
                                break;
                        }

                        break;
                    case 2: //Items
                        byte itemType = data[i++];
                        int serverItemX = (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
                        int serverItemY = (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
                        
                        switch(itemType){
                            case 0:
                                HP_Crate hpCrateItem = new HP_Crate(serverItemX, serverItemY);
                                hpCrateItem.init(game.getTextures());
                                items.add(hpCrateItem);
                                break;
                            case 1:
                                Machine_Clip machineClipItem = new Machine_Clip(serverItemX, serverItemY);
                                machineClipItem.init(game.getTextures());
                                items.add(machineClipItem);
                                break;
                            case 2:
                                NineMM_Clip nineMMClipItem = new NineMM_Clip(serverItemX, serverItemY);
                                nineMMClipItem.init(game.getTextures());
                                items.add(nineMMClipItem);
                                break;
                            default:
                                System.out.println("Invalid Item " + itemType);
                                System.out.println("ItemX ==  " + serverItemX + ", ItemY == " + serverItemY);
                                break;
                        }
                        break;
                    case 3: // Environment
                        byte envID = data[i++];
                        byte envState = data[i++];
                        game.getMap().updateEnvironment(frame, envID, toUnsignByte(envState));
                        break;

                    case 99: //picked up items
                        byte itemPickUpType = data[i++];
                        int pickUpArg0 = (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
                        
                        switch(itemPickUpType){
                            case 1:
                                if(game.getMe()!=null){
                                    Weapon addAmmoWeapon = new Machine(null);
                                    addAmmoWeapon.setAmmo(pickUpArg0);
                                    game.getMe().addWeapon(addAmmoWeapon);
                                }
                                break;
                            case 2:
                                if(game.getMe()!=null){
                                    Weapon addAmmoWeapon = new NineMM(null);
                                    addAmmoWeapon.setAmmo(pickUpArg0);
                                    game.getMe().addWeapon(addAmmoWeapon);
                                }
                                break;
                            default:
                                System.out.println("Invalid PickUpItem " + itemPickUpType);
                                System.out.println("arg0 ==  " + pickUpArg0);
                                break;
                        }
                        break;

                }
                //</editor-fold>

            }while(i<conn.getBytesInUDP());
            
            conn.flushTCP();
            
            //<editor-fold defaultstate="collapsed" desc="RemoveActors">
            //Remove actors that are not pressent in data
            actors = game.getActorsIterator();
            while(actors.hasNext()){
                Actor actor = actors.next();

                if(actor.isUpdated()){
                    if(game.getMe()==null && actor.getID()==id){
                        game.setMe(actor);
                    }
                }else{
                    //                System.out.println("Actor removed : " + actor.getID());
                    if(actor.getID()==id){
                        isKilled=true;
                        id=-1;
                        game.setMe(null);
                    }
                    actors.remove();
                }
            }
            //</editor-fold>
        }
    }
    
    @Override
    public void gameLoop(){
        readTCP();
        readUDP();
        
        if(!game.getKeyboard().esc){
            game.getUserInput();
        }
        game.updateGameMechanics();
        game.renderGame();
        
        writeActorUDP(game.getMe());
        writeStatusTCP();
    }
    
}

package Game.Network;

import Game.Actors.*;
import Game.Actors.Items.*;
import Game.Actors.Weapons.*;
import Game.*;
import Game.Maps.Environment.Environment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class TDM_Connection  extends Connection{
    
    
    public static void listenForNewConnections(){
        Thread listen = new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    TDM_Connection newConnection = new TDM_Connection();
                    if(newConnection.newConnection()){
                        newConnection.connID = Connection.assignConnectionID();
                        newConnection.writeInitTCP();
                        statsChanged=true;
                        connections.add(newConnection);
                        System.out.println("Nr. Of Connections : " + connections.size());
                        System.out.println("New Connection ID : " + newConnection.connID);
//                        newConnection.udp.setTMPSTR("Writting to con#" + connections.size() + " connID:"+newConnection.connID);
                    }
                }
            }
        });
        listen.start();
    }

    public static void readAllTCP(){
        for(Connection conn : connections){
            ((TDM_Connection)conn).readTCP();
        }
    }

    public void readTCP() {
        byte type = tcp.read();
//        System.out.println(type);
        switch(type){
            case 1:
                //Add new actor
                byte byteActor=tcp.read();
                byte nameLenght=tcp.read();
                
                //Spawn
                Point spawn;
                if(game.getMap().getSpawnPoints()!=null){
                    spawn = game.getMap().getSpawnPoints().get((int)(Math.random()*(game.getMap().getSpawnPoints().size()-1)));
                }else{
                    spawn = new Point((int) (Math.random()*game.getMap().getLayer0().getWidth()), 
                                    (int) (Math.random()*game.getMap().getLayer0().getHeight()));
                }
                
                //Actor
                switch(byteActor){
                    case 0:
                        //zombie
                        id = game.assignID(Game.ActorClasses.Zombie);
                        me = new Zombie1(id, spawn.x, spawn.y, 0);
                        break;
                    case 1:
                        //hitman
                        id = game.assignID(Game.ActorClasses.Hitman);
                        me = new Hitman1(id, spawn.x, spawn.y, 0);
                        break;
                    case 2:
                        //hitman
                        id = game.assignID(Game.ActorClasses.Robot);
                        me = new Robot1(id, spawn.x, spawn.y, 0);
                        break;
                    default:
                        System.out.println("[" + connID + "]Invalid byteActor " +byteActor);
                        break;
                }
                
                //Name
                if(nameLenght==0){
                    if("anonim".equals(name)){
                        name = "Player" + id;
                    }
                }else{
                    byte[] message = new byte[nameLenght];
                    for(int i=0; i<nameLenght;i++){
                        message[i] = tcp.read();
                    }
                    name = new String(message);
                    me.setName(name);
                }

                //Team
                if(team==-1){
                    ((MPTDM)game).addToAvailableTeam(this);
                }
                
                me.setTeam(team);
                game.add(me);
//                byte[] newByte = {2, id,team};
                tcp.write(new byte[]{2, id,team});
                tcp.flush();
                statsChanged=true;
                break;
                
            default:
                super.parseTCP(type);
                break;
        }
    }
    @Override
    public void writeStatsTCP(){
        byte[] stats = new byte[(connections.size()*5)+2];
        stats[0]=6;
        stats[1]=(byte) (connections.size());
        int i=2;
        for(Connection conn : connections){
            stats[i++]=(byte)conn.connID;
            stats[i++]=(byte)conn.id;
            stats[i++]=(byte)conn.kills;
            stats[i++]=(byte)conn.deaths;
            stats[i++]=(byte)conn.accuracy;
        }
        tcp.write(stats);
        tcp.flush();
    }
    @Override
    public byte[] gatherUpdates(){
        if(me!=null){
            if(me.isAlive()){
                if(curentKills<me.getKills()){
                    curentKills++;
                    kills++;
                    if(curentKills==me.getKills()){
                        statsChanged=true;
                    }
                }
            }else{
                deaths++;
                curentKills=0;
                id=-2;
//                if(game instanceof MPCoop){chatRoom.add("\t* " + name + " is killed *");}
                me=null;
                statsChanged=true;
            }

        }

//        ArrayList<Actor> actors = game.getActors();
        ArrayList<Grenade> grenades = Grenade.getGrenades();
        ArrayList<Item> items = Item.items;

        byte[][] sendData2D;

        if(me!=null){
            sendData2D = new byte[game.getNrOfActors() + me.getPickedUpItems().size() + grenades.size() + items.size() + game.getMap().getEnvironmentSize()][];
        }else{
            sendData2D = new byte[game.getNrOfActors() + grenades.size() + items.size() + game.getMap().getEnvironmentSize()][];
        }


        Rectangle frame = new Rectangle(cameraX, cameraY, cameraWidth, cameraHeight);

        int i=0;
        int a=0;
        Iterator<Actor> actors = game.getActorsIterator();
        while(actors.hasNext()){
            Actor actor = actors.next();
            if((actor==me) || frame.contains(actor.getX(), actor.getY())){
                sendData2D[i] = new byte[]{
                                0,
                                actor.getID(),
                                1,
                                actor.getTeam(),
                                (byte)((short)actor.getX() >>> 8),
                                (byte)((short)actor.getX()),
                                (byte)((short)actor.getY() >>> 8),
                                (byte)((short)actor.getY()),
                                (byte)((short)actor.getAngle() >>> 8),
                                (byte)((short)actor.getAngle()),
                                actor.getWeaponByte(),
                                (byte) actor.getHitPoints()
                };
                a+=12;
            }else{
                sendData2D[i] = new byte[]{0, actor.getID(), 0, actor.getTeam()};
                a+=4;
            }
            i++;
        }

        int b=0;
        for(Grenade grenade : grenades){
            if(frame.contains(grenade.getX(), grenade.getY())){
                sendData2D[i] = new byte[]{
                                1,
                                (byte)grenade.getType(),
                                (byte)grenade.getTimeUntilExplosion(),
                                (byte)((short)grenade.getX() >>> 8),
                                (byte)((short)grenade.getX()),
                                (byte)((short)grenade.getY() >>> 8),
                                (byte)((short)grenade.getY())
                };
                b+=7;
            }else{
                sendData2D[i] = null;
//                sendData2D[i] = new byte[]{(byte) -1};
//                    b+=1;
            }
            i++;
        }
        int c=0;
        for(Item item : items){
            if(frame.contains(item.getX(), item.getY())){
                sendData2D[i] = new byte[]{
                                2,
                                (byte)item.getType(),
                                (byte)((short)item.getX() >>> 8),
                                (byte)((short)item.getX()),
                                (byte)((short)item.getY() >>> 8),
                                (byte)((short)item.getY())
                };
                c+=6;
            }else{
                sendData2D[i] = null;
//                sendData2D[i] = new byte[]{(byte) -1};
//                    c+=1;
            }
            i++;
        }

        int e=0;
        Iterator<Environment> environmentIterator = game.getMap().getEnvironmentIterator();
        while(environmentIterator.hasNext()){
            Environment env = environmentIterator.next();
//                
            if(frame.intersects(env.getBounds())){
                int envState= env.getState();

                sendData2D[i] = new byte[]{
                                (byte) 3,
                                (byte)(env.getID()),
                                (byte) envState,
                };
                e+=3;
            }else{
//                sendData2D[i] = new byte[]{(byte) -1};
                sendData2D[i] = null;
            }
            i++;
        }


        //PickUpsZ
        int d=0;
        if(me!=null){
            for(Actor.ItemPickUp pickUpItem : me.getPickedUpItems()){
                sendData2D[i] = new byte[]{
                                (byte) 99,
                                (byte)(pickUpItem.getItem().getType()),
                                (byte)((short)pickUpItem.getArg0() >>> 8),
                                (byte)((short)pickUpItem.getArg0())
                };
                d+=4;
                i++;
                pickUpItem.consume();
            }
        }



        byte[] sendData = new byte[4 + a+b+c+d+e];
        int timeMilis = (int)(System.currentTimeMillis()%1000000000);// 4 bytes
        sendData[0] = (byte)(timeMilis >>> 24);
        sendData[1] = (byte)(timeMilis >>> 16);
        sendData[2] = (byte)(timeMilis >>> 8);
        sendData[3] = (byte)(timeMilis);

        i=4;
        for(byte[] sendData1D : sendData2D) {
            if(sendData1D!=null){
                for(byte sendDataD: sendData1D){
                    sendData[i] = sendDataD;
                    i++;
                }
            }
        }
        return sendData;
    }
    
    private byte[] sendPickedUpItems(){
        if(me!=null){
            byte[][] sendData2D = new byte[me.getPickedUpItems().size()][];
            int i=0;
            int d=0;
            
            for(Actor.ItemPickUp pickUpItem : me.getPickedUpItems()){
                sendData2D[i] = new byte[]{
                                (byte) 99,
                                (byte)(pickUpItem.getItem().getType()),
                                (byte)((short)pickUpItem.getArg0() >>> 8),
                                (byte)((short)pickUpItem.getArg0())
                };
                d+=4;
                i++;
//                pickUpItem.consume();
                pickUpItem.markAsSend();
            }
            
            
            byte[] sendData = new byte[4 + d];
            sendData[0] = (byte)0;
            sendData[1] = (byte)0;
            sendData[2] = (byte)0;
            sendData[3] = (byte)0;
            
            i=4;
            for(byte[] sendData1D : sendData2D) {
                if(sendData1D!=null){
                    for(byte sendDataD: sendData1D){
                        sendData[i] = sendDataD;
                        i++;
                    }
                }
            }
            return sendData;
        }else{
            return null;
        }
    }
}

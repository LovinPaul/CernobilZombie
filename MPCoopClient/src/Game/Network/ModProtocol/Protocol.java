package Game.Network.ModProtocol;

import Game.Actors.Actor;
import Game.Actors.Hitman1;
import Game.Actors.Robot1;
import Game.Actors.Zombie1;
import Game.Mods.Game;
import Game.Mods.MPTDM;
import Game.Network.Connection.Connection;
import Main.SettingsManager;
import Main.Textures.Initilializer;
import Main.Textures.LoadTextures;
import java.util.Iterator;

public abstract class Protocol implements Initilializer{
    
//    protected String name;
    protected byte team;
    protected byte id=-1;
    protected boolean isKilled=true;
    
    protected Connection conn;
    protected Game game;
    protected SettingsManager settings;
    
    protected int frameWidth;
    protected int frameHeight;
    protected int lastStableFrame;
            
    public Protocol(Connection conn) {
        this.conn = conn;
    }
    
    public Game getGame(){
        return game;
    }
    public byte getTeam(){
        return team;
    }
    
    
    @Override
    public void setTexture(LoadTextures texture) {
        game.setTexture(texture);
    }
    public void setSettings(SettingsManager settings){
        this.settings=settings;
        game.setSettings(settings);
    }
    
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
            default:
                parseTCP(type);
                break;
        }
        
    }
    public void parseTCP(byte type){
        
        switch(type){
            case -1:
                break;
            case 0:
                System.out.println("heartbeat sent");
                conn.writeTCP((byte)0);
                conn.flushTCP();
                break;
            case 3:
                
                
                byte messageLenght;
                while((messageLenght=conn.readTCP())!=-1){
                
                    game.setMessageStatus("Received...");
                    if(messageLenght>0){
                        byte[] message = new byte[messageLenght];
                        for(int i=0; i<messageLenght;i++){
                            message[i] = conn.readTCP();
                        }
                        game.chatRoomAppend(new String(message));
                    }
                }
                

                break;
            case 5:
                byte byteActor=conn.readTCP();
                byte nameLenght=conn.readTCP();
                String actorName;
                //Name
                if(nameLenght==0){
//                    actorName = "Player" + byteActor;
                    actorName="";
                }else{
                    byte[] message = new byte[nameLenght];
                    for(int i=0; i<nameLenght;i++){
                        message[i] = conn.readTCP();
                    }
                    actorName = new String(message);
                }
                Iterator<Actor> actors = game.getActorsIterator();
                while(actors.hasNext()){
                    Actor actor = actors.next();
                    if(actor.getID()==byteActor){
                        actor.setName(actorName);
                    }
                }
                    
                break;
            default:
                System.out.println("ReadTcp() Invalid Type " + type);
                break;
        }
    }
    public void flushTCP(){
        conn.flushTCP();
    }
    protected void writeStatusTCP(){
        if(frameHeight!=game.getHeight() || frameWidth!=game.getWidth()){
            lastStableFrame = 3*30;
            frameWidth=game.getWidth();
            frameHeight=game.getHeight();
        }
        
        if(lastStableFrame>0){
            lastStableFrame--;
            if(lastStableFrame==0){
                byte[] data = new byte[1 + 8];
                data[0] = 8;
                data[1] = (byte)((short)frameWidth >>> 8);
                data[2] = (byte)((short)frameWidth);
                data[3] = (byte)((short)frameHeight >>> 8);
                data[4] = (byte)((short)frameHeight);
                conn.writeTCP(data);
            }
        }
    }
    
    public abstract void readUDP();
    
    public void writeInitMeTCP(byte me, String name){
//        if(!"".equals(name)){this.name=name;}
        
//        byte[] newByte = {1, me, (byte)name.getBytes().length};
        byte[] message1 = new byte[3+name.getBytes().length];
        
        message1[0]=1;
        message1[1]=me;
        message1[2]=(byte)name.getBytes().length;
        
        for(int i=3; i<name.getBytes().length+3; i++){
            message1[i] = name.getBytes()[i-3];
        }
        
        conn.writeTCP(message1);
        conn.flushTCP();
    }
    public void writeChatTCP(byte[] message){
        
        byte[] message3 = new byte[2+message.length];
        message3[0] = 3;
        message3[1] = (byte) message.length;
        
        for(int i=0; i<message.length; i++){
            message3[i+2] = message[i];
        }
        
        conn.writeTCP(message3);
        conn.flushTCP();
    }
    public void writeMyNameTCP(){
        
    }
    public void writeActorUDP(Actor me){
        if(me!=null){
            byte mAlocWeaponFire=0;
            byte mAlocWeapons=me.getNrOfWeapons();
            
            if(me.getWeapon()!=null && game.getMouseButton1() && me.getWeapon().silentFire()){
                mAlocWeaponFire=4;
            }

            byte[] sendData= new byte[18 + mAlocWeaponFire + mAlocWeapons];
            
//            if(me.getWeapon()!=null && game.getMouseButton1() && me.getWeapon().silentFire()){
//                sendData = new byte[21];
//                sendData[5] = -2;
//                // 4 bytes
//                sendData[17] =  (byte)((int)game.getMousePointer().getX() >>> 8);
//                sendData[18] =  (byte)((int)game.getMousePointer().getX());
//                sendData[19] =  (byte)((int)game.getMousePointer().getY() >>> 8);
//                sendData[20] =  (byte)((int)game.getMousePointer().getY());
//            }else{
//                sendData = new byte[17];
//                sendData[5] = 2;
//            }
            
            
            
            
            // 4 bytes
            int timeMilis = (int)(System.currentTimeMillis()%1000000000);
            sendData[0] = (byte)(timeMilis >>> 24);
            sendData[1] = (byte)(timeMilis >>> 16);
            sendData[2] = (byte)(timeMilis >>> 8);
            sendData[3] = (byte)(timeMilis);
            // 1 byte
            
            // 1 bytes
            sendData[4] = conn.getConnectionID();
            
            sendData[5] = 1;
            
            // 4 bytes
            sendData[6] = game.getKeyboard().goUp() ? (byte)1 : (byte)0;
            sendData[7] = game.getKeyboard().goDown() ? (byte)1 : (byte)0;
            sendData[8] = game.getKeyboard().goLeft() ? (byte)1 : (byte)0;
            sendData[9] = game.getKeyboard().goRight() ? (byte)1 : (byte)0;
            // 2 bytes
            sendData[10] = (byte)((short)me.getAngle() >>> 8);
            sendData[11] = (byte)((short)me.getAngle());
            
            sendData[12] = (byte)((int)game.getCamera().getX() >>> 8);
            sendData[13] = (byte)((int)game.getCamera().getX());
            sendData[14] = (byte)((int)game.getCamera().getY() >>> 8);
            sendData[15] = (byte)((int)game.getCamera().getY());
            
            sendData[16] = mAlocWeapons;
            
            if(mAlocWeapons>0){
                
                int i=17;
                for(byte b : me.getWeaponsByte()){
                    sendData[i]=b;
                    i++;
                }
                
                if(mAlocWeaponFire>0){
                    sendData[i++] = (byte) -me.getWeaponByte();
                    // 4 bytes
                    sendData[i++] =  (byte)((int)game.getMousePointer().getX() >>> 8);
                    sendData[i++] =  (byte)((int)game.getMousePointer().getX());
                    sendData[i++] =  (byte)((int)game.getMousePointer().getY() >>> 8);
                    sendData[i++] =  (byte)((int)game.getMousePointer().getY());
                }else{
                    sendData[i++] = (byte) me.getWeaponByte();
                }
            }
            



            
            
            conn.writeUDP(sendData);
        }else{
            byte[] sendData = new byte[10];
            // 4 bytes
            int timeMilis = (int)(System.currentTimeMillis()%1000000000);
            sendData[0] = (byte)(timeMilis >>> 24);
            sendData[1] = (byte)(timeMilis >>> 16);
            sendData[2] = (byte)(timeMilis >>> 8);
            sendData[3] = (byte)(timeMilis);
            // 1 bytes
            sendData[4] = conn.getConnectionID();
            sendData[5] = 0; // me null flag
            sendData[6] = (byte)((int)game.getCamera().getX() >>> 8);
            sendData[7] = (byte)((int)game.getCamera().getX());
            sendData[8] = (byte)((int)game.getCamera().getY() >>> 8);
            sendData[9] = (byte)((int)game.getCamera().getY());
//            System.out.println(Arrays.toString(sendData));
            conn.writeUDP(sendData);
        }
    }
    
    public void initGame(){
        game.init();
    }
    protected Actor makeActor(byte id){
        Actor actor = null;
        if(id>=0 && id<20){
            actor = new Zombie1(id);
        }else if(id>=20 && id<40){
            actor = new Hitman1(id);
        }else if(id>=40 && id<60){
            actor = new Robot1(id);
        }else{
            System.out.println("Invalid Actor " + id);
        }
        actor.init(game.getTextures());
        return actor;
    }
    public boolean isMeKilled(){
        return isKilled;
    }
    
    protected int toUnsignByte(byte b){
        return b<0?b+256:b;
    }
    
    public abstract void gameLoop();
}
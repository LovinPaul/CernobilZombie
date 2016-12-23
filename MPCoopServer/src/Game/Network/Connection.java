package Game.Network;

import Game.*;
import Game.Actors.*;
import Game.Actors.Items.Item;
import Game.Actors.Weapons.*;
import java.awt.Graphics;
import java.awt.Point;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.xml.transform.Source;

public class Connection {
    
    public static  ArrayList<Connection> connections = new ArrayList<>();
    protected static Game game;
    public static  ArrayList<String> chatRoom = new ArrayList<>();
    
    
    protected TCPConnection tcp;
    protected UDPConnection udp;

    private int chatRoomSize;
    
    static int bytesInUDP;
    
    protected String name="anonim";
    protected byte id=-2;
    protected MPTDM.Team myTeam;
    protected byte team=-1;
    protected Actor me;
    protected int deaths;
    protected int kills;
    protected int accuracy=100;
    protected int curentKills;
    protected static boolean statsChanged;    
    protected short cameraX;
    protected short cameraY;
    protected short cameraWidth=1366;
    protected short cameraHeight=600;
    
    protected byte connID;
    private final int heartbeatInterval=10*32; 
    private int lastHeartbeat;
    private int nrOfHeartbeats;
    private boolean isAlive=true;
    
    public static void addGame(Game gm){
        game = gm;
    }
    protected static byte assignConnectionID(){
        byte availableConnID=0;
        for(int i=0; i<connections.size(); i++){
            if(connections.get(i).connID==availableConnID){
                i=0;
                availableConnID++;
            }
        }
        return availableConnID;
    }
    
    public static void readAllUDP(){
        if(connections.isEmpty()){return;}
        DatagramPacket receivePacket;
        byte[] data;
        byte receivedConnID;
//        boolean isAvailable;
        do{
            receivePacket = UDPConnection.read();
            
            if(receivePacket!=null){
                data = receivePacket.getData();
                receivedConnID  = data[4];
                for(Connection conn : connections){
                    if(conn.connID==receivedConnID){
                        conn.updateUDP(data);
                        conn.udp.setPort(receivePacket.getPort());
                    }
                }
            }
        }while(receivePacket!=null);
        
        
        for(Connection conn : connections){
            conn.updateMe();
        }
    }
    
    public static void writeAllUDP(){
        synchronized(connections){
            for(Connection conn : connections){
                conn.writeUDP(conn.gatherUpdates());
            }
        }
    }
    public static void writeAllTCP(){
        synchronized(connections){
            for(Connection conn : connections){
                conn.writeChatRoomTCP();
                if(statsChanged){conn.writeStatsTCP();}
            }
            statsChanged=false;
        }
    }

    public static void verifyConnection(){
        synchronized(connections){
            Iterator<Connection> connectionsIterator = connections.iterator();
            while ( connectionsIterator.hasNext() ) {
                Connection connection = connectionsIterator.next();
                if(!connection.isAlive()){
                    connection.dispose();
                    connectionsIterator.remove();
                }
            }
        }
    }
    public static int nrOfConnections(){
        return connections.size();
    }
    
    public Connection() {
        this.chatRoomSize = chatRoom.size();
    }
    
    public byte getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setTeam(byte team){
        this.team=team;
    }
    public void setTeam(MPTDM.Team myTeam){
        this.myTeam=myTeam;
    }    

    public void parseTCP(byte type){
        if(type!=-1 && type!=-2){
            if(type!=0){nrOfHeartbeats=0;}
            lastHeartbeat=0;
        }
        switch(type){
            case -2:
                isAlive=false;
                break;
            case -1:
                //no data is available to read;
                if(lastHeartbeat==heartbeatInterval){
//                    System.out.println("Heartbeat sent...");
                    tcp.write((byte)0);
                    tcp.flush();
                }
                if(lastHeartbeat>=1.5*heartbeatInterval){
                    System.out.println("[" + connID + "]No Heartbeat pressent.");
                    isAlive=false;
                }
                lastHeartbeat++;
                break;
            case 0:
                //heartbeat
//                nrOfHeartbeats++;
//                if(nrOfHeartbeats>12){
//                    System.out.println("Idle disconect.");
////                    tcp.write((byte)-1);
////                    tcp.flush();
//                    isAlive=false;
//                }
//                System.out.println("Heartbeat received...");
                break;
            case 3:
                byte messageLenght = tcp.read();
                byte[] message = new byte[messageLenght];
                for(int i=0; i<messageLenght;i++){
                    message[i] = tcp.read();
                }
                if(me!=null){
                    if(me.isAlive()){
                        chatRoom.add(name + " > " + new String(message));
                    }
                }else{
                    chatRoom.add("(Dead)" + name + " > " + new String(message));
                }
                
//                chatRoomSize++;
//                
//                tcp.write(new byte[]{3, 0});
//                tcp.flush();                
                
                break;
            case 5:
                byte actorID = tcp.read();
                byte[] actorName = new byte[]{};
                Iterator<Actor> actors = game.getActorsIterator();
                while(actors.hasNext()){
                    Actor actor = actors.next();
                    if(actor.getID()==actorID){
                        actorName = actor.getName().getBytes();
                    }
                }
                
                byte[] message5 = new byte[3+actorName.length];
                message5[0]=5;
                message5[1]=actorID;
                message5[2]=(byte)actorName.length;
                
                for(int i=0; i<actorName.length; i++){
                    message5[i+3] = actorName[i];
                }
                
                
                tcp.write(message5);
                tcp.flush();
                break;
            case 7:
                if(me!=null){
                    me.setHitPoints(0);
                }
                break;
            case 8:
                cameraWidth = (short)((tcp.read() & 0xFF) << 8 | (tcp.read() & 0xFF));
                cameraHeight = (short)((tcp.read() & 0xFF) << 8 | (tcp.read() & 0xFF));
                
                System.out.println("[" + connID + "]New Camera Size : " + cameraWidth + "x" + cameraHeight);
                break;
            default:
                System.out.println("[" + connID + "]ReadTcp() Invalid Type " + type);
                break;
                
        }
    }
    
    public void writeInitTCP(){
        
        tcp.write(new byte[]{4, connID, game.getGameMode(), game.getMap().getID()});
        tcp.flush();
    }
    public void writeStatsTCP(){
        System.out.println("Need Oweride - void writeStatsTCP()");
    }
    public void writeChatRoomTCP(){
        synchronized(chatRoom){
            if(chatRoomSize!=chatRoom.size()){
                byte[][] sendData2D = new byte[chatRoom.size()-chatRoomSize][];
                
                int j=0;
                int a=0;
                for(int i=chatRoomSize; i<chatRoom.size(); i++){
                    sendData2D[j]= new byte[1+chatRoom.get(i).getBytes().length];
                    sendData2D[j][0]=(byte) chatRoom.get(i).getBytes().length;
                    
                    for(int f=0; f<chatRoom.get(i).getBytes().length; f++){
                        sendData2D[j][f+1]=chatRoom.get(i).getBytes()[f];
                    }
                    j++;
                    a+=chatRoom.get(i).getBytes().length+1;
                }
                
                byte[] sendData = new byte[2 + a];
                sendData[0]=3;
                
                int i=1;
                for(byte[] sendData1D : sendData2D) {
                    for(byte sendDataD: sendData1D){
                        sendData[i] = sendDataD;
                        i++;
                    }
                }
                sendData[i]=-1;
                tcp.write(sendData);
                tcp.flush();
                chatRoomSize = chatRoom.size();
            }
        }
    }

    public void writeUDP(byte[] data){
        if(data!=null){
            udp.write(data);
        }
    }
    public byte[] gatherUpdates(){
        return null;
    }
    
    protected boolean newConnection(){
        tcp = new TCPConnection();
        udp = new UDPConnection();
        if(tcp.newConnection()){
            udp.setAddress(tcp.getAddress());
            return true;
        }else{
            return false;
        }
    }
    public boolean isAlive(){
        return isAlive;
    }
    public void dispose(){
            if(me!=null){me.setHitPoints(0);}
            if(myTeam!=null){myTeam.removeFromTeam();}
            chatRoom.add("\t* " + name +" has disconected *");
            System.out.println("[" + connID + "]Killing the connection with : "+tcp.getAddress().getHostAddress() + " (" + name + ")");
            statsChanged=true;
            tcp.close();
            udp.close();
//            tcp=null;
//            udp=null;
    }
    public void updateMe(){
        byte[] data = udp.getData();
//        System.out.println(data);
        if(data!=null){
            
            
            if(data[5]==0){
                cameraX = (short)((data[6] & 0xFF) << 8 | (data[7] & 0xFF));
                cameraY = (short)((data[8] & 0xFF) << 8 | (data[9] & 0xFF));
//                game.camera.setX(cameraX);
//                game.camera.setY(cameraY);
            }
            if(me!=null){// && me.getID()==data[4]
                lastHeartbeat=0;
                if(data[6]==1){me.goUp();}
                if(data[7]==1){me.goDown();}
                if(data[8]==1){me.goLeft();}
                if(data[9]==1){me.goRight();}
                short clientAngle = (short)((data[10] & 0xFF) << 8 | (data[11] & 0xFF));
                if(me.getAngle()!=clientAngle){me.setAngle((me.getAngle()+clientAngle)/2);}                

                cameraX = (short)((data[12] & 0xFF) << 8 | (data[13] & 0xFF));
                cameraY = (short)((data[14] & 0xFF) << 8 | (data[15] & 0xFF));
                
//                game.camera.setX(cameraX);
//                game.camera.setY(cameraY);
                if(data[16]>0){
                    byte[] clientWeapons = new byte[data[16]];
                    
                    int i=17;
                    for(int j=0; j<data[16];i++,j++){
                        clientWeapons[j]=data[i];
                    }
                    
                    me.setWeaponsByte(clientWeapons);
                    me.setWeapon((byte) Math.abs(data[i]));
                    
                    if(data[i]<0){
                        i++;
                        me.fire((int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF)), 
                                (int)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF)));
                    }else{
                        me.stopFire();
                    }
                    
                    
                }                

//                me.setWeapon(data[16]);
//                
//                me.stopFire();
//                
//                if(data[5]<0){
//                    me.fire((int)((data[17] & 0xFF) << 8 | (data[18] & 0xFF)), 
//                            (int)((data[19] & 0xFF) << 8 | (data[20] & 0xFF)));
//                }
//                else if(data[5]==1){
//                }

            }
        }
    }
    public void updateUDP(byte[] receiveData){
        udp.updateData(receiveData);
    }
    
    public void resetStats(){
        kills=0;
        deaths=0;
    }
    
    public void draw(Graphics g){
//        g.drawString(cameraX + "   " + cameraY, 50, 500);
        g.drawString(lastHeartbeat+"", 50, 500);
    }
}
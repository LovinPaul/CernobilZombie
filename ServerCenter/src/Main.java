
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main implements Runnable{
    
    private InetAddress address;
    private DatagramSocket UDPsock;
    private DatagramPacket sendPacket;
    private int portIn=4245;
    private int portOut=4246;
    
    Thread t;
//    private long ONE_SECOND = 1000;
    
    ArrayList<ServerInfo> servers;
    
    public static void main(String args[]){
        Main main = new Main();
        main.init();
    }
    
    private void init(){
        servers = new ArrayList<>();
        
//        servers.add(new Server(1, 1, 1, 1, 0, 0, 13));
//        servers.add(new Server(1, 1, 1, 2, 0, 0, 13));
//        servers.add(new Server(1, 1, 1, 3, 0, 0, 13));
//        servers.add(new Server(1, 1, 1, 4, 0, 0, 13));
//        servers.add(new Server(1, 1, 1, 5, 0, 0, 13));
        
        try {
            if(UDPsock!=null){return;}
            UDPsock = new DatagramSocket(portIn);//
            UDPsock.setSoTimeout(1);
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        t = new Thread(this);
        t.start();
    }
    
    public DatagramPacket read(){
        byte[] receiveData = new byte[128];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        
        try{
            UDPsock.receive(receivePacket);
            return receivePacket;
        }catch(SocketTimeoutException stex){
            return null;
        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    private void parseData(){
        DatagramPacket receivePacket = read();
        byte[] data = null;
        if(receivePacket!=null && (data=receivePacket.getData())!=null){
            int i;
            switch(data[0]){
                case 0:
//                    System.out.println("Server Data Received");
                    dataReceived(data);
                    break;
                case 1:
                    try {
                        System.out.println("Request Received From : " + receivePacket.getAddress().getCanonicalHostName());//tmp.getAddress()
                        byte[] dataToSend = dataToSend();
                        sendPacket = new DatagramPacket(dataToSend, dataToSend.length, receivePacket.getAddress(), receivePacket.getPort());//tmp.getAddress()
                        UDPsock.send(sendPacket);
                    } catch (IOException ex) {
                        System.out.println("sendUpdate() :" + ex.getMessage());
                        ex.printStackTrace();
                    }
                    break;
            }
        }
    }
    
    private void checkServers(){
        Iterator<ServerInfo> serverIterator = servers.iterator();
        while(serverIterator.hasNext()){
            ServerInfo server = serverIterator.next();
            
            if((System.currentTimeMillis()-server.getLastUpdateTime())>6*5000){ //5000 - 5 sec
            System.out.println("Server Removed" +
                ", Name: " + server.getServerName());
                serverIterator.remove();
            }
            
        }
        
    }
    
    private void dataReceived(byte[] data){
        int i=1;
        boolean isNewServer=true;

        int ip1=toUnsignByte(data[i++]);
        int ip2=toUnsignByte(data[i++]);
        int ip3=toUnsignByte(data[i++]);
        int ip4=toUnsignByte(data[i++]);

        short tcpPort = (short)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
        short udpInPort = (short)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));
        short udpOutPort = (short)((data[i++] & 0xFF) << 8 | (data[i++] & 0xFF));



        byte gameMode = data[i++];
        byte gameMap = data[i++];
        byte nrOfActors = data[i++];

        byte serverNameLenght = data[i++];
        byte[] serverName = new byte[serverNameLenght];

        for(int j=0; j<serverNameLenght; i++, j++){
            serverName[j] = data[i];
        }

        String srvName = new String(serverName);

        for(ServerInfo server : servers){
            if(server.ip1==ip1 && server.ip2==ip2 && server.ip3==ip3 && server.ip4==ip4){
//            System.out.println("   -Updated" +
//                ", IP: " + (toUnsignByte(data[1]) + "." + toUnsignByte(data[2]) + "." + toUnsignByte(data[3]) + "." + toUnsignByte(data[4])) +
//                ", Name: " + srvName);
                isNewServer=false;
                server.update( gameMode, gameMap, nrOfActors);
            }
        }

        if(isNewServer){
            System.out.println("   -Created" +
                ", IP: " + (toUnsignByte(data[1]) + "." + toUnsignByte(data[2]) + "." + toUnsignByte(data[3]) + "." + toUnsignByte(data[4])) +
                ", Name: " + srvName);
            ServerInfo srvInfo = new ServerInfo(ip1, ip2, ip3, ip4, tcpPort, udpInPort, udpOutPort, srvName);
            srvInfo.update( gameMode, gameMap, nrOfActors);
            servers.add(srvInfo);
        }
    }
    private byte[] dataToSend(){
        
        byte[][] serversData = new byte[servers.size()][];
        int i=0;
        
        byte ip1;
        byte ip2;
        byte ip3;
        byte ip4;
        short tcpPort;
        short udpInPort;
        short udpOutPort;
        String serverName;
        
        int nrOfBytes = 0;
        for(ServerInfo server : servers){

                    ip1 = (byte) server.ip1;
                    ip2 = (byte) server.ip2;
                    ip3 = (byte) server.ip3;
                    ip4 = (byte) server.ip4;
                    tcpPort = server.tcpPort;
                    udpInPort = server.udpInPort;
                    udpOutPort = server.udpOutPort;
                    serverName = server.serverName;

                    byte[] serverData = new byte[14 + serverName.getBytes().length];

                    serverData[0] = ip1;
                    serverData[1] = ip2;
                    serverData[2] = ip3;
                    serverData[3] = ip4;

                    serverData[4] = (byte)((short)tcpPort >>> 8);
                    serverData[5] = (byte)((short)tcpPort);
                    serverData[6] = (byte)((short)udpInPort >>> 8);
                    serverData[7] = (byte)((short)udpInPort);
                    serverData[8] = (byte)((short)udpOutPort >>> 8);
                    serverData[9] = (byte)((short)udpOutPort);

                    serverData[10] = server.gameMode;
                    serverData[11] = server.gameMap;
                    serverData[12] = server.nrOfPlayers;

                    serverData[13] = (byte) serverName.getBytes().length;

                    for(int j=14; j-14<serverName.getBytes().length; j++){
                        serverData[j]=serverName.getBytes()[j-14];
                    }
                    
                    nrOfBytes+=serverData.length;
                    serversData[i++] = serverData;
        }
        
        byte[] sendData = new byte[1 + nrOfBytes];
        sendData[0]=1;
        
        i=1;
        for(byte[] sendData1D : serversData) {
            for(byte sendData0D: sendData1D){
                sendData[i] = sendData0D;
                i++;
            }
        }
        return sendData;
        
    }
    
    private int toUnsignByte(byte b){
        return b<0?b+256:b;
    }

    @Override
    public void run() {
        while(true){
            
            parseData();
            checkServers();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    
    public class ServerInfo {
    
        int ip1;
        int ip2;
        int ip3;
        int ip4;
        
        short tcpPort;
        short udpInPort;
        short udpOutPort;
        
        byte gameMode;
        byte gameMap;
        byte nrOfPlayers;
        
        double lastUpdate;
        
        String serverName;

        public ServerInfo(int ip1, int ip2, int ip3, int ip4, short tcpPort, short udpInPort, short udpOutPort, String serverName) {
            this.ip1 = ip1;
            this.ip2 = ip2;
            this.ip3 = ip3;
            this.ip4 = ip4;
            this.tcpPort = tcpPort;
            this.udpInPort = udpInPort;
            this.udpOutPort = udpOutPort;
            this.serverName = serverName;
            
            lastUpdate=System.currentTimeMillis();
        }
        
        public void update(byte gameMode, byte gameMap, byte nrOfPlayers){
            this.gameMode = gameMode;
            this.gameMap = gameMap;
            this.nrOfPlayers = nrOfPlayers;
            lastUpdate=System.currentTimeMillis();
        }
        
        public double getLastUpdateTime(){
            return lastUpdate;
        }
        public String getServerName(){
            return serverName;
        }
        public int getNrOfPlayers(){
            return nrOfPlayers;
        }
}
    
    
}

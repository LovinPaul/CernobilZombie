package Game.Network.Broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastListen implements Runnable{
    
    private InetAddress address;
    private int portIn;
    private int portOut=4245;
    private boolean localGame;
    
    //Internet
    private DatagramSocket UDPsock;
//    private DatagramPacket sendPacket;
    private int bytesIn;
    
    //Local
    private String groupName = "229.5.38.17";
    private MulticastSocket MCsocket;
    
    
//    private byte[] data;
    ArrayList<ServerInfo> servers;

    public BroadcastListen() {
        servers=new ArrayList<>();
    }
    
    
    
    public void setLocal(boolean localGame){
        this.localGame=localGame;
    }
        
    
    public void init(){
        
        if(localGame){
            portIn=4245;
            try {
                MCsocket = new MulticastSocket(portIn);
                MCsocket.setSoTimeout(1);
                
//                MCsocket.setInterface(InetAddress.getByName("127.0.0.1"));
//                MCsocket.joinGroup(InetAddress.getByName(groupName));
                
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iface = interfaces.nextElement();
                    if (iface.isLoopback() || !iface.isUp())
                        continue;

                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while(addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if(addr.isSiteLocalAddress()){
                            MCsocket.setInterface(addr);
                            MCsocket.joinGroup(InetAddress.getByName(groupName));
                        }
                    }
                }
            } catch (IOException ex) {
                
                ex.printStackTrace();
    //            Logger.getLogger(BroadcastListen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            portIn=4246;
            try {
                address = InetAddress.getByName("78.97.157.147");
//                if(UDPsock!=null){return;}
                UDPsock = new DatagramSocket(portIn);//
                UDPsock.setSoTimeout(2*5000);
            } catch (SocketException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (UnknownHostException ex) {
                Logger.getLogger(BroadcastListen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void close(){
        if(localGame){
            try {
                MCsocket = new MulticastSocket(4245);
                MCsocket.setSoTimeout(1);
                
//                MCsocket.setInterface(InetAddress.getByName("127.0.0.1"));
//                MCsocket.leaveGroup(InetAddress.getByName(groupName));
                
                
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iface = interfaces.nextElement();
                    if (iface.isLoopback() || !iface.isUp())
                        continue;

                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while(addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if(addr.isSiteLocalAddress()){
                            MCsocket.setInterface(addr);
                            MCsocket.leaveGroup(InetAddress.getByName(groupName));
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
    //            Logger.getLogger(BroadcastListen.class.getName()).log(Level.SEVERE, null, ex);
            }

            MCsocket.close();
            MCsocket = null;
        }else{
            UDPsock.close();
            UDPsock = null;
        }
    }
    public void clear(){
        servers.clear();
    }
    
    public void requestLocalData(){
        checkLocalServers();
        bytesIn=0;
        try {
            DatagramPacket packet;
            try{
                do{
                    byte[] buf = new byte[128];
                    packet = new DatagramPacket(buf, buf.length);
                    MCsocket.receive(packet);
                    parseData(packet.getData(), packet.getLength());
                    bytesIn += packet.getLength();
                }while(true);
            }catch(SocketTimeoutException stex){
            }
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(BroadcastListen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void requestInternetData(){
        servers.clear();
        bytesIn=0;
        //send request
        try {
//            byte[] dataRequest= {1, 78, 97, (byte)157, (byte)147};
            DatagramPacket sendPacket;
            byte[] dataRequest= {1};//, 78, 97, (byte)157, (byte)147

            sendPacket = new DatagramPacket(dataRequest, dataRequest.length, address, portOut);                
            UDPsock.send(sendPacket);
        } catch (IOException ex) {
            System.out.println("sendUpdate() :" + ex.getMessage());
            ex.printStackTrace();
        }
        
        //Wait reponse to request
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] receiveData = new byte[512];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try{
                    UDPsock.receive(receivePacket);
                    parseData(receivePacket.getData(), receivePacket.getLength());
                    bytesIn = receivePacket.getLength();
                }catch(SocketTimeoutException stex){
                    bytesIn=-1;
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        th.start();
        
//        bytesIn=2;
//        for(int i=0;i<30;i++){
//            ServerInfo srvInfo = new ServerInfo(0, 0, 0, i, (short)1, (short)2, (short)3, "Server#" + i);
//            srvInfo.update( 0, (int)(Math.random()*3), (int)(Math.random()*25));
//            servers.add(srvInfo);
//        }
        
    }

    
    public int getBytesIn(){
        return bytesIn;
    }
    public int getServerListSize(){
        return servers.size();
    }
    public ArrayList<ServerInfo> getServers(){
        return servers;
    }
    
    public void parseData(byte[] data, int lenght){
        for(int i=1; i<lenght;){
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
            
            
            for(ServerInfo server : servers){
                if(server.ip1==ip1 && server.ip2==ip2 && server.ip3==ip3 && server.ip4==ip4){
                    isNewServer=false;
                    server.update( gameMode, gameMap, nrOfActors);
                }
            }

            if(isNewServer){
                ServerInfo srvInfo = new ServerInfo(ip1, ip2, ip3, ip4, tcpPort, udpInPort, udpOutPort, new String(serverName));
                srvInfo.update( gameMode, gameMap, nrOfActors);
                servers.add(srvInfo);
            }
            
            
        }
    }
    
    
    
    private void checkLocalServers(){
        Iterator<ServerInfo> serverIterator = servers.iterator();
        while(serverIterator.hasNext()){
            ServerInfo server = serverIterator.next();
            
            if((System.currentTimeMillis()-server.getLastUpdateTime())>5000){ //5000 - 5 sec
                serverIterator.remove();
            }
            
        }
        
    }
    private int toUnsignByte(byte b){
        return b<0?b+256:b;
    }
    @Override
    public void run() {
        
//        while(listen){
//            
//            listen();
//            
//                // sleep for a while
//            try {
//                Thread.sleep(TEN_SECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            
//        }
        
    }
    
}

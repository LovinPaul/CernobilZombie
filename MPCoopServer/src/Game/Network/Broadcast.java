package Game.Network;

import Game.Game;
import java.io.*;
import java.net.*;
import java.util.*;

public class Broadcast implements Runnable{
    
    String serverName = "<GagarinStudio Server>";
    String ip = null;
    private InetAddress address;
    private int portOut=4245;
//    private int portIn=4246;
    private boolean localGame;
    
    //Internet
    private static DatagramSocket UDPsock;
    private DatagramPacket sendPacket;
    
    //Local
    private String groupName = "229.5.38.17";
    MulticastSocket MCsocket;
    
    
    Thread thread;
    Game game;
    
    private long updateInterval;
            
    public Broadcast(Game game, boolean localGame) {
        this.game=game;
        this.localGame=localGame;
        
        if(localGame){
            initMulticast();
        }else{
            initDatagramSocket();
        }
    }
    
    private void initMulticast(){
        updateInterval = 1000;
        
        try {
            address = getLocalAddress();//InetAddress.getByName("192.168.0.171");
            if(address==null){
                //network unavailable
                System.out.println("network unavailable");
            }else{
                ip=address.getHostAddress();
                MCsocket = new MulticastSocket();
                MCsocket.setSoTimeout(1);

                System.out.println(address.getHostAddress());
                MCsocket.setInterface(address);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(Broadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initDatagramSocket(){
        updateInterval = 5000;
        ip = "78.97.157.147";
        
        try {
            address = InetAddress.getByName("78.97.157.147");
            UDPsock = new DatagramSocket();//
            UDPsock.setSoTimeout(1);
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
//                Logger.getLogger(Broadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startBroadcast(){
        thread = new Thread(this);
        thread.start();
    }
    
    private void sendLocal() {
        try {
            
            byte[] data = getBroadcastMessage();
            
            InetAddress group = InetAddress.getByName(groupName);
            DatagramPacket packet = new DatagramPacket(data, data.length, group, portOut);
            
            
            
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    MCsocket.setInterface(addr);
                    MCsocket.send(packet);
                }
            }
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
//            Logger.getLogger(Broadcast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(Broadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void sendInternet(){
        try {
//            byte[] data= {0, 78, 97, (byte)157, (byte)147, game.getGameMode(), game.getMap().getID(), (byte)game.getNrOfActors()};
            byte[] data = getBroadcastMessage();
            
            sendPacket = new DatagramPacket(data, data.length, address, portOut);                
            UDPsock.send(sendPacket);
        } catch (IOException ex) {
            System.out.println("sendUpdate() :" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public byte[] getBroadcastMessage() {
        
        
        String[] ipSubStr = ip.split("\\.");
        
        byte ip1 = (byte) Integer.parseInt(ipSubStr[0]);
        byte ip2 = (byte) Integer.parseInt(ipSubStr[1]);
        byte ip3 = (byte) Integer.parseInt(ipSubStr[2]);
        byte ip4 = (byte) Integer.parseInt(ipSubStr[3]);
        
        short tcpPort = 4242;
        short udpInPort = 4242;
        short udpOutPort = 4242;
        
        
        
        byte[] dataToSend = new byte[15 + serverName.getBytes().length];
        
        dataToSend[0]=0;
        
        dataToSend[1] = ip1;
        dataToSend[2] = ip2;
        dataToSend[3] = ip3;
        dataToSend[4] = ip4;
        
        dataToSend[5] = (byte)((short)tcpPort >>> 8);
        dataToSend[6] = (byte)((short)tcpPort);
        dataToSend[7] = (byte)((short)udpInPort >>> 8);
        dataToSend[8] = (byte)((short)udpInPort);
        dataToSend[9] = (byte)((short)udpOutPort >>> 8);
        dataToSend[10] = (byte)((short)udpOutPort);
        
        dataToSend[11] = game.getGameMode();
        dataToSend[12] = game.getMap().getID();
        dataToSend[13] = (byte)game.getNrOfActors();
        
        dataToSend[14] = (byte) serverName.getBytes().length;
        
        for(int i=15; i-15<serverName.getBytes().length; i++){
            dataToSend[i]=serverName.getBytes()[i-15];
        }

        return dataToSend;
        
        
    }
    
    public void close(){
        MCsocket.close();
    }

    @Override
    public void run() {
        
        while(true){
            
            if(localGame){
                sendLocal();
            }else{
                sendInternet();
            }
            
            try {
                Thread.sleep(updateInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    


    private static InetAddress getLocalAddress(){
        
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while( b.hasMoreElements()){
                for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses()){
                    if ( f.getAddress().isSiteLocalAddress()){
                        return f.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        //if network uavailable return loopback address
        try {
            return InetAddress.getByName("127.0.0.1");//
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
//            Logger.getLogger(Broadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }


    
}

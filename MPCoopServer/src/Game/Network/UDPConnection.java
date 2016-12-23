package Game.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPConnection extends Connection{
    
    private InetAddress address;
    private static DatagramSocket UDPsock;
    private DatagramPacket sendPacket;
    private int port;

    private int latency = -1;
    private int bytesIn;
    private int bytesOut;
    byte[] data;
    
//    String TMPSTR;
    
    public UDPConnection() {
        init();
    }

    private void init(){
        try {
            if(UDPsock!=null){return;}
            UDPsock = new DatagramSocket(4243);//
            UDPsock.setSoTimeout(1);
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void setAddress(InetAddress address){
        this.address = address;
    }
    public void setPort(int port){
        this.port=port;
    }
    
    public static DatagramPacket read(){//
//        byte[] data;
        byte[] receiveData = new byte[512];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        
        
        
        try{
            UDPsock.receive(receivePacket);
//            data = receivePacket.getData();
//            bytesIn = receivePacket.getLength();
            return receivePacket;
        }catch(SocketTimeoutException stex){
            return null;
        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public void updateData(byte[] receiveData){
        int nowTimeMillis = (int)(System.currentTimeMillis() % 1000000000);
        int tempTimeMillis;
        int tempLatency;

        if(data==null){
            data = receiveData.clone();
            tempTimeMillis = data[0] << 24 | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
            latency = nowTimeMillis-tempTimeMillis;
            bytesIn += data.length;
        }else{
            tempTimeMillis = receiveData[0] << 24 | (receiveData[1] & 0xFF) << 16 | (receiveData[2] & 0xFF) << 8 | (receiveData[3] & 0xFF);
            tempLatency = nowTimeMillis-tempTimeMillis;

            if(latency > tempLatency){
                data = receiveData.clone();
                latency = tempLatency;
                bytesIn += data.length;
            }
        }
    }
    public byte[] getData(){
        byte[] newData = data;
        data = null;
        return newData;
    }

//    public void setTMPSTR(String plm){
//        TMPSTR = plm;
//    }
    
    public void write(byte[] data){
        try {
            sendPacket = new DatagramPacket(data, data.length, address, port);                
            UDPsock.send(sendPacket);
        } catch (IOException ex) {
            System.out.println("sendUpdate() :" + ex.getMessage());
            ex.printStackTrace();
        }

    }
    
    public void close(){
//        UDPsock.close();
//        UDPsock=null;
    }
}

package Game.Network.Connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPConnection {
    
    private InetAddress address;
    //UDP
    DatagramSocket UDPsock;
    DatagramPacket sendPacket;
    
    private int latency = -1;
    private int bytesIn;
    private int bytesOut;

    private String ip;
    private int portIn;
    private int portOut;
    
    
    public void setAddress(InetAddress address){
        this.address = address;
    }
    public void setPortIn(int port){
        portIn = port;
    }
    public void setPortOut(int port){
        portOut = port;
    }
    public void startListen(){
        try {
            UDPsock = new DatagramSocket(portIn);
            UDPsock.setSoTimeout(1);
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public byte[] read(){
        byte[] receiveData = new byte[512];
        byte[] data;
        int nowTimeMillis = (int)(System.currentTimeMillis() % 1000000000);
        int tempTimeMillis;
        int tempLatency;
        
        data = null;
        try {
            
            try{
                
                do{
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    UDPsock.receive(receivePacket);
                    
                    //pickedUpItems
                    tempTimeMillis = receivePacket.getData()[0] << 24 | (receivePacket.getData()[1] & 0xFF) << 16 | (receivePacket.getData()[2] & 0xFF) << 8 | (receivePacket.getData()[3] & 0xFF);
                    if(tempTimeMillis==0){
                        return receivePacket.getData().clone();
                    }
                    
                    //
                    if(data==null){
                        data = receivePacket.getData().clone();//
                        tempTimeMillis = data[0] << 24 | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
                        latency = Math.abs(nowTimeMillis-tempTimeMillis);
                        bytesIn = receivePacket.getLength();
                    }else{
                        byte[] tempData = receivePacket.getData().clone();
                        tempTimeMillis = tempData[0] << 24 | (tempData[1] & 0xFF) << 16 | (tempData[2] & 0xFF) << 8 | (tempData[3] & 0xFF);
                        tempLatency = Math.abs(nowTimeMillis-tempTimeMillis);
                        
                        if(latency > tempLatency){
                            data = tempData.clone();//
                            latency = tempLatency;
                            bytesIn = receivePacket.getLength();
                        }                        
                        
                    }

                }while(true);

            }catch(SocketTimeoutException stex){
                return data;
            }
        } catch (IOException ex) {
            System.out.println("getDatagram() IOException :" +ex.getMessage());
        }
        return null;
    }

    public int getLatency(){
        return latency;
    }
    public int getBytesIn(){
        return bytesIn;
    }
    public int getBytesOut(){
        return bytesOut;
    }
    public void write(byte[] data){
        try {
            bytesOut = data.length;
            sendPacket = new DatagramPacket(data, data.length, address, portOut);                
            UDPsock.send(sendPacket);
        }catch(IOException ex){
            System.out.println("sendUpdate() : " + ex.getMessage());
            ex.printStackTrace();
        }

    }
    public void dispose(){
        UDPsock.close();
    }

}

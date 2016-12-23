package Game.Network;


import Game.Actors.Actor;
import Game.Actors.Hitman1;
import Game.Actors.Zombie1;
import Game.Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPConnection{
    ////TCP
    private static ServerSocket serversock;
    private Socket sock = null;
//    private ObjectInputStream objInputStream;
//    private ObjectOutputStream objOutputStream;
    
    OutputStream out;
    DataOutputStream dos;
    
    InputStream in;
    DataInputStream dis;
    
    public TCPConnection() {
        try {
            if(serversock==null){
                serversock = new ServerSocket(4242);
            }
        } catch (IOException ex) {
            System.out.println("TCPConnection() :" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public boolean newConnection(){
        System.out.println("Listen for new connections...");
        try {
            
            sock = serversock.accept();
            sock.setTcpNoDelay(true);
//            objOutputStream = new ObjectOutputStream(sock.getOutputStream());
//            objInputStream = new ObjectInputStream(sock.getInputStream());

            out = sock.getOutputStream();
            dos = new DataOutputStream(out);
            
            in = sock.getInputStream();
            dis = new DataInputStream(in);
            
            
            System.out.println("New connection...");
            System.out.println("IP/port : "+sock.getInetAddress().getHostAddress() + ":" + sock.getPort());
            
        } catch (IOException ex) {
            System.out.println("listenForConnections() :" + ex.getMessage());
            ex.printStackTrace();
        }
        return true;
    }
    public InetAddress getAddress(){
        return sock.getInetAddress();
    }

    public byte read(){
        
        try {
            if(dis.available()>0){
                byte tmp = dis.readByte();
//                System.out.println(tmp);
                return tmp;
            }else{
                return -1;
            }
        } catch (IOException ex) {
            System.out.println("getChatUpdate() :" + ex.getMessage());
            ex.printStackTrace();
            return -2;
        }
        
        
//        try {
//            if(objInputStream.available()>0){
//                byte tmp = objInputStream.readByte();
//                return tmp;
//            }else{
//                return -1;
//            }
//        } catch (IOException ex) {
//            System.out.println("getChatUpdate() :" + ex.getMessage());
//            ex.printStackTrace();
//            return -2;
//        }
    }

//    public void write(byte type, byte[] message){
//        try{
//            
//            dos.writeByte(type);
//            dos.write(message);
//            dos.flush();
//        }catch (IOException ex){
//            System.out.println("sendAssignedID() :" + ex.getMessage());
//        }
//    }
    public void write(byte message){
        try{
            dos.writeByte(message);
            dos.flush();
        }catch (IOException ex){
            System.out.println("sendAssignedID() :" + ex.getMessage());
        }
    }
    public void write(byte[] message){
        try{
            dos.write(message);
            dos.flush();
        }catch (IOException ex){
            System.out.println("sendAssignedID() :" + ex.getMessage());
        }
    }
    public void flush(){
//        try {
//            dos.flush();
////            objOutputStream.reset();        
//        } catch (IOException ex) {
//            System.out.println("flush() :" + ex.getMessage());
//            ex.printStackTrace();
//        }
    }
    
    public void close(){
        try {
            
            dis.close();
            dis=null;
            dis=null;
            out =null;
            in = null;
            
            sock.close();
            sock=null;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}

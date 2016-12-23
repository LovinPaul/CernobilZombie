package Game.Network.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnection{
    ////TCP
    ////TCP
    private Socket sock;
//    private ObjectInputStream objInputStream;
//    private ObjectOutputStream objOutputStream;
    
    OutputStream out;
    DataOutputStream dos;
    
    InputStream in;
    DataInputStream dis;
    
    
    private String ip;
    private int port;
    
    private String errorMessage;
    
    public Socket getSocket(){
        return sock;
    }
    public InetAddress getAddress(){
        return sock.getInetAddress();
    }
    public String getErrorMessage(){
        return errorMessage;
    }
    
    public void setConnection(Socket newSock){
        try {
            sock = newSock;
            sock.setTcpNoDelay(true);
            sock.setKeepAlive(true);

            out = sock.getOutputStream();
            dos = new DataOutputStream(out);
            
            in = sock.getInputStream();
            dis = new DataInputStream(in);

        } catch (IOException ex) {
            System.out.println("initSocket() :" + ex.getMessage());
            errorMessage = ex.toString();// + ex.getMessage();
            ex.printStackTrace();
        }
        
        
    }
    
    public boolean newConnection(String ip, int port){
        try {
            System.out.println("Try to connect ("+ip+":"+port+")" );
            sock = new Socket(ip, port);
            sock.setTcpNoDelay(true);
            sock.setKeepAlive(true);

            out = sock.getOutputStream();
            dos = new DataOutputStream(out);
            
            in = sock.getInputStream();
            dis = new DataInputStream(in);

        } catch (IOException ex) {
            System.out.println("initSocket() :" + ex.getMessage());
            errorMessage = ex.toString();// + ex.getMessage();
            ex.printStackTrace();
            return false;
        }
        
        System.out.println("Connection successful.");
        System.out.println("IP/port : "+sock.getInetAddress().getHostAddress() + ":" + sock.getPort());
        
        return true;
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
            System.out.println("readTCP() :" + ex.getMessage());
            ex.printStackTrace();
            return -2;
        }
    }
    public void write(byte message){
        try{
            dos.writeByte(message);
//            dos.flush();
        }catch (IOException ex){
            System.out.println("write(byte message) :" + ex.getMessage());
        }
    }
    public void write(byte[] message){
        try{
            dos.write(message);
//            dos.flush();
        }catch (IOException ex){
            System.out.println("write(byte[] message) :" + ex.getMessage());
        }
    }
    public void flush(){
        try {
            dos.flush();
        } catch (IOException ex) {
            System.out.println("flush() :" + ex.getMessage());
        }
    }
    public void dispose() {
        try {
            dis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

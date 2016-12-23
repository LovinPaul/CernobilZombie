package Game.Network.Connection;

import Game.Mods.Game;
import Game.Network.ModProtocol.*;
import Main.SettingsManager;
import Main.Textures.Initilializer;
import Main.Textures.LoadTextures;

public class Connection implements Initilializer{
    
    protected TCPConnection tcp;
    protected UDPConnection udp;
    protected Protocol protocol;
    
    protected byte connID;
    protected byte gameMode;
    protected byte gameMap;
    private String ip;
    private int tcpPort;
    private int udpOutPort;
    private int udpInPort;
    
    protected boolean isConnected;
    protected boolean serverInitReceived;
    

    
    public Connection() {
        tcp = new TCPConnection();
        udp = new UDPConnection();
    }
    
    public void setIp(String ip){
        this.ip=ip;
    }
    public void setTCPPort(int port){
        tcpPort=port;
    }
    public void setUDPOutPort(int port){
        udpOutPort=port;
    }
    public void setUDPInPort(int port){
        udpInPort=port;
    }
    @Override
    public void setTexture(LoadTextures texture) {
        protocol.setTexture(texture);
        protocol.initGame();
    }
    public void setSettings(SettingsManager settings){
        protocol.setSettings(settings);
    }
    
    public Game getGame(){
        return protocol.getGame();
    }
    public byte getGameMode(){
        return gameMode;
    }
    public byte getGameMap(){
        return gameMap;
    }
    public byte getConnectionID(){
        return connID;
    }
    public String getErrorMessage(){
        return tcp.getErrorMessage();
    }
    public int getLatencyUDP(){
        return udp.getLatency();
    }
    public int getBytesInUDP(){
        return udp.getBytesIn();
    }
    public int getBytesOutUDP(){
        return udp.getBytesOut();
    }
    
    public boolean connectTCP(){
        if(tcp.newConnection(ip, tcpPort)){
            isConnected=true;
            return true;
        }else{
            return false;
        }
    }
    public boolean tryToConnect(){
        
//        String ip = "78.97.157.147";
//        int tcpPort = 4242;
//        int udpOutPort = 4243;
//        int udpInPort = 4244;
        
        
        if(tcp.newConnection(ip, tcpPort)){
            
            udp.setAddress(tcp.getAddress());
            udp.setPortIn(udpInPort);
            udp.setPortOut(udpOutPort);
            udp.startListen();
            isConnected=true;
            return true;
        }else{
            return false;
        }
    }
    
    public byte readParseTCP() {
        byte type = tcp.read();
        
        switch(type){
            case 4:
                //Assign ConnectionID
                connID = tcp.read();
                gameMode = tcp.read();
                gameMap = tcp.read();
                makeNewProtocol();
                serverInitReceived=true;
                
                return -1;
            default:
                return type;
        }
    }
    public byte readTCP() {
        return tcp.read();
    }
    public void writeTCP(byte data){
        tcp.write(data);
    }
    public void writeTCP(byte[] data){
        tcp.write(data);
    }
    public void flushTCP(){
        tcp.flush();
    }
    
    public byte[] readUDP(){
        return udp.read();
    }
    public void writeUDP(byte[] data){
        udp.write(data);
    }

    public boolean isConnected(){
        return isConnected;
    }
    public boolean isServertInitReceived(){
        return serverInitReceived;
    }
    public boolean isGameDisposed(){
        return protocol.getGame().isDisposed();
    }
    
    private void makeNewProtocol(){
        switch(gameMode){
            case 0:
                protocol = new TDM_Protocol(this);
                break;
        }
    }
    public void gameLoop(){
        protocol.gameLoop();
    }
    
    public void dispose(){
        tcp.dispose();
        udp.dispose();
    }


    
}

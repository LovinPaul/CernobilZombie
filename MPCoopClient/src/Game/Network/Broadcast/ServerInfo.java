package Game.Network.Broadcast;

public class ServerInfo {
    
        int ip1;
        int ip2;
        int ip3;
        int ip4;
        
        short tcpPort;
        short udpInPort;
        short udpOutPort;
        
        int gameMode;
        int gameMap;
        int nrOfPlayers;
        
        double lastUpdate;
        
        String serverName="New Server";
        String ip;
        String gameModeUI;
        String gameMapUI;

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
            updateUI();
        }
        
        public void update(int gameMode, int gameMap, int nrOfPlayers){
            this.gameMode = gameMode;
            this.gameMap = gameMap;
            this.nrOfPlayers = nrOfPlayers;
            lastUpdate=System.currentTimeMillis();
            updateUI();
        }
        
        public double getLastUpdateTime(){
            return lastUpdate;
        }
        public String getGameModeUI(){
            return gameModeUI;
        }
        public String getGameMapUI(){
            return gameMapUI;
        }
        public String getIP(){
            return ip;
        }
        public String getServerName(){
            return serverName;
        }
        public int getNrOfPlayers(){
            return nrOfPlayers;
        }
        
        private void updateUI(){
            ip = (ip1)+"."+(ip2)+"."+(ip3)+"."+(ip4);
            
            gameModeUI="Mode: ";
            gameMapUI="Map: ";
            
            switch(gameMode){
                case 0:
                    gameModeUI += "TDM";
                    break;
                default:
                    gameModeUI += "Uknown(" + gameMode+")";
                    break;
            }
            switch(gameMap){
                case 0:
                    gameMapUI += "Prologue";
                    break;
                case 1:
                    gameMapUI += "UrbanDistrict9";
                    break;
                case 2:
                    gameMapUI += "CR8";
                    break;
                default:
                    gameMapUI += "Uknown(" + gameMap+")";
                    break;
            }
        }
}

package Game.UI.Components;

import Game.Network.Broadcast.ServerInfo;
import Input.Mouse;
import Main.Textures.LoadTextures;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ServerList extends Component{
    
    
    protected ArrayList<ListElement> list;

    private BufferedImage normalText;
    private BufferedImage hoverText;
    
    private BufferedImage normalButton;
    private BufferedImage hoverButton;
    
    Color elementBackground;
    
    Mouse mouse;
    
    
    
    public ServerList(Mouse mouse, LoadTextures textures) {
        super(textures);
        this.mouse=mouse;
        normalText = textures.normalText;
        hoverText = textures.hoverText;
        normalButton = textures.normalButton;
        hoverButton = textures.hoverButton;
        
        elementBackground = new Color(100, 100, 100, 200);
        
        list=new ArrayList<>();
        centerText=false;
    }

    
    public void updateServerList(ArrayList<ServerInfo> servers){
        list.clear();
        for(ServerInfo serverInfo : servers){
            list.add(new ListElement(serverInfo));
        }
    }
    
    public class ListElement{
        
        ServerInfo server;
        
        boolean isSelected;
        private Rectangle joinButton;
        private boolean joinServer;
        
        public ListElement(ServerInfo server) {
            this.server=server;
        }
        
        public String getIP(){
            return server.getIP();
        }
        
        public boolean joinServer(){
            boolean returnJoinServer = joinServer;
            joinServer=false;
            return returnJoinServer;
        }
        
        public void drawElement(Graphics g,byte index, int x, int y){
            Graphics2D gText = (Graphics2D) g.create();
            
            joinButton = new Rectangle(getX()+x+810, getY()+y, 90, height);
            
            gText.setColor(elementBackground);
            gText.fillRect(getX()+x-2, getY()+y-2, width, height);//36
            gText.fillRect(joinButton.x, joinButton.y, joinButton.width, joinButton.height);//36
            
            if((mouse.mouseY>y+getY() && mouse.mouseY<y+36+getY()) || isSelected){
                gText.setColor(Color.LIGHT_GRAY);
                gText.fillRect(getX()+x-2, getY()+y-2, width, height);//36
            }
            
            if(joinButton.contains(mouse.mouseX, mouse.mouseY)){
                if(mouse.b1Released){
                    joinServer=true;
                }
                gText.setColor(Color.BLACK);
                gText.fillRect(joinButton.x, joinButton.y, joinButton.width, joinButton.height);//36
            }

            drawText(gText, 820, y+10, normalText, "Join");

            drawText(gText, 0, y, normalText, index + " " +server.getServerName());
            drawText(gText, 0, y+20, normalText, server.getGameModeUI());
            drawText(gText, 300, y+20, normalText, server.getGameMapUI());
            drawText(gText, 700, y+20, normalText, "Pl: "+server.getNrOfPlayers());
        }
        
        
    }
}

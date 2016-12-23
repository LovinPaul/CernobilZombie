package Game.UI.Components;

import Input.Mouse;
import Main.Textures.LoadTextures;
import java.awt.Graphics;
import javax.sound.midi.Soundbank;

public class ServerBrowser extends ServerList{
    
    private int nrOfDrawnServers;
    private int carPosition;
    
    private int frameHeight;
    private int serverBrowserHeight;
    
    private byte joinIndex;
    private ListElement serverToJoin;
    
    
    public ServerBrowser(Mouse mouse, LoadTextures textures) {
        super(mouse, textures);
        width=900;
        height=40;
        
        nrOfDrawnServers = 0;
        carPosition=0;
    }
    
    public boolean joinServer(){
        return serverToJoin!=null;
    }
    
    public void scrollDown(){
        if(carPosition+nrOfDrawnServers<list.size()){
            carPosition++;
        }
    }
    public void scrollUp(){
        if(carPosition>0){
            carPosition--;
        }
    }
    
    public void nrOfDrawnServers(int height){
        if(frameHeight!=height){
            frameHeight=height;
            //y=150;
            //50*4
            
            serverBrowserHeight = frameHeight-y-50*5;
            nrOfDrawnServers = serverBrowserHeight/45;
            
            if(list.size()<nrOfDrawnServers){
                nrOfDrawnServers=list.size();
            }            
            if(nrOfDrawnServers+carPosition>list.size() || carPosition<0){
                if(list.size()>0){
                    carPosition=list.size()-nrOfDrawnServers;
                }else{
                    carPosition=0;
                }
                
            }

            
        }
        
        
    }
    
    public ListElement getServerToJoin(){
        ListElement returnServerToJoin=serverToJoin;
        serverToJoin=null;
        return returnServerToJoin;
    }
    
    public void clear(){
        list.clear();
        nrOfDrawnServers = 0;
        carPosition=0;
    }
    
    public void refresh(){
        clear();
        frameHeight=0;
        serverBrowserHeight=0;
    }
    
    public void draw(Graphics g){
        
        if(list.isEmpty()){return;}
        
        if(mouse.mouseY>y && mouse.mouseY<y+serverBrowserHeight){
            if(mouse.mouseWhell>0){
                scrollDown();
                mouse.mouseWhell=0;
            }else if(mouse.mouseWhell<0){
                scrollUp();
                mouse.mouseWhell=0;
            }
        }

        for(int i=carPosition, j=0; i<carPosition+nrOfDrawnServers; i++, j++){
            list.get(i).drawElement(g, (byte) (i+1), 0, (45*j));//
            if(list.get(i).joinServer()){
                serverToJoin = list.get(i);
            }
        }
        
    }
    
    
}

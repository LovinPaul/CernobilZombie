package Main;

import java.net.*;
import java.io.*;

public class SettingsManager {
    
    URL file;
    
    public boolean showResourceMonitor;
    
    public boolean fullscreen;
    public boolean maximized=false;
    public int width = 1366;
    public int height = 600;
    
    public String ip="78.97.157.147";
    public int tcpPort=4242;
    public int udpOutPort=4243;
    public int udpInPort=4244;
    
    public int goUp;//='W';
    public int goDown;//='S';
    public int goLeft;//='A';
    public int goRight;//='D';
    public int reloadKey;//=82;
//    public int statsKey;//=80;
    public int chatAreaKey;//=72;
    public int chatInputKey;//=80;
    
    public float mouseSensitivity=0.5f;
    
    
    public String name="Javatar+";
    
    public SettingsManager() {
        file = this.getClass().getResource("/Data/config");
    }
    
    public void read() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(file.openStream()))){
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                parseLine(inputLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void parseLine(String line){
//        System.out.println(line);
        String[] lineSplit = line.split(" = ");
        switch(lineSplit[0]){
            //frame
            case "maximized":
                maximized = lineSplit[1].equals("true");
                break;
            case "width":
                width=Integer.parseInt(lineSplit[1]);
                break;
            case "height":
                height=Integer.parseInt(lineSplit[1]);
                break;
            //network
            case "ip":
                ip=lineSplit[1];
                break;
            case "tcpPort":
                tcpPort=Integer.parseInt(lineSplit[1]);
                break;
            case "udpOutPort":
                udpOutPort=Integer.parseInt(lineSplit[1]);
                break;
            case "udpInPort":
                udpInPort=Integer.parseInt(lineSplit[1]);
                break;
            //controls
            case "goUp":
                goUp=Integer.parseInt(lineSplit[1]);
                break;
            case "goDown":
                goDown=Integer.parseInt(lineSplit[1]);
                break;
            case "goLeft":
                goLeft=Integer.parseInt(lineSplit[1]);
                break;
            case "goRight":
                goRight=Integer.parseInt(lineSplit[1]);
                break;
            case "reloadKey":
                reloadKey=Integer.parseInt(lineSplit[1]);
                break;
            case "chatAreaKey":
                chatAreaKey=Integer.parseInt(lineSplit[1]);
                break;
            case "chatInputKey":
                chatInputKey=Integer.parseInt(lineSplit[1]);
                break;
            case "mouseSensitivity":
                mouseSensitivity=Float.parseFloat(lineSplit[1]);
                break;
            //player
            case "name":
                name=lineSplit[1];
                break;
            default:
                System.out.println("Invalid Config Line : " + line);
                break;
        }
    }
    
    public void write(){
        try {
            File fil = new File(file.toURI());
            
            try (FileOutputStream fop = new FileOutputStream(fil)) {
                String content;
                content = "maximized = " + maximized + "\n"
                         + "width = " + width + "\n"
                         + "height = " + height + "\n"
                         + "ip = " + ip + "\n"
                         + "tcpPort = " + tcpPort + "\n"
                         + "udpOutPort = " + udpOutPort + "\n"
                         + "udpInPort = " + udpInPort + "\n"
                         + "goUp = " + goUp + "\n"
                         + "goDown = " + goDown + "\n"
                         + "goLeft = " + goLeft + "\n"
                         + "goRight = " + goRight + "\n"
                         + "reloadKey = " + reloadKey + "\n"
                         + "chatAreaKey = " + chatAreaKey + "\n"
                         + "chatInputKey = " + chatInputKey + "\n"
                         + "mouseSensitivity = " + mouseSensitivity + "\n"
                         + "name = " + name;
                
			fop.write(content.getBytes());
			fop.flush();
			fop.close();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        
    }
    
}

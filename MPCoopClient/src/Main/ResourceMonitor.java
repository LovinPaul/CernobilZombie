package Main;

import Game.Network.Connection.Connection;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ResourceMonitor {
    
    Connection conn;
    float memoryUsed;
    float memoryFree;
    float memoryTotal;
    float memoryMax;
    
    float memoryUsedMax;
    float memoryFreeMax;
    float memoryTotalMax;
    
    float memoryUsedMin;
    float memoryFreeMin;
    float memoryTotalMin;
    
    float memoryUsedMed;
    float memoryFreeMed;
    float memoryTotalMed;
    
    ArrayList<Float> memoryFreeMedList;
    ArrayList<Float> memoryUsedMedList;
    
    float networkLatency;
    float networkBytesIn;
    float networkBytesOut;
    
    float networkLatencyMax;
    float networkBytesInMax;
    float networkBytesOutMax;
    
    float networkLatencyMin;
    float networkBytesInMin;
    float networkBytesOutMin;
    
    float networkLatencyMed;
    float networkBytesInMed;
    float networkBytesOutMed;
    
    
    ArrayList<Float> networkLatencyList;
    ArrayList<Float> networkBytesInList;
    
    int mb = 1024*1024;

    public ResourceMonitor() {
        memoryFreeMedList = new ArrayList<>();
        memoryUsedMedList = new ArrayList<>();
        networkLatencyList = new ArrayList<>();
        networkBytesInList = new ArrayList<>();
    }
    
    
    
    public void startMonitoring(){
        
        Thread t = new Thread(new Runnable(){
            private final Runtime runtime = Runtime.getRuntime();
            
            @Override
            public void run(){
                memoryUsedMin = ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb);
                memoryFreeMin = (Runtime.getRuntime().freeMemory() / mb);
                memoryTotalMin = (Runtime.getRuntime().totalMemory() / mb);
                while(true){
                    
                    memoryUsed = ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb);
                    memoryFree = (Runtime.getRuntime().freeMemory() / mb);
                    memoryTotal = (Runtime.getRuntime().totalMemory() / mb);
                    memoryMax = (Runtime.getRuntime().maxMemory() / mb);
                    
                    if(memoryUsed>memoryUsedMax){memoryUsedMax=memoryUsed;}
                    if(memoryFree>memoryFreeMax){memoryFreeMax=memoryFree;}
                    if(memoryTotal>memoryTotalMax){memoryTotalMax=memoryTotal;}
                    
                    if(memoryUsed<memoryUsedMin){memoryUsedMin=memoryUsed;}
                    if(memoryFree<memoryFreeMin){memoryFreeMin=memoryFree;}
                    if(memoryTotal<memoryTotalMin){memoryTotalMin=memoryTotal;}
                    
                    
                    
                    if(memoryUsedMedList.size()>=100){memoryUsedMedList.remove(0);}
                    memoryUsedMedList.add(memoryUsed);
                    float totalUsedMem = 0;
                    for(Float flt : memoryUsedMedList){
                        totalUsedMem+=flt;
                    }
                    memoryUsedMed = totalUsedMem/memoryUsedMedList.size();
                    
                    
                    if(memoryFreeMedList.size()>=100){memoryFreeMedList.remove(0);}
                    memoryFreeMedList.add(memoryFree);
                    float totalFreeMem = 0;
                    for(Float flt : memoryFreeMedList){
                        totalFreeMem+=flt;
                    }
                    memoryFreeMed = totalFreeMem/memoryFreeMedList.size();
                    
                    //Network
                    if(conn!=null && conn.isConnected()){

                        if(conn.getLatencyUDP() >= 0){
                            networkLatency=conn.getLatencyUDP();
                        }
                        networkBytesIn = conn.getBytesInUDP();
                        networkBytesOut = conn.getBytesOutUDP();
                        
                        if(networkLatency>networkLatencyMax){networkLatencyMax=networkLatency;}
                        if(networkBytesIn>networkBytesInMax){networkBytesInMax=networkBytesIn;}
                        if(networkBytesOut>networkBytesOutMax){networkBytesOutMax=networkBytesOut;}

                        if(networkLatency<networkLatencyMin){networkLatencyMin=networkLatency;}
                        if(networkBytesIn<networkBytesInMin){networkBytesInMin=networkBytesIn;}
                        if(networkBytesOut<networkBytesOutMin){networkBytesOutMin=networkBytesOut;}
                        
                        if(networkLatencyList.size()>=100){networkLatencyList.remove(0);}
                        networkLatencyList.add(networkLatency);
                        float totalNetworkLatency = 0;
                        for(Float flt : networkLatencyList){
                            totalNetworkLatency+=flt;
                        }
                        networkLatencyMed = totalNetworkLatency/networkLatencyList.size();
                        
                        if(networkBytesInList.size()>=100){networkBytesInList.remove(0);}
                        networkBytesInList.add(networkBytesIn);
                        float totalNetworkBytesIn = 0;
                        for(Float flt : networkBytesInList){
                            totalNetworkBytesIn+=flt;
                        }
                        networkBytesInMed = totalNetworkBytesIn/networkBytesInList.size();
                        
                    }
                    
                    
                    
                    
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
        });
        t.start();
    }
    
    public void monitorNetwork(Connection conn){
        this.conn=conn;
    }
    
    public void disposeNetworkMonitor(){
        conn=null;
    }
    
    public void drawResource(Graphics g, int x, int y){
        Graphics g2 = g.create();
        Graphics gRes = g.create();
        
        gRes.drawString("Memory", 50, 90);
        g2.setColor(Color.yellow);
        g2.fillRect(50, 95, (int) memoryUsed, 15);
        g2.fillRect(50, 115, (int) memoryFree, 15);
        g2.fillRect(50, 135, (int) memoryTotal, 15);
        g2.setColor(Color.red);
        g2.fillRect(50 + (int)memoryUsedMed, 95, 5, 15);
        g2.fillRect(50 + (int)memoryFreeMed, 115, 5, 15);
        
        
        gRes.drawString("Used: " + memoryUsed + " MB (max: " + memoryUsedMax + "MB, min: " + memoryUsedMin + "MB)", 50, 110);
        gRes.drawString("Free: " + memoryFree + " MB (max: " + memoryFreeMax + "MB, min: " + memoryFreeMin + "MB)", 50, 130);
        gRes.drawString("Total: " + memoryTotal + " MB (max: " + memoryTotalMax + "MB, min: " + memoryTotalMin + "MB)", 50, 150);
        gRes.drawString("Max: " + memoryMax + " MB", 50, 170);
        

        
        if(conn!=null && conn.isConnected()){
            g2.setColor(Color.yellow);
            g2.fillRect(50, 205, (int) networkLatency, 15);
            g2.fillRect(50, 225, (int) networkBytesIn, 15);
            g2.fillRect(50, 245, (int) networkBytesOut, 15);
            g2.setColor(Color.red);
            g2.fillRect(50 + (int)networkLatencyMed, 205, 5, 15);
            g2.fillRect(50 + (int)networkBytesInMed, 225, 5, 15);
            
            
            gRes.drawString("Network", 50, 200);
            gRes.drawString("Latency : " + networkLatency + " ms (max: " + networkLatencyMax + "ms, min: " + networkLatencyMin + "ms)", 50, 220);
            gRes.drawString("BytesIn : " + (int) networkBytesIn + " B (max: " + networkBytesInMax + "B, min: " + networkBytesInMin + "B)", 50, 240);
            gRes.drawString("BytesOut : " + (int) networkBytesOut+ " B", 50, 260);
        }

        
    }
    
    
}

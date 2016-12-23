package Game.Maps;

import Game.Actors.Actor;
import Game.Maps.Environment.Environment;
import Main.Main;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Map {
    
    //layer1
    private BufferedImage layer0;
    private BufferedImage layer1;
    
    //Actors layer
    
    //layer2
    private BufferedImage layer2;
    private BufferedImage layer3;
    
    public Rectangle finishArea;
    public ArrayList<Rectangle> rectangles;
    public ArrayList<Point> spawnPoints;
    public ArrayList<Environment> environment;
    
    protected byte ID;
    
    public void addSpawnPoint(int x, int y){
        if(spawnPoints==null){spawnPoints = new ArrayList<>();}
        spawnPoints.add(new Point(x, y));
    }
    public void addRectangle(Rectangle rect){
        if(rectangles==null){rectangles = new  ArrayList<>();}
        rectangles.add(rect);
    }
    public void removeRectangle(Rectangle rect){
        rectangles.remove(rect);
    }
    
    public void addEnvironment(Environment env){
        if(environment==null){environment = new  ArrayList<>();}
        environment.add(env);
        addRectangle(env.getBounds());
    }
    public void removeEnvironment(Environment env){
        removeRectangle(env.getBounds());
        environment.remove(env);
    }
    
    public ArrayList<Point> getSpawnPoints(){
        return spawnPoints;
    }
//    public ArrayList<Environment> getEnvironment(){
//        return environment;
//    }
    public Iterator<Environment> getEnvironmentIterator(){
        return environment.iterator();
    }
    public ArrayList<Environment> getEnvironment(){
        return environment;
    }
    public int getEnvironmentSize(){
        return environment.size();
    }
    
    public void setID(byte id){
        ID= id;
    }
    protected void setLayer0(BufferedImage layer0){
        this.layer0 = layer0;
    }
    protected void setLayer1(BufferedImage layer1){
        this.layer1 = layer1;
    }
    protected void setLayer2(BufferedImage layer2){
        this.layer2 = layer2;
    }
    protected void setLayer3(BufferedImage layer3){
        this.layer3 = layer3;
    }
    
    public byte getID(){
        return ID;
    }
    public BufferedImage getLayer0(){
        return layer0;
    }
    public BufferedImage getLayer1(){
        return layer1;
    }
    public BufferedImage getLayer2(){
        return layer2;
    }
    public BufferedImage getLayer3(){
        return layer3;
    }
    

    
    public boolean isInFinishArea(Actor actor){
        return finishArea.contains(actor.getX(), actor.getY());
    }
    
    public void drawLayer1(Graphics g, float x, float y){
        if(layer0!=null){
            g.drawImage(layer0.getSubimage((int)x, (int)y, Main.getWidth(), Main.getHeight()), 0, 0, null);
        }
        if(layer1!=null){
            g.drawImage(layer1.getSubimage((int)x, (int)y, Main.getWidth(), Main.getHeight()), 0, 0, null);
        }
        
        Rectangle frame = new Rectangle((int)x, (int)y, Main.getWidth(), Main.getHeight());
        for(Environment env : environment){
            if(frame.contains(env.getX(), env.getY())){
                env.draw(g, env.getX()-x, env.getY()-y);
//                g.drawRect((int)(env.getBounds().getX()-x),
//                            (int)(env.getBounds().getY()-y),
//                            (int)(env.getBounds().getWidth()),//-Camera.getX()
//                            (int)(env.getBounds().getHeight()));//-Camera.getY() 
            }
        }
        
        if(rectangles!=null){
            for(Rectangle rect : rectangles){
                g.drawRect((int)(rect.getX()-x),
                            (int)(rect.getY()-y),
                            (int)(rect.getWidth()),//-Camera.getX()
                            (int)(rect.getHeight()));//-Camera.getY() 
            }

        }
    }
    
    public void drawLayer2(Graphics g, float x, float y){
        if(layer2!=null){
            g.drawImage(layer2.getSubimage((int)x, (int)y, Main.getWidth(), Main.getHeight()), 0, 0, null);
        }
        if(layer3!=null){
            g.drawImage(layer3.getSubimage((int)x, (int)y, Main.getWidth(), Main.getHeight()), 0, 0, null);
        }
    }
}

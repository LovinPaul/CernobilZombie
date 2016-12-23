package Game.Maps;

import Game.Actors.Actor;
import Game.Maps.Environment.Environment;
import Game.Maps.Environment.Traps.Trap;
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
    protected ArrayList<Environment> environment;
    
    protected byte ID;
    
    public void init(){
    }
    
    
    public void addRectangle(Rectangle rect){
        if(rectangles==null){rectangles = new  ArrayList<>();}
        rectangles.add(rect);
    }
    public void removeRectangle(Rectangle rect){
        rectangles.remove(rect);
    }
    public boolean containsRectangle(Rectangle rect){
        return rectangles.contains(rect);
    }
    public void addEnvironment(Environment env){
        if(environment==null){environment = new  ArrayList<>();}
        environment.add(env);
//        addRectangle(env.getBounds());
    }
    public void removeEnvironment(Environment env){
        removeRectangle(env.getBounds());
        environment.remove(env);
    }
    
    public void addSpawnPoint(int x, int y){
        if(spawnPoints==null){spawnPoints = new ArrayList<>();}
        spawnPoints.add(new Point(x, y));
    }
    public ArrayList<Point> getSpawnPoints(){
        return spawnPoints;
    }
    public ArrayList<Environment> getEnvironment(){
        return environment;
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
    
    public void updateEnvironment(Rectangle frame, byte id, int crateState){
//        System.out.println("received " + id + " - " + crateState);
        for(Environment env : environment){
            if(frame.intersects(env.getBounds()) && env.getID()==id){//&& env.getType()==type 
                env.setState(crateState);
                
                env.setIsUpdated(true);
//                System.out.println("UPDATED " + id + " - " + crateState);
                return;
            }
        }
//        System.out.println("Environment not found!");
    }
    public void removeEnvironment(Rectangle frame){
        Iterator<Environment> environmentIterator = environment.iterator();
        while(environmentIterator.hasNext()){
            Environment env = environmentIterator.next();
            if(frame.contains(env.getBounds()) && !env.isUpdated()){
                removeRectangle(env.getBounds());
                environmentIterator.remove();
            }
        }
    }
    public void setEnvironmentIsUpdated(boolean isUpdated){
        for(Environment env : environment){
            env.setIsUpdated(isUpdated);
        }
    }
    
    public boolean isInFinishArea(Actor actor){
        return finishArea.contains(actor.getX(), actor.getY());
    }
    public boolean isInVisualRange(Actor actor1, Actor actor2){
//        if(actor instanceof Hitman1){
//            if((Math.random()*100) < ((Hitman1)actor).getInvisibilityPower()){
//                return false;
//            }
//        }
        if(rectangles!=null){
            for(Rectangle rect : rectangles){
                if(rect.intersectsLine(actor1.getX(), actor1.getY(), actor2.getX(), actor2.getY())){
                    return false;
                }
            }
        }
        if(environment!=null){
            for(Environment env : environment){
                if(env.isUpdated() && env.getBounds().intersectsLine(actor1.getX(), actor1.getY(), actor2.getX(), actor2.getY())){
                    return false;
                }
            }
        }

        return true;
    }
    public boolean isInVisualRange(float x1, float y1, float x2, float y2){
        if(rectangles!=null){
            for(Rectangle rect : rectangles){
                if(rect.intersectsLine(x1, y1, x2, y2)){
                    return false;
                }
            }
        }
        if(environment!=null){
            for(Environment env : environment){
                if(env.isUpdated() && env.getBounds().intersectsLine(x1, y1, x2, y2)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isInVisualRange(Actor actor1, float x2, float y2){
        if(rectangles!=null){
            for(Rectangle rect : rectangles){
                if(rect.intersectsLine(actor1.getX(), actor1.getY(), x2, y2)){
                    return false;
                }
            }
        }
        if(environment!=null){
            for(Environment env : environment){
                if(env.isUpdated() && env.getBounds().intersectsLine(actor1.getX(), actor1.getY(), x2, y2)){
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public void drawLayer1(Graphics g, float x, float y){
        if(layer0==null){System.out.println("Map Layer0 == null");return;}
        int x1=0;
        int y1=0;
        if(x<0){x1=(int) Math.abs(x);x=0;}
        if(y<0){y1=(int) Math.abs(y);y=0;}
        
        int width1=Main.getWidth();
        int height1=Main.getHeight();        
        if(x>layer0.getWidth()-Main.getWidth()){width1=(int) (layer0.getWidth()-x);}
        if(y>layer0.getHeight()-Main.getHeight()){height1=(int) (layer0.getHeight()-y);}

        if(width1<=0 || height1<=0){return;}
        
        
        g.drawImage(layer0.getSubimage((int)x, (int)y, width1, height1), x1, y1, null);
//        g.drawImage(layer1.getSubimage((int)x, (int)y, width1, height1), x1, y1, null);


//        if(rectangles!=null){
//            for(Rectangle rect : rectangles){
//                g.drawRect((int)(rect.getX()-x),
//                            (int)(rect.getY()-y),
//                            (int)(rect.getWidth()),//-Camera.getX()
//                            (int)(rect.getHeight()));//-Camera.getY() 
//            }
//
//        }
    }
    
    public void drawLayer2(Graphics g, float x, float y){
//        if(layer2==null){return;}
//        int x1=0;
//        int y1=0;
//        if(x<0){x1=(int) Math.abs(x);x=0;}
//        if(y<0){y1=(int) Math.abs(y);y=0;}
//        
//        int width1=Main.getWidth();
//        int height1=Main.getHeight();        
//        if(x>layer2.getWidth()-Main.getWidth()){width1=(int) (layer2.getWidth()-x);}
//        if(y>layer2.getHeight()-Main.getHeight()){height1=(int) (layer2.getHeight()-y);}
//        
//        if(width1<=0 || height1<=0){return;}
//        
//        
//        g.drawImage(layer2.getSubimage((int)x, (int)y, width1, height1), x1, y1, null);
//        g.drawImage(layer3.getSubimage((int)x, (int)y, width1, height1), x1, y1, null);
        
    }
    
    public void drawEnvironment(Graphics g, float x, float y){
        if(environment!=null){
            for(Environment env : environment){
                if(env.isUpdated()){
                    env.draw(g, env.getX()-x, env.getY()-y);
    //                g.drawRect((int)(env.getBounds().getX()-x),
    //                            (int)(env.getBounds().getY()-y),
    //                            (int)(env.getBounds().getWidth()),//-Camera.getX()
    //                            (int)(env.getBounds().getHeight()));//-Camera.getY() 
                }
    //            env.setIsUpdated(false);
            }
        }
        
    }
}

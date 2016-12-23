package Game.Network.ModProtocol.Stats;

import Game.Actors.Actor;
import java.util.ArrayList;
import java.util.Iterator;

public class ActorStats {
    
    ArrayList<Stats> stats;

    public ActorStats() {
        stats = new ArrayList<>();
    }
    
    public void updateStats(byte connID, byte actorID, byte kills, byte deaths, byte accuracy){
        for(Stats stat : stats){
            if(stat.connID==connID){
                stat.actorID=actorID;
                stat.kills=kills;
                stat.deaths=deaths;
                stat.accuracy=accuracy;
                stat.updated=true;
//                System.out.println("Stat Updated : connID " + connID + ", actorID " + actorID + ", kills " + kills + ", deaths " + deaths);
                return;
            }
        }
        
        stats.add(new Stats(connID, actorID, kills, deaths, accuracy));
//        System.out.println("Stat Created : connID " + connID + ", actorID " + actorID + ", kills " + kills + ", deaths " + deaths);
    }
    
    public void finalUpdateStats(Iterator<Actor> actorIterator){
        Iterator<Stats> statsIterator = stats.iterator();
        while(statsIterator.hasNext()){
            Stats stat = statsIterator.next();
            if(stat.updated){
                stat.updated=false;
                Iterator<Actor> newActorIterator = actorIterator;
                while(newActorIterator.hasNext()){
                    Actor actor = newActorIterator.next();
                    if(actor.getID()==stat.actorID){
                        stat.name = actor.getName();
                        stat.team = actor.getTeam();
                    }
                }
            }else{
//                System.out.println("Stat Removed : connID " + stat.connID + ", actorID " + stat.actorID + ", kills " + stat.kills + ", deaths " + stat.deaths);
                statsIterator.remove();
            }            
        }
    }
    public void finalUpdateStats(ArrayList<Actor> actors){
        Iterator<Stats> statsIterator = stats.iterator();
        while(statsIterator.hasNext()){
            Stats stat = statsIterator.next();
            if(stat.updated){
                stat.updated=false;
                for(Actor actor : actors){
                    if(actor.getID()==stat.actorID){
                        stat.name = actor.getName();
                        stat.team = actor.getTeam();
                    }
                }
            }else{
//                System.out.println("Stat Removed : connID " + stat.connID + ", actorID " + stat.actorID + ", kills " + stat.kills + ", deaths " + stat.deaths);
                statsIterator.remove();
            }            
        }
    }
    public Iterator<Stats> getStats(){
        return stats.iterator();
    }
    
    public class Stats{
        private byte connID;
        private byte actorID;
        private byte kills;
        private byte deaths;
        private byte accuracy;
        private String name="NULL";
        private byte team;

        private boolean updated;
        
        public Stats(byte connID, byte actorID, byte kills, byte deaths, byte accuracy) {
            this.connID = connID;
            this.kills = kills;
            this.deaths = deaths;
            this.accuracy = accuracy;
            updated=true;
        }
        
        public String getName(){
            return name;
        }
        public byte getTeam(){
            return team;
        }
        public byte getKills(){
            return kills;
        }
        public byte getDeaths(){
            return deaths;
        }
        public byte getAccuracy(){
            return accuracy;
        }
        
    }
}
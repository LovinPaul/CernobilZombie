package Game;

import Game.Actors.*;
import Game.Actors.AI.TDM_AI;
import Game.Actors.Items.*;
import Game.Actors.Weapons.*;
import Game.Maps.*;
import Game.Network.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MPTDM extends Game{
    
    
    ArrayList<Team> teams;
    TDM_AI ai;
    
    private boolean localGame=true;
    
    public MPTDM() {
        init();
    }
    
    private void init(){

        gameMode=0;
        
        ai = new TDM_AI(this);
        setMap(new In4());
        camera.setFreeCam(true);
        teams = new ArrayList<>();
        teams.add(new Team((byte)0));
        teams.add(new Team((byte)1));
//        teams.add(new Team((byte)2));
        Broadcast br = new Broadcast(this, localGame);
        br.startBroadcast();
    }
    
    public Team createNewTeam(){
        byte returnInt=0;
        
        for(int i=0; i<teams.size(); i++){
            if(returnInt==teams.get(i).team){
                i=0;
                returnInt++;
            }
        }
//        teams.add(new Team(returnInt));

        return new Team(returnInt);
    }
    
    public void addToAvailableTeam(Connection player){
        
        Team availableTeam=null;
        int teamSize=0;
        for(Team team :teams){
            if(availableTeam==null){
                availableTeam=team;
                teamSize=availableTeam.nrOfPlayers;
            }else{
                if(teamSize>team.nrOfPlayers){
                    availableTeam=team;
                    teamSize=team.nrOfPlayers;
                }
            }
        }
        if(availableTeam==null){
            availableTeam = createNewTeam();
            availableTeam.addPlayer(player);
            teams.add(availableTeam);
        }else{
            availableTeam.addPlayer(player);
        }
    }
    public void addToAvailableTeam(Actor player){
        
        Team availableTeam=null;
        int teamSize=0;
        for(Team team :teams){
            if(availableTeam==null){
                availableTeam=team;
                teamSize=availableTeam.nrOfPlayers;
            }else{
                if(teamSize>team.nrOfPlayers){
                    availableTeam=team;
                    teamSize=team.nrOfPlayers;
                }
            }
        }
        if(availableTeam==null){
            availableTeam = createNewTeam();
            availableTeam.addPlayer(player);
            teams.add(availableTeam);
        }else{
            availableTeam.addPlayer(player);
        }
    }
    
    private void addBot(){
        if(nrOfZombies + nrOfHitmans + nrOfRobots<18){
            Point spawn = getMap().getSpawnPoints().get((int)(Math.random()*getMap().getSpawnPoints().size()));
            Weapon weapon;
            Actor newActor = null;
            
            switch((int)(Math.random()*3)){
                case 0:
                    newActor = new Zombie1(assignID(Game.ActorClasses.Zombie), spawn.x, spawn.y, 0);
                    break;
//                case 2:
                case 1 :
                    newActor = new Hitman1(assignID(Game.ActorClasses.Hitman), spawn.x, spawn.y, 0);
                    break;
                case 2 :
                    newActor = new Robot1(assignID(Game.ActorClasses.Robot), spawn.x, spawn.y, 0);
                    break;
                default :
                    newActor = new Robot1(assignID(Game.ActorClasses.Robot), spawn.x, spawn.y, 0);
                    break;
            }
            
//            switch((int)(Math.random()*4)){
//                case 1:
//                    weapon = new M134();
//                    break;
//                case 2:
//                    weapon = new Machine();
//                    break;
//                case 3:
                    weapon = new NineMM();
//                    weapon.firePower=1;
//                    break;
////                case 4:
//                default :
//                    weapon = new Machine();
//                    break;
//            }
            int team0=0;
            int team1=0;
            for(Actor actor : actors){
                if(actor.getTeam()==0){
                    team0++;
                }else{
                    team1++;
                }
            }

            if(team0<team1){
                newActor.setTeam((byte)0);
            }else{
                newActor.setTeam((byte)1);
            }
//            addToAvailableTeam(newActor);
//            actor.setTeam((byte) Math.round(Math.random()*3));
            newActor.setFollowAngle(true);
            newActor.setName("Bot");
//            weapon.firePower=0.1f;//Temporary
            weapon.fireWithCoolDown=true; //temporary
            newActor.setWeapon(weapon);
            weapon.setOwner(newActor);
            
            actors.add(newActor);
        }
    }
    
    @Override
    public void getUserInput(){
        super.getUserInput();
    }
    @Override
    public void updateGameMechanics() {
        super.updateGameMechanics();
        
        TDM_Connection.verifyConnection();
        TDM_Connection.readAllTCP();
        TDM_Connection.readAllUDP();
        
        
        if(camera!=null){camera.updateCoordonate();}
        
        if(Item.items.size()<3){
            Point spawn = getMap().spawnPoints.get((int)(Math.random()*getMap().spawnPoints.size()));
            Item.items.add(new NineMM_Clip(spawn.x, spawn.y));
            
            spawn = getMap().spawnPoints.get((int)(Math.random()*getMap().spawnPoints.size()));
            Item.items.add(new Machine_Clip(spawn.x, spawn.y));
            
            spawn = getMap().spawnPoints.get((int)(Math.random()*getMap().spawnPoints.size()));
            Item.items.add(new HP_Crate(spawn.x, spawn.y));
        }
        
        
        addBot();
        synchronized(actors){
            nrOfZombies=0;
            nrOfHitmans=0;
            nrOfRobots=0;
            
            Iterator<Actor> actorsIterator = actors.iterator();
            while ( actorsIterator.hasNext() ) {
                Actor actor = actorsIterator.next();

                if(actor instanceof Zombie1){
                    nrOfZombies++;
                }else if(actor instanceof Hitman1){
                    nrOfHitmans++;
                }else if(actor instanceof Robot1){
                    nrOfRobots++;
                }

                if(actor.isAlive()){
                    if(actor.getWeapon()!=null && actor.getWeapon().fireWithCoolDown){
                        actor.getWeapon().coolDown();
                        actor.getWeapon().stopFire();
                    }
                    
                    ai.control(actor);
                    actor.updateActorMechanics();
                    handleColision(actor);
                    bulletColision(actor);
                    trapColision(actor);
                    
                }else{
                    actorsIterator.remove();
                }
            }
        }
        
        TDM_Connection.writeAllUDP();
        TDM_Connection.writeAllTCP();
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = g.create();
        
        Iterator<Actor> actorIterator = actors.iterator();
        while(actorIterator.hasNext()){
            Actor actor = actorIterator.next();
            g2.setColor(Color.BLACK);
            g2.fillRect((int)(actor.getTargetX()-camera.getX()), (int)(actor.getTargetY()-camera.getY()), 5, 5);
            if(actor.isHit()){
                g2.setColor(Color.red);
                g2.fillRect((int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-25), 100/2, 5);
                g2.setColor(Color.green);
                g2.fillRect((int)(actor.getX()-camera.getX()), (int)(actor.getY()-camera.getY()-25), (int)actor.getHitPoints()/2, 5);                
            }            
        }
        
//        g.drawString(Connection.nrOfConnections()+"", 50, 50);
        
        if(!Connection.connections.isEmpty()){
            Connection.connections.get(0).draw(g);
        }
        
        
//        g2.setColor(Color.yellow);
//        g2.fillRect(300, 90, conn.getLatencyUDP(), 20);
//        g.drawString("Latency : " + conn.getLatencyUDP(), 300, 110);
//        g2.fillRect(300, 110, conn.getBytesInUDP(), 20);
//        g.drawString("BytesIn : " + conn.getBytesInUDP(), 300, 130);
        
//        g.drawString("BytesOut : " + bytesOut, 300, 150);
    }
    
    
    public class Team{
        
        private byte team;
        private int nrOfPlayers;
        
        public Team(byte team) {
            this.team = team;
        }
        
        private void addPlayer(Connection player){
            nrOfPlayers++;
            player.setTeam(team);
            player.setTeam(this);
        }
        private void addPlayer(Actor player){
            nrOfPlayers++;
            player.setTeam(team);
//            player.setTeam(this);
        }
        public void removeFromTeam(){
            nrOfPlayers--;
        }
        
    }
    
}

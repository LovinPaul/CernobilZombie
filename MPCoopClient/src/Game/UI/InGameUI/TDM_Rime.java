package Game.UI.InGameUI;

import Game.Actors.Actor;
import Game.Actors.Weapons.*;
import Game.Animations.Animation;
import Game.Animations.Blood.BloodSprite5;
import Game.Animations.UI.HpHeart;
import Game.Mods.Game;
import Game.Mods.MPTDM;
import Game.Network.ModProtocol.Stats.ActorStats;
import Game.Network.ModProtocol.Stats.ActorStats.Stats;
import Game.UI.Components.Buttons;
import Main.Textures.LoadTextures;
import Main.SettingsManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class TDM_Rime extends InGameUI{
    JList actorList;
    DefaultListModel actors;
    JList weaponList;
    DefaultListModel weapons;
    
    BufferedImage clipBackground;
    BufferedImage clipBullets;
    
    BufferedImage hpBackground;
    BufferedImage hpBar;
    BufferedImage hpBarRight;
    BufferedImage hpBarLeft;
    Animation hpHeart;
    int hpHeartAnimationSpeed=0;
    int hpHeartAnimation;

    
    private Buttons deploy;
//    BufferedImage deploy;
    int[] selectedWeapons;
    
    
    public TDM_Rime(MPTDM game, SettingsManager settings, LoadTextures textures) {
        super(game, settings, textures);
        
        deploy = new Buttons("Deploy", textures);
        deploy.setMouse(game.getMouse());
        
        pauseButtons = new ArrayList<>();
        button =new Buttons("Redeploy", textures);
        button.setMouse(game.getMouse());
        pauseButtons.add(button);
        button =new Buttons("Options", textures);
        button.setMouse(game.getMouse());
        pauseButtons.add(button);
        button =new Buttons("Quit", textures);
        button.setMouse(game.getMouse());
        pauseButtons.add(button);
        
        selectedWeapons = new int[]{0,1,-1,-1};
        
        actorsImages=new ArrayList();
        actorsImages.add(textures.hitmanUI);
        actorsImages.add(textures.zombieUI);
        actorsImages.add(textures.robotUI);
//        actorsImages.add(LoadTextures.hitman);
//        actorsImages.add(LoadTextures.zombie);
//        actorsImages.add(LoadTextures.hitman);
//        actorsImages.add(LoadTextures.zombie);
//        actorsImages.add(LoadTextures.zombie);
        
        weaponsImages=new ArrayList();
        weaponsImages.add(textures.nineMMUI);
        weaponsImages.add(textures.machineUI);
        weaponsImages.add(textures.M134UI);
//        weaponsImages.add(textures.molotovUI);
//        deploy = textures.deploy;

        hoverClass = new Color(100, 0, 0, 100);
        selectedClass = new Color(100, 100, 0, 100);
        hoverWeapon = new Color(0, 0, 100, 100);
        selectedWeapon = new Color(0, 100, 100, 100);
        
        clipBackground = textures.clipBackGround;
        clipBullets = textures.clipBullets;
        
        hpBackground = textures.hpBackground;
        hpBar = textures.hpBar;
        hpBarLeft = textures.hpBarLeft;
        hpBarRight = textures.hpBarRight;
        hpHeart = new HpHeart(textures);
        
        actors = new DefaultListModel();
        weapons = new DefaultListModel();
        
        actors.addElement("Hitman");
        actors.addElement("Zombie");
        actors.addElement("Robot");
        
        
        weapons.addElement("9mm");
        weapons.addElement("Machine");
        weapons.addElement("M134");
        weapons.addElement("Molotov");
        
        actorList = new JList(actors);
        weaponList = new JList(weapons);
        
        actorList.setLocation(550, 50);
        actorList.setSize(150, 150);
        actorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorList.setVisible(false);
        
        weaponList.setLocation(750, 50);
        weaponList.setSize(150, 150);
        weaponList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        weaponList.setVisible(false);
        
        game.setLayout(null);
        game.add(actorList);
        game.add(weaponList);
        
        actorList.setSelectedIndex((int) (Math.random()*3));
        cursor.setX(game.getWidth()/2);
        cursor.setY(game.getHeight()/2);
    }

    public void deploy(){
        //AddActor
        switch(actorList.getSelectedValue().toString()){
            case "Hitman":
                ((MPTDM)game).selectMe(Game.ActorClasses.Hitman);
                break;
            case "Zombie":
                ((MPTDM)game).selectMe(Game.ActorClasses.Zombie);
                break;
            case "Robot":
                ((MPTDM)game).selectMe(Game.ActorClasses.Robot);
                break;
        }

        //AddWeapons
        Thread t= new Thread(new Runnable(){
            @Override
            public void run(){
                do{
                    if(game.getMe()!=null){
                        for(Object obj : weaponList.getSelectedValuesList()){
                            switch(obj.toString()){
                                case "9mm":
                                    game.getMe().addWeapon(new NineMM(textures));
                                    break;
                                case "Machine":
                                    game.getMe().addWeapon(new Machine(textures));
                                    break;
                                case "M134":
                                    game.getMe().addWeapon(new M134(textures));
                                    break;
                                case "Molotov":
                                    game.getMe().addWeapon(new Molotov());
                                    break;
                            }
                        }
                        return;
                    }else{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                }while(true);
            }
        });
        t.run();
        game.grabFocus();
    }
    
    @Override
    public void drawDeployScreen(Graphics g){
        super.drawDeployScreen(g);
        Graphics g2 = g.create();
        
//        g2.drawImage(deploy, game.getWidth()/2-deploy.getWidth()/2, game.getHeight()/2+150, null);


        
//        if(!game.key.esc && cursor.getX()>game.getWidth()/2-deploy.getWidth()/2 && cursor.getX()<game.getWidth()/2-deploy.getWidth()/2+deploy.getWidth() && 
//                cursor.getY()>game.getHeight()/2+150 && cursor.getY()<game.getHeight()/2+150+deploy.getHeight()){
//                g2.setColor(hoverWeapon);
//                g2.fillRoundRect(game.getWidth()/2-deploy.getWidth()/2, game.getHeight()/2+150, 
//                        deploy.getWidth(), deploy.getHeight(), 60, 60);
//                if(game.mouse.b1Released){
//                    deploy();
//                }
//        }
        
        int i=0;
        for(BufferedImage actrImage : actorsImages){
            
            int x=(game.getWidth()/2)+(150*(i-actorsImages.size()/2));
            int y=(game.getHeight()/2)-150;
            
            //Selected
            if(i==actorList.getSelectedIndex()){
                g2.setColor(selectedClass);
                g2.fillRoundRect(x, y, actrImage.getWidth(), actrImage.getHeight(), 25, 25);
            }
            
            //Hovered
            if(!game.getKeyboard().esc && cursor.getX()>x && cursor.getX()<x+actrImage.getWidth() && 
                    cursor.getY()>y && cursor.getY()<y+actrImage.getHeight()){
                g2.setColor(hoverClass);
                g2.fillRoundRect(x, y, actrImage.getWidth(), actrImage.getHeight(), 25, 25);
                if(game.getMouse().b1Released){
                    actorList.setSelectedIndex(i);
                }
            }
            //Draw
            g2.drawImage(actrImage, x, y, null);
            i++;
        }
        
        i=0;
        for(BufferedImage wpnImage : weaponsImages){
            
            int x=(game.getWidth()/2)+(150*(i-weaponsImages.size()/2));
            int y=(game.getHeight()/2)+50;
            
            //Selected
            for(int z=0; z<weaponList.getSelectedIndices().length;z++){
                if(i==weaponList.getSelectedIndices()[z]){
                    g2.setColor(selectedWeapon);
                    g2.fillRoundRect(x-10, y-10, wpnImage.getWidth()+20, wpnImage.getHeight()+20, 25, 25);
                }
            }

            //Hovered
            if(!game.getKeyboard().esc && cursor.getX()>x-10 && cursor.getX()<x+wpnImage.getWidth()+10 && 
                    cursor.getY()>y-10 && cursor.getY()<y+wpnImage.getHeight()+10){
                g2.setColor(hoverWeapon);
                g2.fillRoundRect(x-10, y-10, wpnImage.getWidth()+20, wpnImage.getHeight()+20, 25, 25);
                if(game.getMouse().b1Released){
                    if(selectedWeapons[i]==i){
                        selectedWeapons[i]=-1;
                    }else{
                        selectedWeapons[i]=i;
                    }
                }
            }
            //Draw
            g2.drawImage(wpnImage, x, y, null);
            i++;
        }
        weaponList.setSelectedIndices(selectedWeapons);
        if(!game.getKeyboard().esc){
            
            deploy.setX(game.getWidth()/2 -deploy.getWidth()/2);
            deploy.setY(game.getHeight()/2+150);
            deploy.draw(g);

            if(deploy.isB1Released()){
                deploy();
            }
            
            cursor.draw(g);
            
            
        }
    }
    
    @Override
    public void drawStats(Graphics g){
        super.drawStats(g);
        int x=450;
        int y=75;
        int i=0;
        g.drawString("Name", x, y+(i*15));
        g.drawString("Kills ", x+100, y+(i*15));
        g.drawString("Deaths", x+200, y+(i*15));
        g.drawString("Name", x+300, y+(i*15));
        g.drawString("Kills ", x+400, y+(i*15));
        g.drawString("Deaths", x+500, y+(i*15));
        i++;
        
        Iterator<ActorStats.Stats> actorStats = ((MPTDM)game).getProtocolStats();
        
//        Iterator<Actor> actors = game.getActorsIterator();
        while(actorStats.hasNext()){
            Stats actor = actorStats.next();
            if(actor.getTeam()==0){
                g.drawString(actor.getName(), x, y+(i*20));
                g.drawString(""+actor.getKills(), x+100, y+(i*20));
                g.drawString(""+actor.getDeaths(), x+200, y+(i*20));
                i++;
            }
        }
        
        i=1;
        actorStats = ((MPTDM)game).getProtocolStats();
        while(actorStats.hasNext()){
            Stats actor = actorStats.next();
            if(actor.getTeam()==1){
                g.drawString(actor.getName(), x+300, y+(i*20));
                g.drawString(""+actor.getKills(), x+400, y+(i*20));
                g.drawString(""+actor.getDeaths(), x+500, y+(i*20));
                i++;
            }
        }
    }
    
    @Override
    public void drawHP(Graphics g){
        Graphics g2 = g.create();
        g2.drawImage(hpBackground, 50, game.getHeight()-130, null);
        g2.drawImage(hpBarLeft, 100, game.getHeight()-120, null);
        g2.drawImage(hpBarRight, (int)game.getMe().getHitPoints()+108, game.getHeight()-120, null);
        g2.drawImage(hpBar, 108, game.getHeight()-120, (int)game.getMe().getHitPoints(), 18, null);


        hpHeart.draw(g, 60, game.getHeight()-130);
        if(hpHeartAnimation>0){
            hpHeartAnimation--;
        }else{
            hpHeart.nextFrame();
            if(hpHeart.isConsumed()){hpHeart.replay();}
            hpHeartAnimation=hpHeartAnimationSpeed;
        }
    }
    
    @Override
    public void drawAmmo(Graphics g){
        Graphics g2 = g.create();
        g2.drawImage(clipBackground, 50, game.getHeight()-90, null);
        g2.drawString(game.getMe().getWeapon().getName(), 60, game.getHeight()-75);
        
        if(game.getMe().getWeapon() instanceof Gun){
            g2.drawString(((Gun)game.getMe().getWeapon()).getClip()+" / "+game.getMe().getWeapon().getAmmo(), 60, game.getHeight()-60);
            int procentLa10 = ((Gun)game.getMe().getWeapon()).getClip()*10/((Gun)game.getMe().getWeapon()).getClipSize();
            int i=1;
            while(i<=procentLa10){
                g2.drawImage(clipBullets, 225- (i*11), game.getHeight()-85, null);
                i++;
            }
            
//            g2.drawString(((Gun)game.me().getWeapon()).getClip()+"", 225- (i*9), game.getHeight()-75);
        }else{
            g2.drawString(" / "+game.getMe().getWeapon().getAmmo(), 60, game.getHeight()-60);
        }
    }

    @Override
    public void drawPauseMenu(Graphics g) {
        super.drawPauseMenu(g);
        int i;
        switch(menuPage){
            case 0:
                drawMainPauseMenu(g);
                break;
            case 1:
                break;
            case 2:
                drawOptions(g);
                break;
            case 3:
                drawYesNo(g);
                break;
        }
        
        if(game.getMouse().b1Released){
            Animation bloodsprite = new BloodSprite5(textures);
//            bloodsprite.setOpacity(0.5f);
            bloodsprite.setX(game.getMouse().mouseX);
            bloodsprite.setY(game.getMouse().mouseY);
            bloodEffects.add(bloodsprite);
        }
        if(!bloodEffects.isEmpty()){
            Iterator<Animation> bloodIterator = bloodEffects.listIterator();
            while ( bloodIterator.hasNext() ) {
                Animation blood = bloodIterator.next();
                if(blood.getOpacity()<0.05f){
                    bloodIterator.remove();
                }else{
                    blood.setOpacity(blood.getOpacity()-0.05f);
                    blood.draw(g, blood.getX(), blood.getY());
                    if(blood.hasNextFrame()){
                        blood.nextFrame();
                    }
                    
                }
            }
        }
        
        cursor.draw(g);
        
    }
    
    private void drawMainPauseMenu(Graphics g){
        int i=0;
        int x=(game.getWidth()/2)-150;
        int y;
        for(Buttons pauseButt : pauseButtons){

            
            y=(game.getHeight()/2)+(150*(i-pauseButtons.size()/2));
            pauseButt.setX(x);
            pauseButt.setY(y);
            pauseButt.draw(g);
            
            if(pauseButt.isB1Released()){
                    switch(i){
                        case 0:
                            System.out.println("Redeploy");
                            game.redeploy();
                            game.getKeyboard().esc=false;
                            break;
                        case 1:
                            System.out.println("Options");
                            menuPage=2;
                            break;
                        case 2:
                            System.out.println("Quit");
                            menuPage=3;
                            break;
                    }
                }
            i++;
        }
    }
    
}

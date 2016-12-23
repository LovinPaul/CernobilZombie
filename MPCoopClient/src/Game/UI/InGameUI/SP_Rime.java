package Game.UI.InGameUI;

import Game.Actors.Actor;
import Game.Actors.Weapons.*;
import Game.Animations.Animation;
import Game.Animations.Blood.BloodSprite5;
import Game.Animations.UI.HpHeart;
import Game.Mods.*;
import Game.UI.Components.Buttons;
import Main.Textures.LoadTextures;
import Main.SettingsManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class SP_Rime extends InGameUI{
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

    
//    BufferedImage deploy;
    int[] selectedWeapons;
    
    private int gameOverTimeout = 100;
    
    public SP_Rime(SP game, SettingsManager settings, LoadTextures textures) {
        super(game, settings, textures);
        
        selectedWeapons = new int[]{0,1,-1,-1};
        
        actorsImages=new ArrayList();
        actorsImages.add(textures.hitmanUI);
        actorsImages.add(textures.zombieUI);
//        actorsImages.add(LoadTextures.hitman);
//        actorsImages.add(LoadTextures.zombie);
//        actorsImages.add(LoadTextures.hitman);
//        actorsImages.add(LoadTextures.zombie);
//        actorsImages.add(LoadTextures.zombie);
        
        weaponsImages=new ArrayList();
        weaponsImages.add(textures.nineMMUI);
        weaponsImages.add(textures.machineUI);
        weaponsImages.add(textures.M134UI);
        weaponsImages.add(textures.molotovUI);
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
        
        pauseButtons = new ArrayList<>();
        button =new Buttons("Restart Level", textures);
        button.setMouse(game.getMouse());
        pauseButtons.add(button);
        button =new Buttons("Options", textures);
        button.setMouse(game.getMouse());
        pauseButtons.add(button);
        button =new Buttons("Quit", textures);
        button.setMouse(game.getMouse());
        pauseButtons.add(button);
        
        game.setLayout(null);
        game.add(actorList);
        game.add(weaponList);
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
                            System.out.println("Restart Level");
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
    
    public void drawGameOver(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        
        String gameOverText;
        String more_levels="";
        if(((SP)game).isObjectiveReached()){
            gameOverText = "You are safe... for now";
            more_levels="More levels in next release.";
        }else{
            gameOverText = "You are dead... but only for those living.";
        }
        
        if(gameOverTimeout>0){
            gameOverTimeout--;
            g2.setColor(background);
            g2.fillRect(0, 0, game.getWidth(), game.getHeight());
            g2.setFont(exitGame);
            g2.drawString(gameOverText, (game.getWidth()/2)-224, (game.getHeight()/2)-49);
            g2.drawString(more_levels, (game.getWidth()/2)-224, (game.getHeight()/2)+49);
            g2.setColor(Color.BLACK);
            g2.drawString(gameOverText, (game.getWidth()/2)-225, (game.getHeight()/2)-50);
            g2.drawString(more_levels, (game.getWidth()/2)-224, (game.getHeight()/2)+50);
        }else{
            drawPauseMenu(g);
        }
        
    }
    
}


package Main;

import Game.Mods.Game;
import Game.Actors.Weapons.M134;
import Game.Actors.Weapons.Machine;
import Game.Actors.Weapons.Molotov;
import Game.Actors.Weapons.NineMM;
import Game.*;
import Game.Network.Connection.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.Scanner;

public class Console{// extends JPanel

    Main main;
    JTextArea textArea;
    JTextField text;
    JScrollPane jsp;
    Scanner scanner;
    
    public Console(Main main) {
        this.main = main;
        scanner = new Scanner(System.in);
//        init();
    }
    private void init(){

//        this.setLayout(null);
        textArea = new JTextArea();
        textArea.setLocation(0, 0);
        textArea.setSize(Main.getWidth(), 100);
        textArea.setVisible(true);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        jsp = new JScrollPane(textArea);
        jsp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        jsp.setLocation(0, 0);
        jsp.setSize(Main.getWidth(), 100);
        jsp.setEnabled(true);
        jsp.setVisible(true);
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        
//        this.add(jsp);
        
        
        text = new JTextField();
        text.setLocation(0, 100);
        text.setSize(Main.getWidth(), 25);
        text.setVisible(true);
//        text.addKeyListener((KeyListener) key);
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textArea.append("\n" + text.getText());
                textArea.append(input(text.getText()));
                text.setText("");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
//        this.add(text);
//        text.addKeyListener((KeyListener) key);
    }

//    @Override
//    public void setVisible(boolean aFlag) {
//        super.setVisible(aFlag); //To change body of generated methods, choose Tools | Templates.
//        if(aFlag){
//            text.grabFocus();
//        }
//    }
    
    public void getInput(){
        input(scanner.next());
    }
    
    public String input(String in){
        
        String out = "\nunknown command;";
        String[] inArray = in.split("_");
        
        switch(inArray[0]){
            case "quit":
                System.exit(0);
                break;
            case "init":
                switch(inArray[1]){
                    case "SP":
                        if(inArray.length>2 && (Integer.parseInt(inArray[2])>=-1 || Integer.parseInt(inArray[2])<=2)){
//                            main.initSP(Integer.parseInt(inArray[2]));
                            out = "\nnew game started;";
                        }else{
                            out = "\ninvalid command;\nno enemies = -1;\neasy = 0;\nmedium = 1;\nhard = 2;";
                        }
                        break;
                    case "MPFFA":
//                        main.initMPFFA();
                        out = "\nnew game started;";
                        break;
                    case "MPCoop":
//                        main.initMPCoop();
                        out = "\nnew game started;";
                        break;
                    case "MPTDM":
//                        main.initMPTDM();
                        out = "\nnew game started;";
                        break;
                    default:
                        out = "\ninvalid command;\nSP for single player game;\nMP for multiplayer game;";
                        break;
                }
//                if(main.game!=null){main.game.setLocation(0, 125);}
                break;
            case "mouse.setSensitivity":
//                main.game.setSensitivity(Float.parseFloat(inArray[1]));
                out = "\nnew Sensitivity set;";
                break;
            case "me.setAgility":
                main.game.getMe().setAgility(Integer.parseInt(inArray[1]));
                out = "\nnew agility set;";
                break;
            case "me.setHP":
                main.game.getMe().setHitPoints(Integer.parseInt(inArray[1]));
                out = "\nnew agility set;";                
                break;
            case "me.setGodMode":
                if(Integer.parseInt(inArray[1])==1){
                    main.game.getMe().isInvincible=true;
                    out = "\nGodMode on;";
                }else{
                    main.game.getMe().isInvincible=false;
                    out = "\nGodMode off;";
                }
                        
                break;
            case "me.setImune":
                if(Integer.parseInt(inArray[1])==1){
                    main.game.getMe().isImune=true;
                    out = "\nImune on;";
                }else{
                    main.game.getMe().isImune=false;
                    out = "\nImune off;";
                }
                break;
            case "me.setAutoAim":
                if(Integer.parseInt(inArray[1])==1){
                    main.game.getMe().autoAim=true;
                    out = "\nAutoAim on;";
                }else{
                    main.game.getMe().autoAim=false;
                    out = "\nAutoAim off;";
                }
                break;
            case "me.addWeapon":
//                switch(inArray[1]){
//                    
//                    case "NineMM":
//                        main.game.getMe().addWeapon(new NineMM());
//                        out = "\nNineMM Weapon set;";
//                        break;
//                    case "Machine":
//                        main.game.getMe().addWeapon(new Machine());
//                        out = "\nMachine Weapon set;";
//                        break;
//                    case "M134":
//                        main.game.getMe().addWeapon(new M134());
//                        out = "\nM134 Weapon set;";
//                        break;
//                    case "Molotov":
//                        main.game.getMe().addWeapon(new Molotov());
//                        out = "\nMolotov Weapon set;";
//                        break;
//                    case "none":
//                        main.game.getMe().addWeapon(null);
//                        out = "\nno Weapon set;";
//                        break;
//                }
                break;
            case "new.me":
                String name;
                if(inArray.length>2){name=inArray[2];}else{name="";}
                switch(inArray[1]){
                    case "Zombie":
                        main.game.selectMe(Game.ActorClasses.Zombie, name);
                        out = "\nnew Zombie;";
                        break;
                    case "Hitman":
                        main.game.selectMe(Game.ActorClasses.Hitman, name);
                        out = "\nnew Hitman;";
                        break;
                    default:
                        out = "\ninvalid command;\nClasses available : Zombie, Hitman";
                        break;
                }
                break;
            case "connection.setIp":
//                Connection.setIp(inArray[1]);
                break;
        }


        
        if("help".equals(inArray[0])){
            out = "\nquit = quits the application;";
            out += "\ncc = closes the console";
            out += "\ninit = initiates a new game  (ex init SP 0 (-1 no enemyes, 0 easy, 1 medium, 2 hard), init MPCoop)";
            out += "\nmouse.setSensitivity = sets mouse sensitivity (use floats ex 0.1, 0.9, 2.1, 5.9)";
            out += "\nme.setAgility = sets the agility";
            out += "\nme.setHP = sets the hit points";
            out += "\nme.setGodMode = sets the invicibility";
            out += "\nme.setImune = sets the imunity to infections";
            out += "\nme.setAutoAim = sets the auto aim";
            out += "\nme.addWeapon = adds a new weapon (M134, Machine, Molotov)";
            out += "\nnew.me = creates an instance of the user on the server with the coresponding name (ex new.me Hitman Gigel)";
//            out += "\n
        }
        
        return out;
    }
    
}

package Game.Actors;

import Game.Animations.Blood.ElectricSprite1;
import Game.Animations.Blood.ElectricSprite2;
import Main.Textures.LoadTextures;


public class Robot1 extends Actor{
    
    public Robot1(byte ID, float x, float y, float angle) {
        super(ID, x, y, angle);
        super.setAgility(7);
        
    }
    public Robot1(byte ID) {
        super(ID);

    }
    
    @Override
    public void init(LoadTextures textures) {
        super.init(textures);
        super.setAgility(7);
        super.setImages(textures.robot1_hold, textures.robot1_stand, textures.robot1_left_hand, textures.robot1_right_hand);     
    }
    
    @Override
    protected void addBloodEffect(){
        switch((int)(Math.random()*3)){//
            case 0:
                bloodIterator.add(new ElectricSprite1(textures));
//                bloodEffects.add();
                break;
            case 1:
                bloodIterator.add(new ElectricSprite2(textures));
//                bloodEffects.add(new BloodSprite2());
                break;
            case 2:
                bloodIterator.add(new ElectricSprite1(textures));
//                bloodEffects.add(new BloodSprite3());
                break;
            case 3:
                bloodIterator.add(new ElectricSprite2(textures));
//                bloodEffects.add(new BloodSprite4());
                break;
        }
    }
    
}

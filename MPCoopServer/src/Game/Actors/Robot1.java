package Game.Actors;

import Main.LoadTextures;

public class Robot1 extends Actor{
    
    public Robot1(byte ID, float x, float y, float angle) {
        super(ID, x, y, angle);
        super.setAgility(7);
        super.setImages(LoadTextures.robot1_hold, LoadTextures.robot1_stand, LoadTextures.robot1_left_hand, LoadTextures.robot1_right_hand);
    }
    
}

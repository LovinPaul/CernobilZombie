
package Game.Actors;

import Main.Textures.LoadTextures;

public class Zombie1 extends Actor{
    
    private float targetX=x;
    private float targetY=y;
    
    private float infectionRange=200;
    private float infectionPower=1;
    private float bitePower=1;
    
    public Zombie1(byte ID, float x, float y, float angle) {
        super(ID, x, y, angle);
    }
    public Zombie1(byte ID) {
        super(ID);
    }

    @Override
    public void init(LoadTextures textures) {
        super.init(textures);
        super.setAgility(3);
        super.setInfected(true);
        super.setImages(textures.zombie1_hold, textures.zombie1_stand, textures.zombie1_left_hand, textures.zombie1_right_hand);
    }
    
    
    
    public float getInfectionRange(){
        return infectionRange;
    }
    public float getInfectionPower(){
        return infectionPower;
    }
    public float getBitePower(){
        return bitePower;
    }

    public void setInfectionRange(float infectionRange){
        this.infectionRange=infectionRange;
    }    
    public void setInfectionPower(float infectionPower){
        this.infectionPower=infectionPower;
    }
    public void setBitePower(float bitePower){
        this.bitePower = bitePower;
    }
    
}

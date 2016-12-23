package Game.Maps.Environment.Crates;


public abstract class ExploadingCrate extends Crate{
    
    protected float fireDistance;
    public float firePower;

    


    public ExploadingCrate(byte ID, float x, float y) {
        super(ID, x, y);
    }
    
    public float getFireDistance(){
        return fireDistance;
    }
    public float getFirePower(){
        return firePower;
    }
    
    @Override
    public void gotHit(float damage){
        hitPoints-=damage;
//        switch((int)(Math.random()*3)){//
//            case 0:
//                bloodEffects.add(new BloodSprite1());
//                break;
//            case 1:
//                bloodEffects.add(new BloodSprite2());
//                break;
//            case 2:
//                bloodEffects.add(new BloodSprite3());
//                break;
//            case 3:
//                bloodEffects.add(new BloodSprite4());
//                break;
//        }
    }
    
}

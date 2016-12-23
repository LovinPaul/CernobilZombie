package Game.Actors.Weapons;

import Game.Animations.Animation;
import Game.Actors.Actor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Weapon {
    //
    protected byte ID;
    protected int ammoSize;
    protected int fireRate;
    protected int weaponCooldownTime;
    protected float firePower;
    protected boolean twoHandedWeapon;
    protected boolean leftHanded;
    protected boolean rightHanded=true;
    protected float relativWeaponX;
    protected float relativWeaponY;
    protected float relativMuzzleFlashX;
    protected float relativMuzzleFlashY;
    protected float cameraShake;

    
    protected int ammo;
    protected int weaponCooldown;
    protected Actor owner;
    protected BufferedImage image;
    protected BufferedImage muzzle_flash;
    
    protected Animation anim_image;
    protected Animation explosion;

    protected float targetX; 
    protected float targetY;
    
    protected boolean isFired;
    protected String name;

    public byte getID(){
        return ID;
    }
    public float getCameraShake(){
        return cameraShake;
    }
    public int getAmmo(){
        return ammo;
    }
    public String getName(){
        return name;
    }
    public float getFirePower(){
        return firePower;
    }
    public float getTargetX(){
        return targetX;
    }
    public float getTargetY(){
        return targetY;
    }
    
    public void setOwner(Actor owner){
        this.owner = owner;
    }
    public void setIsFired(boolean fired){
        isFired=fired;
    }
    public void setAmmo(int ammo){
        this.ammo=ammo;
    }
    public void addAmmo(int ammo){
        this.ammo+=ammo;
    }
    
    public void coolDown(){
        if(weaponCooldown>0){weaponCooldown--;}
    }
    
    public boolean isTwoHandedWeapon(){
        return twoHandedWeapon;
    }
    public boolean isLeftHandedWeapon(){
        return leftHanded;
    }
    public boolean isRightHandedWeapon(){
        return rightHanded;
    }
    public boolean isCool(){
        return weaponCooldown==0;
    }
    public boolean isFired(){
        return isFired;
    }
    
    public void fire(){
        weaponCooldown=weaponCooldownTime;
        ammo--;
        isFired=true;
    }
    public void fire(float targetX, float targetY){
        weaponCooldown=weaponCooldownTime;
        ammo--;
        isFired=true;
    }
    public boolean silentFire(){
        weaponCooldown=weaponCooldownTime;
//        ammo--;
        return false;
    }

    
    public void draw(Graphics g, float x, float y){
        Graphics2D gWeapon = (Graphics2D) g.create();
        gWeapon.rotate(Math.toRadians(owner.getAngle()), (int)x, (int)y);
        
        if(anim_image!=null){
            if(anim_image.isConsumed()){anim_image.replay();}
            anim_image.draw(gWeapon, (int)(x+relativWeaponX), (int)(y+relativWeaponY));
            anim_image.nextFrame();
        }else{
            gWeapon.drawImage(image, (int)(x+relativWeaponX), (int)(y+relativWeaponY), null);
        }

        
    }
    
}

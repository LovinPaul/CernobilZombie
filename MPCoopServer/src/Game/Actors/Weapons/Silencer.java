package Game.Actors.Weapons;

import Game.Actors.Actor;
import Main.LoadTextures;


public class Silencer extends Gun{
    public Silencer(Actor owner) {
        super(owner);
        firePower=2;
        weaponCooldownTime=5;
        relativWeaponX = 5;
        relativWeaponY = -30;
        image = LoadTextures.silencer;
    }
}

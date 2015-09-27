package com.tophattiger.GameObjects.Characters;

import com.tophattiger.Helper.Buff;

/**
 * Created by Collin on 7/1/2015.
 */
public class Helper1 extends com.tophattiger.GameObjects.Characters.GeneralHelper {

    final String name = "Bob";
    final int positionX = 586;
    final int positionY = 414;
    final int projectileX = 680;
    final int projectileY = 500;
    final int xV0 = 500;
    final int yV0 = 250;
    final int attackFrames = 6;
    final int idleFrames = 3;
    final int projectileAnimationFrames = 3;
    final float projectileAnimationTime = 1f;
    final float attackTime = 1f;
    final float idleTime = 1f;
    final float projectileOffsetTime = attackTime/2;
    final float gravity = -250f;


    /**
     * Create the  1st helper.
     * Set variables and create buffs
     * @param helpers List of helpers to put this helper in
     */
    public Helper1(com.tophattiger.GameObjects.Characters.Helpers helpers){
        super(helpers);
        super.name = name;
        super.positionX = positionX;
        super.positionY = positionY;
        super.eachProjectileTime = attackTime;
        super.gravity = gravity;
        super.projectileX = projectileX;
        super.projectileY = projectileY;
        super.xV0 = xV0;
        super.yV0 = yV0;
        super.attackFrames = attackFrames;
        super.attackTime = attackTime;
        super.projectileAnimationFrames = projectileAnimationFrames;
        super.projectileAnimationTime = projectileAnimationTime;
        super.projectileTime -= projectileOffsetTime;
        super.idleFrames = idleFrames;
        super.idleTime = idleTime;
        createBuffs();
    }

    /**
     * Set the power, gold, and the costs for this helper specifically
     */
    @Override
    public void set() {
        super.autoPower = super.pLevel;
        super.autoGold = super.gLevel;
        super.autoPowerCost = 10 * (super.pLevel + 1)*(super.pLevel+1);
        super.autoGoldCost = 10 * (super.gLevel + 1)*(super.gLevel+1);
        super.set();
    }

    /**
     * Create the 9 custom buffs
     */
    @Override
    public void createBuffs() {
        buff1 = new Buff("Gain a sword.",1, Buff.TYPE.HELPERDAMAGE);
        buff2 = new Buff("Help the hero more.",.1, Buff.TYPE.HERODAMAGE);
        buff3 = new Buff("Help all your partners.",2, Buff.TYPE.ALLHELPERDAMAGE);
        buff4 = new Buff("Find more gold.",.5, Buff.TYPE.GOLD);
        buff5 = new Buff("Gain a second sword.",1.5, Buff.TYPE.HELPERDAMAGE);
        buff6 = new Buff("Become best friends with the hero.",.1, Buff.TYPE.HERODAMAGE);
        buff7 = new Buff("Hand out ammo",.2, Buff.TYPE.ALLHELPERDAMAGE);
        buff8 = new Buff("Get a metal detector",1, Buff.TYPE.GOLD);
        buff9 = new Buff("Dual wield metal detectors",3, Buff.TYPE.GOLD);
        super.createBuffs();
    }
}

package com.tophattiger.GameObjects.Characters;
import com.tophattiger.Helper.Buff;

/**
 * Created by Collin on 7/1/2015.
 */
public class Helper2 extends com.tophattiger.GameObjects.Characters.GeneralHelper {

    final String name = "Nina";
    final int positionX = 586;
    final int positionY = 542;
    final int projectileX = 640;
    final int projectileY = 590;
    final int xV0 = 1300;
    final int yV0 = 10;
    final int attackFrames = 12;
    final int projectileAnimationFrames = 4;
    final int idleFrames = 4;
    final float projectileAnimationTime = .5f;
    final float attackTime = .7f;
    final float idleTime = 1f;
    final float projectileOffsetTime = attackTime/2;
    final float gravity = -250f;

    /**
     * Create the  2nd helper.
     * Set variables and create buffs
     * @param helpers List of helpers to put this helper in
     */
    public Helper2(com.tophattiger.GameObjects.Characters.Helpers helpers){
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

    @Override
    protected double levelGoldAlg(int goldLevel){
        return goldLevel*7;
    }

    @Override
    protected double levelPowerAlg(int powerLevel) {
        return powerLevel*7;
    }

    @Override
    protected double levelGoldCostAlg(int goldLevel) {
        return 150 * (goldLevel + 1) * (goldLevel + 1);
    }

    @Override
    protected double levelPowerCostAlg(int powerLevel) {
        return 150 * (powerLevel + 1) * (powerLevel + 1);
    }

    /**
     * Create the 9 custom buffs
     */
    @Override
    protected void createBuffs() {
        buff1 = new Buff("Sword",2.5, Buff.TYPE.HELPERDAMAGE);
        buff2 = new Buff("Dagger",1.1, Buff.TYPE.HERODAMAGE);
        buff3 = new Buff("Helping Hand",1.2, Buff.TYPE.ALLHELPERDAMAGE);
        buff4 = new Buff("Money",1.5, Buff.TYPE.GOLD);
        buff5 = new Buff("Sword",2.5, Buff.TYPE.HELPERDAMAGE);
        buff6 = new Buff("Dagger",1.1, Buff.TYPE.HERODAMAGE);
        buff7 = new Buff("Helping Hand",1.2, Buff.TYPE.ALLHELPERDAMAGE);
        buff8 = new Buff("Money",1.5, Buff.TYPE.GOLD);
        buff9 = new Buff("Money",1.5, Buff.TYPE.GOLD);
        super.createBuffs();
    }
}

package com.tophattiger.GameObjects.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.GameObjects.Projectile;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Buff;
import com.tophattiger.Helper.Data.DataManagement;

/**
 * Created by Collin on 7/1/2015.
 */
public class GeneralHelper extends Actor {

    final int maxX = 1550;

    String name;
    Image picture;
    boolean abilitySpeed;
    int level,pLevel,gLevel,positionX,positionY,attackFrames,projectileX,projectileY,xV0,yV0,projectileAnimationFrames,idleFrames;
    float animationTime,attackTime,eachProjectileTime,projectileTime,gravity,projectileAnimationTime,abilitySpeedTime,idleTime;
    double totalPower,totalGold,autoPower,autoGold,autoPowerCost,autoGoldCost,abilitySpeedAmount;
    Buff buff1,buff2,buff3,buff4,buff5,buff6,buff7,buff8,buff9;
    FloatArray buffPower = new FloatArray();
    FloatArray buffGold = new FloatArray();
    FloatArray buffAllPower = new FloatArray();
    FloatArray comboPower = new FloatArray();
    Array<Buff> buffs = new Array<Buff>();
    com.tophattiger.GameObjects.Characters.Helpers helpers;
    TextureRegion frame;
    Animation attack,idle;
    GeneralHelper helper = this;
    Array<Projectile> activeProjectiles = new Array<Projectile>();
    Pool<Projectile> projectilePool = new Pool<Projectile>() {
        @Override
        protected Projectile newObject() {
            return new Projectile(helper);
        }
    };
    Projectile projectile;

    /**
     * A general helper that specific helpers can inherit. The helper can shoot a projectile that will damage the enemy.
     * The helpere will generate gold while the user is not using the app, and can gain buffs and increase damage/gold from leveling
     * up or combo buffs
     * @param _helpers  Helper list to place the helper inside of
     */
    public GeneralHelper(com.tophattiger.GameObjects.Characters.Helpers _helpers){
        helpers = _helpers;
        totalGold = totalPower = autoPower = autoGold = autoPowerCost = autoGoldCost = 0;
        pLevel = gLevel = level = 0;
        abilitySpeedTime = projectileTime=0;
        abilitySpeed = false;
    }

    /**
     * Logic for projectiles
     * Checks if power level is greater than 0
     * Applies extra speed if ability is active, then shoots a new projectile every eachProjectileTime
     * If the level is greater than 0 but no power, then it won't shoot projectiles and only show an idle animation
     * @param delta Change in time between function calls
     */
    @Override
    public void act(float delta) {
        if(pLevel >0) {             //Is the power level greater than 0?
            animationTime += delta;
            projectileTime += delta;

            if(abilitySpeed){       //Is the ability speed ability active?
                abilitySpeedTime -= delta;
                if(abilitySpeedTime < 0){
                    abilitySpeedTime = 0;
                    abilitySpeed = false;
                    eachProjectileTime *= abilitySpeedAmount;   //Decrease the time inbetween projectiles
                }
            }

            if (projectileTime >= eachProjectileTime) {     //If it's time for the next projectile
                projectileTime = 0;
                projectile = projectilePool.obtain();
                projectile.init();
                activeProjectiles.add(projectile);      //Create a new projectile and add it to the pool
            }

            if (activeProjectiles.size != 0) {
                for (int i = activeProjectiles.size; --i >= 0; ) {
                    projectile = activeProjectiles.get(i);
                    if (projectile.isDead()) {              //If the projectile is dead, hit the enemy and remove projectile
                        hitEnemy();
                        activeProjectiles.removeIndex(i);
                        projectilePool.free(projectile);
                    }
                    else
                        projectile.act(delta);           //Else run act on projectile
                }
            }
        }
        else if(level > 0){     //If the helper has been leveled up but no power, then just show idle animation
            animationTime += delta;
        }
    }

    /**
     * Draw frame of helper and projectile
     * @param batch Batch to draw to
     * @param parentAlpha   Parent's alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(level > 0 && animationTime >= 0) {
            if(pLevel > 0) {    //Set animation to attack if power level is greater than 0
                frame = attack.getKeyFrame(animationTime, true);
                if (activeProjectiles.size != 0) {
                    for (int i = activeProjectiles.size; --i >= 0; ) { //Run through projectiles and check if they are active, then draw
                        projectile = activeProjectiles.get(i);
                        projectile.draw(batch, parentAlpha);
                    }
                }
            }
            else
                frame = idle.getKeyFrame(animationTime,true);
            batch.draw(frame, positionX, positionY, 128, 128);
        }

    }

    /**
     * Set the variables based on the levels and buffs
     */
    public void set(){
        totalGold = autoGold;
        totalPower = autoPower;
        if(buffPower.size != 0){    //If the buff power is active, then increase the total power
            for(int i =0;i<buffPower.size;i++){
                totalPower *= buffPower.get(i);
            }
        }
        if(buffAllPower.size != 0){     //If the buff all helper power is active, then increase the total power
            for (int i=0;i<buffAllPower.size;i++){
                totalPower *= buffAllPower.get(i);
            }
        }
        if(comboPower.size != 0){       //If the combo power is active, then increase the total power
            for(int i=0;i<comboPower.size;i++){
                totalPower *= comboPower.get(i);
            }
        }
        if(buffGold.size != 0){         //If the buff gold is active, increase the total gold
            for(int i=0;i<buffGold.size;i++){
                totalGold *= buffGold.get(i);
            }
        }
    }

    /**
     * Set the picture of the helper and animations based off the helper's name
     */
    private void setPicture(){
        picture = new Image(AssetLoader.textureAtlas.findRegion(name));
        picture.setSize(128, 128);
        attack = new Animation(attackTime/attackFrames,AssetLoader.textureAtlas.findRegions(name + "Attack"));
        idle = new Animation(idleTime/idleFrames,AssetLoader.textureAtlas.findRegions(name + "Idle"));
    }


    public Image getPicture(){return picture;}

    public Drawable getPictureDrawable(){return new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion(name)); }

    /**
     * Increment gold level, level, check if new buffs are unlocked and then set the variables
     */
    public void levelGold(){
        gLevel ++;
        level ++;
        checkBuffs();
        set();
    }

    /**
     * Increment power level, level, check if new buffs are unlocked and then set the variables
     */
    public void levelPower(){
        pLevel ++;
        level++;
        checkBuffs();
        set();
    }

    /**
     * Add buffs to list and set their descriptions and set the picture
     */
    protected void createBuffs(){
        buffs.add(buff1);
        buffs.add(buff2);
        buffs.add(buff3);
        buffs.add(buff4);
        buffs.add(buff5);
        buffs.add(buff6);
        buffs.add(buff7);
        buffs.add(buff8);
        buffs.add(buff9);
        for(int i = 0;i < 9; i++){
           buffs.get(i).setDescription(i);
        }
        setPicture();
    }

    /**
     * Check which buffs to activate based off level of helper
     */
    private void activateBuffs(){
        int i = 0;
        if(level >= 1000)i = 9;
        else if(level >= 750)i = 8;
        else if(level >= 500)i = 7;
        else if(level >= 250)i = 6;
        else if(level >= 200)i = 5;
        else if(level >= 100)i = 4;
        else if(level >= 50)i = 3;
        else if (level >= 25)i = 2;
        else if (level >= 10)i = 1;
        for(int _i = 0;_i < i; _i++){
            buffs.get(_i).activate(this);
        }
    }

    /**
     * Reset each buffer
     */
    private void resetBuffs(){
        for(int i = 0;i<9;i++){
            buffs.get(i).reset();
        }
    }

    /**
     * Check leveled up to activate the next buff
     */
    private void checkBuffs(){
        if(level == 1000)buff9.activate(this);
        else if(level == 750)buff8.activate(this);
        else if(level == 500)buff7.activate(this);
        else if(level == 250)buff6.activate(this);
        else if(level == 200)buff5.activate(this);
        else if(level == 100)buff4.activate(this);
        else if(level == 50)buff3.activate(this);
        else if (level == 25)buff2.activate(this);
        else if (level == 10)buff1.activate(this);
    }

    /**
     * Add the amount to increase the power by from the buff
     * @param amount Amount to multiply power by
     */
    public void buffPower(double amount){
        buffPower.add((float)amount);
        set();
    }

    /**
     * Run through all the helpers to have their power be increased
     * @param amount Amount to multiply power by
     */
    public void buffAllPower(double amount){
        helpers.buffAllPower(amount);
    }

    /**
     * Add the amount to increase the power by from the all power buff
     * @param amount Amount to multiply power by
     */
    public void setBuffAllPower(double amount){
        buffAllPower.add((float)amount);
        set();
    }

    /**
     * Add the amount to increase the power by from the power combo
     * @param amount Amount to multiply power by
     */
    public void setComboPower(double amount){
        comboPower.add((float)amount);
        set();
    }

    /**
     * Undo the power bonuses from combos
     */
    public void undoComboPower(){
        comboPower.clear();
        set();
    }

    /**
     * Set variables for when the increased helper speed ability is activated
     * @param amount    Amount to increase the speed
     * @param length    Length of time to run ability for
     */
    public void abilitySpeed(double amount, int length){
        eachProjectileTime /= amount;
        abilitySpeedAmount = amount;
        abilitySpeedTime = length;
        abilitySpeed = true;
    }

    public String getBuffDesc(int i){
        return buffs.get(i).getDesc();
    }

    public Boolean buffActive(int i){
        return buffs.get(i).getIsActive();
    }

    public Drawable getBuffImg(int i){
        return buffs.get(i).getImage();
    }

    /**
     * Hit the enemy for total power
     */
    private void hitEnemy(){
        helpers.getGame().getEnemies().get(helpers.getGame().getEnemies().size -1).helperAttack(totalPower);
    }

    /**
     * Function to load the data from JSON
     * set the level, power level, gold level and check buffs
     * @param i Which index in json list
     */
    public void load(int i){
        level = DataManagement.JsonData.helpers.get(3*i);
        pLevel = DataManagement.JsonData.helpers.get(1+3*i);
        gLevel = DataManagement.JsonData.helpers.get(2+3*i);
        activateBuffs();
        set();
    }

    /**
     * Function to save the data from JSON
     * Save the level, power level, gold level
     */
    public void save(){
        DataManagement.JsonData.helpers.add(level);
        DataManagement.JsonData.helpers.add(pLevel);
        DataManagement.JsonData.helpers.add(gLevel);
    }

    /**
     * Function to reset the data in the helper
     */
    public void reset(){
        pLevel = gLevel = level = 0;
        buffGold.clear();buffPower.clear();buffAllPower.clear();comboPower.clear();
        resetBuffs();
        if(abilitySpeed){
            abilitySpeedTime = 0;
            eachProjectileTime *= abilitySpeedAmount;
            abilitySpeedAmount = 0;
            abilitySpeed = false;
        }
        set();
    }

    public void resetAnimation(){animationTime = 0;}

    public Helpers getHelpers(){return helpers;}

    public String getName(){return name;}

    public int getLevel(){return level;}

    public int getPLevel(){return pLevel;}

    public int getGLevel(){return gLevel;}

    public double getAutoGoldCost(){return autoGoldCost;}

    public double getAutoPowerCost(){return autoPowerCost;}

    public double getAutoPower(){return totalPower;}

    public double getAutoGold(){return totalGold;}

    public int getProjectileX(){return projectileX;}

    public int getProjectileY(){return projectileY;}

    public int getProjectileXV0(){return xV0;}

    public int getProjectileYV0(){return yV0;}

    public int getProjectileMaxX(){return maxX;}

    public int getProjectileAnimationFrames(){return projectileAnimationFrames;}

    public float getProjectileGravity(){return gravity;}

    public float getProjectileAnimationTime(){return projectileAnimationTime;}
}

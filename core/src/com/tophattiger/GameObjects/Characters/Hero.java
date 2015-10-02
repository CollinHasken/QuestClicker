package com.tophattiger.GameObjects.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 5/27/2015.
 */
public class Hero extends Actor {

    final int idleFrames = 7;
    final int attackFrames = 6;
    final float idleTime = 1.5f;
    final float attackTime = .7f;
    final float levelTime = 1.5f;
    final int levelFrames = 12;
    final int positionX = 998;
    final int positionY = 414;

    int questCompleted,questRequired;
    double questProgress;
    float animationTime,levelAnimationTime;
    int touchLevel;
    double touchCost,touchPower;
    boolean level;
    Image picture;
    String name;
    Animation idle,attack,animation,levelAnimation;
    TextureRegion frame,levelFrame;

    /**
     * The hero stores the click data, quest data, name and level
     */
    public Hero(){
        questCompleted = 0;
        touchPower = 1;
        questProgress = 0;
        animationTime = 0;
        touchLevel = 1;
        touchCost = 10;
        name = "Hero's Name";
        level = false;
        idle = new Animation(idleTime/idleFrames,AssetLoader.textureAtlas.findRegions("heroIdle"));
        attack = new Animation(attackTime/attackFrames,AssetLoader.textureAtlas.findRegions("heroAttack"));
        levelAnimation = new Animation(levelTime/levelFrames,AssetLoader.textureAtlas.findRegions("level"));
        animation = idle;
        picture = new Image(AssetLoader.textureAtlas.findRegion("heroMug"));
        picture.setSize(128, 128);
    }

    /**
     * Check if the user has made enough progress to go to next quest
     * @param delta Amount of time between calls
     */
    @Override
    public void act(float delta) {
        animationTime += delta;
        levelAnimationTime += delta;
        if(questProgress >= questRequired){
            questProgress = 0;
            questRequired += 10*questCompleted;
            questCompleted ++;
        }
        checkAnimation();
    }

    /**
     * Draw the hero and if he has recently leveled, play the level up graphic
     * @param batch Batch to draw to
     * @param parentAlpha   Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(level){
            levelFrame = levelAnimation.getKeyFrame(levelAnimationTime);
            batch.draw(levelFrame,positionX-80,positionY-40,256,256);
        }
        frame = animation.getKeyFrame(animationTime,true);
        batch.draw(frame,positionX,positionY,256,256);
    }

    /**
     * Level up the touch, cost and play animation
     */
    public void levelTouch(){
        touchLevel ++;
        touchCost = touchCost * 1.5;
        touchPower = touchLevel*1.2;
        levelAnimation();
    }

    /**
     * Check what state the animation is in
     */
    private void checkAnimation(){
        if (animation == attack && animation.isAnimationFinished(animationTime)){   //If the user is attacking and is done, then become idle
            animation = idle;
            animationTime = 0;
        }
        if(levelAnimation.isAnimationFinished(levelAnimationTime) && level){    //If user leveled and the animation is now over, turn off level
            level = false;
        }
    }

    /**
     * Start the level animation, but don't reset it for multiple level ups
     */
    public void levelAnimation(){
        if(!level)
            levelAnimationTime = 0;
        level = true;
    }

    /**
     * Play the attack animation
     */
    public void attack(){
        if(animation != attack) {
            animation = attack;
            animationTime = 0;
        }
    }

    /**
     * Change the attack power based off of combo
     * @param amount Amount to multiply damage by
     */
    public void comboDamage(double amount){
        touchPower *= amount;
    }

    /**
     * Undo the attack power based off of combo
     * @param amount Amount to undo damage by
     */
    public void undoComboDamage(double amount){
        touchPower /= amount;
    }

    public String getName(){
        return name;
    }
    public void setName(String _name){name = _name;}

    public Image getImage(){
        return picture;
    }

    public double getTouchCost(){return touchCost;}
    public void setTouchCost(double _cost){touchCost = _cost;}

    public int getLevel(){
        return touchLevel;
    }
    public int getTouchLevel(){return touchLevel;}
    public  void setTouchLevel(int _level){touchLevel = _level;}

    public String getTouchPowerString(){return Gold.getNumberWithSuffix(touchPower);}
    public double getTouchPower(){return touchPower;}
    public void setTouchPower(double _power){touchPower = _power;}
    public int getQuestCompleted(){return questCompleted;}
    public int getQuestRequired(){return questRequired;}

    /**
     * Load the quest, name and power data from JSON
     * @param jData Class that holds data
     */
    public void load(DataManagement.JsonData jData){
        if(jData.currentVersion >= 1) {
            setTouchPower(jData.clickPower);
            questCompleted = jData.questCompleted;
            questProgress = jData.questProgress;
            questRequired = jData.questRequired;
            setName(jData.name);
            setTouchCost(jData.touchCost);
            setTouchLevel(jData.touchLevel);
        }
    }

    /**
     * Save the quest, name and power data to JSON
     * @param jData Class that holds data
     */
    public void save(DataManagement.JsonData jData){
        jData.clickPower = getTouchPower();
        jData.questCompleted = questCompleted;
        jData.questProgress = questProgress;
        jData.questRequired = questRequired;
        jData.touchCost = getTouchCost();
        jData.touchLevel = getTouchLevel();
        jData.name = getName();
    }

    /**
     * Reset the class to default
     */
    public void reset(){
        touchPower = 1;
        questCompleted = 0;
        questProgress = 0;
        questRequired = 10;
        name = "Hero";
        touchCost = 10;
        touchLevel = 1;
    }
}

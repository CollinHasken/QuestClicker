package com.tophattiger.GameObjects.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.Helper.Data.Gold;

import java.util.Random;

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

    int questCompleted,questRequired, touchLevel,artifacts,questSelect;
    double questProgress,touchCost,touchPower,artifactDamage;
    float animationTime,levelAnimationTime;
    boolean level,retired;
    Random rand;
    Image picture;
    String name,questDescription;
    Animation idle,attack,animation,levelAnimation;
    TextureRegion frame,levelFrame;

    /**
     * The hero stores the click data, quest data, name and level
     */
    public Hero(){
        questCompleted = artifacts= 0;
        touchPower = artifactDamage = 1;
        questProgress = 0;
        rand = new Random();
        newQuest();
        animationTime = 0;
        touchLevel = 1;
        touchCost = 10;
        name = "Hero's Name";
        retired = level = false;
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
        batch.draw(frame, positionX, positionY, 256, 256);
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
     * Restart the game with artifacts based off of how many enemies defeated and other stats
     */
    public void retire(){
        retired = true;
        artifacts += touchLevel; //Need to come up with algorithm
    }

    /**
     * Set the quest description to a random quest from a pool of quests.
     */
    public void newQuest(){
        int newQuestNumber = questSelect;
        int totalQuests = 10;
        while(newQuestNumber == questSelect){
            newQuestNumber = rand.nextInt(totalQuests);
        }
        questSelect = newQuestNumber;
        setQuestDescription(questSelect);
    }

    public void setQuestDescription(int questNumber){
        switch (questNumber){
            case 0:
                questDescription = "Raise your self-esteem by defeating animals.";
                break;
            case 1:
                questDescription = "Find the holy stick of butt scratching.";
                break;
            case 2:
                questDescription = "Rescue Mike Hawk from being eaten by wild animals.";
                break;
            case 3:
                questDescription = "Run around in circles for a bit.";
                break;
            case 4:
                questDescription = "Hang on while I think of another quest.";
                break;
            case 5:
                questDescription = "Go to the farthest corner of the world. Then clean it.";
                break;
            case 6:
                questDescription = "Grab me some ice cream, I'm starving!";
                break;
            case 7:
                questDescription = "Fix our over population problem. Or was it endangered...";
                break;
            case 8:
                questDescription = "Find a date for the royal ball. Ask the princess, or prince. I don't judge.";
                break;
            case 9:
                questDescription = "Find your spirit animal. My guess is it's a chicken. A baby chicken.";
                break;

        }
    }
    /**
     * Change the attack power by multiplying by the damage from the combo
     * @param amount Amount to multiply damage by
     */
    public void comboDamage(double amount){
        touchPower *= amount;
    }

    /**
     * Undo the attack power by dividing by the damage from the combo
     * @param amount Amount to undo damage by
     */
    public void undoComboDamage(double amount){
        touchPower /= amount;
    }

    /**
     * Change the attack power by multiplying by the artifact amount
     * @param amount Amount to multiply damage by
     */
    public void artifactDamage(double amount){
        touchPower /= artifactDamage;
        touchPower *= amount;
        artifactDamage = amount;
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

    public int getArtifacts(){return artifacts;}
    public void setArtifacts(int amount){artifacts = amount;}
    public boolean hasRetired(){return retired;}

    public String getTouchPowerString(){return Gold.getNumberWithSuffix(touchPower);}
    public double getTouchPower(){return touchPower;}
    public void setTouchPower(double _power){touchPower = _power;}
    public int getQuestCompleted(){return questCompleted+1;}
    public int getQuestRequired(){return questRequired;}
    public double getQuestProgress(){return questProgress;}
    public String getQuestDescription(){return questDescription;}

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
            setArtifacts(jData.artifactsUnlocked);
            retired = jData.hasRetired;
            questSelect = jData.questSelect;
            setQuestDescription(questSelect);
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
        jData.artifactsUnlocked = getArtifacts();
        jData.hasRetired = retired;
        jData.questSelect = questSelect;
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
        newQuest();
        touchCost = 10;
        touchLevel = 1;
    }

    /**
     * Resets fully
     */
    public void hardReset(){
        retired = false;
        artifacts = 0;
    }
}

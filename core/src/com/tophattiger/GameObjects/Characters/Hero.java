package com.tophattiger.GameObjects.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Combos.ComboBuff;
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

    int questCompleted,questRequired, touchLevel,inheritance,questSelect;
    int volCompleted, volRequired;
    double questProgress,touchCost,touchPower,artifactDamage, comboDamage, dps;
    double volProgress;
    float animationTime,levelAnimationTime, dpsTime, dpsStop, adTime;
    boolean level,retired, dpsStart, adActivate;
    Random rand;
    Image picture;
    String name,questDescription,volDescription;
    Animation idle,attack,animation,levelAnimation;
    TextureRegion frame,levelFrame;
    GameRenderer game;
    ComboBuff adBuff; //Using combo buff because very similar

    /**
     * The hero stores the click data, quest data, name and level
     */
    public Hero(GameRenderer _game){
        game = _game;
        volCompleted = questCompleted = inheritance= 0;
        touchPower = artifactDamage = comboDamage = 1;
        volProgress = questProgress = 0;
        rand = new Random();
        newQuest();
        newVol();
        dpsStop = 3;
        dps = dpsTime = animationTime = adTime = 0;
        touchLevel = 1;
        touchCost = 10;
        adActivate = dpsStart = false;
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
        if(adActivate){
            if(adTime <= 0){
                adActivate = false;
                adBuff.undo();
            }
            adTime -= delta;
        }
        if(dpsStart){
            dpsStop -= delta;
            dpsTime += delta;
        }
        if(questProgress >= questRequired){
            questProgress = 0;
            questRequired += 10*questCompleted;
            questCompleted ++;
            newQuest();
            volProgress++;
            if(volProgress >= volRequired){
                volProgress = 0;
                volRequired += 10*volCompleted;
                volCompleted ++;
                newVol();
            }
        }
        if(dpsStop <= 0){
            dpsTime = 0.00001f;
            dpsStop = 3;
            dps = 0;
            dpsStart = false;
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
        frame = animation.getKeyFrame(animationTime,true);
        batch.draw(frame, positionX, positionY, 256, 256);
        if(level){
            levelFrame = levelAnimation.getKeyFrame(levelAnimationTime);
            batch.draw(levelFrame, positionX - 80, positionY - 40, 256, 256);
        }
    }

    /**
     * Level up the touch, cost and play animation
     */
    public void levelTouch(){
        touchLevel ++;
        touchCost = touchCost * 1.5;
        touchPower = levelAlg(touchLevel);
        levelAnimation();
        game.getTable().updateInheritance();
    }

    private double levelAlg(int touchLevel){
        return touchLevel * 1.2;
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
        dps ++;
        dpsStop = 3;
        dpsStart = true;
        if(animation != attack) {
            animation = attack;
            animationTime = 0;
        }
    }

    /**
     * Restart the game with artifactsUnlocked based off of how many enemies defeated and other stats
     */
    public void retire(){
        retired = true;
        inheritance += touchLevel; //Need to come up with algorithm
    }

    public int getPossibleInheritance(){
        return touchLevel;
    }

    /**
     * subtract inheritance after buying artifact
     */
    public void buyArtifact(double cost){
        inheritance -= cost;
    }

    public void newVol(){
        switch (volCompleted){
            case 0:
                volDescription = "A Baby's First Click";
                break;
            case 1:
                volDescription = "The Clickening";
                break;
            default:
                volDescription = "The Final Click";
        }
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
                questDescription = "Raise your self-esteem by defeating animals";
                break;
            case 1:
                questDescription = "Find the holy stick of butt scratching";
                break;
            case 2:
                questDescription = "Kill some slimes to get jelly for my toast";
                break;
            case 3:
                questDescription = "Run around in circles for a bit";
                break;
            case 4:
                questDescription = "Hang on while I think of another quest";
                break;
            case 5:
                questDescription = "Go to the farthest corner of the world. Then clean it";
                break;
            case 6:
                questDescription = "Grab me some ice cream, I'm starving!";
                break;
            case 7:
                questDescription = "Fix our over population problem. Or were they endangered...";
                break;
            case 8:
                questDescription = "Find a date for the royal ball. Ask the princess, or prince. I don't judge";
                break;
            case 9:
                questDescription = "Get some ingredients for rabbit stew";
                break;

        }
    }

    public double totalPower(double amount){
        double power = amount;
        power *= comboDamage;
        power *= artifactDamage;
        return power;
    }

    public ComboBuff makeAdBuff(){
        int buffSelect = rand.nextInt(3);
        switch (buffSelect){
            case 0:
                adBuff = new ComboBuff(1.5,10, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
                adTime = 60;
                break;
            case 1:
                adBuff = new ComboBuff(1.5,10, ComboBuff.TYPE.HERODAMAGE,game);
                adTime = 60;
                break;
            case 2:
                adBuff = new ComboBuff(1.5,10, ComboBuff.TYPE.GOLD,game);
                adTime = 120;
        }
        return adBuff;
    }

    public void AdActivate(){
        adActivate = true;
        adBuff.activate();
    }

    /**
     * Change the attack power by multiplying by the damage from the combo
     * @param amount Amount to multiply damage by
     */
    public void comboDamage(double amount){
        comboDamage *= amount;
    }

    /**
     * Undo the attack power by dividing by the damage from the combo
     * @param amount Amount to undo damage by
     */
    public void undoComboDamage(double amount){
        comboDamage /= amount;
    }

    /**
     * Change the attack power by multiplying by the artifact amount
     * @param amount Amount to multiply damage by
     */
    public void artifactDamage(double amount){
        artifactDamage *= amount;
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

    public boolean hasRetired(){return retired;}
    public int getInheritance(){return inheritance;}

    public String getTouchPowerString(){return Gold.getNumberWithSuffix(totalPower(touchPower));}
    public double getTouchPower(){return totalPower(touchPower);}
    public String getNextTouchPowerString(){return Gold.getNumberWithSuffix(totalPower(levelAlg(touchLevel+1)));}
    public String getDPSString(){return dpsTime == 0 ? "0" : Gold.getNumberWithSuffix((int)((dps*totalPower(touchPower))/dpsTime));}
    public void setTouchPower(double _power){touchPower = _power;}
    public int getQuestCompleted(){return questCompleted;}
    public int getQuestRequired(){return questRequired;}
    public double getQuestProgress(){return questProgress;}
    public String getQuestDescription(){return questDescription;}
    public int getVolCompleted(){return volCompleted;}
    public int getVolRequired(){return volRequired;}
    public double getVolProgress(){return volProgress;}
    public String getVolDescription(){return volDescription;}

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
            volCompleted = jData.volCompleted;
            volRequired = jData.volRequired;
            volProgress = jData.volProgress;
            newVol();
            setName(jData.name);
            setTouchCost(jData.touchCost);
            setTouchLevel(jData.touchLevel);
            retired = jData.hasRetired;
            questSelect = jData.questSelect;
            setQuestDescription(questSelect);
            inheritance = jData.inheritance;
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
        jData.volCompleted = volCompleted;
        jData.volProgress = volProgress;
        jData.volRequired = volRequired;
        jData.touchCost = getTouchCost();
        jData.touchLevel = getTouchLevel();
        jData.name = getName();
        jData.hasRetired = retired;
        jData.questSelect = questSelect;
        jData.inheritance = inheritance;
    }

    /**
     * Reset the class to default
     */
    public void reset(){
        touchPower = 1;
        volCompleted = questCompleted = 0;
        volProgress =  questProgress = 0;
        volRequired = questRequired = 10;
        name = "Hero";
        newQuest();
        newVol();
        touchCost = 10;
        touchLevel = 1;
    }

    /**
     * Resets fully
     */
    public void hardReset(){
        retired = false;
        inheritance = 0;
    }
}

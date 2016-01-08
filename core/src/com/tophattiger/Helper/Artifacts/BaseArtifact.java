package com.tophattiger.Helper.Artifacts;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 10/8/2015.
 */
public class BaseArtifact {
    public enum TYPE{
        HERODAMAGE,ALLHELPERDAMAGE,ENEMYGOLD
    }

    String description;
    double amount,cost,originalAmount,currentAmount;
    int level;
    boolean offering;
    TYPE type;
    Image picture;
    GameRenderer game;

    /**
     * Artifact that the user can buy after retiring and can then upgrade
     * @param _amount Amount the artifact does
     * @param _cost Cost of artifact
     * @param _type Type of artifact
     * @param _game Game to put in
     */
    public BaseArtifact(double _amount, int _cost, TYPE _type, GameRenderer _game){
        originalAmount = _amount;
        type = _type;
        game = _game;
        cost = _cost;
        offering = false;
        currentAmount = 0;
        level = 0;
        picture = new Image(AssetLoader.textureAtlas.findRegion(getName()));
        picture.setSize(128, 128);
        amount = originalAmount;
        setDescription();
    }

    /**
     * Activate the artifact. Check what the type is and activate the corresponding artifact
     */
    private void activate(){
        if(type == TYPE.ALLHELPERDAMAGE){
            game.getHelpers().artifactDamage(currentAmount);
        }
        else if(type == TYPE.HERODAMAGE){
            game.getHero().artifactDamage(currentAmount);
        }
        else if(type == TYPE.ENEMYGOLD){
            game.artifactEnemyGold(currentAmount);
        }
    }

    /**
     * Increase level and amount. Reset the description and cost and play character level animation
     */
    public void level(){
        level ++;
        currentAmount = amount;
        amount *= 1.2;
        setDescription();
        setCost();
        game.getHero().levelAnimation();
        activate();
    }

    /**
     * Set the description for the upgrade table
     */
    private void setDescription(){
        description = "Multiply ";
        if(type == TYPE.ALLHELPERDAMAGE){
            description += "all helper damage ";
        }
        else if(type == TYPE.HERODAMAGE){
            description += "the hero's damage ";
        }
        else if(type == TYPE.ENEMYGOLD){
            description += "gold dropped by enemies ";
        }
    }

    /**
     * Get the name of the artifact based off of it's type
     * @return Name of artifact
     */
    public String getName(){
        if(type == TYPE.ALLHELPERDAMAGE)
            return "Moral-Banner";
        else if(type == TYPE.HERODAMAGE)
            return "Cosmic-Ghost-Sword";
        else if(type == TYPE.ENEMYGOLD)
            return "Golden-Skinning-Knife";
        return "";
    }
    public String getDescription(){
        return description;
    }

    public void setCost(){
        cost = (int)(0.5*(level)*(level) + 2);
    }

    public void switchOffering(){offering = !offering;}

    public boolean isOffering(){return offering;}

    public double getCost(){return cost;}

    public int getLevel(){return level;}

    public Image getPicture(){return picture;}

    public String getCurrentAmountString(){return Gold.getNumberWithSuffix(currentAmount);}
    public String getNextAmountString(){return Gold.getNumberWithSuffix(amount);}

    /**
     * Reset to default values and reset description and cost
     */
    public void reset(){
        amount = originalAmount;
        currentAmount = 0;
        level = 0;
        offering = false;
        setDescription();
        setCost();
    }

    /**
     * Load level, amount and reset description and cost
     * @param i Index for information
     */
    public void load(int i){
        if(DataManagement.JsonData.artifacts != null) {
            level = DataManagement.JsonData.artifacts.get(i);
            amount = 1.5*(level+1);
            currentAmount = 1.5*level;
            setDescription();
            setCost();
            if(level > 0)
                activate();
        }
    }

    /**
     * Save the level
     */
    public void save(){
        DataManagement.JsonData.artifacts.add(level);
    }

}

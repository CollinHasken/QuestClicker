package com.tophattiger.Helper;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tophattiger.GameObjects.Characters.GeneralHelper;

/**
 * Created by Collin on 7/1/2015.
 */
public class Buff {

    public enum TYPE{
        HELPERDAMAGE,HERODAMAGE,ALLHELPERDAMAGE,GOLD
    }

    int order;
    String name,description,stat,level,total;
    Drawable inactive,active;
    TYPE type;
    double amount;
    boolean isActive;

    /**
     * Buff that become unlocked when a helper is leveled to a certain level
     * @param _description Description for the buff
     * @param _amount Amount it holds
     * @param _type Type of buff
     */
    public Buff(String _description, double _amount, TYPE _type){
        amount = _amount;
        type = _type;
        description = _description;
        isActive = false;
    }

    /**
     * Activate the buff. Check what the type is and activate the corresponding buff
     * @param helper Helper to put the buff on
     */
    public void activate(GeneralHelper helper){
        if(type == TYPE.HELPERDAMAGE){
            helper.buffPower(1+amount);
        }
        else if(type == TYPE.ALLHELPERDAMAGE){
            helper.buffAllPower(1+amount);
        }
        else if(type == TYPE.HERODAMAGE){
            helper.getHelpers().getGame().getHero().comboDamage(1+amount);
        }
        else if(type == TYPE.GOLD){

        }
        isActive = true;
    }

    /**
     * Set the description of the buff in the buff table
     * @param i Order of the buff out of 9
     */
    public void setDescription(int i){
        order = i + 1;
        name = Integer.toString(order);
        description += "\n";
        setLevel(order);
        setStat(amount);
        total = level + description + stat;
        active = new TextureRegionDrawable(com.tophattiger.Helper.Data.AssetLoader.textureAtlas.findRegion(name+"a"));
        inactive = new TextureRegionDrawable(com.tophattiger.Helper.Data.AssetLoader.textureAtlas.findRegion(name+"i"));
    }

    /**
     * Set the level requirement for the description
     * @param i Order of the buff out of 9
     */
    private void setLevel(int i){
        level = "Required Level: ";
        if (i == 1)level += "10";
        else if(i ==2)level +="25";
        else if(i ==3)level +="50";
        else if(i ==4)level +="100";
        else if(i ==5)level +="200";
        else if(i ==6)level +="250";
        else if(i ==7)level +="500";
        else if(i ==8)level +="750";
        else if(i ==9)level +="1000";
        level += "\n";
    }

    /**
     * Set the stats for the description
     * @param amount Amount for the buff
     */
    private void setStat(double amount){
        stat ="Increase ";
        if(type == TYPE.HELPERDAMAGE){
            stat += "this character's damage by ";
        }
        else if(type == TYPE.ALLHELPERDAMAGE){
            stat += "all helpers' damage by ";
        }
        else if(type == TYPE.HERODAMAGE){
            stat += "the hero's damage by ";
        }
        else if(type == TYPE.GOLD){
            stat += "all gold dropped by ";
        }
        amount *= 100;
        stat += com.tophattiger.Helper.Data.Gold.getNumberWithSuffix(amount) + "%";
    }

    public String getDesc(){
        return total;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public Drawable getImage(){
        if(isActive)return active;
        return inactive;
    }

    public void reset(){
        isActive = false;
    }
}

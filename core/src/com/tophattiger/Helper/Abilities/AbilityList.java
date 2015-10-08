package com.tophattiger.Helper.Abilities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameWorld.GameRenderer;

/**
 * Created by Collin on 8/2/2015.
 */
public class AbilityList {
    BaseAbility ability1,ability2,ability3,ability4;
    Array<BaseAbility> abilities = new Array<BaseAbility>();
    int abilitiesUnlocked;

    /**
     * List of abilities the user can upgrade and use
     * Has unique aspect, duration, image and level
     * @param game  Game to attach abilities to
     */
    public AbilityList(GameRenderer game){
        createAbilities(game);
        abilitiesUnlocked = 1;
    }

    /**
     * Create the abilities and add to list
     * @param game Game to put abilites to
     */
    private void createAbilities(GameRenderer game){
        ability1 = new BaseAbility(BaseAbility.TYPE.BIGDAMAGE,game);
        abilities.add(ability1);
        ability2 = new BaseAbility(BaseAbility.TYPE.HELPERSPEED,game);
        abilities.add(ability2);
        ability3 = new BaseAbility(BaseAbility.TYPE.GOLDDROP,game);
        abilities.add(ability3);
        ability4 = new BaseAbility(BaseAbility.TYPE.RETIRE,game);
        abilities.add(ability4);
    }

    /**
     * Add the actors to the scene
     * @param stage Stage to put abilities into
     */
    public void addActors(Stage stage){
        for(int i = 0; i < abilities.size;i++){
          stage.addActor(abilities.get(i));
        }
    }

    /**
     * Function to move the abilities over when the upgrade table is clicked
     */
    public void move(){
        for(int i = 0; i < abilitiesUnlocked;i++){
            abilities.get(i).move();
        }
    }

    /**
     * Add an ability to be seen
     */
    public void addAbility(){
        abilitiesUnlocked ++;
        abilities.get(abilitiesUnlocked-1).transformCoordinates();
    }

    public BaseAbility getAbility(){
        return abilities.get(abilitiesUnlocked-1);
    }

    public BaseAbility getAbility(int i){
        return abilities.get(i);
    }

    public int getAbilitiesUnlocked(){return abilitiesUnlocked;}
    public void setAbilitiesUnlocked(int _abilitiesUnlocked){abilitiesUnlocked= _abilitiesUnlocked;}
    public int getAbilityMax(){return abilities.size;}
    public Array<BaseAbility> getAbilities(){return abilities;}

    /**
     * Reset the abilities and the amount unlocked
     */
    public void reset(){
        for(int i = 0;i< getAbilitiesUnlocked();i++){
            abilities.get(i).reset();
        }
        abilitiesUnlocked = 1;
    }

    /**
     * Save each ability
     */
    public void save(){
        for(int i = 0;i< getAbilitiesUnlocked();i++){
            abilities.get(i).save();
        }
    }

    /**
     * Load each ability
     */
    public void load(){
        for(int i = 0;i< getAbilitiesUnlocked();i++){
            abilities.get(i).load(i);
        }
    }
}

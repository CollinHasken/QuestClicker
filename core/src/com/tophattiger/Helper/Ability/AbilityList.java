package com.tophattiger.Helper.Ability;

import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.*;

/**
 * Created by Collin on 8/2/2015.
 */
public class AbilityList {
    com.tophattiger.Helper.Ability.Ability ability1,ability2,ability3,ability4;
    Array<com.tophattiger.Helper.Ability.Ability> abilities = new Array<com.tophattiger.Helper.Ability.Ability>();
    int abilitiesUnlocked;

    public AbilityList(GameRenderer game){
        createAbilities(game);
        abilitiesUnlocked = 1;
    }

    private void createAbilities(GameRenderer game){
        ability1 = new com.tophattiger.Helper.Ability.Ability(com.tophattiger.Helper.Ability.Ability.TYPE.BIGDAMAGE,game);
        abilities.add(ability1);
        ability2 = new com.tophattiger.Helper.Ability.Ability(com.tophattiger.Helper.Ability.Ability.TYPE.HELPERSPEED,game);
        abilities.add(ability2);
        ability3 = new com.tophattiger.Helper.Ability.Ability(com.tophattiger.Helper.Ability.Ability.TYPE.GOLDDROP,game);
        abilities.add(ability3);
      //  ability4 = new Ability(Ability.TYPE.RETIRE,game);
       // abilities.add(ability4);
    }

    public void addActors(com.badlogic.gdx.scenes.scene2d.Stage stage){
        for(int i = 0; i < abilities.size;i++){
          stage.addActor(abilities.get(i));
        }
    }

    public void move(){
        for(int i = 0; i < abilitiesUnlocked;i++){
            abilities.get(i).move();
        }
    }

    public void addAbility(){
        abilitiesUnlocked ++;
        abilities.get(abilitiesUnlocked-1).transformCoordinates();
    }

    public com.tophattiger.Helper.Ability.Ability getAbility(){
        return abilities.get(abilitiesUnlocked-1);
    }

    public com.tophattiger.Helper.Ability.Ability getAbility(int i){
        return abilities.get(i);
    }

    public int getAbilitiesUnlocked(){return abilitiesUnlocked;}
    public void setAbilitiesUnlocked(int _abilitiesUnlocked){abilitiesUnlocked= _abilitiesUnlocked;}
    public int getAbilityMax(){return abilities.size;}
    public Array<com.tophattiger.Helper.Ability.Ability> getAbilities(){return abilities;}

    public void reset(){
        for(int i = 0;i< getAbilitiesUnlocked();i++){
            abilities.get(i).reset();
        }
        abilitiesUnlocked = 1;
    }

    public void save(){
        for(int i = 0;i< getAbilitiesUnlocked();i++){
            abilities.get(i).save();
        }
    }
    public void load(){
        for(int i = 0;i< getAbilitiesUnlocked();i++){
            abilities.get(i).load(i);
        }
    }
}

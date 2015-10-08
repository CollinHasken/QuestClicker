package com.tophattiger.GameObjects.Characters;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 7/1/2015.
 */
public class Helpers {
    double autoPower,autoGold;
    Array<GeneralHelper> helpers = new Array<GeneralHelper>();
    Helper1 helper1;
    Helper2 helper2;
    GameRenderer game;

    /**
     * List that holds the Helpers
     * @param _game Game to make the helpers in
     */
    public Helpers(GameRenderer _game){
        game = _game;
        createHelpers();
        autoPower = autoGold = 0;
    }

    /**
     * Create the helpers and add them to the list
     */
     private void createHelpers(){
        helper1 = new Helper1(this);
        helper2 = new Helper2(this);

        helpers.add(helper1);
        helpers.add(helper2);
    }

    /**
     * Add the helper actors to the stage
     * @param stage Stage to add the helpers to
     */
    public void addActors(Stage stage){
        for(int i = 0;i<helpers.size;i++) {
            stage.addActor(helpers.get(i));
        }
    }

    /**
     * Set the helpers gold and power and then retrieve it
     */
    public void load(){
        if (helpers.size != 0){
            for(int i = 0; i < DataHolder.helperAmount;i++){
                helpers.get(i).set();
                autoPower += helpers.get(i).getAutoPower();
                autoGold += helpers.get(i).getAutoGold();
            }
        }
    }

    /**
     * Load the helper data from the JSON
     */
    public void loadGame(){
        for(int i = 0; i < DataManagement.JsonData.helpers.size/3;i++){
            helpers.get(i).load(i);
        }
    }

    /**
     * Save the helper data into the JSON
     */
    public void saveGame(){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).save();
        }
    }

    /**
     * Add the gold gained from time when the game was closed
     */
    public void autoGold(){
        Gold.add(autoGold * DataHolder.timeDif);
    }


    public GeneralHelper getHelper(int i){
        return helpers.get(i);
    }

    /**
     * Level the helper's power and then update the total power
     * @param helper Helper to level up
     */
    public void levelPower(GeneralHelper helper){
        autoPower -= helper.getAutoPower();     //Take away current amount of gold and power from the total
        autoGold -= helper.getAutoGold();       //gold and power
        helper.levelPower();                    //Level
        autoPower += helper.getAutoPower();     //Add back the new power and gold
        autoGold += helper.getAutoGold();
    }

    /**
     * Level the helper's gold and then update the total gold
     * @param helper Helper to level up
     */
    public void levelGold(GeneralHelper helper){
        autoPower -= helper.getAutoPower();     //Take away current amount of gold and power from the total
        autoGold -= helper.getAutoGold();       //gold and power
        helper.levelGold();                     //Level
        autoPower += helper.getAutoPower();     //Add back the new power and gold
        autoGold += helper.getAutoGold();
    }

    /**
     * Set amount of power extra from all power buff
     * @param amount    Amount to multiply all damage
     */
    public void buffAllPower(double amount){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).setBuffAllPower(amount);
        }
    }

    /**
     * Set amount of power extra from combo damage
     * @param amount    Amount to multiply all damage
     */
    public void comboDamage(double amount){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).setComboPower(amount);
        }
    }

    /**
     * Undo amount of power extra from combo damage
     */
    public void undoComboDamage(){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).undoComboPower();
        }
    }

    /**
     * Multiply each helper's power by the artifacts damage
     * @param amount Amount to multiply all damage
     */
    public void artifactDamage(double amount){
        for(int i =0; i < DataHolder.helperAmount;i++){
            helpers.get(i).setArtifactPower(amount);
        }
    }

    /**
     * Increase helper attack speed
     * @param amount    Amount to increase attack speed
     * @param length    How long to keep extra speed
     */
    public void abilitySpeed(double amount, int length){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).abilitySpeed(amount,length);
        }
    }

    public int getMax(){return helpers.size;}

    public GameRenderer getGame(){return game;}

    /**
     * Reset all helpers
     */
    public void reset(){
        for(int i = 0; i<DataHolder.helperAmount; i++){
            getHelper(i).reset();
        }
        autoPower = 0;
        autoGold = 0;
    }
}

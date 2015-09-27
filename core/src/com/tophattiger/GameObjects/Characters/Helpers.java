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

    public Helpers(GameRenderer _game){
        game = _game;
        createHelpers();
        autoPower = autoGold = 0;
    }

    void createHelpers(){
        helper1 = new Helper1(this);
        helper2 = new Helper2(this);

        helpers.add(helper1);
        helpers.add(helper2);
    }

    public void addActors(Stage stage){
        for(int i = 0;i<helpers.size;i++) {
            stage.addActor(helpers.get(i));
        }
    }

    public void load(){
        if (helpers.size != 0){
            for(int i = 0; i < DataHolder.helperAmount;i++){
                helpers.get(i).set();
                autoPower += helpers.get(i).getAutoPower();
                autoGold += helpers.get(i).getAutoGold();
            }
        }
    }

    public void loadGame(){
        for(int i = 0; i < DataManagement.JsonData.helpers.size/3;i++){
            helpers.get(i).load(i);
        }
    }

    public void saveGame(){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).save();
        }
    }

    public void autoGold(){
        Gold.add(autoGold * DataHolder.timeDif);
    }


    public GeneralHelper getHelper(int i){
        return helpers.get(i);
    }

    public void levelPower(GeneralHelper helper){
        autoPower -= helper.getAutoPower();
        autoGold -= helper.getAutoGold();
        helper.levelPower();
        autoPower += helper.getAutoPower();
        autoGold += helper.getAutoGold();
    }

    public void levelGold(GeneralHelper helper){
        autoPower -= helper.getAutoPower();
        autoGold -= helper.getAutoGold();
        helper.levelGold();
        autoPower += helper.getAutoPower();
        autoGold += helper.getAutoGold();
    }

    public void buffAllPower(double amount){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).setBuffAllPower(amount);
        }
    }

    public void comboDamage(double amount){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).setComboPower(amount);
        }
    }

    public void undoComboDamage(){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).undoComboPower();
        }
    }

    public void abilitySpeed(double amount, int length){
        for(int i = 0; i < DataHolder.helperAmount;i++){
            helpers.get(i).abilitySpeed(amount,length);
        }
    }

    public int getMax(){return helpers.size;}

    public GameRenderer getGame(){return game;}

    public void reset(){
        for(int i = 0; i<DataHolder.helperAmount; i++){
            getHelper(i).reset();
        }
        autoPower = 0;
        autoGold = 0;
    }
}

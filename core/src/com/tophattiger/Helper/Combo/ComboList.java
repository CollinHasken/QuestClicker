package com.tophattiger.Helper.Combo;

import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.*;

/**
 * Created by Collin on 7/15/2015.
 */
public class ComboList {

    com.tophattiger.Helper.Combo.ComboBuff combo1,combo2,combo3,combo4,combo5,combo6,combo7,combo8,combo9;
    Array<com.tophattiger.Helper.Combo.ComboBuff> combos = new Array<com.tophattiger.Helper.Combo.ComboBuff>();
    int combosUnlocked;
    GameRenderer game;

    public ComboList(GameRenderer _game){
        game = _game;
        createCombos();
        combosUnlocked = 1;
    }

    private void createCombos(){
        combo1 = new com.tophattiger.Helper.Combo.ComboBuff(1.25,10, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo1);
        combo2 = new com.tophattiger.Helper.Combo.ComboBuff(1.2,25, com.tophattiger.Helper.Combo.ComboBuff.TYPE.HERODAMAGE,game);
        combos.add(combo2);
        combo3 = new com.tophattiger.Helper.Combo.ComboBuff(1.4,50, com.tophattiger.Helper.Combo.ComboBuff.TYPE.GOLD,game);
        combos.add(combo3);
        combo4 = new com.tophattiger.Helper.Combo.ComboBuff(2,100, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo4);
        combo5 = new com.tophattiger.Helper.Combo.ComboBuff(2,250, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo5);
        combo6 = new com.tophattiger.Helper.Combo.ComboBuff(2,500, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo6);
        combo7 = new com.tophattiger.Helper.Combo.ComboBuff(2,750, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo7);
        combo8 = new com.tophattiger.Helper.Combo.ComboBuff(2,1000, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo8);
        combo9 = new com.tophattiger.Helper.Combo.ComboBuff(2,2000, com.tophattiger.Helper.Combo.ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo9);
    }

    public void check(int combo){
        for(int i = 0; i < combosUnlocked; i++){
            combos.get(i).check(combo);
        }
    }

    public void undo(){
        for(int i = 0; i < combosUnlocked; i++){
            combos.get(i).undo();
        }
    }

    public void addCombo(){
        combosUnlocked ++;
    }

    public com.tophattiger.Helper.Combo.ComboBuff getCombo(){
        return combos.get(combosUnlocked-1);
    }

    public com.tophattiger.Helper.Combo.ComboBuff getCombo(int i){
        return combos.get(i);
    }

    public int getComboMax(){
        return combos.size;
    }

    public int getCombosUnlocked(){return combosUnlocked;}
    public void setCombosUnlocked(int _combosUnlocked){combosUnlocked = _combosUnlocked;}

    public void reset(){
        for(int i = 0;i< getCombosUnlocked();i++){
           combos.get(i).reset();
        }
        combosUnlocked = 1;
    }

    public void save(){
        for(int i = 0;i< getCombosUnlocked();i++){
            combos.get(i).save();
        }
    }
    public void load(){
        for(int i = 0;i< getCombosUnlocked();i++){
            combos.get(i).load(i);
        }
    }

}

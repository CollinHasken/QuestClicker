package com.tophattiger.Helper.Combos;

import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameWorld.GameRenderer;

/**
 * Created by Collin on 7/15/2015.
 */
public class ComboList {

    ComboBuff combo1,combo2,combo3,combo4,combo5,combo6,combo7,combo8,combo9;
    Array<ComboBuff> combos = new Array<ComboBuff>();
    int combosUnlocked;
    GameRenderer game;

    /**
     * List of all of the combos the user can buy and upgrade
     * @param _game Game to put the combos into
     */
    public ComboList(GameRenderer _game){
        game = _game;
        createCombos();
        combosUnlocked = 1;
    }

    /**
     * Create the combos and add them to the array
     */
    private void createCombos(){
        combo1 = new ComboBuff(1.25,10, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo1);
        combo2 = new ComboBuff(1.2,25, ComboBuff.TYPE.HERODAMAGE,game);
        combos.add(combo2);
        combo3 = new ComboBuff(1.4,50, ComboBuff.TYPE.GOLD,game);
        combos.add(combo3);
        combo4 = new ComboBuff(2,100, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo4);
        combo5 = new ComboBuff(2,250, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo5);
        combo6 = new ComboBuff(2,500, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo6);
        combo7 = new ComboBuff(2,750, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo7);
        combo8 = new ComboBuff(2,1000, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo8);
        combo9 = new ComboBuff(2,2000, ComboBuff.TYPE.ALLHELPERDAMAGE,game);
        combos.add(combo9);
    }

    /**
     * Check each combo for whether they needed to be activated
     * @param combo Current combo
     */
    public void check(int combo){
        for(int i = 0; i < combosUnlocked; i++){
            combos.get(i).check(combo);
        }
    }

    /**
     * Undo the buff the combo applies
     */
    public void undo(){
        for(int i = 0; i < combosUnlocked; i++){
            combos.get(i).undo();
        }
    }

    /**
     * Increase the amount of combos that have been unlocked
     */
    public void addCombo(){
        combosUnlocked ++;
    }

    public ComboBuff getCombo(){
        return combos.get(combosUnlocked-1);
    }

    public ComboBuff getCombo(int i){
        return combos.get(i);
    }

    public int getComboMax(){
        return combos.size;
    }

    public int getCombosUnlocked(){return combosUnlocked;}
    public void setCombosUnlocked(int _combosUnlocked){combosUnlocked = _combosUnlocked;}

    /**
     * Reset the each combo buff
     */
    public void reset(){
        for(int i = 0;i< getCombosUnlocked();i++){
           combos.get(i).reset();
        }
        combosUnlocked = 1;
    }

    /**
     * Save each combo buff
     */
    public void save(){
        for(int i = 0;i< getCombosUnlocked();i++){
            combos.get(i).save();
        }
    }

    /**
     * Load each combo buff
     */
    public void load(){
        for(int i = 0;i< getCombosUnlocked();i++){
            combos.get(i).load(i);
        }
    }

}

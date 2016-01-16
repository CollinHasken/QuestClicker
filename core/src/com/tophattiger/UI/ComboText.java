package com.tophattiger.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.Helper.Combos.Combo;
import com.tophattiger.Helper.Combos.ComboList;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.DataManagement;

/**
 * Created by Collin on 7/14/2015.
 */
public class ComboText extends Actor {

    final float maxComboTime = 1;
    final int originX = 1647;

    BitmapFont font;
    GlyphLayout comboText,comboAmount;
    ComboList combos;
    Array<Combo> activeCombos = new Array<Combo>();
    Pool<Combo> comboPool = new Pool<Combo>() {
        @Override
        protected Combo newObject() {
            return new Combo();
        }
    };
    int combo;
    float comboTime;
    Combo text;
    Color comboColor;

    /**
     * Manage the combo texts that get generated when the user attacks
     * @param _combos List of combos
     */
    public ComboText(ComboList _combos){
        combos = _combos;
        font = new BitmapFont(Gdx.files.internal("combo.fnt"));
        comboTime = 0;
        comboColor = font.getColor();
        comboText = new GlyphLayout();
        comboAmount = new GlyphLayout();
    }

    /**
     * Manage the time in between taps to still count as a combo and manage dead combo texts
     * @param delta Time between calls
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if(activeCombos.size != 0) {
            for (int i = 0; i < activeCombos.size; i++) {
                if (activeCombos.get(i).isDead()) {
                    comboPool.free(activeCombos.get(i));
                    activeCombos.removeIndex(i);
                }
            }
        }
        if(comboTime < 0){
            comboTime = 0;
            if(DataManagement.JsonData.maxCombo < combo)
                DataManagement.JsonData.maxCombo = combo;
            if(combos.getGame().getAdsController().getSignedInGPGS())
                combos.getGame().getAdsController().stepAchievementGPGS(DataHolder.hundredCombo,combo);
            combo = 0;
            combos.undo();
        }
        else if(comboTime > 0){
            comboTime -= delta;
            for(int i = 0; i < activeCombos.size; i++) {
                activeCombos.get(i).update(delta);
            }
        }
    }

    /**
     * Set the text and draw each combo text
     * @param batch Batch to draw to
     * @param parentAlpha Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(comboTime>0){    //If combo is still going
            for(int i = 0; i < activeCombos.size; i++){ //Go through each combo
                comboAmount.setText(font, activeCombos.get(i).getText(), activeCombos.get(i).setColor(), 0, 0, false);  //Set the text
                if(activeCombos.get(i).isCurrent()){    //If this combo is the most recent one
                    comboText.setText(font, "Combo: ", activeCombos.get(i).getColor(), 0, 0, false);
                    activeCombos.get(i).setX(originX + ((comboAmount.width + comboText.width ) / 2)); //Set the x and text
                    font.draw(batch, comboText, originX + ((comboText.width + comboAmount.width) / 2) - comboAmount.width, activeCombos.get(i).getY()); //Draw the text
                }
                font.draw(batch, comboAmount, activeCombos.get(i).getX(), activeCombos.get(i).getY()); //Draw the amount
            }
        }
    }

    /**
     * Increase the combo and activate combos when the screen has been tapped
     */
    public void tap(){
        combo ++;
        combos.check(combo);        //Check for combos
        comboTime = maxComboTime;

        for(int i = 0; i < activeCombos.size; i++){
            activeCombos.get(i).start();    //Start any active combos
        }
        text = comboPool.obtain();
        text.init(Integer.toString(combo),font.getColor());
        activeCombos.add(text);
    }
}

package com.tophattiger.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.Helper.Combo.Combo;
import com.tophattiger.Helper.Combo.ComboList;

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

    public ComboText(ComboList _combos){
        combos = _combos;
        font = new BitmapFont(Gdx.files.internal("combo.fnt"));
        comboTime = 0;
        comboColor = font.getColor();
        comboText = new GlyphLayout();
        comboAmount = new GlyphLayout();
    }

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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(comboTime>0){
            for(int i = 0; i < activeCombos.size; i++){
                comboAmount.setText(font, activeCombos.get(i).getText(), activeCombos.get(i).setColor(), 0, 0, false);
                if(activeCombos.get(i).isCurrent()){
                    comboText.setText(font, "Combo: ", activeCombos.get(i).getColor(), 0, 0, false);
                    activeCombos.get(i).setX(originX + ((comboAmount.width + comboText.width ) / 2));
                    font.draw(batch, comboText, originX + ((comboText.width + comboAmount.width) / 2) - comboAmount.width, activeCombos.get(i).getY());
                }
                font.draw(batch, comboAmount, activeCombos.get(i).getX(), activeCombos.get(i).getY());
            }
        }
    }

    public void tap(){
        combo ++;
        combos.check(combo);
        comboTime = maxComboTime;

        for(int i = 0; i < activeCombos.size; i++){
            activeCombos.get(i).start();
        }
        text = comboPool.obtain();
        text.init(Integer.toString(combo),font.getColor());
        activeCombos.add(text);
    }
}

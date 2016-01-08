package com.tophattiger.UI.Table.Hero;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.tophattiger.Helper.Combos.ComboBuff;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 7/16/2015.
 */
public class ComboGroup {

    HeroTable table;
    Image picture;
    Label level, description,currentAmount;
    TextButton comboButton;
    ComboBuff combo;

    public ComboGroup(HeroTable _table,ComboBuff _combo){
        table = _table;
        combo = _combo;
        level = new Label("Level " + Gold.getNumberWithSuffix(combo.getLevel()),table.getSkin(),"level");
        currentAmount = new Label(combo.getCurrentAmountString() + " -> " + combo.getNextAmountString(),table.getSkin(),"value");
        description = new Label(combo.getDescription(),table.getSkin(),"combo");
        description.setWrap(true);
        picture = combo.getPicture();
        comboButton = new TextButton(Gold.getNumberWithSuffix(combo.getCost()),table.skin,"gold");
        comboButton.setDisabled(true);
        comboButton.getLabel().setAlignment(Align.right);
        comboButton.padRight(20);
        comboButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!comboButton.isChecked() && combo.getCost() <= Gold.getGold()) {
                    Gold.subtract(combo.getCost());
                    combo.level();
                    comboButton.setText(Gold.getNumberWithSuffix(combo.getCost()));
                    level.setText("Level " + Gold.getNumberWithSuffix(combo.getLevel()));
                    currentAmount.setText(combo.getCurrentAmountString() + " -> " + combo.getNextAmountString());
                    description.setText(combo.getDescription());
                    if (combo.getLevel() == 1 && table.getComboSize() < table.getComboMax()) {
                        table.addCombo();
                    }
                    return true;
                }
                return false;
            }
        });
        updateState();
    }
    public void updateState(){
        if(Gold.isLess(combo.getCost())){
            comboButton.setChecked(true);
        }
        else comboButton.setChecked(false);
    }
    public void reset(){
        level.setText("Level " + Gold.getNumberWithSuffix(combo.getLevel()));
        currentAmount.setText(combo.getCurrentAmountString() + " -> " + combo.getNextAmountString());
        description.setText(combo.getDescription());
        comboButton.setText(Gold.getNumberWithSuffix(combo.getCost()));
    }

    public void add(){
        table.add(picture).width(128).left().padLeft(20f);
        if(table.getNameTextLength()>= 114)
            table.add(description).pad(5, 20, 0, 20).width(560-table.getNameTextLength());
        else
            table.add(description).pad(5, 20, 0, 20).width(446);
        table.add(comboButton).width(200f).row();
        table.add(level).left().padLeft(20f);table.add(currentAmount).colspan(2).right().padRight(40).row();
    }
}

package com.tophattiger.UI.Table.Hero;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.tophattiger.Helper.Ability.Ability;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 8/5/2015.
 */
public class AbilityGroup {
    HeroTable table;
    Image picture;
    Label level, description,currentAmount;
    TextButton abilityButton;
    Ability ability;

    public AbilityGroup(HeroTable _table,Ability _ability){
        table = _table;
        ability = _ability;
        level = new Label("Level " + Gold.getNumberWithSuffix(ability.getLevel()),table.getSkin(),"level");
        currentAmount = new Label("Current: " + Gold.getNumberWithSuffix(ability.getCurrentAmount()),table.getSkin(),"level");
        description = new Label(ability.getDescription(),table.getSkin(),"combo");
        description.setWrap(true);
        description.setAlignment(Align.right);
        picture = ability.getPicture();
        abilityButton = new TextButton(Gold.getNumberWithSuffix(ability.getCost()),table.skin,"gold");
        abilityButton.setDisabled(true);
        abilityButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!abilityButton.isChecked() && ability.getCost() <= Gold.getGold()) {
                    Gold.subtract(ability.getCost());
                    ability.level();
                    abilityButton.setText(Gold.getNumberWithSuffix(ability.getCost()));
                    level.setText("Level " + Gold.getNumberWithSuffix(ability.getLevel()));
                    currentAmount.setText("Current: " + Gold.getNumberWithSuffix(ability.getCurrentAmount()));
                    description.setText(ability.getDescription());
                    if (ability.getLevel() == 1 && table.getAbilitySize() < table.getAbilityMax()) {
                        table.addAbility();
                    }
                    return true;
                }
                return false;
            }
        });
        updateState();
    }
    public void updateState(){
        if(Gold.isLess(ability.getCost())){
            abilityButton.setChecked(true);
        }
        else abilityButton.setChecked(false);
    }
    public void reset(){
        level.setText("Level " + Gold.getNumberWithSuffix(ability.getLevel()));
        currentAmount.setText("Current: " + Gold.getNumberWithSuffix(ability.getCurrentAmount()));
        description.setText(ability.getDescription());
        abilityButton.setText(Gold.getNumberWithSuffix(ability.getCost()));
    }

    public void add(){
        table.add(picture).width(128).left().padLeft(20f);
        if(table.getNameTextLength()>= 114)
            table.add(description).pad(5, 20, 0, 20).width(560-table.getNameTextLength());
        else
            table.add(description).pad(5, 20, 0, 20).width(446);
        table.add(abilityButton).width(200f).row();
        table.add(level).left().padLeft(20f);table.add();table.add(currentAmount).row();
    }
}

package com.tophattiger.UI.Table.Helper;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.tophattiger.GameObjects.Characters.GeneralHelper;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.Gold;
import com.tophattiger.UI.Table.ScrollTable;

/**
 * Created by Collin on 6/2/2015.
 */
public class HelperGroup {

    public Label name, level, pLevel, gLevel;
    public TextButton gold,power,buff;
    ScrollTable table;
    Image picture;
    GeneralHelper helper;

    public HelperGroup(final ScrollTable _table, GeneralHelper _helper){
        helper = _helper;
        helper.set();
        table = _table;
        name = new Label(helper.getName(),table.skin,"name");
        picture = _helper.getPicture();
        level = new Label("Level " + Gold.getNumberWithSuffix(helper.getLevel()),table.skin,"level");
        pLevel = new Label("Power Lvl " + Gold.getNumberWithSuffix(helper.getPLevel()),table.skin,"level");
        gLevel = new Label("Gold Lvl " + Gold.getNumberWithSuffix(helper.getGLevel()),table.skin,"level");
        gold = new TextButton(Gold.getNumberWithSuffix(helper.getAutoGoldCost()),table.skin,"gold");
        gold.setDisabled(true);
        gold.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!gold.isChecked() && Gold.isGreaterEqual(helper.getAutoGoldCost())) {
                    Gold.subtract(helper.getAutoGoldCost());
                    table.helpers.levelGold(helper);
                    gold.setText(Gold.getNumberWithSuffix(helper.getAutoGoldCost()));
                    gLevel.setText("Gold Lvl " + Gold.getNumberWithSuffix(helper.getGLevel()));
                    level.setText("Level " + Gold.getNumberWithSuffix(helper.getLevel()));
                    if (helper.getLevel() == 1 && DataHolder.helperAmount < table.getMaxHelpers()) {
                        table.addHelper();
                    }
                    return true;
                }
                return false;
            }
        });
        power = new TextButton(Gold.getNumberWithSuffix(helper.getAutoPowerCost()),table.skin, "power");
        power.setDisabled(true);
        power.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!power.isChecked() && Gold.isGreaterEqual(helper.getAutoPowerCost())) {
                    Gold.subtract(helper.getAutoPowerCost());
                    table.helpers.levelPower(helper);
                    power.setText(Gold.getNumberWithSuffix(helper.getAutoPowerCost()));
                    pLevel.setText("Power Lvl " + Gold.getNumberWithSuffix(helper.getPLevel()));
                    level.setText("Level " + Gold.getNumberWithSuffix(helper.getLevel()));
                    if(helper.getLevel() == 1 && DataHolder.helperAmount < table.getMaxHelpers()){
                        table.addHelper();
                    }
                    else if(helper.getPLevel() == 1){
                        helper.resetAnimation();
                    }
                    return true;
                }
                return false;
            }
        });
        buff = new TextButton("Buffs",table.skin, "buff");
        buff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                table.buffScreen.show(helper);
                return true;
            }
        });
        level.setAlignment(Align.center);
        pLevel.setAlignment(Align.center);
        gLevel.setAlignment(Align.center);
        updateState();
        add();
    }
    public void updateState(){
        if(Gold.isLess(helper.getAutoGoldCost())){
            gold.setChecked(true);
        }
        else gold.setChecked(false);
        if(Gold.isLess(helper.getAutoPowerCost())){
            power.setChecked(true);
        }
        else power.setChecked(false);
    }
    public void reset(){
        power.setText(Gold.getNumberWithSuffix(helper.getAutoPowerCost()));
        gold.setText(Gold.getNumberWithSuffix(helper.getAutoGoldCost()));
        gLevel.setText("Gold Lvl " + Gold.getNumberWithSuffix(helper.getGLevel()));
        pLevel.setText("Power Lvl " + Gold.getNumberWithSuffix(helper.getPLevel()));
        level.setText("Level " + Gold.getNumberWithSuffix(helper.getLevel()));
    }

    public void add(){
        table.add(picture).width(128).padLeft(20);table.add(level).width(200).padLeft(25);table.add(pLevel).width(200).padLeft(53);table.add(gLevel).width(200).padLeft(25).row();
        table.add(name).padBottom(40f).padLeft(20);table.add(buff).width(200).padLeft(25).center().top();table.add(power).width(200).center().top().padLeft(53);table.add(gold).width(200).padLeft(25).center().top().row();
    }
}

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

    Label name, level, pLevel, gLevel;
    TextButton gold,power,buff;
    ScrollTable table;
    Image picture;
    GeneralHelper helper;

    /**
     * Table cell for helper information for the upgrade table
     * @param _table Table to put into
     * @param _helper Helper this information is for
     */
    public HelperGroup(final ScrollTable _table, GeneralHelper _helper){
        helper = _helper;
        helper.set();
        table = _table;
        name = new Label(helper.getName(),table.getSkin(),"name");
        picture = _helper.getPicture();
        level = new Label("Level " + Gold.getNumberWithSuffix(helper.getLevel()),table.getSkin(),"level");
        pLevel = new Label("Damage\n" + helper.getCurrentTotalPowerString() + " -> " + helper.getNextTotalPowerString(),table.getSkin(),"value");
        gLevel = new Label("Idle Gold\n" + helper.getCurrentTotalGoldString() + " -> " + helper.getNextTotalGoldString(),table.getSkin(),"value");
        gold = new TextButton(Gold.getNumberWithSuffix(helper.getAutoGoldCost()),table.getSkin(),"gold");   //Button to level gold accumulation
        gold.setDisabled(true);
        gold.getLabel().setAlignment(Align.right);
        gold.padRight(20);
        gold.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!gold.isChecked() && Gold.isGreaterEqual(helper.getAutoGoldCost())) {   //Check whether the button is available and you have enough gold
                    Gold.subtract(helper.getAutoGoldCost());    //Subtract cost, level the helper and reset text
                    table.getHelpers().levelGold(helper);
                    gold.setText(Gold.getNumberWithSuffix(helper.getAutoGoldCost()));
                    gLevel.setText("Idle Gold\n" + helper.getCurrentTotalGoldString() + " -> " + helper.getNextTotalGoldString());
                    level.setText("Level " + Gold.getNumberWithSuffix(helper.getLevel()));
                    if (helper.getLevel() == 1 && DataHolder.helperAmount < table.getMaxHelpers()) {
                        if(helper.getName().equals("Bob") && table.getHelpers().getGame().getAdsController().getSignedInGPGS())
                            table.getHelpers().getGame().getAdsController().unlockAchievementGPGS(DataHolder.guildOwner);
                        else if(helper.getName().equals("Nina") && table.getHelpers().getGame().getAdsController().getSignedInGPGS())
                            table.getHelpers().getGame().getAdsController().unlockAchievementGPGS(DataHolder.nina);
                        table.addHelper();      //If this is the first level, create the next helper
                    }
                    return true;
                }
                return false;
            }
        });
        power = new TextButton(Gold.getNumberWithSuffix(helper.getAutoPowerCost()),table.getSkin(), "power"); //Button to level power
        power.setDisabled(true);
        power.getLabel().setAlignment(Align.right);
        power.padRight(20);
        power.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!power.isChecked() && Gold.isGreaterEqual(helper.getAutoPowerCost())) {     //Check whether the button is available and you have enough gold
                    Gold.subtract(helper.getAutoPowerCost());       //Subtract cost, level the helper and reset text
                    table.getHelpers().levelPower(helper);
                    power.setText(Gold.getNumberWithSuffix(helper.getAutoPowerCost()));
                    pLevel.setText("Damage\n" + helper.getCurrentTotalPowerString() + " -> " + helper.getNextTotalPowerString());
                    level.setText("Level " + Gold.getNumberWithSuffix(helper.getLevel()));
                    if(helper.getLevel() == 1 && DataHolder.helperAmount < table.getMaxHelpers()){
                        table.addHelper();      //If this is the first level, create the next helper
                    }
                    else if(helper.getPLevel() == 1){
                        helper.resetAnimation(); //Reset animation to line up with projectile if it first time
                    }
                    return true;
                }
                return false;
            }
        });
        buff = new TextButton("Buffs",table.getSkin(), "buff"); //Button to show buff screen
        buff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                table.getBuffScreen().show(helper);
                return true;
            }
        });
        pLevel.setAlignment(Align.center);
        gLevel.setAlignment(Align.center);
        pLevel.setWrap(true);
        gLevel.setWrap(true);
        updateState();
        add();
    }

    /**
     * Update which buttons should be accessible
     */
    public void updateState(){
        if(Gold.isLess(helper.getAutoGoldCost())){
            gold.setChecked(true);
        }
        else gold.setChecked(false);
        if(Gold.isLess(helper.getAutoPowerCost())){
            power.setChecked(true);
        }
        else power.setChecked(false);
        pLevel.setText("Damage\n" + helper.getCurrentTotalPowerString() + " -> " + helper.getNextTotalPowerString());
        gLevel.setText("Idle Gold\n" + helper.getCurrentTotalGoldString() + " -> " + helper.getNextTotalGoldString());
    }

    /**
     * Reset this groups text
     */
    public void reset(){
        power.setText(Gold.getNumberWithSuffix(helper.getAutoPowerCost()));
        gold.setText(Gold.getNumberWithSuffix(helper.getAutoGoldCost()));
        gLevel.setText("Idle Gold\n" + helper.getCurrentTotalGoldString() + " -> " + helper.getNextTotalGoldString());
        pLevel.setText("Damage\n" + helper.getCurrentTotalPowerString() + " -> " + helper.getNextTotalPowerString());
        level.setText("Level " + Gold.getNumberWithSuffix(helper.getLevel()));
    }

    /**
     * Add the items to the table cell
     */
    private void add(){
        table.add(picture).width(128).padLeft(20);table.add(name).width(200).padLeft(25).padBottom(10).bottom().left();table.add(pLevel).width(200).padLeft(53);table.add(gLevel).width(200).padLeft(25).row();
        table.add(level).bottom().padBottom(10f).padLeft(20);table.add(buff).width(200).padLeft(25).center().top();table.add(power).width(200).center().top().padLeft(53);table.add(gold).width(200).padLeft(25).center().top().row();
    }
}

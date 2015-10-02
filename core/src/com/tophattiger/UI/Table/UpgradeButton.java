package com.tophattiger.UI.Table;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.UI.Menu.NameScreen;

/**
 * Created by Collin on 5/31/2015.
 */
public class UpgradeButton extends Button {

    UpgradeTable table;
    MoveToAction mta = new MoveToAction();

    /**
     * Button to open and close the upgrade table
     * @param skin Skin for looks
     * @param styleName Name for style
     * @param _table Table the button is hooked with
     */
    public UpgradeButton(Skin skin, String styleName, UpgradeTable _table) {
        super(skin, styleName);
        this.setPosition(0, 285);
        this.setSize(64, 390);
        this.setWidth(64);
        DataHolder.open = false;
        table = _table;


        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!NameScreen.SettingName) {
                    mta.reset();
                    mta.setPosition(911, 285);
                    mta.setDuration(0.5f);
                    table.move();
                    if (DataHolder.open) {
                        mta.setX(0);
                        DataHolder.open = false;
                    } else DataHolder.open = true;
                    actor.addAction(mta);
                    table.getGame().getAbilityList().move();
                }
            }
        });
    }
}

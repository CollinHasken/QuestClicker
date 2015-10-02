package com.tophattiger.UI.Table;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Collin on 6/27/2015.
 */
public class SwitchTableButton extends TextButton {

    Table table;
    UpgradeTable upgrade;

    /**
     * Button to switch between upgrade tablers
     * @param _text Text for the button
     * @param _skin Skin for looks
     * @param _style Style name for button
     * @param _table Table to put table into
     * @param _upgrade Upgrade table to switch to
     */
    public SwitchTableButton(String _text,Skin _skin, String _style,Table _table,UpgradeTable _upgrade){

        super(_text,_skin,_style);
        table = _table;
        upgrade = _upgrade;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!isChecked()) {
                    upgrade.setAsContainer(table);
                    return true;
                }
                return false;
            }
        });
    }
}

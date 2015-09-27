package com.tophattiger.UI.Menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.DataManagement;

/**
 * Created by Collin on 6/3/2015.
 */

public class ResetButton extends ImageButton {

    GameRenderer game;

    public ResetButton(Skin skin, String styleName, GameRenderer _game) {
        super(skin, styleName);
        game = _game;
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DataManagement.reset(game);
                return true;
            }
        });
    }
}

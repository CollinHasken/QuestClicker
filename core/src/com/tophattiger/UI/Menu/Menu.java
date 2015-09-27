package com.tophattiger.UI.Menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;

/**
 * Created by Collin on 8/1/2015.
 */
public class Menu extends Table {

    Drawable sprite = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("MenuPopup"));
    ImageButton exit;
    ResetButton resetButton;
    Label title,version;
    Menu menuPopup;
    TextureRegion fade;

    public Menu(Skin skin,GameRenderer game) {
        super();
        menuPopup = this;
        setBackground(sprite);
        setHeight(900);
        setWidth(500);
        setPosition(710,90);
        resetButton = new ResetButton(skin,"reset",game);
        fade = AssetLoader.textureAtlas.findRegion("fade");
        createAssets(skin);
        addActors();
        setVisible(false);
    }

    public void show() {
        NameScreen.SettingName = true;
        setVisible(true);
    }

    public void createAssets(Skin skin){
        title = new Label("Quest Clicker",skin,"title");
        version = new Label("Version: " + GameRenderer.getNewestVersionString(),skin,"version");
        exit = new ImageButton(skin, "exit");
        exit.setSize(64, 64);
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                close();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(fade,0,0);
        super.draw(batch, parentAlpha);
    }

    public void addActors(){
        add(exit).pad(20,0,50,20).right().top().row();
        add(title).padBottom(50).center().row();
        add(resetButton).center().row();
        add(version).expand().center().bottom().padBottom(30);
    }

    public void close(){
        menuPopup.setVisible(false);
        NameScreen.SettingName = false;
    }
}

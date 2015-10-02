package com.tophattiger.UI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;

/**
 * Created by Collin on 8/1/2015.
 */
public class NameScreen extends Table {
    public static boolean SettingName;

    Drawable sprite = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("NameScreen"));
    ImageButton go;
    TextField text;
    Label title,nameAsk;
    NameScreen nameScreen;
    GameRenderer game;

    /**
     * Popup that allows the user to insert a name for their hero.
     * Will show up first time playing, reseting the game, and retiring
     * @param skin Skin for the looks
     * @param _game Game to put into
     */
    public NameScreen(Skin skin,GameRenderer _game) {
        super();
        game = _game;
        nameScreen = this;
        setBackground(sprite);
        setHeight(1080f);
        setWidth(1920f);
        setPosition(0, 0);
        createAssets(skin);
        addActors();
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               return true;
            }
        });
        setVisible(false);
    }

    /**
     * Show the popup
     */
    public void show() {
        SettingName = true;
        setVisible(true);
        this.toFront();
    }

    /**
     * Create the assets for the popup
     * @param skin Skin for looks
     */
    private void createAssets(Skin skin){
        title = new Label("What is your hero's name?",skin,"nameTitle");
        nameAsk = new Label("Name: ",skin,"nameAsk");
        text = new TextField("Hero's Name",skin,"name");
        text.setMaxLength(12);
        text.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(text.getText().equals("Hero's Name")){
                    text.setText("");
                }
                return true;
            }
        });
        go = new ImageButton(skin, "go");
        go.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                game.getHero().setName(text.getText());
                Gdx.input.setOnscreenKeyboardVisible(false);
                game.getTable().updateName();
                nameScreen.setVisible(false);
                game.getMenu().close();
                SettingName = false;
                return true;
            }
        });
    }

    /**
     * Add actors to the popup(table)
     */
    private void addActors(){
        this.top().left();
        add().width(610).height(250).row();
        add();add(title).colspan(3).top().left().row();
        add();add(nameAsk).pad(50,170,10,0).right();add(text).bottom().left().padBottom(10).width(260).left();add(go).padTop(20).bottom().right();
    }
}

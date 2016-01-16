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
    ImageButton exit,stats,achievements,rate;
    ResetButton resetButton;
    Label title,version;
    Menu menuPopup;
    TextureRegion fade;
    GameRenderer game;

    /**
     * Menu which hold the reset button, version of the game and more
     * @param skin Skin for the looks
     * @param game Game to put into
     */
    public Menu(Skin skin,GameRenderer game) {
        super();
        menuPopup = this;
        this.game = game;
        setBackground(sprite);
        setHeight(900);
        setWidth(500);
        setPosition(710, 90);
        resetButton = new ResetButton(skin,"reset",game);
        fade = AssetLoader.textureAtlas.findRegion("fade");
        createAssets(skin);
        addActors();
        setVisible(false);
    }

    /**
     * Show the menu
     */
    public void show() {
        NameScreen.SettingName = true;
        setVisible(true);
    }

    /**
     * Create the assets for the menu
     * @param skin Skin to create the looks
     */
    private void createAssets(Skin skin){
        title = new Label("Quest Clicker",skin,"title");
        version = new Label("Version: " + GameRenderer.getNewestVersionString(),skin,"version");
        stats = new ImageButton(skin, "stat");
        stats.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                game.getStatScreen().updateText();
                game.getStatScreen().ShowOrClose();
                return true;
            }
        });
        achievements = new ImageButton(skin, "achievement");
        achievements.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (game.getAdsController().getSignedInGPGS())
                    game.getAdsController().getAchievementsGPGS();
                return true;
            }
        });
        rate = new ImageButton(skin, "rate");
        rate.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                game.getAdsController().rateGame();
                return true;
            }
        });
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

    /**
     * Draw the menu and faded background
     * @param batch Batch to draw to
     * @param parentAlpha Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!game.getStatScreen().isOpen())
            toFront();
        batch.draw(fade,0,0);
        super.draw(batch, parentAlpha);
    }

    /**
     * Add actors to the menu(table)
     */
    private void addActors(){
        add(exit).pad(20,0, 50, 20).right().top().row();
        add(title).padBottom(50).center().row();
        add(stats).center().padBottom(50).row();
        add(achievements).center().padBottom(50).row();
        add(rate).center().padBottom(50).row();
        add(resetButton).center().row();
        add(version).expand().center().bottom().padBottom(30);
    }

    /**
     * Close the menu
     */
    public void close(){
        menuPopup.setVisible(false);
        NameScreen.SettingName = false;
    }
}

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
import com.tophattiger.Helper.Combos.ComboBuff;
import com.tophattiger.Helper.Data.AssetLoader;

/**
 * Created by Collin on 8/1/2015.
 */
public class AdScreen extends Table {
    Drawable sprite = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("NameScreen"));
    ImageButton yes, no;
    Label title;
    AdScreen adScreen;
    GameRenderer game;
    ComboBuff adBuff;

    /**
     * Popup that allows the user to insert a name for their hero.
     * Will show up first time playing, reseting the game, and retiring
     * @param skin Skin for the looks
     * @param _game Game to put into
     */
    public AdScreen(Skin skin,GameRenderer _game) {
        super();
        game = _game;
        adScreen = this;
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
        NameScreen.SettingName = true;
        adBuff = game.getHero().makeAdBuff();
        title.setText("Would you like to watch an ad to " + adBuff.getAdDescription());
        title.setWrap(true);
        game.getUpgradeButton().close();
        setVisible(true);
        this.toFront();
        game.getAdsController().hideBannerAd();
    }

    /**
     * Create the assets for the popup
     * @param skin Skin for looks
     */
    private void createAssets(Skin skin){
        title = new Label("a" ,skin,"nameAsk"); //add buff desc
        yes = new ImageButton(skin, "yes");
        yes.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                game.getAdsController().showOrLoadInterstitial();
                game.getHero().AdActivate();
                adScreen.setVisible(false);
                NameScreen.SettingName = false;
                return true;
            }
        });
        no = new ImageButton(skin, "no");
        no.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                adScreen.setVisible(false);
                NameScreen.SettingName = false;
                return true;
            }
        });
    }

    /**
     * Add actors to the popup(table)
     */
    private void addActors(){
        this.top().left();
        add().width(610).height(225).row();
        add();add(title).colspan(3).width(700).expandY().top().left().row();
        add();add(yes).width(140).padLeft(105).padRight(105).bottom();add(no).width(140).padLeft(105).padRight(105).bottom().row();
        add().height(600);
    }
}

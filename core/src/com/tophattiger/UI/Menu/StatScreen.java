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
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 1/15/2016.
 */
public class StatScreen extends Table {
    Drawable sprite = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("MenuPopup"));
    ImageButton exit;
    Label damage, heroDamage, helperDamage, clicks,enemiesDefeated,gold,idleGold,time,maxCombo;
    TextureRegion fade;
    boolean open;

    public StatScreen(Skin skin) {
        super();
        setBackground(sprite);
        setHeight(900);
        setWidth(500);
        setPosition(710,90);
        fade = AssetLoader.textureAtlas.findRegion("fade");
        createAssets(skin);
        addActors();
        setVisible(false);
        open = false;
    }


    /**
     * Draw the menu and faded background
     * @param batch Batch to draw to
     * @param parentAlpha Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        toFront();
        batch.draw(fade, 0, 0);
        super.draw(batch, parentAlpha);
    }

    /**
     * Show the stat screen
     */
    public void ShowOrClose() {
        updateText();
        setVisible(!isVisible());
        open = !open;
    }

    public void updateText(){
        damage.setText("Damage dealt: " + Gold.getNumberWithSuffix(DataManagement.JsonData.damageDone));
        heroDamage.setText("Damage from taps: " + Gold.getNumberWithSuffix(DataManagement.JsonData.damageDoneByHero));
        helperDamage.setText("Damage from guild: " + Gold.getNumberWithSuffix(DataManagement.JsonData.damageDoneByHelpers));
        clicks.setText("Taps: " + Gold.getNumberWithSuffix(DataManagement.JsonData.clicks));
        enemiesDefeated.setText("Enemies defeated: " + Gold.getNumberWithSuffix(DataManagement.JsonData.enemiesDefeated));
        gold.setText("Total gold: " + Gold.getNumberWithSuffix(DataManagement.JsonData.goldCollected));
        idleGold.setText("Gold from guild: " + Gold.getNumberWithSuffix(DataManagement.JsonData.idleGoldCollected));
        time.setText("Hours spent playing: " + Gold.getNumberWithSuffix((DataManagement.JsonData.timeSpent / 3600)));
        maxCombo.setText("Highest combo: " + Gold.getNumberWithSuffix((DataManagement.JsonData.maxCombo)));

    }

    private void createAssets(Skin skin){
        damage = new Label("Damage dealt: ",skin,"stat");
        heroDamage = new Label("Damage dealt by hero: ",skin,"stat");
        helperDamage = new Label("Damage dealt by your guild: ",skin,"stat");
        clicks = new Label("Taps: ",skin,"stat");
        enemiesDefeated = new Label("Enemies defeated: ",skin,"stat");
        gold = new Label("Gold collected: ",skin,"stat");
        idleGold = new Label("Gold collected by your guild: ",skin, "stat");
        time = new Label("Hours spent playing: ",skin,"stat");
        maxCombo = new Label("",skin,"stat");
        exit = new ImageButton(skin, "exit");
        exit.setSize(64, 64);
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                ShowOrClose();
                return true;
            }
        });
    }

    private  void addActors(){
        add(exit).pad(20, 0, 50, 20).right().top().row();
        add(clicks).padLeft(30).padBottom(10).left().row();
        add(maxCombo).padLeft(30).padBottom(10).left().row();
        add(gold).padLeft(30).padBottom(10).left().row();
        add(idleGold).padLeft(30).padBottom(10).left().row();
        add(damage).padLeft(30).padBottom(10).left().row();
        add(heroDamage).padLeft(30).padBottom(10).left().row();
        add(helperDamage).padLeft(30).padBottom(10).left().row();
        add(enemiesDefeated).padLeft(30).padBottom(10).left().row();
        add(time).padLeft(30).padBottom(10).left().row();
        add().expand().center().bottom().padBottom(30);
    }

    public boolean isOpen(){return open;}
}

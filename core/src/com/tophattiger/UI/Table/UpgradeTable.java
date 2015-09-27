package com.tophattiger.UI.Table;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.UI.Table.Helper.BuffScreen;
import com.tophattiger.UI.Table.Hero.HeroTable;


/**
 * Created by Collin on 6/1/2015.
 */
public class UpgradeTable extends Table {

    int tableInt = 0;
    GameRenderer game;
    MoveToAction mta = new MoveToAction();
    Drawable sprite = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("table"));
    com.tophattiger.UI.Table.ScrollTable helperTable;
    HeroTable heroTable;
    com.tophattiger.UI.Table.SwitchTableButton heroButton,helperButton;
    ScrollPane container;
    Label title;

    public UpgradeTable(Skin _skin, GameRenderer _game) {
        super();
        game = _game;
        helperTable = new com.tophattiger.UI.Table.ScrollTable(_skin, game.getHelpers());
        helperButton = new com.tophattiger.UI.Table.SwitchTableButton("Helpers",_skin,"power",helperTable,this);
        heroTable = new HeroTable(_skin,game.getHero(),game.getComboList(),game.getAbilityList());
        heroButton = new com.tophattiger.UI.Table.SwitchTableButton("Hero",_skin,"power",heroTable,this);
        this.setBackground(sprite);
        this.setFillParent(false);
        this.setHeight(1080f);
        this.setWidth(911f);
        this.setPosition(-911, 0);

        title = new Label("Hero Upgrades",_skin,"upgrade");
        title.setAlignment(Align.center);
        title.setWrap(true);

        setAsContainer(heroTable);
    }

    void setAsContainer(Table table){
        if(table == helperTable){
            tableInt = 1;
            helperTable.setAsContainer(this);
            title.setText("Helper Upgrades");
            heroButton.setChecked(false);
            clearChildren();
            redraw();
        }
        else {
            tableInt = 0;
            heroTable.setAsContainer(this);
            title.setText("Hero Upgrades");
            helperButton.setChecked(false);
            clearChildren();
            redraw();
        }
    }

    void redraw(){
        this.add(title).fill().height(100f).pad(100f, 0, 20f, 0).colspan(2).row();
        this.add(heroButton).padBottom(25f);this.add(helperButton).width(250).padBottom(25f).row();
        this.add(container).expand().align(Align.topLeft).padBottom(100f).colspan(2);
    }

    public void move(){
        mta.reset();
        mta.setPosition(0, 0);
        mta.setDuration(.5f);
        if(DataHolder.open){
            mta.setX(-911);
        }
        this.addAction(mta);
    }

    public void update(){
        if(tableInt == 0){
            heroTable.updateState();
            heroButton.setChecked(true);
        }
        if(tableInt == 1) {
            helperTable.updateState();
            helperButton.setChecked(true);
        }
    }

    public void updateName(){
        heroTable.setNameText();
        heroTable.reset();
    }

    public void loadTables(){
        helperTable.load();
        heroTable.load();
    }

    public void resetButton(){
        helperTable.reset();
        heroTable.reset();
    }

    public BuffScreen getBuffScreen(){
       return helperTable.getBuffScreen();
    }
    public GameRenderer getGame(){return game;}

}

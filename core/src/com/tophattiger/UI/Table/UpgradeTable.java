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
import com.tophattiger.UI.Table.Artifact.ArtifactTable;
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
    ScrollTable helperTable;
    HeroTable heroTable;
    ArtifactTable artifactTable;
    SwitchTableButton heroButton,helperButton,artifactButton;
    ScrollPane container;
    Label title;

    /**
     * Table for user to purchase and level upgrades
     * @param _skin Skin for looks
     * @param _game Game to put this into
     */
    public UpgradeTable(Skin _skin, GameRenderer _game) {
        super();
        game = _game;
        helperTable = new ScrollTable(_skin, game.getHelpers());
        heroTable = new HeroTable(_skin,game.getHero(),game.getComboList(),game.getAbilityList());
        artifactTable = new ArtifactTable(_skin,game.getArtifactList(),game.getHero(),this);
        helperButton = new SwitchTableButton("Helpers",_skin,"power",helperTable,this);
        heroButton = new SwitchTableButton("Hero",_skin,"power",heroTable,this);
        artifactButton = new SwitchTableButton("Artifacts",_skin,"power",artifactTable,this);
        this.setBackground(sprite);
        this.setFillParent(false);
        this.setHeight(1080f);
        this.setWidth(911f);
        this.setPosition(-911, 0);

        title = new Label("Hero Upgrades",_skin,"upgrade");
        title.setAlignment(Align.center);
        title.setWrap(true);

        setAsContainer(heroTable);  //Default to hero upgrade table
    }

    /**
     * Set the container to the specified table
     * @param table Table to set as container
     */
    void setAsContainer(Table table){
        if(table == helperTable){
            tableInt = 1;
            helperTable.setAsContainer(this);
            title.setText("Helper Upgrades");
            heroButton.setChecked(false);
            artifactButton.setChecked(false);
            clearChildren();
            redraw();
        }
        else if(table == artifactTable){
            tableInt = 2;
            artifactTable.setAsContainer(this);
            title.setText("Artifact Upgrades: " + game.getHero().getArtifacts());
            helperButton.setChecked(false);
            heroButton.setChecked(false);
            clearChildren();
            redraw();
        }
        else {
            tableInt = 0;
            heroTable.setAsContainer(this);
            title.setText("Hero Upgrades");
            helperButton.setChecked(false);
            artifactButton.setChecked(false);
            clearChildren();
            redraw();
        }
    }

    /**
     * Redraw the table
     */
    private void redraw(){
        this.add(title).fill().height(100f).pad(100f, 0, 20f, 0).colspan(3).row();
        this.add(heroButton).padBottom(25f);this.add(helperButton).width(250).padBottom(25f);
        if(game.getHero().hasRetired())
            this.add(artifactButton).padBottom(25f);
        this.row();
        this.add(container).expand().align(Align.topLeft).padBottom(100f).colspan(3);
        this.debug();
    }

    /**
     * Updates the title after buying something with the artifacts
     */
    public void updateTitle(){
        title.setText("Artifact Upgrades: " +game.getHero().getArtifacts());
    }
    /**
     * Move the upgrade table with the upgrade button has been pressed
     */
    public void move(){
        mta.reset();
        mta.setPosition(0, 0);
        mta.setDuration(.5f);
        if(DataHolder.open){
            mta.setX(-911);
        }
        this.addAction(mta);
    }

    /**
     * Update the state based off which table it is on
     */
    public void update(){
        if(tableInt == 0){
            heroTable.updateState();
            heroButton.setChecked(true);
        }
        if(tableInt == 1) {
            helperTable.updateState();
            helperButton.setChecked(true);
        }
        if(tableInt == 2){
            artifactTable.updateState();
            artifactButton.setChecked(true);
        }
    }

    /**
     * Update the name in the hero table
     */
    public void updateName(){
        heroTable.setNameText();
        heroTable.reset();
    }

    /**
     * Load each table
     */
    public void loadTables(){
        helperTable.load();
        heroTable.load();
        artifactTable.load();
        clearChildren();
        redraw();
    }

    /**
     * Reset each table
     */
    public void resetButton(){
        helperTable.reset();
        heroTable.reset();
        clearChildren();
        redraw();
    }

    /**
     * Reset the artifact table when not retiring
     */
    public void hardReset(){
        artifactTable.reset();
    }

    public BuffScreen getBuffScreen(){
       return helperTable.getBuffScreen();
    }
    public GameRenderer getGame(){return game;}
    public void setContainer(ScrollPane _container){container = _container;}

}

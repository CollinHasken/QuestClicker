package com.tophattiger.UI.Table.Artifact;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameObjects.Characters.Hero;
import com.tophattiger.Helper.Artifacts.ArtifactList;
import com.tophattiger.Helper.Artifacts.BaseArtifact;
import com.tophattiger.UI.Table.UpgradeTable;

/**
 * Created by Collin on 10/8/2015.
 */
public class ArtifactTable extends Table {
    ScrollPane container;
    ArtifactList artifactList;
    UpgradeTable table;
    Array<ArtifactGroup> artifactGroups = new Array<ArtifactGroup>();
    Skin skin;
    Hero hero;

    /**
     * Table to hold artifacts for lasting upgrades
     * @param _skin Skin for looks
     * @param _artifacts    List of artifacts to make table from
     */
    public ArtifactTable(Skin _skin, ArtifactList _artifacts,Hero _hero,UpgradeTable _table){
        super(_skin);
        hero = _hero;
        skin = _skin;
        table = _table;
        artifactList = _artifacts;
        container = new ScrollPane(this);
    }

    /**
     * Update the state of each artifact group
     */
    public void updateState(){
        for(int i = 0;i< artifactGroups.size;i++){
            artifactGroups.get(i).updateState();
        }
    }

    /**
     * Reset the table and add on a random artifact
     */
    public void reset(){
        this.clearChildren();
        artifactGroups.clear();
        artifactGroups.add(new ArtifactGroup(this, artifactList.getRandomArtifact()));
        artifactGroups.get(0).add();
        for(int i = 0;i< artifactGroups.size;i++){
            artifactGroups.get(i).reset();
        }
    }

    /**
     * Load the artifacts from the saved list if their level is greater than 0 or was currently offered
     */
    public void load(){
        for(int i = 0;i<artifactList.getArtifactMax();i++) {
            if (artifactList.getArtifact(i).getLevel() > 0 || artifactList.getArtifact(i).isOffering()){
                artifactGroups.add(new ArtifactGroup(this, artifactList.getArtifact(i)));
                artifactGroups.get(i).add();
            }
        }
        if(artifactGroups.size == 0) {    //If there are no artifacts loaded, add one
            addArtifact();
            artifactGroups.get(0).add();
        }
    }

    /**
     * Add a random artifact if there are any left in the list
     */
    public void addArtifact(){
        BaseArtifact newArtifact = artifactList.getRandomArtifact();
        if(newArtifact != null){
            artifactGroups.add(new ArtifactGroup(this, newArtifact));
            artifactGroups.get(artifactGroups.size - 1).add();
            artifactList.addArtifact();
        }
    }

    /**
     * Updates the title when an artifact is bought
     */
    public void updateTitle(){
        table.updateTitle();
    }
    public int getMaxArtifacts(){return artifactList.getArtifactMax();}
    public Hero getHero(){return hero;}

    public void setAsContainer(UpgradeTable table){
        table.setContainer(container);
    }
    public ScrollPane getContainer(){return container;}
    public Skin getSkin(){return skin;}
    public  Array<ArtifactGroup> getArtifactGroups(){return artifactGroups;}
    public ArtifactList getArtifactList(){return artifactList;}
}

package com.tophattiger.UI.Table.Artifact;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.Helper.Artifacts.ArtifactList;
import com.tophattiger.Helper.Artifacts.BaseArtifact;
import com.tophattiger.UI.Table.UpgradeTable;

/**
 * Created by Collin on 10/8/2015.
 */
public class ArtifactTable extends Table {
    ScrollPane container;
    ArtifactList artifactList;
    Array<ArtifactGroup> artifactGroups = new Array<ArtifactGroup>();
    Skin skin;

    /**
     * Table to hold artifacts for lasting upgrades
     * @param _skin Skin for looks
     * @param _artifacts    List of artifacts to make table from
     */
    public ArtifactTable(Skin _skin, ArtifactList _artifacts){
        super(_skin);
        skin = _skin;
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
        for(int i = 0;i< artifactGroups.size;i++){
            artifactGroups.get(i).reset();
        }
    }

    /**
     * Load the artifacts from the saved list if their level is greater than 0 or was currently offered
     */
    public void load(){
        for(int i = 0;i<artifactList.getArtifactMax();i++){
            if(artifactList.getArtifact(i).getLevel() > 0 || artifactList.getArtifact(i).isOffering())
                artifactGroups.add(new ArtifactGroup(this, artifactList.getArtifact(i)));
        }
    }

    /**
     * Add a random artifact if there are any left in the list
     */
    public void addArtifact(){
        BaseArtifact newArtifact = artifactList.getRandomArtifact();
        if(newArtifact != null){
            artifactGroups.add(new ArtifactGroup(this, newArtifact));
            artifactList.addArtifact();
            newArtifact.switchOffering();
        }
    }

    public int getMaxArtifacts(){return artifactList.getArtifactMax();}

    public void setAsContainer(UpgradeTable table){
        table.setContainer(container);
    }
    public ScrollPane getContainer(){return container;}
    public Skin getSkin(){return skin;}
    public  Array<ArtifactGroup> getArtifactGroups(){return artifactGroups;}
    public ArtifactList getArtifactList(){return artifactList;}
}

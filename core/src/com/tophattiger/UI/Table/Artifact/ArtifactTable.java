package com.tophattiger.UI.Table.Artifact;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameObjects.Characters.Helpers;
import com.tophattiger.Helper.Artifacts.ArtifactList;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.UI.Table.Helper.BuffScreen;
import com.tophattiger.UI.Table.Helper.HelperGroup;
import com.tophattiger.UI.Table.UpgradeTable;

/**
 * Created by Collin on 10/8/2015.
 */
public class ArtifactTable extends Table {
    ScrollPane container;
    ArtifactList artifactList;
    Array<HelperGroup> helperGroups = new Array<HelperGroup>();
    Skin skin;
    BuffScreen buffScreen;

    public ArtifactTable(Skin _skin, ArtifactList _artifacts){
        super(_skin);
        skin = _skin;
        artifactList = _artifacts;
        buffScreen = new BuffScreen(skin);
        container = new ScrollPane(this);
    }

    public void updateState(){
        for(int i = 0;i< DataHolder.helperAmount;i++){
            helperGroups.get(i).updateState();
        }
    }

    public void reset(){
        this.clearChildren();
        helperGroups.clear();
        helperGroups.add(new HelperGroup(this, artifactList.getHelper(0)));
        for(int i = 0;i< DataHolder.helperAmount;i++){
            helperGroups.get(i).reset();
        }
    }

    public void load(){
        for(int i = 0;i<DataHolder.helperAmount;i++){
            helperGroups.add(new HelperGroup(this, artifactList.getHelper(i)));
        }
    }

    public void addHelper(){
        DataHolder.helperAmount ++;
        helperGroups.add(new HelperGroup(this, artifactList.getHelper(DataHolder.helperAmount - 1)));
    }

    public int getMaxHelpers(){return artifactList.getMax();}

    public BuffScreen getBuffScreen(){
        return buffScreen;
    }

    public void setAsContainer(UpgradeTable table){
        table.container = this.container;
    }
    public ScrollPane getContainer(){return container;}
    public Skin getSkin(){return skin;}
    public  Array<HelperGroup> getHelperGroups(){return helperGroups;}
    public Helpers getArtifactList(){return artifactList;}
}

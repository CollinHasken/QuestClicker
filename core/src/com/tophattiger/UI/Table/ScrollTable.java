package com.tophattiger.UI.Table;


import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameObjects.Characters.Helpers;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.UI.Table.Helper.BuffScreen;
import com.tophattiger.UI.Table.Helper.HelperGroup;

public class ScrollTable extends Table {

    public ScrollPane container;
    Helpers helpers;
    Array<HelperGroup> helperGroups = new Array<HelperGroup>();
    Skin skin;
    BuffScreen buffScreen;

    public ScrollTable(Skin _skin, Helpers _helpers){
        super(_skin);
        skin = _skin;
        helpers = _helpers;
        buffScreen = new BuffScreen(skin);
        container = new ScrollPane(this);
    }

    public void updateState(){
        for(int i = 0;i<DataHolder.helperAmount;i++){
            helperGroups.get(i).updateState();
        }
    }

    public void reset(){
        this.clearChildren();
        helperGroups.clear();
        helperGroups.add(new HelperGroup(this,helpers.getHelper(0)));
        for(int i = 0;i< DataHolder.helperAmount;i++){
            helperGroups.get(i).reset();
        }
    }

    public void load(){
        for(int i = 0;i<DataHolder.helperAmount;i++){
            helperGroups.add(new HelperGroup(this,helpers.getHelper(i)));
        }
    }

    public void addHelper(){
        DataHolder.helperAmount ++;
        helperGroups.add(new HelperGroup(this,helpers.getHelper(DataHolder.helperAmount - 1)));
    }

    public int getMaxHelpers(){return helpers.getMax();}

    public BuffScreen getBuffScreen(){
        return buffScreen;
    }

    public void setAsContainer(UpgradeTable table){
        table.container = this.container;
    }
}

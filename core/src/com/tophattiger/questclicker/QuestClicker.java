package com.tophattiger.questclicker;

import com.badlogic.gdx.Game;
import com.tophattiger.Helper.Controllers.AdsController;
import com.tophattiger.Screens.MainScreen;
import com.tophattiger.Helper.Data.AssetLoader;


/**
 * Created by Collin on 5/24/2015.
 */
public class QuestClicker extends Game {

    private AdsController adsController;

    public QuestClicker(AdsController adsController){
        this.adsController = adsController;
    }

    public void create(){

        AssetLoader.load();

        this.setScreen(new MainScreen(adsController));
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        AssetLoader.dispose();
    }
}

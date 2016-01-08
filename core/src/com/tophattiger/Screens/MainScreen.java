package com.tophattiger.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Controllers.AdsController;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.DataManagement;
import java.util.Calendar;

public class MainScreen implements Screen{

    GameRenderer renderer;
    Stage stage;

    /**
     * Create a stage and renderer
     */
	public MainScreen(AdsController adsController){
        stage = new Stage(new FitViewport(1920,1080));      //Make the resolution 1920x1080
        DataHolder.width = stage.getWidth();
        DataHolder.height = stage.getHeight();
        DataHolder.helperAmount = 1;
        renderer = new GameRenderer(stage, adsController);
        Gdx.input.setInputProcessor(stage);
	}

    /**
     * Delegate rendering to GameRenderer class
     */
	@Override public void render(float delta) {
        renderer.render(delta);
	}

    /**
     * Called when the game becomes inactive
     * Save the time at which the game becomes inactive for future gold
     */
    @Override public void pause() {
        DataHolder.currentTime = Calendar.getInstance();
        DataManagement.saveGame("save.sav",renderer);
    }

    /**
     * Called when the game becomes active again
     * Set current time and calculate gold that was accumulated
     */
    @Override public void resume() {
        DataHolder.currentTime = Calendar.getInstance();
        DataManagement.loadGame("save.sav",renderer);
        renderer.autoGold();
    }

    /**
     * Call renderer's dispose
     */
    @Override public void dispose() {
        renderer.dispose();
    }

    /**
     * Resize the stage to the specified coordinates
     */
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show(){
    }
    @Override public void hide() {
    }
}

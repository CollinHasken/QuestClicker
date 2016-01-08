package com.tophattiger.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.UI.Menu.AdScreen;

/**
 * Created by Collin on 1/7/2016.
 */
public class Chest extends Actor {

    final int appearInterval = 2;
    final float openTime = 2.5f;
    final float runTime = 2.5f;
    final int openFrames = 24;
    final int runFrames = 20;

    boolean opening;
    float appearTime, chestTime,horseTime;
    TextureRegion frame;
    Animation open,run;
    AdScreen adScreen;

    public Chest(GameRenderer game, Skin skin){
        reset();
        open = new Animation(openTime/openFrames, AssetLoader.textureAtlas.findRegions("chest"));
        run = new Animation(runTime/runFrames, AssetLoader.textureAtlas.findRegions("chest"));
        adScreen = new AdScreen(skin, game);
        setBounds(1920,700,128,128);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                opening = true;
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        appearTime -= delta;
        if(appearTime <= 0){
            setX(getX() - (delta * 200));
            if(getX() <= 0 - getWidth())
                reset();
            else if(open.isAnimationFinished(chestTime)) {
                opening = false;
                adScreen.show();
            }
            else if(opening){
                chestTime += delta;
            }
            horseTime += delta;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(getX() < 1920) {
            frame = run.getKeyFrame(horseTime,true);
            batch.draw(frame, getX()-100, getY()-100, 200, 200);
            frame = open.getKeyFrame(chestTime,false);
            batch.draw(frame, getX(), getY(), 100, 100);
        }
    }

    public AdScreen getAdScreen(){return adScreen;}

    private void reset(){
        setX(1920);
        horseTime = 0;
        chestTime = 0;
        appearTime = appearInterval;
        opening = false;
    }
}

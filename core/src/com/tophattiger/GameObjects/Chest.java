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
import com.tophattiger.UI.Menu.NameScreen;

/**
 * Created by Collin on 1/7/2016.
 */
public class Chest extends Actor {

    final int appearInterval = 2;
    final float openTime = 2.1f;
    final float runTime = 1.3f;
    final int openFrames = 24;
    final int runFrames = 13;

    boolean opening;
    float appearTime, chestTime,horseTime;
    TextureRegion frame;
    Animation open,run;
    AdScreen adScreen;

    public Chest(GameRenderer game, Skin skin){
        reset();
        open = new Animation(openTime/openFrames, AssetLoader.textureAtlas.findRegions("chest"));
        run = new Animation(runTime/runFrames, AssetLoader.textureAtlas.findRegions("horseRun"));
        adScreen = new AdScreen(skin, game);
        setBounds(1920 + 340,700,128,128);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if(!NameScreen.SettingName) {
                    opening = true;
                    return true;
                }
                return false;
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
            else if(open.isAnimationFinished(chestTime) && opening) {
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
        if(getX() < 1920 + 340) {
            frame = open.getKeyFrame(chestTime,false);
            batch.draw(frame, getX(), getY(), 100, 100);
            frame = run.getKeyFrame(horseTime,true);
            batch.draw(frame, getX()-340, getY()-82, 480, 220);
        }
    }

    public AdScreen getAdScreen(){return adScreen;}

    private void reset(){
        setX(1920+340);
        horseTime = 0;
        chestTime = 0;
        appearTime = appearInterval;
        opening = false;
    }
}

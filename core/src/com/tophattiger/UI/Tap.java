package com.tophattiger.UI;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;

/**
 * Created by Collin on 1/16/2016.
 */
public class Tap extends Actor {

    IntArray xHit, yHit;
    FloatArray timeHit;
    Animation hit;
    TextureRegion frame;

    public Tap(){
        setSize(75,75);
        timeHit = new FloatArray();
        xHit = new IntArray();
        yHit = new IntArray();
        hit = new Animation((float).7/8, AssetLoader.textureAtlas.findRegions("tap"));
    }

    public void tap(int x, int y){
        xHit.add(x);
        yHit.add(y);
        timeHit.add(0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(int i = 0; i < timeHit.size; i++){
            frame = hit.getKeyFrame(timeHit.get(i),false);
            batch.draw(frame,xHit.get(i) - getWidth()/2,yHit.get(i) - getHeight()/2,getWidth(),getHeight());
        }

    }

    @Override
    public void act(float delta) {
        if(timeHit.size > 0){
            for(int i = 0; i < timeHit.size; i++) {
                timeHit.incr(i,delta);
            }
            while(timeHit.size > 0 && hit.isAnimationFinished(timeHit.get(0))){
                if(timeHit.size > 1) {
                    timeHit.swap(0, timeHit.size - 1);
                    xHit.swap(0,xHit.size-1);
                    yHit.swap(0,yHit.size-1);
                }
                timeHit.pop();
                yHit.pop();
                xHit.pop();
                if(timeHit.size > 1){
                    for(int i = 1; i < timeHit.size; i++) {
                        timeHit.swap(i-1, i);
                        yHit.swap(i-1, i);
                        xHit.swap(i-1, i);
                    }
                }
            }
        }
    }
}

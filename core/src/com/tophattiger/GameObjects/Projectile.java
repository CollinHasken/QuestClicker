package com.tophattiger.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.Helper.Data.AssetLoader;

import java.util.Random;

/**
 * Created by Collin on 7/29/2015.
 */
public class Projectile extends Actor implements Pool.Poolable{

    Random rand;
    int randomNum,x,y,xV0,yV0,maxX,originalX,originalY,animationFrames;
    float time,gravity,animationTime;

    Animation animation;
    TextureRegion frame;

    public Projectile(com.tophattiger.GameObjects.Characters.GeneralHelper helper){
        rand = new Random();
        originalX = helper.getProjectileX();
        originalY = helper.getProjectileY();
        gravity = helper.getProjectileGravity();
        xV0 = helper.getProjectileXV0();
        yV0 = helper.getProjectileYV0();
        maxX = helper.getProjectileMaxX();
        animationFrames = helper.getProjectileAnimationFrames();
        animationTime = helper.getProjectileAnimationTime();
        animation = new Animation(animationTime/animationFrames,AssetLoader.textureAtlas.findRegions(helper.getName()+"Projectile"));
    }

    public void init(){
        x = originalX;
        y = originalY;
        time = 0;
        randomNum = rand.nextInt(8);
        while(randomNum == 0){
            randomNum = rand.nextInt(8);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        x += (xV0 + 10 * randomNum)*(delta);
        y += ((yV0 + 10 * randomNum) + (gravity * time)) * delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        frame = animation.getKeyFrame(time, true);
        batch.draw(frame,x,y);
    }

    public boolean isDead(){
        return(x >= maxX);
    }

    @Override
    public void reset() {
    }
}

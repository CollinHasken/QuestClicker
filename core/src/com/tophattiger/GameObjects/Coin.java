package com.tophattiger.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.Gold;
import java.util.Random;

/**
 * Created by Collin on 6/4/2015.
 */
public class Coin extends Actor implements Pool.Poolable{

    Vector2 position;

    boolean got,alive,newCoin,sign;
    Random rand;
    int randomNum,gold;
    Animation animation;
    TextureRegion frame;
    float time;

    /**
     * Create a coin that will randomly shoot out of a killed enemy. If it is not collected within
     * a given time, it will automatically be picked up.
     */

    public Coin(){
        rand = new Random();
        position = new Vector2();
        this.setSize(128, 128);
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!got && alive) {        //If the user hasn't gotten the coin and it is still alive, get it
                    got();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Initialize the coin with the given gold and get a random direction and force
     * @param _gold Amount of gold to give when picked up
     */
    public void init(int _gold){
        gold = _gold;
        newCoin = true;
        got = false;
        alive = true;
        setTouchable(Touchable.enabled);
        time = 0;
        this.setPosition(1645,600);         //Set position to the center of the enemy
        position.set(1645,600);
        animation = AssetLoader.coinIdle;
        randomNum = rand.nextInt(8);
        while(randomNum == 0){              //Get a randum number that isn't 0 and is between 0 and 8
            randomNum = rand.nextInt(8);
        }
        sign = rand.nextBoolean();          //Make the coin go either to the left or right randomly
    }

    /**
     * Draw the coin with the frame ad position
     * @param batch Batch to draw with
     * @param parentAlpha   Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        frame = animation.getKeyFrame(time, true);
        batch.draw(frame,position.x, position.y, 64, 64);
    }

    /**
     * Reset the coin to starting position
     */
    @Override
    public void reset() {
        this.setPosition(1645, 600);
    }

    /**
     * Give the coin the correct position over time until it hits the wall or floor. If the coin has
     * been clicked, then have it bounce up
     * @param delta Change in time between calls
     */
    public void update(float delta){
        time += delta;
        if(got && animation.isAnimationFinished(time)){ //If the coin has been clicked and the animation is done then kill it
            alive = false;
            got = false;
        }
        else if(time >= 8){     //Get the coin if it's been too long
            got();
        }
        if (position.y >= 230 && !got){
            if(position.x <= 1856 && position.x >= 1065) {      //Update x position
                if(sign)position.add(delta * (50+randomNum*40),0);
                else position.add(-delta * (50+randomNum*40),0);
            }
            position.add(0, (delta * (500 + randomNum * 20)) + (-9.8f * time * delta * 125));   //Update y position
            this.setPosition(position.x-32,position.y-32);
        }
        else if (got){                      //Shoot up into the air if the user clicks on it
            position.add(0,(delta * (700)) + (-9.8f * time * delta * 125));
        }
    }

    /**
     * Set animation and other variables when the coin has been clicked
     */
    public void got(){
        animation = AssetLoader.coinGot;
        time = 0;
        got = true;
        setTouchable(Touchable.disabled);
        Gold.add(gold);
    }

    public boolean isGot(){return got;}
    public boolean isAlive(){return alive;}
    public boolean isNewCoin(){return newCoin;}
    public void setNewCoin(boolean isNewCoin){newCoin = isNewCoin;}
}

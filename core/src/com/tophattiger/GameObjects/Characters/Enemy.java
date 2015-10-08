package com.tophattiger.GameObjects.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;

import java.util.Random;

/**
 * Created by Collin on 6/28/2015.
 */
public class Enemy extends Actor implements Pool.Poolable {

    final float DeathTime = 0.8f;
    final int maxEnemies = 3;

    int gold, progress,coins, identifier;
    double health,totalHealth,multiplier;
    boolean hit,dead,newEnemy;
    float enemyTime;
    TextureRegion enemyFrame;
    Animation animation,eIdle,eHit,eDead;
    GameRenderer game;
    Random rand;

    /**
     * Create an enemy that will take damage from user clicking and helpers. After dieing, it will create coins to be collected
     * @param _game Game to be created in
     */
    public Enemy(GameRenderer _game){
        game = _game;
        rand = new Random();
        newEnemy = false;
    }

    /**
     * Initialize the animations and other variables
     */
    public void init(){
        identifier = rand.nextInt(maxEnemies) + 1;  //Get a random enemy and set the animation
        if(identifier == 1){
            eIdle = AssetLoader.rabIdle;
            eHit = AssetLoader.rabHit;
            eDead = AssetLoader.rabDead;
            multiplier = 1;
            coins = 4;
        }
        else if(identifier == 2){
            eIdle = AssetLoader.slimeIdle;
            eHit = AssetLoader.slimeHit;
            eDead = AssetLoader.slimeDead;
            multiplier = 1.2;
            coins = 6;
        }
        else if(identifier == 3){
            eIdle = AssetLoader.octopusIdle;
            eHit = AssetLoader.octopusHit;
            eDead = AssetLoader.octopusDead;
            multiplier = 1.1;
            coins = 5;
        }
        set();
        hit = false;
        dead = false;
        enemyTime = 0;
        animation = eIdle;
    }

    /**
     * Set the health, gold and progress received
     */
    private void set(){
        health = 10+ game.getHero().questCompleted*game.getHero().questCompleted*5*multiplier;
        totalHealth = health;
        gold = 10 + (int)(game.getHero().questCompleted*game.getArtifactEnemyGold()*multiplier*5);
        progress = (int) (multiplier*(game.getHero().questCompleted+1));
    }

    /**
     * Multiply the gold dropped by the artifact amount
     * @param artifactGold
     */
    public void setGold(double artifactGold){
        gold /= game.getArtifactEnemyGold();
        gold *= artifactGold;
    }

    @Override
    public void reset() {
    }

    /**
     * Check for when enemy dies and what animation is playing
     * @param delta Change in time between calls
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        enemyTime += delta;
        if (health < 0){        //If the helpers bring the enemies health lower than 0, kill it
            dead();
        }
        if(DeathTime < enemyTime && newEnemy){      //If the timer is past the buffer and there needs to be a new enemy, create one
            game.newEnemy();
            newEnemy = false;
        }
        if(hit && this.animation.isAnimationFinished(enemyTime)){   //If the enemies been hit and the animation is finished, go back to idleling
            enemyTime = 0;
            hit = false;
            animation = eIdle;
        }
        else if(dead && this.animation.isAnimationFinished(enemyTime)){ //If the dead animation is done, increase progress and remove enemy
            game.getHero().questProgress += progress;
            game.removeEnemy(this);
        }
        else if(dead && animation != eDead){    //If the enemy has died without the animation being the dead one, then set it as such
            animation = eDead;
        }
    }

    /**
     * Draw the current frame
     * @param batch Batch to draw with
     * @param parentAlpha   Parent's alphas
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        enemyFrame = animation.getKeyFrame(enemyTime,true);
        batch.draw(enemyFrame, 1399, 230, 512, 512);
    }

    /**
     * Function when the enemy has been hit
     * Take away health, if it has more than 0 health then play the hit animation, else call dead
     * @param damage    Amount of damage to deal
     */
    public void getHit(double damage){
        health -= damage;
        if(health> 0){
            hit = true;
            animation = eHit;
        }
        else{
            dead();
        }
    }

    /**
     * Function for the helpers to call when they attack
     * Decrease health by damage and if it has less than 0 health, then call dead
     * @param damage Amount of damage to deal
     */
    public void helperAttack(double damage){
        if(!dead) {
            health -= damage;
            if (health <= 0) {
                dead();
            }
        }
    }

    /**
     * Function to call when the enemy is dead
     * Set animation to dead and drop coins
     */
    private void dead(){
        health = 0;
        hit = false;
        dead = true;
        animation = eDead;
        enemyTime = 0;
        newEnemy = true;
        game.dropCoins(gold,coins);
    }

    public boolean isDead(){return dead;}
    public double getHealth(){return health;}
    public double getTotalHealth(){return totalHealth;}
    public boolean isHit(){return hit;}
    public void setEnemyTime(float time){enemyTime = time;}
}

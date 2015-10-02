package com.tophattiger.Helper.Combos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

/**
 * Created by Collin on 7/13/2015.
 */
public class Combo extends GlyphLayout implements Pool.Poolable {

    final int originY = 800;
    final int xV0 = 20;
    final int yV0 = 100;
    final double gravity = -9.8;
    final int maxTime = 1;

    String comboString;
    int x,y;
    float alpha,time;
    boolean start,sign;
    Random rand;
    int randomNum;
    Color color,origColor;

    /**
     * A String with a position. It will display the combo amount and fade away.
     * If the combo increases, the current combo string will fly away randomly as it disappears
     */
    public Combo(){
        rand = new Random();
    }

    /**
     * Initialize the string, set the position and store the color for fading.
     * Get a random power and direction
     * @param _comboString String to display
     * @param _color Initial color
     */
    public void init(String _comboString,Color _color){
        comboString = _comboString;
        time = 0;
        y = originY;
        alpha = 1;
        start = false;
        randomNum = rand.nextInt(8);
        while(randomNum == 0){      //Get random integer that isn't 0
            randomNum = rand.nextInt(8);
        }
        sign = rand.nextBoolean();
        origColor = new Color(_color);
        color = new Color(_color);
    }

    /**
     * Update position of string
     * @param delta Change in time between calls
     */
    public void update(float delta){
        alpha = delta;
        time += delta;
        if(start){      //If its started to move
            if(sign){   //Move the x dependent of initial velocity and random power
                x += (xV0 + 10 * randomNum)*(delta);
            }
            else x -= (xV0 + 10 * randomNum)*(delta);
            y += ((yV0 + 10 * randomNum) + (gravity * time)) * delta;   //Move y dependent on initial velocity and random num
        }

    }

    /**
     * Start the movement
     */
    public  void start(){
        if(!start) {    //If it hasn't started yet
            start = true;
            alpha = 1;
            time = 0;
            color = origColor;
        }
    }

    /**
     * Check whether it's time has gone past the max time
     * @return True if dead, false if not
     */
    public boolean isDead(){
        if(time>maxTime)
            return true;
        return false;
    }

    public int getX(){return x;}

    public void setX(float _x){x = (int) _x;}

    public int getY(){return y;}

    public String getText(){
        return comboString;
    }

    public Color setColor(){
        return color.add(0,0,0,-alpha);
    }

    public Color getColor(){
        return color;
    }

    public boolean isCurrent(){return !start;}


    @Override
    public void reset() {
        super.reset();
    }
}

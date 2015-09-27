package com.tophattiger.Helper.Combo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public Combo(){
        rand = new Random();
    }

    public void init(String _comboString,Color _color){
        comboString = _comboString;
        time = 0;
        y = originY;
        alpha = 1;
        start = false;
        randomNum = rand.nextInt(8);
        while(randomNum == 0){
            randomNum = rand.nextInt(8);
        }
        sign = rand.nextBoolean();
        origColor = new Color(_color);
        color = new Color(_color);
    }

    public void update(float delta){
        alpha = delta;
        time += delta;
        if(start){
            if(sign){
                x += (xV0 + 10 * randomNum)*(delta);
            }
            else x -= (xV0 + 10 * randomNum)*(delta);
            y += ((yV0 + 10 * randomNum) + (gravity * time)) * delta;
        }

    }

    public  void start(){
        if(!start) {
            start = true;
            alpha = 1;
            time = 0;
            color = origColor;
        }
    }

    public boolean isDead(){
        if(time>maxTime)return true;
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

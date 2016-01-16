package com.tophattiger.Helper.Data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AssetLoader {

    public static Image healthBar,progressBar,volumeBar;
    public static Sprite regBackground,table;
    static public BitmapFont goldFont,healthFont,questFont,timerFont,nameFont,dpsFont,titleFont;
    public static TextureAtlas textureAtlas;
    public static Animation rabIdle,rabHit,rabDead,slimeIdle,slimeHit,slimeDead,octopusIdle,octopusHit,octopusDead,coinIdle,coinGot;

    public static void load(){
        goldFont = new BitmapFont(Gdx.files.internal("goldfont.fnt"));
        healthFont = new BitmapFont(Gdx.files.internal("healthfont.fnt"));
        questFont = new BitmapFont(Gdx.files.internal("questfont.fnt"));
        timerFont = new BitmapFont(Gdx.files.internal("timerfont.fnt"));
        nameFont = new BitmapFont(Gdx.files.internal("namefont.fnt"));
        dpsFont = new BitmapFont(Gdx.files.internal("dpsfont.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("titlefont.fnt"));
        textureAtlas = new TextureAtlas(Gdx.files.internal("images.atlas"));
        rabIdle = new Animation(.25f,textureAtlas.findRegions("rabbitIdle"));
        rabHit = new Animation(.20f,textureAtlas.findRegions("rabbitHit"));
        rabDead = new Animation(.2f,textureAtlas.findRegions("rabbitDead"));
        slimeIdle = new Animation(.2f,textureAtlas.findRegions("slimeIdle"));
        slimeHit = new Animation(.12f,textureAtlas.findRegions("slimeHit"));
        slimeDead = new Animation(.2f,textureAtlas.findRegions("slimeDead"));
        octopusIdle = new Animation(.25f,textureAtlas.findRegions("octopusIdle"));
        octopusHit = new Animation(.10f,textureAtlas.findRegions("octopusHit"));
        octopusDead = new Animation(.2f,textureAtlas.findRegions("octopusDead"));
        coinIdle = new Animation(.25f,textureAtlas.findRegions("coinIdle"));
        coinGot = new Animation(.33f,textureAtlas.findRegions("coinGot"));
        healthBar = new Image(textureAtlas.findRegion("healthBar"));
        healthBar.setPosition(1440, 848);healthBar.setSize(438, 26);
        DataHolder.hBarWidth = (int)healthBar.getWidth();
        progressBar = new Image(textureAtlas.findRegion("progressBar"));
        progressBar.setPosition(28,88);progressBar.setSize(1865, 44);
        DataHolder.pBarWidth = (int)progressBar.getWidth();
        progressBar.setWidth(0);
        volumeBar = new Image(textureAtlas.findRegion("volumeBar"));
        volumeBar.setPosition(28,14);volumeBar.setSize(1865, 46);
        DataHolder.vBarWidth = (int)volumeBar.getWidth();
        volumeBar.setWidth(0);
        regBackground = new Sprite(textureAtlas.findRegion("regBackground"));
        table = new Sprite(textureAtlas.findRegion("table"));
    }
    public static void dispose(){
        goldFont.dispose();
        healthFont.dispose();
        questFont.dispose();
        timerFont.dispose();
        nameFont.dispose();
        dpsFont.dispose();
        textureAtlas.dispose();
        titleFont.dispose();
    }
}

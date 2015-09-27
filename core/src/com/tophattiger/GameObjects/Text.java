package com.tophattiger.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.Gold;


public class Text extends Actor {

    BitmapFont font;
    GlyphLayout layout;
    GameRenderer stage;
    TYPE type;

    public enum TYPE{
        GOLD,QUEST,HEALTH
    }

    public Text(GameRenderer _stage, TYPE _type){
        stage = _stage;
        type = _type;

        switch(type){
            case GOLD:
                font = AssetLoader.goldFont;
                font.setColor(0,0,0,1);
                break;
            case QUEST:
                font = AssetLoader.questFont;
                break;
            case HEALTH:
                font = AssetLoader.healthFont;
                break;
        }
        layout = new GlyphLayout();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        switch(type){
            case GOLD:
                layout.setText(font, Gold.getGoldWithSuffix() + " Gold");
                font.draw(batch, layout, (int) (1650 - layout.width/2), 1050);
                break;
            case QUEST:
                layout.setText(font,"Quest " + Gold.getNumberWithSuffix(stage.getHero().questCompleted));
                font.draw(batch,layout,DataHolder.width*.5f - layout.width/2,128);
                break;
            case HEALTH:
                layout.setText(font,Gold.getNumberWithSuffix(stage.getEnemy().health) + " / " + Gold.getNumberWithSuffix(stage.getEnemy().totalHealth) + " HP");
                font.draw(batch,layout,1650 - layout.width/2,950);
                break;
        }
    }
    public void dispose(){
        font.dispose();
    }
}

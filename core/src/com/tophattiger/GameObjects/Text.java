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

    /**
     * Enume to know what to do with functions, can make 1 class
     */
    public enum TYPE{
        GOLD,QUEST,HEALTH,VOL,HERODPS,HELPERDPS
    }

    /**
     * Create the text to be able to get dynamic width and height for better positioning
     * @param _stage    Stage to place text in
     * @param _type     Type of text to know how to handle drawing
     */
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
            case HERODPS:
                font = AssetLoader.dpsFont;
                break;
            case HELPERDPS:
                font = AssetLoader.dpsFont;
                break;
            case VOL:
                font = AssetLoader.questFont;
        }
        layout = new GlyphLayout();
    }

    /**
     * Draw the text with set text and position based off enum
     * @param batch Batch to draw to
     * @param parentAlpha   Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        switch(type){
            case GOLD:
                layout.setText(font, Gold.getGoldWithSuffix() + " Gold");
                font.draw(batch, layout, (int) (1650 - layout.width / 2), 1050);
                break;
            case QUEST:
                layout.setText(font,"Quest " + (Gold.getNumberWithSuffix(stage.getHero().getQuestCompleted() + 1)) + ": " + stage.getHero().getQuestDescription());
                font.draw(batch,layout,DataHolder.width*.5f - layout.width/2,128);
                break;
            case HEALTH:
                layout.setText(font,Gold.getNumberWithSuffix(stage.getEnemy().getHealth()) + " / " + Gold.getNumberWithSuffix(stage.getEnemy().getTotalHealth()) + " HP");
                font.draw(batch,layout,1650 - layout.width/2,950);
                break;
            case HERODPS:
                layout.setText(font,"Hero DPS: " + stage.getHero().getDPSString());
                font.draw(batch,layout,1000 + (int)(stage.getHelperDPSText().getLayout().width  / 90) * 90,210);
                break;
            case HELPERDPS:
                layout.setText(font,"Helper DPS: " + stage.getHelpers().getTotalDPS());
                font.draw(batch,layout,930,210);
                break;
            case VOL:
                layout.setText(font,"Volume " + (Gold.getNumberWithSuffix(stage.getHero().getVolCompleted() + 1)) + ": " + stage.getHero().getVolDescription());
                font.draw(batch,layout,DataHolder.width*.5f - layout.width/2,50);
        }
    }

    public GlyphLayout getLayout(){
        return layout;
    }
    public void dispose(){
        font.dispose();
    }
}

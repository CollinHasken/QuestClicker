package com.tophattiger.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameObjects.Characters.Enemy;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataHolder;



public class Background extends Actor {
    public Array<Enemy> activeEnemies;
    Enemy enemy;
    float abilityGoldTime;
    boolean touch;
    double abilityGoldAmount;
    ComboText combo;
    GameRenderer game;
    public Stage stage;
    public Sprite sprite;

    public Background(GameRenderer _game){
        game = _game;
        stage = game.getStage();
        sprite = AssetLoader.regBackground;
        sprite.setSize(DataHolder.width, DataHolder.height);
        touch = false;
        abilityGoldAmount = 0;
        abilityGoldTime = 0;

        setBounds(0, 0, stage.getWidth(), stage.getHeight());
        setTouchable(Touchable.enabled);

        combo = _game.getCombo();

        activeEnemies = game.getEnemies();

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!touch && !Menu.NameScreen.SettingName) {
                    if(abilityGoldTime > 0){
                        game.dropCoins((int)abilityGoldAmount,1);
                    }
                    touch = true;
                    combo.tap();
                    for (int i = activeEnemies.size; --i >= 0; ) {
                        game.getHero().attack();
                        enemy = activeEnemies.get(i);
                        if (!enemy.isDead()) {
                            if (!enemy.isHit())
                                enemy.setEnemyTime(0);
                            enemy.getHit(game.getHero().getTouchPower());
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void act(float delta) {
        if(touch){
                touch = false;
        }
        if(abilityGoldTime >= 0){
            abilityGoldTime -= delta;
        }
    }

    @Override
         public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);

    }

    public void abilityGold(double amount,int time){
        abilityGoldAmount = amount;
        abilityGoldTime = time;
    }

    public void reset(){
        abilityGoldTime = 0;
    }


}

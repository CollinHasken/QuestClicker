package com.tophattiger.UI.Table.Helper;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tophattiger.GameObjects.Characters.GeneralHelper;
import com.tophattiger.Helper.Data.AssetLoader;

/**
 * Created by Collin on 7/9/2015.
 */
public class BuffScreen extends Table {

    public static boolean buffOpen;

    Drawable sprite = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("buffScreen"));
    ImageButton exit;
    Label name,buff1,buff2,buff3,buff4,buff5,buff6,buff7,buff8,buff9;
    Image picture,buffI1,buffI2,buffI3,buffI4,buffI5,buffI6,buffI7,buffI8,buffI9;
    Label[] buffs = new Label[9];
    Label.LabelStyle buffa,buffi;
    Image[] buffIs = new Image[9];
    BuffScreen buffScreen;
    Skin skin;

    /**
     * Screen to popup to see the description of the buffs each helper has to unlock
     * @param _skin Skin for looks
     */
    public BuffScreen(Skin _skin) {
        super();
        skin = _skin;
        buffScreen = this;
        setBackground(sprite);
        setHeight(1080f);
        setWidth(1180f);
        setPosition(0, 0);
        exit = new ImageButton(_skin, "exit");
        exit.setBackground(new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("exit")));
        exit.setSize(64, 64);
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buffScreen.setVisible(false);
                buffOpen = false;
                return true;
            }
        });
        createBuffs(_skin);
        addActors();
        buffOpen = false;
        setVisible(false);
    }

    /**
     * Show the buff screen
     * @param _helper Helper the buffs are coming from
     */
    public void show(GeneralHelper _helper) {
        for (int i = 0; i < 9; i++) {       //Retrieve each buff and set the texts
            buffs[i].setText(_helper.getBuffDesc(i));
            if(_helper.buffActive(i))buffs[i].setStyle(buffa);  //Get the right image based on if it's unlocked
            else buffs[i].setStyle(buffi);
            buffIs[i].setDrawable(_helper.getBuffImg(i));
        }
        picture.setDrawable(_helper.getPictureDrawable());;
        name.setText(_helper.getName());
        buffOpen = true;
        setVisible(true);
    }

    /**
     * Create the blank assets to be filled in when the user clicks on a helper
     * @param skin SKin for looks
     */
    private void createBuffs(Skin skin){
        name = new Label("name",skin,"name");
        picture  = new Image(AssetLoader.textureAtlas.findRegion("default128"));
        buff1 = new Label("buff 1",skin,"buffi");
        buff2 = new Label("buff 2",skin,"buffi");
        buff3 = new Label("buff 3",skin,"buffi");
        buff4 = new Label("buff 4",skin,"buffi");
        buff5 = new Label("buff 5",skin,"buffi");
        buff6 = new Label("buff 6",skin,"buffi");
        buff7 = new Label("buff 7",skin,"buffi");
        buff8 = new Label("buff 8",skin,"buffi");
        buff9 = new Label("buff 9",skin,"buffi");
        addBuffs();
        buffi = new Label.LabelStyle(buff1.getStyle());
        buffa = new Label.LabelStyle(buff1.getStyle());
        buffa.background = new TextureRegionDrawable(AssetLoader.textureAtlas.findRegion("buffa"));
        for(int i=0;i<9;i++){
            buffs[i].setWrap(true);
            buffIs[i] = new Image(AssetLoader.textureAtlas.findRegion("default128"));
        }
    }

    /**
     * Add buffs to lists
     */
    private void addBuffs(){
        buffs[0] = buff1;
        buffs[1] = buff2;
        buffs[2] = buff3;
        buffs[3] = buff4;
        buffs[4] = buff5;
        buffs[5] = buff6;
        buffs[6] = buff7;
        buffs[7] = buff8;
        buffs[8] = buff9;
        buffIs[0] = buffI1;
        buffIs[1] = buffI2;
        buffIs[2] = buffI3;
        buffIs[3] = buffI4;
        buffIs[4] = buffI5;
        buffIs[5] = buffI6;
        buffIs[6] = buffI7;
        buffIs[7] = buffI8;
        buffIs[8] = buffI9;
    }

    /**
     * Add actors to the screen(table)
     */
    private void addActors(){
        add(picture).width(128);
        add(name).bottom().left();
        add().width(128);
        add();
        add(exit).width(64).top().right().row();
        for (int i = 0; i < 9; i++) {
            if(i == 8){
                add(buffIs[i]).pad(20f, 0, 0, 0).width(128f).top();
                add(buffs[i]).width(420).padTop(20f).expand().top().left();
            }
            else {
                add(buffIs[i]).pad(20f, 0, 0, 0).width(128f).center();
                if (i % 2 == 1) {
                    add(buffs[i]).width(420).padTop(20f).colspan(2).row();
                } else {
                    add(buffs[i]).width(420).padTop(20f).padRight(20f).left();
                }
            }
        }
    }
    }

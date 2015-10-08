package com.tophattiger.Helper.Abilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tophattiger.GameWorld.GameRenderer;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.Helper.Data.Gold;
import com.tophattiger.UI.Table.Helper.BuffScreen;

/**
 * Created by Collin on 8/2/2015.
 */
public class BaseAbility extends Button{

    final int PAD = 10;

    public enum TYPE{
        BIGDAMAGE,HELPERSPEED,GOLDDROP,RETIRE
    }

    MoveToAction mta;
    TYPE type;
    String timeText;
    float resetTime;
    double amount,cost,originalAmount,currentAmount;
    int level,x,y,transformX,transformY,maxTime;
    String description;
    GameRenderer game;
    BitmapFont font;
    GlyphLayout layout;
    Image picture;

    /**
     * An ability has an image, level and function
     * The user can buy and level up the ability and then use it
     * They have a duration and cool down
     * @param _type Type of ability
     * @param _game Game to place them in
     */
    public BaseAbility(TYPE _type, GameRenderer _game){
        super(_game.getSkin());
        level = 0;
        resetTime = 0;
        game = _game;
        type = _type;
        timeText = "0:00";
        mta = new MoveToAction();
        regularCoordinates();
        setStats();
        setCost();
        setDescription();
        if(type == TYPE.BIGDAMAGE){     //Deals large damage to the enemy
            this.setStyle(new ButtonStyle(game.getSkin().getDrawable("abilityDamage"),game.getSkin().getDrawable("abilityDamageUsed"),game.getSkin().getDrawable("abilityDamageUsed")));
            picture = new Image(AssetLoader.textureAtlas.findRegion("abilityDamageIcon"));
            transformX = x + 410;
            transformY = y;
        }
        else if(type == TYPE.HELPERSPEED){      //Increase the helpers' attack speed
            setStyle(new ButtonStyle(game.getSkin().getDrawable("abilityHelper"),game.getSkin().getDrawable("abilityHelperUsed"),game.getSkin().getDrawable("abilityHelperUsed")));
            picture = new Image(AssetLoader.textureAtlas.findRegion("abilityHelperIcon"));
            transformX = x + 410;
            transformY = y;
        }
        else if(type == TYPE.GOLDDROP){     //Drop gold every time the user clicks
            setStyle(new ButtonStyle(game.getSkin().getDrawable("abilityGold"),game.getSkin().getDrawable("abilityGoldUsed"),game.getSkin().getDrawable("abilityGoldUsed")));
            picture = new Image(AssetLoader.textureAtlas.findRegion("abilityGoldIcon"));
            transformX = x + 110;
            transformY = (int)(y - getHeight() - PAD - 40);
        }
        if(type != TYPE.RETIRE){        //Creates and positions the icon to use the ability if it is not retire
            font = AssetLoader.timerFont;
            layout = new GlyphLayout();
            setPosition(x, y);
            setSize(128, 128);
            setChecked(false);
            removeListener(getListeners().get(0));
            addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (resetTime == 0 && !BuffScreen.buffOpen) {
                        activate();
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });

        }
    }

    /**
     * Check for when the cool down is over
     * @param delta Time between function calls
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if(resetTime > 0){
            resetTime -= delta;
            setText();
            layout.setText(font, timeText);
        }
        else if(resetTime < 0) {
            resetTime = 0;
            setChecked(false);
        }
    }

    /**
     * Draw the button if it has been bought
     * @param batch Batch to draw to
     * @param parentAlpha   Parent alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(level>0 && type != TYPE.RETIRE){
            super.draw(batch, parentAlpha);
            if(!timeText.equals("0:00"))    //If the cool down isn't over, show how much time remains
                font.draw(batch, layout, getX() + (getWidth() / 2) - (layout.width / 2), getY());
        }
    }

    /**
     * Set the text of how much time is left on the cool down
     */
    private void setText(){
        timeText = Integer.toString((int)(resetTime/60)) + ":";
        if(resetTime % 60 < 10)
            timeText += "0";
        timeText += Integer.toString((int)(resetTime % 60));
    }

    /**
     * Set the variables for next purchase depending on the ability type
     */
    private void setStats(){
        if(type == TYPE.BIGDAMAGE){
            amount = 150 + level*100;
        }
        else if(type == TYPE.HELPERSPEED){
            amount = 2 + level*2;
            maxTime = 30 + 5 * level;
        }
        else if(type == TYPE.GOLDDROP){
            amount = 10 + level*5;
            maxTime = 20 + level * 5;
        }
    }

    /**
     * Set the variables for the amount for the actual level
     */
    private void setCurrentStats(){
        if(type == TYPE.BIGDAMAGE){
            currentAmount = 150 + (level-1)*100;
        }
        else if(type == TYPE.HELPERSPEED){
            currentAmount = 2 + (level-1)*2;
            maxTime = 30 + 5 * (level-1);
        }
        else if(type == TYPE.GOLDDROP){
            currentAmount = 10 + (level-1)*5;
            maxTime = 20 + (level-1) * 5;
        }
    }

    /**
     * Set the cost of upgrading
     */
    private void setCost(){
        if(type == TYPE.BIGDAMAGE){
            cost = 100 + level*100;
        }
        else if(type == TYPE.HELPERSPEED){
            cost = 500 + level*200;
        }
        else if(type == TYPE.GOLDDROP){
            cost = 2000 + level*3000;
        }
        else if(type == TYPE.RETIRE){
            cost = 80000;
        }
    }

    /**
     * Sets the coordinates of the abilities to the position without the upgrade table opened
     */
    private void regularCoordinates(){
        if(type == TYPE.BIGDAMAGE){
            x = 500;
        }
        else if(type == TYPE.HELPERSPEED){
            x = 650;
        }
        else if(type == TYPE.GOLDDROP){
            x = 800;
        }
        y = (int)(game.getStage().getHeight() - getHeight() - PAD);
    }

    /**
     * Set the coordinates to the position they should be when the upgrade table is opened
     */
    public void transformCoordinates(){
        setPosition(transformX,transformY);
    }

    /**
     * Set the description of the ability in the upgrade shop
     */
    private void setDescription(){
        description = "Activate ability to ";
        if(type == TYPE.BIGDAMAGE){
            description += "deal " + Gold.getNumberWithSuffix(amount) + " damage to the enemy";
        }
        else if(type == TYPE.HELPERSPEED){
            description += "multiply helpers' attack speed by " + Gold.getNumberWithSuffix(amount);
        }
        else if(type == TYPE.GOLDDROP){
            description += "drop "+ Gold.getNumberWithSuffix(amount) + " gold every tap";
        }
    }

    /**
     * Increase the level, cost, and set the stats and description
     */
    public void level(){
        level ++;
        cost *= level;
        currentAmount = amount;
        setStats();
        setCost();
        setDescription();
    }

    /**
     * Activate the ability and set checked
     */
    public void activate(){
        if(type == TYPE.BIGDAMAGE){
           for(int i = 0; i< game.getEnemies().size;i++){
               if(!game.getEnemies().get(i).isDead()){
                   game.getEnemies().get(i).getHit(currentAmount);
               }
           }
            resetTime = 300;
        }
        else if(type == TYPE.HELPERSPEED){
            game.getHelpers().abilitySpeed(currentAmount, maxTime);
            resetTime = 600;
        }
        else if(type == TYPE.GOLDDROP){
            game.getBackground().abilityGold(currentAmount,maxTime);
            resetTime = 1800;
        }
        else if(type == TYPE.RETIRE){
            game.getHero().retire();
            DataManagement.reset(game);
        }
        setChecked(true);
    }

    /**
     * Move the ability to the correct position based on if the table is open or closed
     */
    public void move(){
        mta.reset();
        mta.setPosition(x, y);
        mta.setDuration(0.5f);
        if(DataHolder.open){
            mta.setPosition(transformX, transformY);
        }
        this.addAction(mta);
    }

    /**
     * Load the ability data
     * @param i Index in list of abilities from JSON
     */
    public void load(int i){
        if(DataManagement.JsonData.abilityLevels != null) {
            level = DataManagement.JsonData.abilityLevels.get(i*2);
            resetTime = DataManagement.JsonData.abilityLevels.get((i*2)+1);
            resetTime -= DataHolder.timeDif;        //Make the reset time count amount of time the game has been closed for
            if(resetTime <= 0)
                resetTime = 0;
            else
                setChecked(true);
            setCurrentStats();
            setStats();
            setCost();
            setDescription();
        }
    }

    /**
     * Save the level and reset time of the ability
     */
    public void save(){
        DataManagement.JsonData.abilityLevels.add(level);
        DataManagement.JsonData.abilityLevels.add((int) resetTime);
    }

    /**
     * Resets all data for ability
     */
    public void reset(){
        level = 0;
        amount = 0;
        currentAmount = 0;
        resetTime = 0;
        setChecked(false);
        if(type != TYPE.RETIRE) {
            setText();
            layout.setText(font, timeText);
        }
        setCost();
        setStats();
        setDescription();
    }

    public String getDescription(){
        return description;
    }

    public double getCost(){return cost;}

    public int getLevel(){return level;}

    public Image getPicture(){return picture;}

    public double getCurrentAmount(){return currentAmount;}

    public TYPE getType(){return type;}

    public void dispose(){
        font.dispose();
    }
}

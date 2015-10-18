package com.tophattiger.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tophattiger.GameObjects.Coin;
import com.tophattiger.GameObjects.Characters.Enemy;
import com.tophattiger.GameObjects.Characters.Helpers;
import com.tophattiger.GameObjects.Characters.Hero;
import com.tophattiger.GameObjects.Text;
import com.tophattiger.Helper.Abilities.AbilityList;
import com.tophattiger.Helper.Artifacts.ArtifactList;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Combos.ComboList;
import com.tophattiger.Helper.Data.DataHolder;
import com.tophattiger.Helper.Data.DataManagement;
import com.tophattiger.UI.Background;
import com.tophattiger.UI.ComboText;
import com.tophattiger.UI.Menu.Menu;
import com.tophattiger.UI.Menu.NameScreen;
import com.tophattiger.UI.Table.UpgradeButton;
import com.tophattiger.UI.Table.UpgradeTable;


public class GameRenderer {

    //Update to keep record of which version is downloaded
    private static final int newestVersion = 8;
    private static final String newestVersionString = "0.2.3 ";

    Stage stage;
    Skin skin;
    Background background;
    GameRenderer renderer = this;

    Hero hero;
    Helpers helpers;
    UpgradeTable table;
    Enemy enemy;

    UpgradeButton upgradeButton;
    ImageButton menuButton;
    Menu menu;
    NameScreen nameScreen;
    Text goldText,questText,healthText;
    Image progressBar,healthBar;

    AbilityList abilityList;
    ArtifactList artifactList;
    Coin coin;
    ComboText comboText;
    ComboList combos;
    double comboGold,artifactEnemyGold;
    float runTime;


    Array<Coin> activeCoins = new Array<Coin>();
    Pool<Coin> coinPool = new Pool<Coin>() {
        @Override
        protected Coin newObject() {
            return new Coin();
        }
    };
    Array<Enemy> activeEnemies = new Array<Enemy>();
    Pool<Enemy> enemyPool = new Pool<Enemy>(){
        @Override
        protected Enemy newObject() {
            return new Enemy(renderer);
        }
    };

    /**
     * Create the game renderer
     * @param stag Stage to put the game renderer in
     */
    public GameRenderer(Stage stag){
        stage = stag;

        loadAssets();
        addActors();
        artifactEnemyGold = comboGold = 1;
    }

    /**
     * Function to render the scene
     * @param runTime Time since the game has started
     */
    public void render(float runTime){
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        table.update();
        updateCoins(runTime);

        setBar();

        stage.act();

        stage.draw();
    }

    /**
     * Set the bar graphics
     */
    public void setBar(){
        progressBar.setWidth((int) (((hero.getQuestProgress() / hero.getQuestRequired())) * DataHolder.pBarWidth));
        healthBar.setWidth((int) ((enemy.getHealth() / enemy.getTotalHealth()) * DataHolder.hBarWidth));
    }

    /**
     * Add the created actors to the stage
     */
    public void addActors(){
        stage.addActor(background);
        stage.addActor(progressBar);
        stage.addActor(healthBar);
        stage.addActor(enemy);
        stage.addActor(goldText);
        stage.addActor(questText);
        stage.addActor(healthText);
        stage.addActor(hero);
        helpers.addActors(stage);
        stage.addActor(menuButton);
        stage.addActor(table);
        stage.addActor(upgradeButton);
        stage.addActor(enemy);
        abilityList.addActors(stage);
        stage.addActor(comboText);
        stage.addActor(table.getBuffScreen());
        stage.addActor(menu);
        stage.addActor(nameScreen);
    }

    /**
     * Initialize all the assets
     */
    public void loadAssets(){
        DataHolder.Initialize();

        hero = new Hero();
        enemy = enemyPool.obtain();
        enemy.init();
        activeEnemies.add(enemy);
        helpers = new Helpers(this);
        skin = new Skin(Gdx.files.internal("images.json"));
        combos = new ComboList(this);
        comboText = new ComboText(combos);
        abilityList = new AbilityList(this);
        artifactList = new ArtifactList(this);
        background = new Background(this);
        healthBar = AssetLoader.healthBar;
        progressBar = AssetLoader.progressBar;
        goldText = new Text(this, Text.TYPE.GOLD);
        questText = new Text(this,Text.TYPE.QUEST);
        healthText = new Text(this,Text.TYPE.HEALTH);
        table = new UpgradeTable(skin,this);
        upgradeButton = new UpgradeButton(skin,"upgrade",table);
        nameScreen = new NameScreen(skin,this);
        menu = new Menu(skin,this);
        menuButton = new ImageButton(skin,"menu");
        menuButton.setPosition(20, 916);
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menu.show();
                return true;
            }
        });
        //Load the game data and tables
        DataManagement.loadGame("save.sav", this);
        table.loadTables();

        helpers.load();
    }

    /**
     * Update the state of the coins
     * @param _runTime Runtime of the stage
     */
    public void updateCoins(float _runTime){
        runTime = _runTime;
        int len = activeCoins.size;
        for (int i = len; --i >= 0;) {
            coin = activeCoins.get(i);
            if (!coin.isAlive()) {
                coin.remove();
                activeCoins.removeIndex(i);
                coinPool.free(coin);
            }
            else if(coin.isNewCoin()){
                stage.addActor(coin);
                coin.setNewCoin(false);
            }
            else coin.update(runTime);
        }
    }

    /**
     * Call helper's auto gold function when coming back
     */
    public void autoGold(){
        helpers.autoGold();
    }

    /**
     * Function to create coins in the stage
     * @param gold Amount of total gold to drop
     * @param coins Amount of coins to drop
     */
    public void dropCoins(int gold,int coins){
        int amount = (int)((gold/coins)*comboGold);
        for(int i = 0;i<coins;i++){
            Coin coin = coinPool.obtain();
            coin.init(amount);
            activeCoins.add(coin);
        }
    }

    /**
     * Remove the enemy from the array and pool
     * @param enemy Enemy to delete
     */
    public void removeEnemy(Enemy enemy){
        enemy.remove();
        activeEnemies.removeValue(enemy, true);
        enemyPool.free(enemy);
    }

    /**
     * Create a new enemy in the pool and array
     */
    public void newEnemy(){
        enemy = enemyPool.obtain();
        enemy.init();
        activeEnemies.add(enemy);
        stage.addActor(enemy);
    }

    /**
     * Multiply the combo gold amount increase
     * @param amount Amount to multiply by
     */
    public void comboGold(double amount){
        comboGold *= amount;
    }

    /**
     * Undo the combo gold amount to original value
     * @param amount Amount to divide by
     */
    public void undoComboGold(double amount){
        comboGold /= amount;
    }

    /**
     * Set the amount to multiply the gold dropped from the enemy
     * @param gold Amount to multiply the enemy gold by
     */
    public void artifactEnemyGold(double gold){
        enemy.setGold(artifactEnemyGold);
        artifactEnemyGold = gold;
    }

    /**
     * Resets everything instead of only when retiring
     */
    public void hardReset(){
        table.hardReset();
        hero.hardReset();
        artifactList.reset();
    }

    //Getters for variables
    public ComboList getComboList(){return combos;}
    public AbilityList getAbilityList(){return abilityList;}
    public ArtifactList getArtifactList(){return artifactList;}
    public Array<Enemy> getEnemies(){return activeEnemies;}
    public Enemy getEnemy(){return enemy;}
    public Stage getStage(){return stage;}
    public ComboText getCombo(){return comboText;}
    public Hero getHero(){return hero;}
    public Menu getMenu(){return menu;}
    public Helpers getHelpers(){return helpers;}
    public Background getBackground(){return background;}
    public NameScreen getNameScreen(){return nameScreen;}
    public UpgradeTable getTable(){return table;}
    public Skin getSkin(){return skin;}
    public int getVersion(){return newestVersion;}
    public static String getNewestVersionString(){return newestVersionString;}
    public double getArtifactEnemyGold(){return artifactEnemyGold;}

    /**
     * Dispose of assets
     */
    public void dispose(){
        healthText.dispose();
        goldText.dispose();
        questText.dispose();
        skin.dispose();
        stage.dispose();
        for(int i = 0;i< abilityList.getAbilities().size;i++){
            abilityList.getAbilities().get(i).dispose();
        }
    }
}

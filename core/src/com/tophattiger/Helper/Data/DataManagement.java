package com.tophattiger.Helper.Data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Json;
import com.tophattiger.GameWorld.GameRenderer;

import java.util.Calendar;

/**
 * Created by Collin on 5/25/2015.
 */
public  class DataManagement {

    /**Called to save the game state to the JSON file
     * @param  fileName Name of the file to save to
     * @param  game Renderer class being used
     */
    public static void saveGame(String fileName, GameRenderer game){
        JsonData jData = new JsonData();

        jData.currentVersion = game.getVersion();
        jData.gold = Gold.getGold();
        game.getHero().save(jData);
        jData.closeTime = DataHolder.currentTime;
        jData.helperAmount = DataHolder.helperAmount;
        jData.combosUnlocked = game.getComboList().getCombosUnlocked();
        jData.abilitiesUnlocked = game.getAbilityList().getAbilitiesUnlocked();
        jData.artifactsUnlocked = game.getArtifactList().getArtifactsUnlocked();
        jData.damageDoneI = JsonData.damageDone;
        jData.damageDoneByHelpersI = JsonData.damageDoneByHelpers;
        jData.damageDoneByHeroI = JsonData.damageDoneByHero;
        jData.clicksI = JsonData.clicks;
        jData.enemiesDefeatedI = JsonData.enemiesDefeated;
        jData.goldCollectedI = JsonData.goldCollected;
        jData.idleGoldCollectedI = JsonData.idleGoldCollected;
        jData.maxComboI = JsonData.maxCombo;
        jData.timeSpentI = JsonData.timeSpent;
        JsonData.helpers.clear();
        JsonData.comboLevels.clear();
        JsonData.abilityLevels.clear();
        JsonData.artifacts.clear();
        game.getHelpers().saveGame();
        game.getComboList().save();
        game.getAbilityList().save();
        game.getArtifactList().save();

        jData.comboLevel = JsonData.comboLevels;
        jData.helper = JsonData.helpers;
        jData.abilityLevel = JsonData.abilityLevels;
        jData.artifact = JsonData.artifacts;

        Json json = new Json();
        writeFiles(fileName,json.toJson(jData));

    }

    /**Called to load the game with a JSON file
     *
     * @param fileName Name of the file to save to
     * @param game  Renderer class being used
     */
    public static void loadGame(String fileName, GameRenderer game) {
        String save = readFile(fileName);
        if (!save.isEmpty()) {
            Json json = new Json();
            JsonData jData = json.fromJson(JsonData.class, save);

            Gold.setGold(jData.gold);
            game.getHero().load(jData);
            game.getEnemy().init();
            //Check for version recorded to make sure version conflicts don't arise when updating
            if(jData.currentVersion >= 9){
                JsonData.artifacts = jData.artifact;
                JsonData.offeringArtifact = jData.offeringArtifactI;
                game.getArtifactList().setArtifactsUnlocked(jData.artifactsUnlocked);
                game.getArtifactList().load();
            }
            if(jData.currentVersion >= 1) {
               DataHolder.pastTime = jData.closeTime;
                DataHolder.helperAmount = jData.helperAmount;
                JsonData.helpers = jData.helper;
                game.getHelpers().loadGame();
                DataHolder.timeDif = (DataHolder.currentTime.getTimeInMillis() - DataHolder.pastTime.getTimeInMillis())/1000;
                game.autoGold();
                game.getComboList().setCombosUnlocked(jData.combosUnlocked);
                JsonData.comboLevels = jData.comboLevel;
                game.getComboList().load();
                if(jData.currentVersion >= 5){
                    JsonData.abilityLevels = jData.abilityLevel;
                    game.getAbilityList().setAbilitiesUnlocked(jData.abilitiesUnlocked);
                    game.getAbilityList().load();
                    if(jData.currentVersion >= 11) {
                        JsonData.clicks = jData.clicksI;
                        JsonData.enemiesDefeated = jData.enemiesDefeatedI;
                        JsonData.damageDone = jData.damageDoneI;
                        JsonData.damageDoneByHelpers = jData.damageDoneByHelpersI;
                        JsonData.damageDoneByHero = jData.damageDoneByHeroI;
                        JsonData.goldCollected = jData.goldCollectedI;
                        JsonData.idleGoldCollected = jData.idleGoldCollectedI;
                        JsonData.timeSpent = jData.timeSpentI;
                        JsonData.maxCombo = jData.maxComboI;
                    }
                }
            }
            else {
                DataHolder.pastTime = DataHolder.currentTime;
                DataHolder.helperAmount = 1;
                JsonData.clicks = JsonData.enemiesDefeated = JsonData.damageDone = JsonData.damageDoneByHelpers = JsonData.damageDoneByHero  = JsonData.goldCollected = JsonData.idleGoldCollected = JsonData.maxCombo= 0;
                JsonData.timeSpent = 0;
            }
        }
        if(save.isEmpty() || game.getHero().getName().equals("Hero's Name"))
            game.getNameScreen().show();
    }

    /** Reset the game to beginning state
     *
     * @param game GameRenderer to reset
     */
    public static void reset(GameRenderer game){
        Gold.reset();
        game.getHero().reset();
        game.getHelpers().reset();
        game.getBackground().reset();
        game.getComboList().reset();
        game.getAbilityList().reset();
        DataHolder.helperAmount = 1;
        game.getTable().resetButton();
        game.getEnemy().reset();
        game.getMenu().close();
        game.getNameScreen().show();
    }

    private static void writeFiles(String fileName, String s){
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    private static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }

    /** Class to hold data for saving and loading*/
    public static class JsonData{
        public int questCompleted,questRequired,questSelect,volCompleted,volRequired,helperAmount,touchLevel,combosUnlocked,abilitiesUnlocked,currentVersion,artifactsUnlocked, inheritance;
        public boolean hasRetired;
        public static IntArray abilityLevels = new IntArray();
        public static IntArray helpers = new IntArray();
        public static IntArray comboLevels = new IntArray();
        public static IntArray artifacts = new IntArray();
        public static int offeringArtifact,clicks,enemiesDefeated,damageDone,damageDoneByHero,damageDoneByHelpers,goldCollected,idleGoldCollected, maxCombo;
        public static float timeSpent;
        IntArray artifact;
        IntArray comboLevel;
        IntArray helper;
        IntArray abilityLevel;
        int offeringArtifactI,clicksI,enemiesDefeatedI,damageDoneI,damageDoneByHeroI,damageDoneByHelpersI,goldCollectedI,idleGoldCollectedI, maxComboI;
        float timeSpentI;
        public double clickPower,questProgress,gold,touchCost,volProgress;
        public Calendar closeTime;
        public String name;
    }
}

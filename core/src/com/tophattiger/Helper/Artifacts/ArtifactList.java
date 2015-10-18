package com.tophattiger.Helper.Artifacts;

import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameWorld.GameRenderer;

import java.util.Random;

/**
 * Created by Collin on 10/8/2015.
 */
public class ArtifactList {
    BaseArtifact artifact1, artifact2, artifact3;
    Array<BaseArtifact> artifacts = new Array<BaseArtifact>();
    int artifactsUnlocked;
    GameRenderer game;
    Random rand;

    /**
     * List of all of the artifacts the user can buy and upgrade
     * @param _game Game to put the combos into
     */
    public ArtifactList(GameRenderer _game){
        game = _game;
        createArtifacts();
        artifactsUnlocked = 0;
        rand = new Random();
    }

    /**
     * Create the artifacts and add them to the array
     */
    private void createArtifacts(){
        artifact1 = new BaseArtifact(1.25,3, BaseArtifact.TYPE.ALLHELPERDAMAGE,game);
        artifacts.add(artifact1);
        artifact2 = new BaseArtifact(1.2,2, BaseArtifact.TYPE.HERODAMAGE,game);
        artifacts.add(artifact2);
        artifact3 = new BaseArtifact(1.4,5, BaseArtifact.TYPE.ENEMYGOLD,game);
        artifacts.add(artifact3);
    }

    /**
     * Increase the amount of artifacts that have been unlocked
     */
    public void addArtifact(){
        artifactsUnlocked ++;
    }

    /**
     * Return a random artifact from the list if there is one remaining.
     * @return Random artifact
     */
    public BaseArtifact getRandomArtifact(){
        if(artifactsUnlocked >= artifacts.size)
            return null;
       while(true){
           int artifact = rand.nextInt(artifacts.size);
           if(artifacts.get(artifact).getLevel() == 0){
               artifacts.get(artifact).switchOffering();
               return artifacts.get(artifact);
           }
       }
    }

    public BaseArtifact getArtifact(int i){
        return artifacts.get(i);
    }

    public int getArtifactMax(){
        return artifacts.size;
    }

    public int getArtifactsUnlocked(){return artifactsUnlocked;}
    public void setArtifactsUnlocked(int _artifactsUnlcocked){artifactsUnlocked= _artifactsUnlcocked;}

    /**
     * Reset the each artifact
     */
    public void reset(){
        for(int i = 0;i< artifactsUnlocked;i++){
            artifacts.get(i).reset();
        }
        artifactsUnlocked = 1;
    }

    /**
     * Save each artifact
     */
    public void save(){
        for(int i = 0;i< artifactsUnlocked;i++){
            artifacts.get(i).save();
        }
    }

    /**
     * Load each artifact
     */
    public void load(){
        for(int i = 0;i< artifactsUnlocked;i++){
            artifacts.get(i).load(i);
        }
    }

}

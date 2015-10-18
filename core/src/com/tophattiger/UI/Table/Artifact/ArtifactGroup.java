package com.tophattiger.UI.Table.Artifact;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.tophattiger.Helper.Artifacts.BaseArtifact;
import com.tophattiger.Helper.Data.Gold;

/**
 * Created by Collin on 10/8/2015.
 */
public class ArtifactGroup {
    ArtifactTable table;
    Image picture;
    Label level, description,currentAmount;
    TextButton artifactButton;
    BaseArtifact artifact;

    /**
     * Group to handle artifact upgrades. Has amount, description, picture and button
     * @param _table Table to add to
     * @param _artifact Artifact for group
     */
    public ArtifactGroup(ArtifactTable _table, BaseArtifact _artifact){
        table = _table;
        artifact = _artifact;
        level = new Label("Level " + Gold.getNumberWithSuffix(artifact.getLevel()),table.getSkin(),"level");
        currentAmount = new Label("Current: " + Gold.getNumberWithSuffix(artifact.getCurrentAmount()),table.getSkin(),"level");
        description = new Label(artifact.getDescription(),table.getSkin(),"combo");
        description.setWrap(true);
        description.setAlignment(Align.right);
        picture = artifact.getPicture();
        artifactButton = new TextButton(Gold.getNumberWithSuffix(artifact.getCost()),table.skin,"gold");
        artifactButton.setDisabled(true);
        artifactButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!artifactButton.isChecked() && artifact.getCost() <= table.getHero().getInheritance()) {  //If the user has enough gold
                    table.getHero().buyArtifact(artifact.getCost());
                    artifact.level();
                    artifactButton.setText(Gold.getNumberWithSuffix(artifact.getCost()));
                    level.setText("Level " + Gold.getNumberWithSuffix(artifact.getLevel()));
                    currentAmount.setText("Current: " + Gold.getNumberWithSuffix(artifact.getCurrentAmount()));
                    description.setText(artifact.getDescription());
                    if (artifact.getLevel() == 1) {     //If this is the first level, add another artifact to the table
                        artifact.switchOffering();
                        table.addArtifact();
                    }
                    return true;
                }
                return false;
            }
        });
        updateState();
    }

    /**
     * Check whether the button should be able to press or not if the player has enough goldd
     */
    public void updateState(){
        if(artifact.getCost() >= table.getHero().getInheritance()){
            artifactButton.setChecked(true);
        }
        else artifactButton.setChecked(false);
    }

    /**
     * Resets the text and button
     */
    public void reset(){
        level.setText("Level " + Gold.getNumberWithSuffix(artifact.getLevel()));
        currentAmount.setText("Current: " + Gold.getNumberWithSuffix(artifact.getCurrentAmount()));
        description.setText(artifact.getDescription());
        artifactButton.setText(Gold.getNumberWithSuffix(artifact.getCost()));
    }

    /**
     * Add the table elements to the table
     */
    public void add(){
        table.add(picture).width(128).left().padLeft(20f);
        table.add(description).pad(5, 20, 0, 20).width(446);
        table.add(artifactButton).width(200f).row();
        table.add(level).left().padLeft(20f);table.add();table.add(currentAmount).row();
    }
}

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
    TextButton abilityButton;
    BaseArtifact artifact;

    public ArtifactGroup(ArtifactTable _table, BaseArtifact _artifact){
        table = _table;
        artifact = _artifact;
        level = new Label("Level " + Gold.getNumberWithSuffix(artifact.getLevel()),table.getSkin(),"level");
        currentAmount = new Label("Current: " + Gold.getNumberWithSuffix(artifact.getCurrentAmount()),table.getSkin(),"level");
        description = new Label(artifact.getDescription(),table.getSkin(),"combo");
        description.setWrap(true);
        description.setAlignment(Align.right);
        picture = artifact.getPicture();
        abilityButton = new TextButton(Gold.getNumberWithSuffix(artifact.getCost()),table.skin,"gold");
        abilityButton.setDisabled(true);
        abilityButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!abilityButton.isChecked() && artifact.getCost() <= Gold.getGold()) {
                    Gold.subtract(artifact.getCost());
                    artifact.level();
                    abilityButton.setText(Gold.getNumberWithSuffix(artifact.getCost()));
                    level.setText("Level " + Gold.getNumberWithSuffix(artifact.getLevel()));
                    currentAmount.setText("Current: " + Gold.getNumberWithSuffix(artifact.getCurrentAmount()));
                    description.setText(artifact.getDescription());
                    if (artifact.getLevel() == 1 && table.getAbilitySize() < table.getAbilityMax()) {
                        table.addAbility();
                    }
                    return true;
                }
                return false;
            }
        });
        updateState();
    }
    public void updateState(){
        if(Gold.isLess(artifact.getCost())){
            abilityButton.setChecked(true);
        }
        else abilityButton.setChecked(false);
    }
    public void reset(){
        level.setText("Level " + Gold.getNumberWithSuffix(artifact.getLevel()));
        currentAmount.setText("Current: " + Gold.getNumberWithSuffix(artifact.getCurrentAmount()));
        description.setText(artifact.getDescription());
        abilityButton.setText(Gold.getNumberWithSuffix(artifact.getCost()));
    }

    public void add(){
        table.add(picture).width(128).left().padLeft(20f);
        if(table.getNameTextLength()>= 114)
            table.add(description).pad(5, 20, 0, 20).width(560-table.getNameTextLength());
        else
            table.add(description).pad(5, 20, 0, 20).width(446);
        table.add(abilityButton).width(200f).row();
        table.add(level).left().padLeft(20f);table.add();table.add(currentAmount).row();
    }
}

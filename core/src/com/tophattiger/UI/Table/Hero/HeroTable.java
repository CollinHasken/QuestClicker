package com.tophattiger.UI.Table.Hero;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.tophattiger.GameObjects.Characters.Hero;
import com.tophattiger.Helper.Abilities.AbilityList;
import com.tophattiger.Helper.Data.AssetLoader;
import com.tophattiger.Helper.Combos.ComboList;
import com.tophattiger.Helper.Data.Gold;
import com.tophattiger.UI.Table.UpgradeTable;


/**
 * Created by Collin on 6/24/2015.
 */
public class HeroTable extends Table {

    final int FONTWIDTH = 100;

    ScrollPane container;
    Skin skin;
    TextButton heroButton;
    Image heroImage;
    Label name, heroLevel,touchPower;
    Hero hero;
    GlyphLayout nameText;
    ComboList combos;
    AbilityList abilityList;
    Array<ComboGroup> comboGroups = new Array<ComboGroup>();
    Array<AbilityGroup> abilityGroups = new Array<AbilityGroup>();
    String[] names = new String[8];

    public HeroTable(Skin _skin, Hero _hero, ComboList _combos, AbilityList _abilityList){
        super(_skin);
        hero = _hero;
        skin = _skin;
        combos = _combos;
        abilityList = _abilityList;
        setVariables();
        container = new ScrollPane(this);
    }

    public void setAsContainer(UpgradeTable table){
        table.setContainer(this.container);
    }

    void setVariables(){
        heroImage = hero.getImage();
        nameText = new GlyphLayout(AssetLoader.nameFont,hero.getName());
        name = new Label(nameText.toString(),skin, "name");
        heroLevel = new Label("Level " + Gold.getNumberWithSuffix(hero.getLevel()),skin,"level");
        touchPower = new Label(Gold.getNumberWithSuffix(hero.getTouchPower()) + " Damage",skin,"level");
        heroButton = new TextButton(Gold.getNumberWithSuffix(hero.getTouchCost()),skin,"gold");
        heroButton.setDisabled(true);
        heroButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (!heroButton.isChecked() && Gold.isGreaterEqual(hero.getTouchCost())) {
                    Gold.subtract(hero.getTouchCost());
                    hero.levelTouch();
                    heroButton.setText(Gold.getNumberWithSuffix(hero.getTouchCost()));
                    heroLevel.setText("Level " + Gold.getNumberWithSuffix(hero.getLevel()));
                    touchPower.setText(hero.getTouchPowerString() + " Damage");
                    return true;
                }
                return false;
            }
        });
    }

    public void updateState(){
        if(Gold.isGreaterEqual(hero.getTouchCost())){
            heroButton.setChecked(false);
        }
        else heroButton.setChecked(true);
        if(combos.getCombosUnlocked() != 0) {
            for (int i = 0; i < combos.getCombosUnlocked(); i++) {
                comboGroups.get(i).updateState();
            }
        }
        if(abilityList.getAbilitiesUnlocked() != 0) {
            for (int i = 0; i < abilityList.getAbilitiesUnlocked(); i++) {
                abilityGroups.get(i).updateState();
            }
        }
    }

    public void addCombo(){
        combos.addCombo();
        comboGroups.add(new ComboGroup(this, combos.getCombo()));
        clear();
        addHero();
        addAbilities();
        addCombos();
    }

    public void addAbility(){
        abilityList.addAbility();
        abilityGroups.add(new AbilityGroup(this, abilityList.getAbility()));
        clear();
        addHero();
        addAbilities();
        addCombos();
    }

    public void addAbilities(){
        for(int i = 0;i<abilityGroups.size;i++){
            abilityGroups.get(i).add();
        }
    }

    public void addCombos(){
        for(int i = 0;i<combos.getCombosUnlocked();i++){
            comboGroups.get(i).add();
        }
    }

    public int getComboMax(){
        return combos.getComboMax();
    }

    public int getAbilityMax(){
        return abilityList.getAbilityMax();
    }

    public int getComboSize(){
        return combos.getCombosUnlocked();
    }

    public int getAbilitySize(){
        return abilityList.getAbilitiesUnlocked();
    }

    public void setNameText(){
        nameText.setText(AssetLoader.nameFont,hero.getName());
        names = nameText.toString().split(",");
        name.setText(names[0]);
    }

    public float getNameTextLength(){
        return nameText.width;
    }
    public void load(){
        setNameText();
        addHero();
        for(int i = 0;i<abilityList.getAbilitiesUnlocked();i++){
            abilityGroups.add(new AbilityGroup(this, abilityList.getAbility(i)));
            abilityGroups.get(i).add();
        }
        for(int i = 0;i<combos.getCombosUnlocked();i++){
            comboGroups.add(new ComboGroup(this,combos.getCombo(i)));
            comboGroups.get(i).add();
        }
        heroLevel.setText("Level " + Gold.getNumberWithSuffix(hero.getLevel()));
        touchPower.setText(Gold.getNumberWithSuffix(hero.getTouchPower()) + " Damage");
        heroButton.setText(Gold.getNumberWithSuffix(hero.getTouchCost()));
    }

    public void addHero(){
        this.setWidth(800);
        add(heroImage).width(128).left().padLeft(20f);add(heroLevel).right().padRight(20);add(heroButton).width(200).row();
        add(name).padBottom(20f).center().padLeft(20f);add();add(touchPower).center().row();
    }

    public void updateInheritance(){
        abilityGroups.get(abilityList.getAbilitiesUnlocked() - 1).updateDescription();
    }

    public void reset(){
        this.clearChildren();
        addHero();
        heroButton.setText(Gold.getNumberWithSuffix(hero.getTouchCost()));
        heroLevel.setText("Level " + Gold.getNumberWithSuffix(hero.getLevel()));
        touchPower.setText(Gold.getNumberWithSuffix(hero.getTouchPower()) + " Damage");
        abilityGroups.clear();
        abilityGroups.add(new AbilityGroup(this, abilityList.getAbility(0)));
        abilityGroups.get(0).add();
        abilityGroups.get(0).updateState();
        comboGroups.clear();
        comboGroups.add(new ComboGroup(this, combos.getCombo(0)));
        comboGroups.get(0).add();
        comboGroups.get(0).updateState();
    }

    public ScrollPane getContainer(){return container;}
}

package com.game.object.Skills;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.GUI.GUI;
import com.game.GUI.SkillBar;
import com.game.object.klasy.Super_Class_Klasy;

import java.util.ArrayList;

/**
 * Created by Mazek on 2016-05-21.
 */
public class SkillWindow {

    private static Stage skillWindow = GUI.getGUI_stage();
    private static ArrayList<Skill_Info> cell_Skill = new ArrayList<Skill_Info>();
    private static ArrayList<Skill> skills;
//    private static Group widget;
//    private static ScrollPane scrollPane;

    public SkillWindow(Super_Class_Klasy klasa) {
        this.skills = (ArrayList<Skill>) klasa.getSkillList();
        //this.widget = new Group();

        for(int i = 0; i < 8; i++){
            Skill_Info e = new Skill_Info(klasa.getSkillList().get(i));
            e.setVisible(false);
            e.setPosition(100 + (i * 200), 150);
            cell_Skill.add(e);
            GUI.getGUI_stage().addActor(cell_Skill.get(i));
            }
    }

    public static void addSource(){
        for(int i =0; i < cell_Skill.size(); i++){
            for(int j = 0; j < skillWindow.getActors().size; j++){
                if(skillWindow.getRoot().getChildren().get(j).getName().startsWith("Skill_info+")){
                    skillWindow.getRoot().getChildren().get(j).setVisible(true);
                }

            }
            SkillBar.dragAndDrop.addSource(
                    createSource(
                            cell_Skill.get(i)
                    ));
        }
        System.out.println("Dodałem source");
    }

    public static void removeSource(){
        for(int i =0; i < cell_Skill.size(); i++){
            SkillBar.dragAndDrop.removeSource(
                    createSource(
                            cell_Skill.get(i)
                    ));
            for(int j = 0; j < skillWindow.getActors().size; j++){
                if(skillWindow.getRoot().getChildren().get(j).getName().startsWith("Skill_info+")){
                    skillWindow.getRoot().getChildren().get(j).setVisible(false);
                }

                }
            }
        System.out.println("Usunąłem source");
    }

    public static Source createSource(final Actor source){
        return new Source(source) {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {

                } else {
                    ((Image)target.getActor()).setDrawable(((Skill_Info) source).getSkillImage().getDrawable());
                }
            }

            public Payload dragStart (InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                payload.setObject(source);

                payload.setDragActor(new Label("Pole", new LabelStyle(new BitmapFont(), Color.WHITE)));

                Label validLabel = new Label("Upusc!", new LabelStyle(new BitmapFont(), Color.WHITE));
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Co ty Kurwa robisz ?!", new LabelStyle(new BitmapFont(), Color.WHITE));
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        };
    }
}
package com.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.game.object.Skills.SkillSlot;
import com.game.operations.WorldController;

import java.util.ArrayList;
/**
 * Pasek Skilla, znajdują sie tutaj skille które
 * uzytkownik wybierze do uzytku podczas walki
 * prawdopodobnie znajdą sie tutaj też potki i inne pierdoly
 * do których użytkownik bedzie miał błyskawiczny dostep
 * za pomocą klawiszy od 1 do 0
 * Created by Mazek on 2016-05-16.
 */
class SkillBar {

    ArrayList<Actor> sourceActor;
    ArrayList<SkillSlot> validActor;
    ArrayList<Actor> invalidActor;

    WorldController worldController;


    private NinePatch noCheckTexture = new NinePatch (new Texture(Gdx.files.internal ("res/gui/SkillBar/NoCheck.png")),9,9,9,9);
    private NinePatch checkTexture = new NinePatch (new Texture (Gdx.files.internal ("res/gui/SkillBar/Check.png")),9,9,9,9);

    SkillBar(WorldController wC){
        this.worldController = wC;

        final Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("Slot", new Texture("res/gui/SkillBar/NoCheck.png"));
        skin.add("badlogic", new Texture("badlogic.jpg"));

        DragAndDrop dragAndDrop = new DragAndDrop();
        validActor = new ArrayList<SkillSlot>();
        invalidActor = new ArrayList<Actor>();
        sourceActor = new ArrayList<Actor>();

        for(int i = 0; i < 10; i++){

            final Actor emptySlot = new Actor();
            emptySlot.setBounds(390 + (i*50), 0, 50, 50);
            SkillSlot slot = new SkillSlot(i, emptySlot,new Image(skin, "Slot"));
            worldController.objectMap.
                    get(0).addActor(slot.getImageEmptySlot());
            worldController.objectMap.
                    get(1).addActor(slot.getImageEmptySlot());
            worldController.objectMap.
                    get(2).addActor(slot.getImageEmptySlot());
            dragAndDrop.addTarget(createTarget(slot, true));
            validActor.add(slot);
        }

        final Image sourceImage1 = new Image(skin, "badlogic");
        sourceImage1.setBounds(50, 125, 50, 50);
        worldController.objectMap.
                get(worldController.aMap).addActor(sourceImage1);
        sourceActor.add(sourceImage1);

        dragAndDrop.addSource(createSource(sourceImage1, skin));

        Image sourceImage2 = new Image(skin, "badlogic");
        sourceImage2.setBounds(125, 125, 50, 50);
        worldController.objectMap.
                get(worldController.aMap).addActor(sourceImage2);
        sourceActor.add(sourceImage2);

        dragAndDrop.addSource(createSource(sourceImage2, skin));

//        skill_block = new ArrayList<Cell>();
//        for(int i=0; i < 10; i++){
//            Rectangle e = new Rectangle(390 + (i*50), 0, 50, 50);
//            Actor actor = new Actor();
//            skill_block.add(new Cell(e, actor));
//            wC.objectMap.get(wC.aMap).getStage().addActor(actor);
//        }
    }

    void render(SpriteBatch batch, int skill){
        batch.begin();
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //worldController.objectMap.get(worldController.aMap).act(Gdx.graphics.getDeltaTime());
        worldController.objectMap.get(worldController.aMap).draw();
        batch.end();
    }


    private Target createTarget(SkillSlot skillSlot, final boolean valid){
        final Image target = skillSlot.getImageEmptySlot();
        return new Target(target) {
            @Override
            public void reset(Source source, Payload payload) {
                super.reset(source, payload);
            }

            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                payload.getValidDragActor().setColor(Color.GREEN);
                return valid;
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {

                source.getActor().setPosition(target.getX(),target.getY());
                target.setPosition(-50,-50);
            }
        };
    }

    private Source createSource(final Actor source, final Skin skin){
        return new Source(source) {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {
                    Actor actor = (Actor) payload.getObject();
                    actor.setPosition(50, 125);

                    System.out.println(pointer);

                    for (int i = 0; i < validActor.size(); i++) {
                        if ((validActor.get(i).getEmptySlot().getX() != validActor.get(i).getImageEmptySlot().getX()) &&
                                (validActor.get(i).getEmptySlot().getY() != validActor.get(i).getImageEmptySlot().getY())) {
                            validActor.get(i).getImageEmptySlot().setPosition(
                                    validActor.get(i).getEmptySlot().getX(),
                                    validActor.get(i).getEmptySlot().getY()
                            );
                        }
                    }
                }
            }

            public Payload dragStart (InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                payload.setObject(source);

                payload.setDragActor(new Label("Pole", skin));

                Label validLabel = new Label("Upusc!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Co ty Kurwa robisz ?!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);



                return payload;
            }
        };
    }
}

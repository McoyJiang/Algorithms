package com.danny_jiang.algorithm.data_structure.queue;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.views.BaseGdxActor;

import java.util.ArrayList;
import java.util.List;

class QueueAdapter extends AlgorithmAdapter {

    private static final int DEMO_OUT = 1;
    private static final String[] person_list = new String[]{
            "data_structure/queue/queue_person1.png",
            "data_structure/queue/queue_person2.png",
            "data_structure/queue/queue_person3.png",
            "data_structure/queue/queue_person4.png"
    };
    private List<Actor> demoActors = new ArrayList<>();

    private QueueContainer stackContainer;
    @Override
    protected void inflateStage() {
        stackContainer = new QueueContainer();
        stackContainer.setSize(300, 500);
        stackContainer.setPosition(stage.getWidth() / 2 - stackContainer.getWidth() / 2 - 12.5f,
                stage.getHeight() - stackContainer.getHeight() - 280);
        stage.addActor(stackContainer);

        for (int i = 0; i < person_list.length; i++) {
            BaseGdxActor actor = new BaseGdxActor(
                    new TextureRegion(new Texture(person_list[i])));
            actor.setSize(100, 120);
            actor.setPosition(-150, stage.getHeight() - actor.getHeight() - 50);
            demoActors.add(actor);
        }
        for (int i = person_list.length - 1; i >= 0; i--) {
            Actor actor = demoActors.get(i);
            stage.addActor(actor);
            MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - actor.getWidth() / 2,
                    stage.getHeight() - actor.getHeight() - 50);
            firstMove.setDuration(0.5f);
            MoveByAction secondMove = Actions.moveBy(0,
                    -(actor.getY() - stackContainer.getY()) + i * 110 + 30);
            secondMove.setDuration(0.5f);
            SequenceAction sequence = Actions.sequence(Actions.delay(i * 0.3f),
                    firstMove, secondMove);
            actor.addAction(sequence);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case DEMO_OUT:
                demoOut();
                break;
        }
    }

    private void demoOut() {
        Gdx.app.postRunnable(() -> {
            for (int i = person_list.length - 1; i >= 0; i--) {
                Actor actor = demoActors.get(i);
                stage.addActor(actor);
                MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - actor.getWidth() / 2,
                        stackContainer.getY() - actor.getHeight() - 10);
                firstMove.setDuration(0.5f);
                MoveByAction secondMove = Actions.moveBy(stage.getWidth() / 2 + 100,0);
                secondMove.setDuration(0.5f);
                SequenceAction sequence = Actions.sequence(Actions.delay(i * 0.3f),
                        firstMove, secondMove);
                actor.addAction(sequence);
            }
        });
    }

    @Override
    protected void algorithm() {
        await();

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEMO_OUT, 0, 0));
        });
    }

}

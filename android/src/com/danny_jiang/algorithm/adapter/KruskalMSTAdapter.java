package com.danny_jiang.algorithm.adapter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.utils.BezierAction;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.List;

public class KruskalMSTAdapter extends ApplicationAdapter{
    Stage stage;
    Group group;
    List<AlgorithmBall> actorList;

    @Override
    public void create() {
        super.create();

        stage = new Stage();
        group = new Group();
        group.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);
        actorList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            AlgorithmBall actor = new AlgorithmBall("" + i);
            actor.setIndex(i);
            actor.setSize(100, 100);
            actor.setPosition(i * 200 + 100, 200);
            actor.setDebug(true);
            actorList.add(actor);
            group.addActor(actor);
            actor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AlgorithmBall ball = (AlgorithmBall) event.getListenerActor();
                    int index = ball.getIndex();
                    switchChild(index, (index + 1) % 5);
                }
            });
        }


    }

    @Override
    public void render() {
        super.render();
        // 黄色清屏
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    public void switchChild(int first, int second) {
        final AlgorithmBall actorFirst = actorList.get(first);
        final AlgorithmBall actorSecond = actorList.get(second);

        float actorFirstX = actorFirst.getX();
        float actorFirstY = actorFirst.getY();

        float actorSecondX = actorSecond.getX();
        float actorSecondY = actorSecond.getY();

        Bezier<Vector2> bezierFirst = new Bezier<Vector2>(
                new Vector2(actorFirstX, actorFirstY),
                new Vector2(actorFirstX - actorFirstX / 3, actorFirstY - actorFirstY / 6),
                new Vector2(actorSecondX - actorSecondX / 4, actorSecondY - actorSecondY / 6),
                new Vector2(actorSecondX, actorSecondY)
        );
        Bezier<Vector2> bezierSecond = new Bezier<Vector2>(
                new Vector2(actorSecondX, actorSecondY),
                new Vector2(actorSecondX + actorSecondX / 4, actorSecondY + actorSecondY / 6),
                new Vector2(actorFirstX + actorFirstX / 3, actorFirstY + actorFirstY / 6),
                new Vector2(actorFirstX, actorFirstY)
        );
        BezierAction bezierFirstAction = BezierAction.createAction(bezierFirst, 1f);
        bezierFirstAction.setTarget(actorFirst);
        BezierAction bezierSecondAction = BezierAction.createAction(bezierSecond, 1f);
        bezierSecondAction.setTarget(actorSecond);

        ParallelAction parallel = Actions.parallel(bezierFirstAction, bezierSecondAction);
        RunnableAction resetList = Actions.run(new Runnable() {
            @Override
            public void run() {
                actorFirst.setIndex(second);
                actorSecond.setIndex(first);
                actorList.remove(first);
                actorList.add(first, actorSecond);
                actorList.remove(second);
                actorList.add(second, actorFirst);

            }
        });
        stage.addAction(Actions.sequence(parallel, resetList));
    }
}

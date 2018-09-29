package com.danny_jiang.algorithm.bubble_sort;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.utils.AnimationUtils;
import com.danny_jiang.algorithm.utils.BezierAction;
import com.danny_jiang.algorithm.views.AlgorithmBall;
import com.danny_jiang.algorithm.views.BaseGdxActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BubbleSortAdapter extends ApplicationAdapter {

    private int[] array = {45, 17, 23, 5, 76, 10, 4};
    private Stage stage;
    private List<AlgorithmBall> actorList;

    @Override
    public void create() {
        super.create();
        Collections.shuffle(Arrays.asList(array));
        stage = new Stage();
        Image bg = new Image(new Texture(Gdx.files.internal("bg/visualizer_bg.png")));
        bg.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.align(Align.center);
        horizontalGroup.space(30);
        horizontalGroup.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(horizontalGroup);

        BaseGdxActor next = new BaseGdxActor(new TextureRegion(new Texture(
                Gdx.files.internal("badlogic.jpg")
        )));
        next.setSize(200, 100);
        next.setPosition(400, 100);
        stage.addActor(next);
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextStep();
            }
        });
        Gdx.input.setInputProcessor(stage);
        actorList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            AlgorithmBall myView = new AlgorithmBall("" + array[i]);
            myView.setIndex(i);
            myView.setSize(100, 100);
            myView.setPosition(i * 150 + 50, 200);
            actorList.add(myView);
            horizontalGroup.expand().rowCenter().addActor(myView);
            myView.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AlgorithmBall ball = (AlgorithmBall) event.getListenerActor();
                    int index = ball.getIndex();
                    switchChild(index, (index + 1) % 7);
                }
            });
        }
    }

    @Override
    public void render() {
        super.render();
        // 黄色清屏
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    private int i = 6;
    private int j = 0;
    public void nextStep() {
        if (j < i) {
            if (array[j] > array[j + 1]) {
                switchChild(j, j + 1);
                swapArray(j);
            }
            j++;
        } else {
            actorList.get(j).deadStatus();
            j = 0;
            i--;
        }
        System.out.println(Arrays.toString(array));
    }

    private void swapArray(int j) {
        int temp = array[j];
        array[j] = array[j + 1];
        array[j + 1] = temp;
    }

    void bubble_sort(int A[], int n) {
        int temp;
        for(int i = 0; i < n-1; i++) {
            // (n-k-1) is for ignoring comparisons of elements which have already been compared in earlier iterations

            for(int j = 0; j < n - i - 1; j++) {
                if(A[j] > A[j + 1] ) {
                    // here swapping of positions is being done.
                    temp = A[j];
                    A[j] = A[j];
                    A[j + 1] = temp;
                }
            }
        }
    }

    public void switchChild(int first, int second) {
        final AlgorithmBall actorFirst = actorList.get(first);
        final AlgorithmBall actorSecond = actorList.get(second);

        stage.addAction(AnimationUtils.curveSwitchActors(actorFirst, actorSecond, new Runnable() {
            @Override
            public void run() {
                actorFirst.setIndex(second);
                actorSecond.setIndex(first);
                actorList.remove(first);
                actorList.add(first, actorSecond);
                actorList.remove(second);
                actorList.add(second, actorFirst);
            }
        }));
    }
}

package com.danny_jiang.algorithm.bubble_sort;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.utils.AnimationUtils;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * void bubble_sort(int A[], int n) {
 *  int temp;
 *  for(int i = 0; i < n-1; i++) {
 *      for(int j = 0; j < n - i - 1; j++) {
 *          if(A[j] > A[j + 1] ) {
 *              // here swapping of positions is being done.
 *              temp = A[j];
 *              A[j] = A[j];
 *              A[j + 1] = temp;
 *          }
 *      }
 *  }
 * }
 */
public class BubbleSortAdapter extends AlgorithmAdapter {

    private HorizontalGroup bubbleSortGroup;
    private List<AlgorithmBall> actorList;

    @Override
    public void create() {
        super.create();

        initializeActors();
    }

    private void initializeActors() {
        actorList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + array[i]);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.setPosition(i * 150 + 50, 200);
            actorList.add(algorithmBall);
            bubbleSortGroup.addActor(algorithmBall);
        }
    }

    @Override
    protected void inflateStage() {
        bubbleSortGroup = new HorizontalGroup();
        bubbleSortGroup.align(Align.center);
        bubbleSortGroup.space(30);
        bubbleSortGroup.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bubbleSortGroup);
    }

    @Override
    protected void initData() {
        array = new int[]{45, 17, 23, 5, 76, 10, 4};
        Collections.shuffle(Arrays.asList(array));
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

    @Override
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

    private void switchChild(int first, int second) {
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

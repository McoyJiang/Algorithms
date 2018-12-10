package com.danny_jiang.algorithm.heap_sort;

import android.os.Message;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.views.AlgorithmBall;

public class HeapSortAdapter extends AlgorithmAdapter{
    private int[] array = new int[]{4, 3, 7, 1, 8, 5};
    private Group group = new Group();

    @Override
    public void create() {
        super.create();

        initializeHeap();
        buildMaxHeap();
    }

    private void initializeHeap() {
        AlgorithmBall firstBall = (AlgorithmBall) group.getChildren().get(4);
        MoveToAction moveToAction = Actions.moveTo(group.getWidth() / 2 - 50,
                group.getHeight() - 400);
        moveToAction.setDuration(2);
        firstBall.addAction(moveToAction);
    }

    private void buildMaxHeap() {

    }

    @Override
    protected void inflateStage() {
        group.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(group);
        for (int i = 0; i < 6; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + array[i]);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.setPosition(i * 150 + 50, group.getHeight() - 200);
            group.addActor(algorithmBall);
        }
    }

    @Override
    public void animation(Message msg) {

    }

    @Override
    protected void algorithm() {

    }

    @Override
    protected void initData() {
    }

}

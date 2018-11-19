package com.danny_jiang.algorithm.data_structure.linkedlist;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

import java.util.Timer;
import java.util.TimerTask;

class LinkListAdapter extends AlgorithmAdapter {

    private int[] arr = new int[]{3, 8, 10, 9, 15, 35};

    @Override
    protected void inflateStage() {
        try {
            NodeActor nodeActor = new NodeActor(4);
            nodeActor.setSize(150, 150);
            nodeActor.setPosition(stage.getWidth() / 2 - nodeActor.getWidth() / 2,
                    stage.getHeight() - nodeActor.getHeight() - 10);
            stage.addActor(nodeActor);

            MoveToAction moveToAction = Actions.moveTo(100, stage.getHeight() * 3 / 4);
            moveToAction.setDuration(1);
            nodeActor.addAction(moveToAction);
        } catch (Exception e) {
            Log.e("BBB", "e here is " + e.getMessage());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {

    }

    @Override
    protected void algorithm() {

    }
}

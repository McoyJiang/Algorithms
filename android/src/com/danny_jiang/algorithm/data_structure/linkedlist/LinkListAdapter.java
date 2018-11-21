package com.danny_jiang.algorithm.data_structure.linkedlist;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

import java.util.ArrayList;
import java.util.List;

class LinkListAdapter extends AlgorithmAdapter {

    private static final int ADD = 1;
    private int[] arr = new int[]{3, 8, 10, 9, 15, 35};

    private List<NodeActor> addedActorList = new ArrayList<>();

    @Override
    protected void inflateStage() {
        try {
            NodeActor currentNodeActor = new NodeActor(8);
            currentNodeActor.setSize(150, 150);
            currentNodeActor.setPosition(
                    stage.getWidth() / 2 - currentNodeActor.getWidth() / 2,
                    stage.getHeight() * 3 / 4 - currentNodeActor.getHeight() / 2);
            stage.addActor(currentNodeActor);
            addedActorList.add(0, currentNodeActor);
        } catch (Exception e) {
            Log.e("BBB", "e here is " + e.getMessage());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case ADD:
                addNodeToHead();
                break;
        }
    }

    private void addNodeToHead() {
        Gdx.app.postRunnable(() -> {
            float x = addedActorList.get(0).getX() + 75;
            float y = addedActorList.get(0).getY();
            NodeActor nodeActor = new NodeActor(4);
            nodeActor.setSize(150, 150);
            nodeActor.setPosition(stage.getWidth() / 2 - nodeActor.getWidth() / 2,
                    stage.getHeight() - nodeActor.getHeight() - 10);
            nodeActor.setTail(false);
            stage.addActor(nodeActor);

            nodeActor.addNodeToHead(x, y);
            for (NodeActor actor : addedActorList) {
                MoveByAction moveByAction = Actions.moveBy(90, 0);
                moveByAction.setDuration(1);
                actor.addAction(moveByAction);
            }
            addedActorList.add(0, nodeActor);
        });
    }

    @Override
    protected void algorithm() {
        await();

        /**
         * ADD
         */
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD, 10, -1));
        });
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD, 10, -1));
        });
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD, 10, -1));
        });
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD, 10, -1));
        });
    }
}

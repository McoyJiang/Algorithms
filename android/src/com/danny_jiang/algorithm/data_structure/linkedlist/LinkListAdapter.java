package com.danny_jiang.algorithm.data_structure.linkedlist;

import android.os.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

import java.util.ArrayList;
import java.util.List;

class LinkListAdapter extends AlgorithmAdapter {

    private static final int ADD_TO_HEAD = 1;
    private static final int ADD_TO_TAIL = 2;

    private int[] arr = new int[]{3, 8, 10, 9, 15, 35};

    private Label stepDescription;
    private List<NodeActor> addedActorList = new ArrayList<>();

    @Override
    protected void inflateStage() {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(
                "data_structure/LinkedList/linkedlist.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = new Color(1, 0, 0, 1);
        stepDescription = new Label("", style);
        stepDescription.setText("链表是一种线性表,它通过指针\n将一组零散的数据链接起来.");
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(30, stage.getHeight() / 3 - 100);
        stage.addActor(stepDescription);

        NodeActor currentNodeActor = new NodeActor(8);
        currentNodeActor.setSize(150, 150);
        currentNodeActor.setPosition(
                stage.getWidth() / 2 - currentNodeActor.getWidth() / 2,
                stage.getHeight() * 3 / 4 - currentNodeActor.getHeight() / 2);
        stage.addActor(currentNodeActor);
        addedActorList.add(0, currentNodeActor);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case ADD_TO_HEAD:
                addNodeToHead(msg.arg1);
                break;
            case ADD_TO_TAIL:
                addNodeToTail(msg.arg1);
                break;
        }
    }

    private void addNodeToHead(final int number) {
        Gdx.app.postRunnable(() -> {
            stepDescription.setText("单链表：addFirst");
            float x = addedActorList.get(0).getX() + 75;
            float y = addedActorList.get(0).getY();
            NodeActor nodeActor = new NodeActor(number);
            nodeActor.setSize(150, 150);
            nodeActor.setPosition(stage.getWidth() / 2 - nodeActor.getWidth() / 2,
                    stage.getHeight() - nodeActor.getHeight() - 10);
            nodeActor.setTail(false);
            stage.addActor(nodeActor);

            MoveToAction moveToAction = Actions.moveTo(x - 180, y);
            moveToAction.setDuration(1);
            nodeActor.addAction(moveToAction);
            nodeActor.addNodeToHead(x, y);
            for (NodeActor actor : addedActorList) {
                MoveByAction moveByAction = Actions.moveBy(90, 0);
                moveByAction.setDuration(1);
                actor.addAction(moveByAction);
            }
            addedActorList.add(0, nodeActor);
        });
    }

    private void addNodeToTail(final int number) {
        Gdx.app.postRunnable(() -> {
            stepDescription.setText("单链表：addLast");
            float x = addedActorList.get(addedActorList.size() - 1).getX() + 75;
            float y = addedActorList.get(0).getY();
            NodeActor nodeActor = new NodeActor(number);
            nodeActor.setSize(150, 150);
            nodeActor.setPosition(stage.getWidth() / 2 - nodeActor.getWidth() / 2,
                    stage.getHeight() - nodeActor.getHeight() - 10);
            nodeActor.setTail(true);
            stage.addActor(nodeActor);

            MoveToAction moveToAction = Actions.moveTo(x + 40, y);
            moveToAction.setDuration(1);
            nodeActor.addAction(moveToAction);
            NodeActor tail = addedActorList.get(addedActorList.size() - 1);
            tail.setTail(false);
            tail.addNodeToHead(x, y);
            for (NodeActor actor : addedActorList) {
                MoveByAction moveByAction = Actions.moveBy(-90, 0);
                moveByAction.setDuration(1);
                actor.addAction(moveByAction);
            }
            addedActorList.add(nodeActor);
        });
    }

    @Override
    protected void algorithm() {
        await();

        /**
         * ADD_TO_HEAD
         */
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD_TO_HEAD, 10, -1));
        });
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD_TO_HEAD, 3, -1));
        });
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD_TO_TAIL, 36, -1));
        });
        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ADD_TO_TAIL, 15, -1));
        });
    }
}

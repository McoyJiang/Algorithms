package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeAdapter extends AlgorithmAdapter {
    
    private static final int HIGHLIGHT_ROOT_NODE = 0;
    private static final int HIGHLIGHT_PARENT_NODE = 1;
    private static final int HIGHLIGHT_LEAF_NODE = 2;

    private List<TreeNodeActor> actorList = new ArrayList<>();

    TreeNodeActor rootNode;
    TreeNodeActor tenNode;
    TreeNodeActor thirtyNineNode;
    TreeNodeActor fortyFiveNode;
    TreeNodeActor fiftySevenNode;
    TreeNodeActor eightyNineNode;
    TreeNodeActor seventyOneNineNode;

    @Override
    protected void inflateStage() {
        // root 60
        rootNode = new TreeNodeActor(60);
        rootNode.setColor(Color.valueOf("#1ca1f2"));
        rootNode.setPosition(stage.getWidth() / 2 - rootNode.getWidth() / 2,
                stage.getHeight() - rootNode.getHeight() * 2);

        // 10
        tenNode = new TreeNodeActor(10);
        tenNode.setColor(Color.valueOf("#e4dd51"));
        tenNode.setPosition(rootNode.getX() - 200, rootNode.getY() - 200);

        // 39
        thirtyNineNode = new TreeNodeActor(39);
        thirtyNineNode.setColor(Color.valueOf("#dc143c"));
        thirtyNineNode.setPosition(rootNode.getX() * 2 - tenNode.getX(), rootNode.getY() - 200);

        // 45
        fortyFiveNode = new TreeNodeActor(45);
        fortyFiveNode.setColor(Color.valueOf("#d199e1"));
        fortyFiveNode.setPosition(700, 800);
        // x + 50 x2 + 50 = (x + 50) * 2
        fortyFiveNode.setPosition(tenNode.getX() - 100, tenNode.getY() - 200);

        // 57
        fiftySevenNode = new TreeNodeActor(57);
        fiftySevenNode.setColor(Color.valueOf("#6a4734"));
        fiftySevenNode.setPosition(700, 800);
        fiftySevenNode.setPosition(tenNode.getX() * 2 - fortyFiveNode.getX(),
                tenNode.getY() - 200);

        // 80
        eightyNineNode = new TreeNodeActor(80);
        eightyNineNode.setColor(Color.valueOf("#6aaf6a"));
        eightyNineNode.setPosition(thirtyNineNode.getX() - 100, thirtyNineNode.getY() - 200);

        // 71
        seventyOneNineNode = new TreeNodeActor(71);
        seventyOneNineNode.setColor(Color.valueOf("#525c68"));
        seventyOneNineNode.setPosition(thirtyNineNode.getX() * 2 - eightyNineNode.getX(),
                thirtyNineNode.getY() - 200);

        stage.addActor(rootNode);
        stage.addActor(tenNode);
        stage.addActor(thirtyNineNode);
        stage.addActor(fortyFiveNode);
        stage.addActor(fiftySevenNode);
        stage.addActor(eightyNineNode);
        stage.addActor(seventyOneNineNode);
        actorList.add(rootNode);
        actorList.add(tenNode);
        actorList.add(thirtyNineNode);
        actorList.add(fortyFiveNode);
        actorList.add(fiftySevenNode);
        actorList.add(eightyNineNode);
        actorList.add(seventyOneNineNode);

        /**
         * construct the Tree
         */
        rootNode.setLeftChild(tenNode);
        rootNode.setRightChild(thirtyNineNode);

        tenNode.setLeftChild(fortyFiveNode);
        tenNode.setRightChild(fiftySevenNode);

        thirtyNineNode.setLeftChild(eightyNineNode);
        thirtyNineNode.setRightChild(seventyOneNineNode);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void animation(Message msg) {
        int number = msg.arg1;
        switch (msg.what) {
            case HIGHLIGHT_ROOT_NODE:
                Gdx.app.postRunnable(() -> highlightNode(Arrays.asList(60)));
                break;
            case HIGHLIGHT_PARENT_NODE:
                Gdx.app.postRunnable(() -> Gdx.app.postRunnable(()
                        -> highlightNode(Arrays.asList(10, 45, 57))));
                break;
            case HIGHLIGHT_LEAF_NODE:
                Gdx.app.postRunnable(() -> Gdx.app.postRunnable(()
                        -> highlightNode(Arrays.asList(45, 57, 80, 71))));
                break;
        }
    }

    private void highlightNode(List numberList) {
        for (int i = 0; i < actorList.size(); i++) {
            TreeNodeActor actor = actorList.get(i);
            if (numberList.contains(actor.getNumber())) {
                actor.reset();
                ScaleToAction scaleLarge = Actions.scaleTo(1.2f, 1.2f, 0.5f);
                ScaleToAction scaleSmall = Actions.scaleTo(0.9f, 0.9f, 0.5f);
                ScaleToAction scaleNormal = Actions.scaleTo(1, 1, 0.5f);
                actor.addAction(Actions.repeat(100,
                        Actions.sequence(scaleLarge, scaleSmall, scaleNormal)));
            } else {
                actor.clearActions();
                actor.blur();
            }
        }
    }

    @Override
    protected void algorithm() {
        await();

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(HIGHLIGHT_ROOT_NODE, 9, -1));
        });

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(HIGHLIGHT_PARENT_NODE, 9, -1));
        });

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(HIGHLIGHT_LEAF_NODE, 9, -1));
        });
    }


}

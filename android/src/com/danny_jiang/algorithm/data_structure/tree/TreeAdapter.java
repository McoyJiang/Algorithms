package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;
import com.danny_jiang.algorithm.views.AlgorithmLine;

public class TreeAdapter extends AlgorithmAdapter {
    
    private static final int INSERT_ROOT_NODE = 0;
    private static final int INSERT_LEFT_NODE = 1;
    private static final int INSERT_RIGHT_NODE = 2;

    private int[] nodeData = new int[]{60, 10, 39, 45, 57, 60, 71};
    TreeNodeActor rootNode;
    TreeNodeActor tenNode;
    TreeNodeActor thirtyNineNode;
    TreeNodeActor fourtyFiveNode;
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
        tenNode = new TreeNodeActor(6);
        tenNode.setColor(Color.valueOf("#e4dd51"));
        tenNode.setPosition(rootNode.getX() - 200, rootNode.getY() - 200);

        // 39
        thirtyNineNode = new TreeNodeActor(39);
        thirtyNineNode.setColor(Color.valueOf("#dc143c"));
        thirtyNineNode.setPosition(rootNode.getX() * 2 - tenNode.getX(), rootNode.getY() - 200);

        // 45
        fourtyFiveNode = new TreeNodeActor(45);
        fourtyFiveNode.setColor(Color.valueOf("#d199e1"));
        fourtyFiveNode.setPosition(700, 800);
        // x + 50 x2 + 50 = (x + 50) * 2
        fourtyFiveNode.setPosition(tenNode.getX() - 100, tenNode.getY() - 200);

        // 57
        fiftySevenNode = new TreeNodeActor(57);
        fiftySevenNode.setColor(Color.valueOf("#6a4734"));
        fiftySevenNode.setPosition(700, 800);
        fiftySevenNode.setPosition(tenNode.getX() * 2 - fourtyFiveNode.getX(),
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
        stage.addActor(fourtyFiveNode);
        stage.addActor(fiftySevenNode);
        stage.addActor(eightyNineNode);
        stage.addActor(seventyOneNineNode);

        /**
         * construct the Tree
         */
        rootNode.setLeftChild(tenNode);
        rootNode.setRightChild(thirtyNineNode);

        tenNode.setLeftChild(fourtyFiveNode);
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
            case INSERT_ROOT_NODE:
                Gdx.app.postRunnable(() -> {
                    addRoot();
                });
                break;
            case INSERT_LEFT_NODE:
                Gdx.app.postRunnable(() -> {
                });
                break;
            case INSERT_RIGHT_NODE:
                Gdx.app.postRunnable(() -> {
                });
                break;
        }
    }

    private void addRoot() {
        ScaleToAction scaleLarge = Actions.scaleTo(1.5f, 1.5f, 0.5f);
        ScaleToAction scaleSmall = Actions.scaleTo(0.9f, 0.9f, 0.5f);
        ScaleToAction scaleNormal = Actions.scaleTo(1, 1, 0.3f);
        rootNode.addAction(Actions.sequence(scaleLarge, scaleSmall, scaleNormal, Actions.delay(0.3f),
                Actions.run(() -> rootNode.setColor(Color.BLACK))));
    }

    @Override
    protected void algorithm() {
        await();

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(INSERT_ROOT_NODE, 9, -1));
        });

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(INSERT_LEFT_NODE, 9, -1));
        });

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(INSERT_RIGHT_NODE, 9, -1));
        });
    }


}

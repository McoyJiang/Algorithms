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

    TreeNodeActor rootNode;

    @Override
    protected void inflateStage() {
        rootNode = new TreeNodeActor(10);
        rootNode.setLineColor(Color.RED);
        rootNode.setPosition(stage.getWidth() / 2,
                visualizerBg.getY() + visualizerBg.getHeight() / 2);

        stage.addActor(rootNode);

        TreeNodeActor sixNode = new TreeNodeActor(6);
        sixNode.setPosition(rootNode.getX() - 200, rootNode.getY() - 80);
        stage.addActor(sixNode);
        TreeNodeActor elevenNode = new TreeNodeActor(11);
        elevenNode.setPosition(700, 800);
        stage.addActor(elevenNode);

        rootNode.setLeftChild(sixNode);
        rootNode.setRightChild(elevenNode);
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
                    rootNode.addLeft("" + number);
                });
                break;
            case INSERT_RIGHT_NODE:
                Gdx.app.postRunnable(() -> {
                    rootNode.addRight("" + number);
                });
                break;
        }
    }

    private void addRoot() {
        ScaleToAction scaleLarge = Actions.scaleTo(1.5f, 1.5f, 0.5f);
        ScaleToAction scaleSmall = Actions.scaleTo(0.9f, 0.9f, 0.5f);
        ScaleToAction scaleNormal = Actions.scaleTo(1, 1, 0.3f);
        rootNode.addAction(Actions.sequence(scaleLarge, scaleSmall, scaleNormal, Actions.delay(0.3f),
                Actions.run(() -> rootNode.setLineColor(Color.BLACK))));
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

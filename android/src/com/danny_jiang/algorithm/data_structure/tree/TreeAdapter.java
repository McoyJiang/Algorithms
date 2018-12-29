package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;
import com.danny_jiang.algorithm.views.AlgorithmLine;

public class TreeAdapter extends AlgorithmAdapter {

    private static final int INSERT_LEFT_NODE = 1;
    private static final int INSERT_RIGHT_NODE = 2;

    TreeNodeActor treeNode;

    @Override
    protected void inflateStage() {
        treeNode = new TreeNodeActor(0, 0, 100, 100, 10);
        treeNode.setText("10");
        treeNode.setLineColor(Color.BLACK);
        treeNode.setPosition(stage.getWidth() / 2,
                visualizerBg.getY() + visualizerBg.getHeight() / 2);

        stage.addActor(treeNode);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void animation(Message msg) {
        int number = msg.arg1;
        switch (msg.what) {
            case INSERT_LEFT_NODE:
                Gdx.app.postRunnable(() -> {
                    treeNode.addLeft("" + number);
                });
                break;
            case INSERT_RIGHT_NODE:
                Gdx.app.postRunnable(() -> {
                    treeNode.addRight("" + number);
                });
                break;
        }
    }

    @Override
    protected void algorithm() {
        await();

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

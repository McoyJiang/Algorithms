package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class RBTreeAdapter extends AlgorithmAdapter {

    private static final int SHOW_CONDITION_TITLE = 1;
    private static final int SHOT_CONDITION = 2;

    private String[] rbConditions = new String[]{
            "data_structure/tree/rb_condition1.jpeg",
            "data_structure/tree/rb_condition2.jpeg",
            "data_structure/tree/rb_condition3.jpeg",
            "data_structure/tree/rb_condition4.jpeg"
    };
    private Image rbTreeTitle;
    private Image rb1;
    private Image rb2;
    private Image rb3;
    private TreeNodeActor rootNode;

    private Map<Integer, Image> conditionsMap = new Hashtable<>();
    private List<TreeNodeActor> actorList = new ArrayList<>();

    @Override
    protected void inflateStage() {
        rbTreeTitle = new Image(new TextureRegion(
                new Texture("data_structure/tree/rb_condition_title.jpeg")));
        rbTreeTitle.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * rbTreeTitle.getPrefHeight()
                        / rbTreeTitle.getPrefWidth());
        rbTreeTitle.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - rbTreeTitle.getHeight() - 20);
        stage.addActor(rbTreeTitle);
        rbTreeTitle.setVisible(false);

        for (int i = 0; i < rbConditions.length; i++) {
            Image image = new Image(new TextureRegion(new Texture(rbConditions[i])));
            image.setSize(visualizerBg.getWidth(),
                    visualizerBg.getWidth() * image.getPrefHeight()
                            / image.getPrefWidth());
            if (i == rbConditions.length - 1) {
                image.setPosition(visualizerBg.getX(),
                        rbTreeTitle.getY() - rbTreeTitle.getHeight() * (i + 2) - 20);
            } else {
                image.setPosition(visualizerBg.getX(),
                        rbTreeTitle.getY() - rbTreeTitle.getHeight() * (i + 1) - 20);
            }
            conditionsMap.put(i, image);
            stage.addActor(image);
            image.addAction(Actions.alpha(0));
        }

        rootNode = new TreeNodeActor(64);
        rootNode.setColor(Color.BLACK);
        rootNode.setRoot(true);
        rootNode.setPosition(stage.getWidth() / 2 - rootNode.getWidth() / 2,
                stage.getHeight() - rootNode.getHeight() * 1.5f);
        stage.addActor(rootNode);
        rootNode.setVisible(false);
        actorList.add(rootNode);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void animation(Message msg) {
        int index = msg.arg1;
        switch (msg.what) {
            case SHOW_CONDITION_TITLE:
                rbTreeTitle.setVisible(true);
                break;
            case SHOT_CONDITION:
                Gdx.app.postRunnable(() -> showCondition(index));
                break;
        }
    }

    private void showCondition(int index) {
        conditionsMap.get(index).addAction(Actions.alpha(1, 0.6f));
        if (index == 0) {
            rootNode.setVisible(true);
        }
    }

    @Override
    protected void algorithm() {
        await();

        await(() -> sDecodingThreadHandler.sendEmptyMessage(SHOW_CONDITION_TITLE));
        for (int i = 0; i < rbConditions.length; i++) {
            final int index = i;
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(SHOT_CONDITION, index, -1)));
        }
    }

}

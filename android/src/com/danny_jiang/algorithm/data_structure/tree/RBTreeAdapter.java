package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Image redBlackTreeDef;
    private Image rbTreeTitle;
    private TreeNodeActor rootNode;
    private TreeNodeActor nil;

    private Map<Integer, Image> conditionsMap = new Hashtable<>();
    private List<TreeNodeActor> actorList = new ArrayList<>();

    @Override
    protected void inflateStage() {
        redBlackTreeDef = new Image(new TextureRegion(
                new Texture("data_structure/tree/red_black_tree.jpeg")));
        redBlackTreeDef.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * redBlackTreeDef.getPrefHeight()
                        / redBlackTreeDef.getPrefWidth());
        redBlackTreeDef.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - redBlackTreeDef.getHeight() - 20);
        stage.addActor(redBlackTreeDef);
        redBlackTreeDef.addAction(Actions.sequence(
                Actions.alpha(0), Actions.alpha(1, 1)));

        rbTreeTitle = new Image(new TextureRegion(
                new Texture("data_structure/tree/rb_condition_title.jpeg")));
        rbTreeTitle.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * rbTreeTitle.getPrefHeight()
                        / rbTreeTitle.getPrefWidth());
        rbTreeTitle.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - rbTreeTitle.getHeight() - 20);
        stage.addActor(rbTreeTitle);
        rbTreeTitle.setVisible(false);

        Image image1 = new Image(new TextureRegion(new Texture(rbConditions[0])));
        image1.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * image1.getPrefHeight()
                        / image1.getPrefWidth());
        image1.setPosition(visualizerBg.getX(),
                rbTreeTitle.getY() - image1.getHeight() - 20);

        Image image2 = new Image(new TextureRegion(new Texture(rbConditions[1])));
        image2.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * image2.getPrefHeight()
                        / image2.getPrefWidth());
        image2.setPosition(visualizerBg.getX(),
                image1.getY() - image2.getHeight() - 20);

        Image image3 = new Image(new TextureRegion(new Texture(rbConditions[2])));
        image3.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * image3.getPrefHeight()
                        / image3.getPrefWidth());
        image3.setPosition(visualizerBg.getX(),
                image2.getY() - image3.getHeight() - 20);

        Image image4 = new Image(new TextureRegion(new Texture(rbConditions[3])));
        image4.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * image4.getPrefHeight()
                        / image4.getPrefWidth());
        image4.setPosition(visualizerBg.getX(),
                image3.getY() - image4.getHeight() - 20);

        conditionsMap.put(0, image1);
        conditionsMap.put(1, image2);
        conditionsMap.put(2, image3);
        conditionsMap.put(3, image4);
        stage.addActor(image1);
        stage.addActor(image2);
        stage.addActor(image3);
        stage.addActor(image4);
        image1.addAction(Actions.alpha(0));
        image2.addAction(Actions.alpha(0));
        image3.addAction(Actions.alpha(0));
        image4.addAction(Actions.alpha(0));

        initRBTree();
    }

    private void initRBTree() {
        rootNode = new TreeNodeActor(9);
        rootNode.setColor(Color.BLACK);
        rootNode.setRoot(true);
        rootNode.setPosition(stage.getWidth() / 2 - rootNode.getWidth() / 2 + 50,
                stage.getHeight() - rootNode.getHeight() * 2f);
        stage.addActor(rootNode);
        actorList.add(rootNode);

        TreeNodeActor sevenNode = rootNode.addChild(7);
        sevenNode.setColor(Color.RED);
        TreeNodeActor tenNode = rootNode.addChild(10);
        tenNode.setColor(Color.BLACK);

        TreeNodeActor fiveNode = sevenNode.addChild(5);
        fiveNode.setColor(Color.BLACK);
        TreeNodeActor eightNode = sevenNode.addChild(8);
        eightNode.setColor(Color.BLACK);

        TreeNodeActor fourNode = fiveNode.addChild(4);
        fourNode.setColor(Color.RED);
        TreeNodeActor sixNode = fiveNode.addChild(6);
        sixNode.setColor(Color.RED);

        actorList.add(sevenNode);
        actorList.add(tenNode);
        actorList.add(fiveNode);
        actorList.add(eightNode);
        actorList.add(fourNode);
        actorList.add(sixNode);
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
                actor.dimNode();
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void animation(Message msg) {
        int index = msg.arg1;
        switch (msg.what) {
            case SHOW_CONDITION_TITLE:
                redBlackTreeDef.setVisible(false);
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
            highlightNode(Arrays.asList(9));
        }
        if (index == 1) {
            nil = findTreeNodeByKey(10).setNilLeft(-1);
            actorList.add(nil);
            highlightNode(Arrays.asList(-1));
        }
        if (index == 2) {
            findTreeNodeByKey(10).emptyLeft();
            nil.remove();
            highlightNode(Arrays.asList(7, 5, 8));
        }
        if (index == 3) {
            clearAllNodesAnimation();
        }
    }

    private void clearAllNodesAnimation() {
        for (TreeNodeActor actor : actorList) {
            actor.reset();
            actor.clearActions();
        }
    }

    private TreeNodeActor findTreeNodeByKey(int key) {
        for (TreeNodeActor nodeActor : actorList) {
            if (nodeActor.getNumber() == key) return nodeActor;
        }
        return null;
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

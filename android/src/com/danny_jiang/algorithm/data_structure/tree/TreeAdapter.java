package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    TreeNodeActor fiftyNode;
    TreeNodeActor seventyNineNode;
    TreeNodeActor tenNode;
    TreeNodeActor fifteenNode;
    TreeNodeActor seventyOneNode;
    TreeNodeActor thirtyNode;
    TreeNodeActor nineNode;
    TreeNodeActor eightyEightNode;

    private Label stepDescription;

    @Override
    protected void inflateStage() {
        // root 64
        rootNode = new TreeNodeActor(64);
        rootNode.setColor(Color.valueOf("#5e6fec"));
        rootNode.setPosition(stage.getWidth() / 2 - rootNode.getWidth() / 2,
                stage.getHeight() - rootNode.getHeight() * 1.5f);

        // 50
        fiftyNode = new TreeNodeActor(50);
        fiftyNode.setColor(Color.valueOf("#6aaf6a"));
        fiftyNode.setPosition(rootNode.getX() - 200, rootNode.getY() - 200);

        // 79
        seventyNineNode = new TreeNodeActor(79);
        seventyNineNode.setColor(Color.valueOf("#dc143c"));
        seventyNineNode.setPosition(rootNode.getX() * 2 - fiftyNode.getX(), rootNode.getY() - 200);

        // 10
        tenNode = new TreeNodeActor(10);
        tenNode.setColor(Color.valueOf("#1ca1f2"));
        tenNode.setPosition(700, 800);
        tenNode.setPosition(fiftyNode.getX() - 100, fiftyNode.getY() - 200);

        // 15
        fifteenNode = new TreeNodeActor(15);
        fifteenNode.setColor(Color.valueOf("#c3c0d2"));
        fifteenNode.setPosition(700, 800);
        fifteenNode.setPosition(fiftyNode.getX() * 2 - tenNode.getX(),
                fiftyNode.getY() - 200);

        // 71
        seventyOneNode = new TreeNodeActor(71);
        seventyOneNode.setColor(Color.valueOf("#850838"));
        seventyOneNode.setPosition(seventyNineNode.getX() - 100, seventyNineNode.getY() - 200);

        // 88
        eightyEightNode = new TreeNodeActor(88);
        eightyEightNode.setColor(Color.valueOf("#ecde9b"));
        eightyEightNode.setPosition(seventyNineNode.getX() * 2 - seventyOneNode.getX(),
                seventyNineNode.getY() - 200);

        // 9
        nineNode = new TreeNodeActor(9);
        nineNode.setColor(Color.valueOf("#d199e1"));
        nineNode.setPosition(tenNode.getX() - 100, tenNode.getY() - 200);

        // 30
        thirtyNode = new TreeNodeActor(30);
        thirtyNode.setColor(Color.valueOf("#6a4734"));
        thirtyNode.setPosition(700, 800);
        thirtyNode.setPosition(tenNode.getX() * 2 - nineNode.getX(),
                tenNode.getY() - 200);

        stage.addActor(rootNode);
        stage.addActor(fiftyNode);
        stage.addActor(seventyNineNode);
        stage.addActor(tenNode);
        stage.addActor(fifteenNode);
        stage.addActor(seventyOneNode);
        stage.addActor(eightyEightNode);
        stage.addActor(nineNode);
        stage.addActor(thirtyNode);

        actorList.add(rootNode);
        actorList.add(fiftyNode);
        actorList.add(seventyNineNode);
        actorList.add(tenNode);
        actorList.add(fifteenNode);
        actorList.add(seventyOneNode);
        actorList.add(eightyEightNode);
        actorList.add(nineNode);
        actorList.add(thirtyNode);

        /**
         * construct the Tree
         */
        rootNode.setLeftChild(fiftyNode);
        rootNode.setRightChild(seventyNineNode);

        fiftyNode.setLeftChild(tenNode);
        fiftyNode.setRightChild(fifteenNode);

        seventyNineNode.setLeftChild(seventyOneNode);
        seventyNineNode.setRightChild(eightyEightNode);

        tenNode.setLeftChild(nineNode);
        tenNode.setRightChild(thirtyNode);

        BitmapFont desFont = new BitmapFont(Gdx.files.internal(
                "data_structure/tree/binary_search_tree.fnt"));
        Label.LabelStyle desStyle = new Label.LabelStyle();
        desStyle.font = desFont;
        desStyle.fontColor = Color.valueOf("#4A4A4A");
        stepDescription = new Label("", desStyle);
        stepDescription.setText("如上图中所示:\n\n" +
                "二叉搜索树是以二叉树来组织的.\n" +
                "树的每个结点中包含一个value值,\n" +
                "以及left和right两个指针,\n" +
                "分别指向左子结点和右子结点.");
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(30, stage.getHeight() / 3 - 100);
        stage.addActor(stepDescription);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void animation(Message msg) {
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

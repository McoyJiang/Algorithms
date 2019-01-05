package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeAdapter extends AlgorithmAdapter {
    
    private static final int BINARY_SEARCH_TREE_INTRO = 0;
    private static final int ADD_ROOT_NODE = 1;
    private static final int ADD_SUB_NODE = 2;
    private static final int CLEAR_NODE_ANIMATION = 6;

    private List<TreeNodeActor> actorList = new ArrayList<>();

    TreeNodeActor rootNode;

    private Image bstIntroImage;
    private Label stepDescription;

    @Override
    protected void inflateStage() {
        bstIntroImage = new Image(new TextureRegion(
                new Texture("data_structure/tree/bst_demo.jpeg")));
        bstIntroImage.setSize(visualizerBg.getWidth(), visualizerBg.getHeight());
        bstIntroImage.setPosition(visualizerBg.getX(), visualizerBg.getY());
        stage.addActor(bstIntroImage);

        BitmapFont desFont = new BitmapFont(Gdx.files.internal(
                "data_structure/tree/binary_search_tree.fnt"));
        Label.LabelStyle desStyle = new Label.LabelStyle();
        desStyle.font = desFont;
        desStyle.fontColor = Color.valueOf("#4A4A4A");
        stepDescription = new Label("", desStyle);
        stepDescription.setText("二叉搜索树是以二叉树来组织的.\n" +
                "树的每个结点中包含一个value值,\n" +
                "以及left和right两个指针,它们\n" +
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
        int number = msg.arg1;
        switch (msg.what) {
            case BINARY_SEARCH_TREE_INTRO:
                stepDescription.setText("二叉搜索树的特点\n" +
                        "父结点大于左子树任何结点的值\n" +
                        "父结点小于右子树任何结点的值");
                break;
            case ADD_ROOT_NODE:
                Gdx.app.postRunnable(() -> addRootNode(number));
                break;
            case ADD_SUB_NODE:
                Gdx.app.postRunnable(() -> addSubNode(number));
                break;
            case CLEAR_NODE_ANIMATION:
                Gdx.app.postRunnable(() -> clearAllNodesAnimation());
                break;
        }
    }

    private void addSubNode(int number) {
        TreeNodeActor parent = findTreeNodeByKey(currentParentNode.key);
        actorList.add(parent.addChild(number));
    }

    private void addRootNode(int number) {
        // root 64
        rootNode = new TreeNodeActor(number);
        rootNode.setRoot(true);
        rootNode.setPosition(stage.getWidth() / 2 - rootNode.getWidth() / 2,
                stage.getHeight() - rootNode.getHeight() * 1.5f);
        stage.addActor(rootNode);
        actorList.add(rootNode);
    }

    private TreeNodeActor findTreeNodeByKey(int key) {
        for (TreeNodeActor nodeActor : actorList) {
            if (nodeActor.getNumber() == key) return nodeActor;
        }
        return null;
    }

    private void clearAllNodesAnimation() {
        for (TreeNodeActor actor : actorList) {
            actor.clearAnimation();
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
                actor.dimNode();
            }
        }
    }

    private int[] arr = new int[]{64, 50, 79, 71, 10, 60, 9, 88, 30};

    @Override
    protected void algorithm() {
        await();

        await(() -> sDecodingThreadHandler.sendEmptyMessage(
                BINARY_SEARCH_TREE_INTRO));

        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                insertRoot(arr[i]);
            } else {
                insert(arr[i]);
            }
        }
    }

    private void insertRoot(int key) {
        bstIntroImage.setVisible(false);
        stepDescription.setText("构建二叉搜索树:\n" +
                "[ " + Arrays.toString(arr) + " ]");
        root = new Node(key);
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(
                        ADD_ROOT_NODE, key, -1)));
    }

    Node root;
    public void insert(int key) {
        root = insertRec(root, key);
        Log.e("DDD", "inserted " + root.key);
    }

    private Node currentParentNode = null;
    /* A recursive function to insert a new key in BST */
    private Node insertRec(Node root, int key) {

        /* If the tree is empty, return a new node */
        if (root == null) {
            root = new Node(key);
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(
                            ADD_SUB_NODE, key, -1)));
            return root;
        }

        currentParentNode = root;
        /* Otherwise, recur down the tree */
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        /* return the (unchanged) node pointer */
        return root;
    }

    public static class Node {
        public int key;
        public Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }
}

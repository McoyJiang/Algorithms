package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TreeAdapter extends AlgorithmAdapter {
    
    private static final int BINARY_SEARCH_TREE_INTRO = 0;
    private static final int INSERT_OPERATION = 1;
    private static final int ADD_ROOT_NODE = 2;
    private static final int ADD_SUB_NODE = 3;
    private static final int CLEAR_NODE_ANIMATION = 4;
    private static final int FIND_LEFT_OPERATION = 5;
    private static final int FIND_RIGHT_OPERATION = 6;
    private static final int NOT_FOUND_OPERATION = 7;
    private static final int FOUND_OPERATION = 8;
    private static final int BINARY_SEARCH_TREE_PROPERTY = 9;

    private String[] insertionImageList = new String[]{
            "data_structure/tree/tree_insertion_0.jpeg",
            "data_structure/tree/tree_insertion_1.jpeg",
            "data_structure/tree/tree_insertion_2.jpeg",
            "data_structure/tree/tree_insertion_3.jpeg",
            "data_structure/tree/tree_insertion_4.jpeg",
            "data_structure/tree/tree_insertion_5.jpeg",
            "data_structure/tree/tree_insertion_6.jpeg",
            "data_structure/tree/tree_insertion_7.jpeg",
            "data_structure/tree/tree_insertion_8.jpeg",
            "data_structure/tree/tree_insertion_9.jpeg",
    };
    Map<Integer, Image> imageMap = new Hashtable<>();

    private List<TreeNodeActor> actorList = new ArrayList<>();

    TreeNodeActor rootNode;

    private Image bstIntroImage;
    private Image treeIntroText;
    private Image treePropertyText;
    private Label stepDescription;

    @Override
    protected void inflateStage() {
        bstIntroImage = new Image(new TextureRegion(
                new Texture("data_structure/tree/bst_demo.jpeg")));
        bstIntroImage.setSize(visualizerBg.getWidth(), visualizerBg.getHeight());
        bstIntroImage.setPosition(visualizerBg.getX(), visualizerBg.getY());
        stage.addActor(bstIntroImage);

        treeIntroText = new Image(new TextureRegion(
                new Texture("data_structure/tree/tree_intro.jpeg")
        ));
        treeIntroText.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * treeIntroText.getPrefHeight()
                        / treeIntroText.getPrefWidth());
        treeIntroText.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - treeIntroText.getHeight() - 20);
        stage.addActor(treeIntroText);

        treePropertyText = new Image(new TextureRegion(
                new Texture("data_structure/tree/tree_property.jpeg")
        ));
        treePropertyText.setSize(visualizerBg.getWidth(),
                visualizerBg.getWidth() * treePropertyText.getPrefHeight()
                        / treePropertyText.getPrefWidth());
        treePropertyText.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - treePropertyText.getHeight() - 20);
        stage.addActor(treePropertyText);
        treePropertyText.setVisible(false);

        for (int i = 0; i < insertionImageList.length; i++) {
            Image image = new Image(new TextureRegion(
                    new Texture(insertionImageList[i])));
            image.setSize(visualizerBg.getWidth(),
                    visualizerBg.getWidth() * image.getPrefHeight()
                            / treePropertyText.getPrefWidth());
            image.setPosition(visualizerBg.getX(),
                    visualizerBg.getY() - image.getHeight() - 20);
            imageMap.put(i, image);
            stage.addActor(image);
            image.setVisible(false);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void animation(Message msg) {
        int number = msg.arg1;
        int insertionIndex = msg.arg2;
        switch (msg.what) {
            case BINARY_SEARCH_TREE_INTRO:
                treeIntroText.remove();
                treePropertyText.setVisible(true);
                break;
            case INSERT_OPERATION:
                treePropertyText.remove();
                bstIntroImage.setVisible(false);
                imageMap.get(0).setVisible(true);
                break;
            case FIND_LEFT_OPERATION:
                Gdx.app.postRunnable(() -> {
                    findTreeNodeByKey(number).animatingLeftLine();
                    signal();
                });
                break;
            case FIND_RIGHT_OPERATION:
                Gdx.app.postRunnable(() -> {
                    findTreeNodeByKey(number).animatingRightLine();
                    signal();
                });
                break;
            case NOT_FOUND_OPERATION:
                break;
            case FOUND_OPERATION:
                break;
            case ADD_ROOT_NODE:
                Gdx.app.postRunnable(() -> {
                    addRootNode(number);
                    imageMap.get(insertionIndex).remove();
                    imageMap.get(insertionIndex + 1).setVisible(true);
                });
                break;
            case ADD_SUB_NODE:
                Gdx.app.postRunnable(() -> {
                    addSubNode(number);
                    imageMap.get(insertionIndex).remove();
                    imageMap.get(insertionIndex + 1).setVisible(true);
                });
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

        await(() -> sDecodingThreadHandler.sendEmptyMessage(
                INSERT_OPERATION));
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                insertRoot(arr[i], i);
            } else {
                insert(arr[i], i);
            }
        }

        search(root, 30);
    }

    public void search(Node root, int key) {
        // Base Cases: root is null or key is present at root
        if (root == null) {
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(
                            NOT_FOUND_OPERATION, 30, -1)));
        }

        if (root.key == key) {
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(
                            FOUND_OPERATION, 30, -1)));
        }

        // val is greater than root's key
        if (root.key > key) {
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(
                            FIND_LEFT_OPERATION, root.key, -1)));
            search(root.left, key);
        } else {
            search(root.right, key);
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(
                            FIND_RIGHT_OPERATION, root.key, -1)));
        }
    }

    private void insertRoot(int key, int insertionIndex) {
        root = new Node(key);
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(
                        ADD_ROOT_NODE, key, insertionIndex)));
    }

    Node root;
    public void insert(int key, int insertionIndex) {
        root = insertRec(root, key, insertionIndex);
    }

    private Node currentParentNode = null;
    /* A recursive function to insert a new key in BST */
    private Node insertRec(Node root, int key, int insertionIndex) {

        /* If the tree is empty, return a new node */
        if (root == null) {
            root = new Node(key);
            await(() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(
                            ADD_SUB_NODE, key, insertionIndex)));
            return root;
        }

        currentParentNode = root;
        /* Otherwise, recur down the tree */
        if (key < root.key)
            root.left = insertRec(root.left, key, insertionIndex);
        else if (key > root.key)
            root.right = insertRec(root.right, key, insertionIndex);

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

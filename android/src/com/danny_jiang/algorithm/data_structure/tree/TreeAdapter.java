package com.danny_jiang.algorithm.data_structure.tree;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.tree.data.BinarySearchTree;
import com.danny_jiang.algorithm.data_structure.tree.data.TreeNodeActor;
import com.danny_jiang.algorithm.views.AlgorithmRect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeAdapter extends AlgorithmAdapter {
    
    private static final int BINARY_SEARCH_TREE_INTRO = 0;
    private static final int ADD_ROOT_NODE = 1;
    private static final int ADD_SUB_NODE = 2;
    private static final int HIGHLIGHT_ROOT_NODE = 3;
    private static final int CLEAR_NODE_ANIMATION = 6;
    private static final int FIND_THIRTY_NODE = 7;

    private List<TreeNodeActor> actorList = new ArrayList<>();

    TreeNodeActor rootNode;
    TreeNodeActor fiftyNode;
    TreeNodeActor seventyNineNode;
    TreeNodeActor tenNode;
    TreeNodeActor sixtyNode;
    TreeNodeActor seventyOneNode;
    TreeNodeActor thirtyNode;
    TreeNodeActor nineNode;
    TreeNodeActor eightyEightNode;

    private Label stepDescription;

    @Override
    protected void inflateStage() {
        // 50
//        fiftyNode = new TreeNodeActor(50);
//        fiftyNode.setPosition(rootNode.getX() - 200, rootNode.getY() - 200);

        // 79
//        seventyNineNode = new TreeNodeActor(79);
//        seventyNineNode.setPosition(rootNode.getX() * 2 - fiftyNode.getX(), rootNode.getY() - 200);

        // 10
//        tenNode = new TreeNodeActor(10);
//        tenNode.setPosition(fiftyNode.getX() - 100, fiftyNode.getY() - 200);

        // 60
//        sixtyNode = new TreeNodeActor(60);
//        sixtyNode.setPosition(fiftyNode.getX() * 2 - tenNode.getX(),
//                fiftyNode.getY() - 200);

        // 71
//        seventyOneNode = new TreeNodeActor(71);
//        seventyOneNode.setPosition(seventyNineNode.getX() - 100, seventyNineNode.getY() - 200);

        // 88
//        eightyEightNode = new TreeNodeActor(88);
//        eightyEightNode.setPosition(seventyNineNode.getX() * 2 - seventyOneNode.getX(),
//                seventyNineNode.getY() - 200);

        // 9
//        nineNode = new TreeNodeActor(9);
//        nineNode.setPosition(tenNode.getX() - 100, tenNode.getY() - 200);

        // 30
//        thirtyNode = new TreeNodeActor(30);
//        thirtyNode.setPosition(tenNode.getX() * 2 - nineNode.getX(),
//                tenNode.getY() - 200);
//
//        stage.addActor(rootNode);
//        stage.addActor(fiftyNode);
//        stage.addActor(seventyNineNode);
//        stage.addActor(tenNode);
//        stage.addActor(sixtyNode);
//        stage.addActor(seventyOneNode);
//        stage.addActor(eightyEightNode);
//        stage.addActor(nineNode);
//        stage.addActor(thirtyNode);
//
//        actorList.add(rootNode);
//        actorList.add(fiftyNode);
//        actorList.add(seventyNineNode);
//        actorList.add(tenNode);
//        actorList.add(sixtyNode);
//        actorList.add(seventyOneNode);
//        actorList.add(eightyEightNode);
//        actorList.add(nineNode);
//        actorList.add(thirtyNode);

        /**
         * construct the Tree
         */
//        rootNode.setLeftChild(fiftyNode);
//        rootNode.setRightChild(seventyNineNode);
//
//        fiftyNode.setLeftChild(tenNode);
//        fiftyNode.setRightChild(sixtyNode);
//
//        seventyNineNode.setLeftChild(seventyOneNode);
//        seventyNineNode.setRightChild(eightyEightNode);
//
//        tenNode.setLeftChild(nineNode);
//        tenNode.setRightChild(thirtyNode);

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
            case HIGHLIGHT_ROOT_NODE:
                Gdx.app.postRunnable(() -> {
                    rootNode.animatingLeftLine();
                });
                break;
            case FIND_THIRTY_NODE:
                Gdx.app.postRunnable(() -> findThirtyNode());
                break;
            case CLEAR_NODE_ANIMATION:
                Gdx.app.postRunnable(() -> clearAllNodesAnimation());
                break;
        }
    }

    private void addSubNode(int number) {
        addChild(currentParentNode.key, number);
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

    private void addChild(int parentKey, int childKey) {
        TreeNodeActor parent = findTreeNodeByKey(parentKey);
        actorList.add(parent.addChild(childKey));
    }

    private TreeNodeActor findTreeNodeByKey(int key) {
        for (TreeNodeActor nodeActor : actorList) {
            if (nodeActor.getNumber() == key) return nodeActor;
        }
        return null;
    }

    private void addChild(TreeNodeActor parent, TreeNodeActor leftChild) {
        parent.addChild(leftChild);
    }

    private void findThirtyNode() {
        RunnableAction run1 = Actions.run(() -> {
            rootNode.animatingLeftLine();
            stepDescription.setText("查找结点30:\n" +
                    "64 => ");
        });

        RunnableAction run2 = Actions.run(() -> {
            fiftyNode.animatingLeftLine();
            stepDescription.setText("查找结点30:\n" +
                    "64 => 50 => ");
        });

        RunnableAction run3 = Actions.run(() -> {
            tenNode.animatingRightLine();
            stepDescription.setText("查找结点30:\n" +
                    "64 => 50 => 10 =>");
        });

        RunnableAction run4 = Actions.run(() -> thirtyNode.setColor(Color.valueOf("f96161")));

        ScaleToAction scaleLarge = Actions.scaleTo(1.2f, 1.2f, 0.3f);
        ScaleToAction scaleSmall = Actions.scaleTo(0.9f, 0.9f, 0.3f);
        ScaleToAction scaleNormal = Actions.scaleTo(1, 1, 0.3f);
        RunnableAction foundAction = Actions.run(() -> stepDescription.setText("找到结点 30:\n"
                + "64 => 50 => 10 => 30"));
        SequenceAction scaleAnimation = Actions.sequence(foundAction, scaleLarge, scaleSmall, scaleNormal);

        SequenceAction sequence = Actions.sequence(run1, Actions.delay(0.8f),
                run2, Actions.delay(0.8f), run3, Actions.delay(0.8f),
                run4, scaleAnimation);
        thirtyNode.addAction(sequence);
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
                actor.blur();
            }
        }
    }

    private BinarySearchTree binarySearchTree = new BinarySearchTree();
    private int[] arr = new int[]{64, 50, 79, 71, 10, 60, 9, 88, 30};

    @Override
    protected void algorithm() {
        await();

        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                insertRoot(arr[i]);
            } else {
                insert(arr[i]);
            }
        }
    }

    private void insertRoot(int key) {
        root = new BinarySearchTree.Node(key);
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(
                        ADD_ROOT_NODE, key, -1)));
    }

    BinarySearchTree.Node root;
    public void insert(int key) {
        root = insertRec(root, key);
        Log.e("DDD", "inserted " + root.key);
    }

    private BinarySearchTree.Node currentParentNode = null;
    /* A recursive function to insert a new key in BST */
    private BinarySearchTree.Node insertRec(BinarySearchTree.Node root, int key) {

        /* If the tree is empty, return a new node */
        if (root == null) {
            root = new BinarySearchTree.Node(key);
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

}

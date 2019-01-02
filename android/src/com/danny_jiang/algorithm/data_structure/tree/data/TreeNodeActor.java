package com.danny_jiang.algorithm.data_structure.tree.data;

import android.text.TextUtils;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TreeNodeActor extends Actor {

    private char A = 'A';
    private BitmapFont bitmapFont;
    private String text;

    private float lineWidth = 10;

    private ShapeRenderer sr;
    private ShapeRenderer lineShader;

    private TreeNodeActor parentNode;
    private TreeNodeActor leftChild;
    private TreeNodeActor rightChild;

    public TreeNodeActor() {
        this(0);
    }
    public TreeNodeActor(int number) {
        sr = new ShapeRenderer();
        sr.setColor(Color.RED);
        lineShader = new ShapeRenderer();
        lineShader.setColor(Color.ORANGE);

        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("font/default.fnt"),
                Gdx.files.internal("font/default.png"), false);
        bitmapFont.setColor(Color.WHITE);

        text = String.valueOf(number);
        setSize(100, 100);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    public void addLeft(String number) {
        TreeNodeActor treeNodeActor = new TreeNodeActor();
        treeNodeActor.setLineColor(Color.BLACK);
        treeNodeActor.setText(number);
        treeNodeActor.setPosition(getX() - getWidth(),
                getY() - getHeight());
        getStage().addActor(treeNodeActor);
    }

    public void addRight(String number) {
        TreeNodeActor treeNodeActor = new TreeNodeActor();
        treeNodeActor.setLineColor(Color.BLACK);
        treeNodeActor.setText(number);
        treeNodeActor.setPosition(getX() + getWidth() / 2 + 50,
                getY() - getHeight());
        getStage().addActor(treeNodeActor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (sr != null) {
            batch.end();

            sr.setProjectionMatrix(batch.getProjectionMatrix());
            sr.setTransformMatrix(batch.getTransformMatrix());
            sr.translate(getX(), getY(), 0);
            lineShader.setProjectionMatrix(batch.getProjectionMatrix());
            lineShader.setTransformMatrix(batch.getTransformMatrix());
            lineShader.translate(getX(), getY(), 0);

            lineShader.begin(ShapeRenderer.ShapeType.Filled);
            if (leftChild != null) {
                Log.e("CCC", "left != null");
                lineShader.rectLine(getOriginX(), getOriginY(), leftChild.getX() + getOriginX() - getX(),
                        leftChild.getY() + getOriginY() - getY(), lineWidth);
            }
            if (rightChild != null) {
                lineShader.rectLine(getOriginX(), getOriginY(), rightChild.getX() + getOriginX() - getX(),
                        rightChild.getY() + getOriginY() - getY(), lineWidth);
            }
            lineShader.end();

            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.circle(getOriginX(), getOriginY(), getWidth() / 2 * getScaleX());
            sr.end();

            Gdx.gl.glLineWidth(1);
            batch.begin();

            if (!TextUtils.isEmpty(text)) {
                int textWidth = bitmapFont.getData().getGlyph(A).width * text.length();
                int textHeight = bitmapFont.getData().getGlyph(A).height;
                bitmapFont.draw(batch, text,
                        getX() + getWidth() / 2 - textWidth / 2 - 3,
                        getY() + getHeight() / 2 + textHeight / 2 + 3);
            }
        }

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setLineColor(Color lineColor) {
        if (sr != null) {
            sr.setColor(lineColor);
        }
    }

    public void setBranchColor(Color lineColor) {
        if (lineShader != null) {
            lineShader.setColor(lineColor);
        }
    }

    public TreeNodeActor getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNodeActor leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNodeActor getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNodeActor rightChild) {
        this.rightChild = rightChild;
    }

    public TreeNodeActor getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNodeActor parent) {
        this.parentNode = parent;
    }
}

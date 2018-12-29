package com.danny_jiang.algorithm.data_structure.tree.data;

import android.text.TextUtils;

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

    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float lineWidth;

    private ShapeRenderer sr;
    private ShapeRenderer lineShader;

    public TreeNodeActor() {
        this(0, 0, 0, 0, 10);
    }

    public TreeNodeActor(float x1, float y1, float x2, float y2, float lineWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.lineWidth = lineWidth;
        sr = new ShapeRenderer();
        lineShader = new ShapeRenderer();
        lineShader.setColor(Color.ORANGE);

        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("font/default.fnt"),
                Gdx.files.internal("font/default.png"), false);
        bitmapFont.setColor(Color.WHITE);

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
            lineShader.rectLine(getOriginX(), getOriginY(), -100, -100, lineWidth);
            lineShader.rectLine(getOriginX(), getOriginY(), getWidth() + 100, -100, lineWidth);
            lineShader.end();

            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.circle(getOriginX(), getOriginY(), getWidth() / 2);
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

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
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
}

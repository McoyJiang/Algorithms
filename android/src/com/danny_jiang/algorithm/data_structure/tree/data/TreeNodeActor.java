package com.danny_jiang.algorithm.data_structure.tree.data;

import android.text.TextUtils;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import javax.microedition.khronos.opengles.GL10;

public class TreeNodeActor extends Actor {

    private char A = 'A';
    private BitmapFont bitmapFont;
    private int number;
    private String text;

    private float lineWidth = 10;

    private boolean shouldAnimation = false;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private Bezier<Vector2> bezier;
    float t;
    float speed = 0.8f;
    private Vector2 tmpV;

    private ShapeRenderer sr;
    private ShapeRenderer lineShader;
    private ShapeRenderer animationShader;

    private TreeNodeActor parentNode;
    private TreeNodeActor leftChild;
    private TreeNodeActor rightChild;
    private Color circleColor;
    private Color lineColor = Color.valueOf("#cfcfca");

    public TreeNodeActor() {
        this(0);
    }
    public TreeNodeActor(int number) {
        sr = new ShapeRenderer();
        sr.setColor(Color.RED);
        lineShader = new ShapeRenderer();
        lineShader.setColor(lineColor);
        animationShader = new ShapeRenderer();
        animationShader.setColor(Color.valueOf("e1e9b7"));

        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("font/default.fnt"),
                Gdx.files.internal("font/default.png"), false);
        bitmapFont.setColor(Color.WHITE);

        this.number = number;
        text = String.valueOf(number);
        setSize(150, 150);
        setOrigin(getWidth() / 2, getHeight() / 2);

        x1 = x2 = getOriginX();
        y1 = y2 = getOriginY();
        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
        tmpV= new Vector2(x1, y1);
    }

    private boolean blur = false;
    public void blur() {
        blur = true;
        circleColor.a = 0.5f;
    }

    public void reset() {
        blur = false;
    }

    public void animatingLeftLine() {
        x1 = getOriginX();
        y1 = getOriginY();
        x2 = leftChild.getX() + getOriginX() - getX();
        y2 = leftChild.getY() + getOriginY() - getY();
        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
        tmpV= new Vector2(x1, y1);
        shouldAnimation = true;
    }

    public void animatingRightLine() {
        shouldAnimation = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (sr != null) {
            batch.end();
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            sr.setProjectionMatrix(batch.getProjectionMatrix());
            sr.setTransformMatrix(batch.getTransformMatrix());
            sr.translate(getX(), getY(), 0);
            lineShader.setProjectionMatrix(batch.getProjectionMatrix());
            lineShader.setTransformMatrix(batch.getTransformMatrix());
            lineShader.translate(getX(), getY(), 0);
            animationShader.setProjectionMatrix(batch.getProjectionMatrix());
            animationShader.setTransformMatrix(batch.getTransformMatrix());
            animationShader.translate(getX(), getY(), 0);

            lineShader.begin(ShapeRenderer.ShapeType.Filled);
            if (blur)
                lineShader.setColor(lineColor.r, lineColor.g, lineColor.b, 0.15f);
            else
                lineShader.setColor(lineColor.r, lineColor.g, lineColor.b, 1f);
            if (leftChild != null) {
                lineShader.rectLine(getOriginX(), getOriginY(), leftChild.getX() + getOriginX() - getX(),
                        leftChild.getY() + getOriginY() - getY(), lineWidth);
            }
            if (rightChild != null) {
                lineShader.rectLine(getOriginX(), getOriginY(), rightChild.getX() + getOriginX() - getX(),
                        rightChild.getY() + getOriginY() - getY(), lineWidth);
            }
            lineShader.end();

            animationShader.begin(ShapeRenderer.ShapeType.Filled);
            if (shouldAnimation && y1 > y2 && tmpV.y > y2) {
                t += speed * Gdx.graphics.getDeltaTime();
                bezier.valueAt(tmpV, t);
            }
            animationShader.rectLine(getOriginX(), getOriginY(), tmpV.x, tmpV.y, 20);
            animationShader.end();

            if (blur)
                sr.setColor(circleColor.r, circleColor.g, circleColor.b, 0.15f);
            else
                sr.setColor(circleColor.r, circleColor.g, circleColor.b, 1f);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.circle(getOriginX(), getOriginY(), getWidth() / 2 * getScaleX());
            sr.end();

            Gdx.gl.glLineWidth(1);
            batch.begin();

            Gdx.gl.glDisable(GL10.GL_BLEND);

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

    public void setColor(Color color) {
        if (sr != null) {
            sr.setColor(color);
            circleColor = color;
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

    public int getNumber() {
        return number;
    }
}

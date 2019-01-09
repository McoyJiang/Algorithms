package com.danny_jiang.algorithm.data_structure.tree.data;

import android.text.TextUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import javax.microedition.khronos.opengles.GL10;

public class TreeNodeActor extends Actor {

    private char A = 'A';
    private BitmapFont bitmapFont;
    private int number;
    private String text;

    private float lineWidth = 10;

    private boolean isRoot = false;
    private boolean shouldAnimation = false;
    private float srcX;
    private float srcY;
    private float leftDstX;
    private float leftDstY;
    private float rightDstX;
    private float rightDstY;
    private Bezier<Vector2> leftBezier;
    private Bezier<Vector2> rightBezier;
    float tLeft;
    float tRight;
    float speed = 1.5f;
    private Vector2 leftTmpV;
    private Vector2 rightTmpV;

    private ShapeRenderer sr;
    private ShapeRenderer lineShader;
    private ShapeRenderer animationShader;
    private boolean dim = false;

    private TreeNodeActor parentNode;
    private TreeNodeActor leftChild;
    private TreeNodeActor rightChild;
    private Color circleColor = Color.valueOf("3984b0");
    private Color lineColor = Color.valueOf("#cfcfca");

    public TreeNodeActor() {
        this(0, 120, 120);
    }

    public TreeNodeActor(int number) {
        this(number, 120, 120);
    }

    public TreeNodeActor(int width, int height) {
        this(0, width, height);
    }

    public TreeNodeActor(int number, int width, int height) {
        sr = new ShapeRenderer();
        sr.setColor(circleColor);
        lineShader = new ShapeRenderer();
        lineShader.setColor(lineColor);
        animationShader = new ShapeRenderer();
        animationShader.setColor(Color.RED);

        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("font/default.fnt"),
                Gdx.files.internal("font/default.png"), false);
        bitmapFont.setColor(Color.WHITE);

        this.number = number;
        text = String.valueOf(number);
        setSize(width, height);
        setOrigin(getWidth() / 2, getHeight() / 2);

        clearAnimation();
    }
    public void dimNode() {
        dim = true;
    }

    public void reset() {
        dim = false;
    }

    public void animatingLeftLine() {
        leftDstX = leftChild.getX() + getOriginX() - getX();
        leftDstY = leftChild.getY() + getOriginY() - getY();
        leftBezier = new Bezier<>(new Vector2(srcX, srcY), new Vector2(leftDstX, leftDstY));
        leftTmpV = new Vector2(srcX, srcY);
        shouldAnimation = true;
    }

    public void animatingRightLine() {
        rightDstX = rightChild.getX() + getOriginX() - getX();
        rightDstY = rightChild.getY() + getOriginY() - getY();
        rightBezier = new Bezier<>(new Vector2(srcX, srcY), new Vector2(rightDstX, rightDstY));
        rightTmpV = new Vector2(srcX, srcY);
        shouldAnimation = true;
    }

    public void clearAnimation() {
        setColor(Color.valueOf("3984b0"));
        shouldAnimation = false;

        srcX = leftDstX = rightDstX = getOriginX();
        srcY = leftDstY = rightDstY = getOriginY();
        leftBezier = new Bezier<>(new Vector2(srcX, srcY), new Vector2(leftDstX, leftDstY));
        rightBezier = new Bezier<>(new Vector2(srcX, srcY), new Vector2(rightDstX, rightDstY));
        leftTmpV = rightTmpV = new Vector2(srcX, srcY);
        tLeft = tRight = 0;
    }

    public TreeNodeActor addChild(int number) {
        return addChild(number, false);
    }

    public TreeNodeActor addChild(int number, boolean shouldAnimation) {
        if (getNumber() > number)
            return setLeftChild(number, shouldAnimation);
        else
            return setRightChild(number, shouldAnimation);
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
            if (dim)
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
            if (shouldAnimation && srcY > leftDstY && leftTmpV.y > leftDstY) {
                tLeft += speed * Gdx.graphics.getDeltaTime();
                leftBezier.valueAt(leftTmpV, tLeft);
            }

            if (shouldAnimation && srcX < rightDstX && rightTmpV.x < rightDstX) {
                tRight += speed * Gdx.graphics.getDeltaTime();
                rightBezier.valueAt(rightTmpV, tRight);
            }
            animationShader.rectLine(getOriginX(), getOriginY(), leftTmpV.x, leftTmpV.y, 20);
            animationShader.rectLine(getOriginX(), getOriginY(), rightTmpV.x, rightTmpV.y, 20);
            animationShader.end();

            if (dim)
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

    public TreeNodeActor setLeftChild(int number, boolean shouldAnimation) {
        this.leftChild = new TreeNodeActor(number);
        float dstX = getX() - (isRoot ? 200 : 100);
        float dstY = getY() - (isRoot ? 100 : 200);
        getStage().addActor(leftChild);
        if (shouldAnimation) {
            leftChild.setPosition(getStage().getWidth() / 2 - getWidth() / 2,
                    getStage().getHeight() / 2 + getHeight());
            MoveToAction moveToAction = Actions.moveTo(dstX, dstY);
            moveToAction.setDuration(0.7f);
            this.leftChild.addAction(Actions.sequence(
                    Actions.delay(0.3f), moveToAction));
        } else {
            leftChild.setPosition(dstX, dstY);
        }
        return this.leftChild;
    }

    public TreeNodeActor setRightChild(int number, boolean shouldAnimation) {
        this.rightChild = new TreeNodeActor(number);
        float dstX = getX() * 2 - leftChild.getX();
        float dstY = getY() - (isRoot ? 100 : 200);
        getStage().addActor(rightChild);
        if (shouldAnimation) {
            rightChild.setPosition(getStage().getWidth() / 2 - getWidth() / 2,
                    getStage().getHeight() / 2 + getHeight());
            MoveToAction moveToAction = Actions.moveTo(dstX, dstY);
            moveToAction.setDuration(0.7f);
            this.rightChild.addAction(Actions.sequence(
                    Actions.delay(0.3f), moveToAction));
        } else {
            rightChild.setPosition(dstX, dstY);
        }
        return this.rightChild;
    }

    public void emptyLeft() {
        leftChild = null;
    }

    public TreeNodeActor setNilLeft(int number) {
        leftChild = new TreeNodeActor(number);
        leftChild.setText("NIL");
        float dstX = getX() - (isRoot ? 200 : 100);
        float dstY = getY() - (isRoot ? 100 : 200);
        leftChild.setPosition(dstX, dstY);
        leftChild.setColor(Color.BLACK);
        getStage().addActor(leftChild);

        return leftChild;
    }

    public TreeNodeActor getRightChild() {
        return rightChild;
    }

    public TreeNodeActor getParentNode() {
        return parentNode;
    }

    public TreeNodeActor getLeftChild() {
        return leftChild;
    }

    public void setParentNode(TreeNodeActor parent) {
        this.parentNode = parent;
    }

    public int getNumber() {
        return number;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }
}

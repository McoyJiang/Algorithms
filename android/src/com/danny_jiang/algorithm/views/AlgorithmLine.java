package com.danny_jiang.algorithm.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by ext.danny.jiang on 17/4/17.
 * <p>
 * A widget used to draw a specific color line
 */

public class AlgorithmLine extends Actor {

    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float lineWidth;

    private ShapeRenderer sr;

    private Bezier<Vector2> bezier;
    float t;
    float speed = 3f;
    private Vector2 tmpV;

    private boolean shouldAnimation = false;

    public AlgorithmLine() {
        this(0, 0, 0, 0, -1);
    }

    public AlgorithmLine(float x1, float y1, float x2, float y2, float lineWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.lineWidth = lineWidth;

        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
        tmpV= new Vector2(x1, y1);

        sr = new ShapeRenderer();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (sr != null) {
            if (y1 < y2 && tmpV.y < y2 || y2 < y1 && tmpV.y > y2) {
                t += speed * Gdx.graphics.getDeltaTime();
                bezier.valueAt(tmpV, t);
            }

            batch.end();

            sr.setProjectionMatrix(batch.getProjectionMatrix());
            sr.setTransformMatrix(batch.getTransformMatrix());
            sr.translate(getX(), getY(), 0);

            sr.begin(ShapeRenderer.ShapeType.Filled);

            if (shouldAnimation) {
                sr.rectLine(x1, y1, tmpV.x, tmpV.y, lineWidth);
            } else {
                sr.rectLine(x1, y1, x2, y2, lineWidth);
            }

            sr.end();

            Gdx.gl.glLineWidth(1);
            batch.begin();
        }

    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
        bezier = new Bezier<>(new Vector2(x1, y1), new Vector2(x2, y2));
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

    public void setShouldAnimation(boolean shouldAnimation) {
        this.shouldAnimation = shouldAnimation;
    }
}

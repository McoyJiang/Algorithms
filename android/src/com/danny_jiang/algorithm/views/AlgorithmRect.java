package com.danny_jiang.algorithm.views;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AlgorithmRect extends Actor {

    private ShapeRenderer sr;

    public AlgorithmRect() {
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
            Log.e("CCC", "AlgorithmRect: draw");
            batch.end();

            sr.setProjectionMatrix(batch.getProjectionMatrix());
            sr.setTransformMatrix(batch.getTransformMatrix());

            Gdx.gl.glLineWidth(10);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.valueOf("#d2143a"));
            sr.rect(getX(), getY(), getWidth(), getHeight());
            sr.end();

            batch.begin();
        }

    }
}

package com.danny_jiang.algorithm.common;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.danny_jiang.algorithm.utils.ShapeRendererUtils;
import com.danny_jiang.algorithm.views.BaseGdxActor;

public class AlgorithmButton extends BaseGdxActor {

    private ShapeRenderer renderer;
    private BitmapFont bitmapFont;
    private float textWidth;
    private float textHeight;

    private String text;

    public AlgorithmButton(String text) {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setColor(Color.valueOf("#f15e38"));

        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("font/algorithm_button.fnt"),
                Gdx.files.internal("font/algorithm_button.png"), false);
        bitmapFont.setColor(Color.WHITE);
        textWidth = 32 * text.length();
        textHeight = 32;

        this.text = text;
    }

    public void setBackgroundColor(Color color) {
        renderer.setColor(color);
    }

    public void setTextColor(Color color) {
        bitmapFont.setColor(color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        try {
            super.draw(batch, parentAlpha);
            batch.end();

            renderer.setProjectionMatrix(batch.getProjectionMatrix());
            renderer.setTransformMatrix(batch.getTransformMatrix());
            renderer.begin();
            renderer.set(ShapeRenderer.ShapeType.Filled);
            ShapeRendererUtils.drawRoundRect(getX(), getY(), getWidth(), getHeight(), renderer,20);
            renderer.end();

            batch.begin();

            bitmapFont.draw(batch, text,
                    getX() + getWidth() / 2 - textWidth / 2 + 5,
                    getY() + getHeight() / 2 + textHeight / 2 + 5);
        } catch (Exception e) {
            Log.e("AlgorithmButton", "e is " + e.getMessage());
        }
    }
}

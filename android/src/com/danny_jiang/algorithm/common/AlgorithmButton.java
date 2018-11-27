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
                Gdx.files.internal("quick_sort/quick_sort.fnt"),
                Gdx.files.internal("quick_sort/quick_sort.png"), false);
        bitmapFont.setColor(Color.WHITE);
        textWidth = bitmapFont.getData().getGlyph('A').width * (text + "").length();
        textHeight = bitmapFont.getData().getGlyph('A').height * 1.8f;
        bitmapFont.getData().scale(0.8f);

        this.text = text;
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

            bitmapFont.draw(batch, text + "",
                    getX() + getWidth() / 2 - textWidth / 2 - 3,
                    getY() + getHeight() / 2 + textHeight / 2);
        } catch (Exception e) {
            Log.e("AlgorithmButton", "e is " + e.getMessage());
        }
    }
}

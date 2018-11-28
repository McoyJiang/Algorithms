package com.danny_jiang.algorithm.data_structure.stack;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.danny_jiang.algorithm.utils.ShapeRendererUtils;
import com.danny_jiang.algorithm.views.BaseGdxActor;

public class StackContainer extends BaseGdxActor {
    private static Color preColor = Color.valueOf("#90a1bd");
    private static Color subColor = Color.valueOf("#dadeeb");
    private ShapeRenderer renderer;

    public StackContainer() {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setColor(Color.valueOf("#90a1bd"));
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
            renderer.rect(getX(), getY(), 25, getHeight(),
                    preColor, subColor, subColor, preColor);

            renderer.rect(getX() + getWidth(), getY(), 25, getHeight(),
                    subColor, preColor, preColor, subColor);

            renderer.rect(getX(), getY(), getWidth() + 25, 25,
                    preColor, preColor, subColor, subColor);
            renderer.end();

            batch.begin();
        } catch (Exception e) {
            Log.e("AlgorithmButton", "e is " + e.getMessage());
        }
    }
}

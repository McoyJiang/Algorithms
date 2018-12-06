package com.danny_jiang.algorithm.data_structure.queue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.danny_jiang.algorithm.utils.ShapeRendererUtils;

public class QueueHorizontalGroup extends HorizontalGroup {
    private ShapeRenderer renderer;

    public QueueHorizontalGroup() {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setColor(Color.valueOf("#f15e38"));
    }

    public void setBackgroundColor(Color color) {
        renderer.setColor(color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Filled);
        ShapeRendererUtils.drawRoundRect(getX(), getY(), getWidth(), getHeight(), renderer,20);
        renderer.end();

        batch.begin();
        super.draw(batch, parentAlpha);
    }
}

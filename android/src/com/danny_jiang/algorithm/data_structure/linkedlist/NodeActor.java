package com.danny_jiang.algorithm.data_structure.linkedlist;


import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.danny_jiang.algorithm.views.BaseGdxActor;

public class NodeActor extends BaseGdxActor {

    private final ShapeRenderer renderer;
    private BitmapFont bitmapFont;
    private float textWidth;
    private float textHeight;

    private float rightEdge = -1;
    private float leftEdge = -1;

    private final int value;


    public NodeActor(int value) {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setColor(Color.BLUE);

        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("quick_sort/quick_sort.fnt"),
                Gdx.files.internal("quick_sort/quick_sort.png"), false);
        bitmapFont.setColor(Color.GRAY);
        textWidth = bitmapFont.getData().getGlyph('A').width * (value + "").length();
        textHeight = bitmapFont.getData().getGlyph('A').height * 1.8f;
        bitmapFont.getData().scale(0.8f);

        this.value = value;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        try {
            super.draw(batch, parentAlpha);
            batch.end();

            renderer.setProjectionMatrix(batch.getProjectionMatrix());
            renderer.setTransformMatrix(batch.getTransformMatrix());
            renderer.begin();

            // draw outer box
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rectLine(getX(), getY(), getX() + getWidth(), getY(), 5);
            renderer.rectLine(getX(), getY(), getX(), getY() + getHeight(), 5);
            renderer.rectLine(getX() + getWidth(), getY(),
                    getX() + getWidth(), getY() + getHeight(), 5);
            renderer.rectLine(getX(), getY() + getHeight(),
                    getX() + getWidth(), getY() + getHeight(), 5);

            // draw middle line within box
            renderer.rectLine(getX() + 5.0f, getY() + getHeight() / 2,
                    getX() + getWidth() - 5.0f, getY() + getHeight() / 2, 5);

            // draw little circle pointer
            float pointerStartX = getX() + getWidth() / 2;
            float pointerStartY = getY() + getHeight() * 3 / 4;
            renderer.circle(pointerStartX, pointerStartY, 6);

            // draw arrow line
            float arrowEndPoint = pointerStartX;
            if (rightEdge != -1 && pointerStartX < rightEdge) {
                arrowEndPoint = rightEdge;
                renderer.triangle(
                        rightEdge, getY() + getHeight() * 3 / 4 - 15,
                        rightEdge, getY() + getHeight() * 3 / 4 + 15,
                        rightEdge + 60, getY() + getHeight() * 3 / 4);
            } else if (leftEdge != -1 && pointerStartX > leftEdge) {
                arrowEndPoint = leftEdge;
                renderer.triangle(
                        leftEdge, getY() + getHeight() * 3 / 4 - 15,
                        leftEdge, getY() + getHeight() * 3 / 4 + 15,
                        leftEdge - 60, getY() + getHeight() * 3 / 4);
            }
            renderer.rectLine(pointerStartX, pointerStartY, arrowEndPoint, pointerStartY, 5);

            renderer.end();
            batch.begin();

            bitmapFont.draw(batch, value + "",
                    getX() + getWidth() / 2 - textWidth / 2 - 3,
                    getY() + textHeight + 10);
        } catch (Exception e) {
            Log.e("BBB", "e is " + e.getMessage());
        }
    }

    public void setRightEdge(float rightEdge) {
        this.rightEdge = rightEdge;
    }

    public void setLeftEdge(float leftEdge) {
        this.leftEdge = leftEdge;
    }
}

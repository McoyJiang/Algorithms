package com.danny_jiang.algorithm.data_structure.linkedlist;


import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.danny_jiang.algorithm.views.BaseGdxActor;

public class NodeActor extends BaseGdxActor {

    private final ShapeRenderer renderer;
    private BitmapFont bitmapFont;
    private float textWidth;
    private float textHeight;

    private boolean isTail = true;

    private float rightEdge = 0;
    private float leftEdge = 0;

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
            if (isTail) {
                renderer.setColor(Color.RED);
                renderer.circle(pointerStartX, pointerStartY, 6);
            } else {
                renderer.setColor(Color.BLUE);
                renderer.circle(pointerStartX, pointerStartY, 6);
            }

            renderer.setColor(Color.BLUE);
            // draw right-arrow line
            renderer.rectLine(pointerStartX, pointerStartY,
                    pointerStartX + rightEdge, pointerStartY, 5);
            // draw left-arrow line
            renderer.rectLine(pointerStartX - leftEdge, pointerStartY,
                    pointerStartX, pointerStartY, 5);
            // draw right-arrow shape
            if (!isTail && rightEdge != 0) {
                renderer.triangle(
                        pointerStartX + rightEdge, getY() + getHeight() * 3 / 4 - 15,
                        pointerStartX + rightEdge, getY() + getHeight() * 3 / 4 + 15,
                        pointerStartX + rightEdge + 60, getY() + getHeight() * 3 / 4);
            }
            // draw left-arrow shape
            if (leftEdge != 0) {
                renderer.triangle(
                        pointerStartX - leftEdge, getY() + getHeight() * 3 / 4 - 15,
                        pointerStartX - leftEdge, getY() + getHeight() * 3 / 4 + 15,
                        pointerStartX - leftEdge - 60, getY() + getHeight() * 3 / 4);
            }

            renderer.end();
            batch.begin();

            bitmapFont.draw(batch, value + "",
                    getX() + getWidth() / 2 - textWidth / 2 - 3,
                    getY() + textHeight + 10);
        } catch (Exception e) {
            Log.e("BBB", "e is " + e.getMessage());
        }
    }

    public void addNodeToHead(float x, float y) {
        if (rightEdge >= 150) {
            return;
        }
        RunnableAction run = Actions.run(() -> rightEdge += 2);
        RepeatAction repeat = Actions.repeat(50, run);
        addAction(repeat);
    }

    public void addNodeToLast(float x, float y) {
        if (leftEdge >= 150) {
            return;
        }
        RunnableAction run = Actions.run(() -> leftEdge += 2);
        RepeatAction repeat = Actions.repeat(50, run);
        addAction(repeat);
    }

    public void setTail(boolean tail) {
        isTail = tail;
    }
}

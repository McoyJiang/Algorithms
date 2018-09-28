package com.danny_jiang.algorithm.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Created by ext.danny.jiang on 17/3/31.
 */

public class BaseGdxActor extends Actor {

    protected TextureRegion region;

    protected InputListener inputListener;

    protected String id;

    protected void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public interface InputCallback {
        boolean touchDown(InputEvent event, float x, float y, int pointer, int button);

        void touchDragged(InputEvent event, float x, float y, int pointer);

        void touchUp(InputEvent event, float x, float y, int pointer, int button);
    }

    protected InputCallback callback;

    public InputCallback getCallback() {
        return callback;
    }

    public void setCallback(InputCallback callback) {
        this.callback = callback;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    protected BaseGdxActor() {
        setInputListener();
    }

    protected BaseGdxActor(String audioPath) {
        setInputListener();
    }

    public BaseGdxActor(TextureRegion region) {
        this.region = region;

        setSize(region.getRegionWidth(), region.getRegionHeight());

        setInputListener();
    }

    private void setInputListener() {
        inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return callback == null
                        ? super.touchDown(event, x, y, pointer, button)
                        : callback.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (callback == null)
                    super.touchDragged(event, x, y, pointer);
                else
                    callback.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (callback == null)
                    super.touchUp(event, x, y, pointer, button);
                else
                    callback.touchUp(event, x, y, pointer, button);
            }
        };

        addListener(inputListener);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // if region is null or Actor is not visible, make it invisible
        if (region == null || !isVisible()) {
            return;
        }

        com.badlogic.gdx.graphics.Color tempBatchColor = batch.getColor();
        com.badlogic.gdx.graphics.Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        /*
         * Draw the texture region
         */
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );

        batch.setColor(tempBatchColor);
    }

    protected RepeatAction repeat(Action action) {
        return Actions.repeat(Integer.MAX_VALUE, action);
    }

    protected RunnableAction flip(final boolean x, final boolean y) {

        return Actions.run(new Runnable() {
            @Override
            public void run() {
                region.flip(x, y);
            }
        });
    }

    protected SequenceAction sequence(Action... actions) {
        return Actions.sequence(actions);
    }

    protected MoveToAction moveTo(float x, float y) {
        return moveTo(x, y, 0);
    }

    protected MoveToAction moveTo(float x, float y, float duration) {
        return Actions.moveTo(x, y, duration);
    }

    protected RotateByAction rotateBy(float rotationAmount, float duration) {
        return Actions.rotateBy(rotationAmount, duration);
    }

    protected ScaleToAction scaleTo(float x, float y) {
        return scaleTo(x, y, 0);
    }

    protected ScaleToAction scaleTo(float x, float y, float duration) {
        return Actions.scaleTo(x, y, duration);
    }

    public void dispose(){
        if(region != null){
            region.getTexture().dispose();
            region = null;
        }
    }
}

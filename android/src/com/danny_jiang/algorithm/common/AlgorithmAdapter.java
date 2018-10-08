package com.danny_jiang.algorithm.common;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.views.BaseGdxActor;

public abstract class AlgorithmAdapter extends ApplicationAdapter {

    protected int[] array;
    protected Stage stage;
    protected BaseGdxActor next;
    protected ClickListener nextClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            disableNextButton();
            nextStep();
        }
    };
    @Override
    public void create() {
        super.create();
        initData();

        stage = new Stage();
        Image bg = new Image(new Texture(Gdx.files.internal("bg/visualizer_bg.png")));
        bg.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);
        Gdx.input.setInputProcessor(stage);

        next = new BaseGdxActor();
        enableNextButton();
        next.setSize(200, 200);
        next.setPosition(stage.getWidth() - 250, 10);
        stage.addActor(next);
        inflateStage();
    }

    @Override
    public void render() {
        super.render();
        // 黄色清屏
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    public void enableNextButton() {
        next.setRegion(new TextureRegion(new Texture(
                Gdx.files.internal("next_step.png")
        )));
        next.addListener(nextClickListener);
    }

    public void disableNextButton() {
        next.setRegion(new TextureRegion(new Texture(
                Gdx.files.internal("next_step_disable.png")
        )));
        next.removeListener(nextClickListener);
    }

    protected abstract void nextStep();

    protected abstract void inflateStage();

    protected abstract void initData();
}

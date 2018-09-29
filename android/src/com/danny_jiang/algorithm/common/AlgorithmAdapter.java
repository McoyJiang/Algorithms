package com.danny_jiang.algorithm.common;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
    @Override
    public void create() {
        super.create();
        initData();

        stage = new Stage();
        Image bg = new Image(new Texture(Gdx.files.internal("bg/visualizer_bg.png")));
        bg.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);
        Gdx.input.setInputProcessor(stage);

        BaseGdxActor next = new BaseGdxActor(new TextureRegion(new Texture(
                Gdx.files.internal("badlogic.jpg")
        )));
        next.setSize(200, 100);
        next.setPosition(400, 100);
        stage.addActor(next);
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextStep();
            }
        });
        inflateStage();
    }

    protected abstract void nextStep();

    protected abstract void inflateStage();

    protected abstract void initData();
}

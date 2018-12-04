package com.danny_jiang.algorithm.data_structure.queue;

import android.os.Message;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

public class BurgerAdapter extends AlgorithmAdapter {

    Image burger;

    @Override
    protected void inflateStage() {
        burger = new Image(new TextureRegion(
                new Texture("data_structure/queue/cheese-burger.png")));
        burger.setSize(100, 100);
        burger.setPosition(0, visualizerBg.getY()
                + visualizerBg.getHeight() / 2 - burger.getHeight() / 2);
        stage.addActor(burger);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {

    }

    @Override
    protected void algorithm() {

    }
}

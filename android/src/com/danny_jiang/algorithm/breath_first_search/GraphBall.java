package com.danny_jiang.algorithm.breath_first_search;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.danny_jiang.algorithm.views.AlgorithmBall;

public class GraphBall extends AlgorithmBall {

    public GraphBall(String text) {
        super(text);
    }

    @Override
    public void defaultStatus() {
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("bg/gray_ball_bg.png"))));
    }

    public void iteratorStatus() {
        setRegion(new TextureRegion(new Texture(
                Gdx.files.internal("bg/iterator_ball_bg.png"))));
    }
}

package com.danny_jiang.algorithm.views;

import android.text.TextUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AlgorithmBall extends BaseGdxActor {

    private char A = 'A';
    private BitmapFont bitmapFont;
    private String text;
    private int index;

    public AlgorithmBall(String text) {
        this.text = text;
        this.bitmapFont = new BitmapFont(
                Gdx.files.internal("font/default.fnt"),
                Gdx.files.internal("font/default.png"), false);
        bitmapFont.setColor(Color.GRAY);
        defaultStatus();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!TextUtils.isEmpty(text)) {
            int textWidth = bitmapFont.getData().getGlyph(A).width * text.length();
            int textHeight = bitmapFont.getData().getGlyph(A).height;
            bitmapFont.draw(batch, text,
                    getX() + getWidth() / 2 - textWidth / 2 - 3,
                    getY() + getHeight() / 2 + textHeight / 2 + 3);
        }
    }

    public void defaultStatus() {
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("bg/default_ball_bg.png"))));
    }

    public void activeStatus() {
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("bg/active_ball_bg.png"))));
    }

    public void deadStatus() {
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("bg/dead_ball_bg.png"))));
        bitmapFont.setColor(Color.WHITE);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}

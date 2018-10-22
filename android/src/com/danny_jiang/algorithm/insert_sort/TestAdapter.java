package com.danny_jiang.algorithm.insert_sort;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.microedition.khronos.opengles.GL10;

public class TestAdapter extends ApplicationAdapter {

    SpriteBatch batch;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();

        /**
         * BitmapFont的初始化。
         * 3个参数分别为:fontFile(字体文件)、imageFile(所对应的png文件)、是否翻转
         */
        font = new BitmapFont(Gdx.files.internal("font/my_font.fnt"),
                Gdx.files.internal("font/my_font.png"), false);
//		font.setColor(0.5f, 0.4f, 0.6f, 1);//设置颜色
        font.setColor(Color.GREEN);//设置字体颜色。如果不处理,默认是那个文件里面的颜色
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(new Texture("badlogic.jpg"), 100, 100);

        /**
         * 把字体绘画出来
         * 4个参数分别为: 所使用的SpriteBathc、所要绘制的字体(英文都可以,中文必须是那个字体文件中有的才可以,显示位置的起点坐标(x,y))
         */
        font.draw(batch, "我是", 200, 200);
        font.draw(batch, "hello libgdx", 100, 100);

        batch.end();

    }

    @Override
    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }
}

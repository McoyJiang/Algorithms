package com.danny_jiang.algorithm.data_structure.stack;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;

public class StackAdapter extends AlgorithmAdapter {
    private AlgorithmButton button;

    @Override
    protected void inflateStage() {
        button = new AlgorithmButton("12");
        button.setSize(200, 100);
        button.setPosition(100, 100);
        stage.addActor(button);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Log.e("StackAdapter", "button clicked");
            }
        });
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

package com.danny_jiang.algorithm.data_structure.stack;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;

public class StackAdapter extends AlgorithmAdapter {
    private AlgorithmButton button;

    private int[] dataList = new int[]{26, 32, 46};
    private String[] colorList = new String[]{"#339900", "#e82a4b", "#ffcc00"};

    @Override
    protected void inflateStage() {
        //next.setVisible(false);
        StackContainer actor = new StackContainer();
        actor.setSize(300, 400);
        actor.setPosition(stage.getWidth() / 2 - actor.getWidth() / 2 - 12.5f,
                stage.getHeight() - actor.getHeight() - 400);
        stage.addActor(actor);
        for (int i = 0; i < dataList.length; i++) {
            button = new AlgorithmButton("" + dataList[i]);
            button.setSize(250, 100);
            button.setBackgroundColor(Color.valueOf(colorList[i]));
            button.setPosition(i * 100 + 100, stage.getHeight() - 200);
            stage.addActor(button);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Log.e("StackAdapter", "button clicked");
                }
            });

            MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - button.getWidth() / 2,
                    button.getY());
            firstMove.setDuration(0.5f);
            MoveByAction secondMove = Actions.moveBy(0,
                    -(button.getY() - actor.getY() - 50 - i * 120));
            secondMove.setDuration(0.5f);
            SequenceAction sequence = Actions.sequence(firstMove, Actions.delay(0.3f, secondMove));
            button.addAction(sequence);
        }
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

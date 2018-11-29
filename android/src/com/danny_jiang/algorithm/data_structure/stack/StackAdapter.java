package com.danny_jiang.algorithm.data_structure.stack;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;

import java.util.ArrayList;
import java.util.List;

public class StackAdapter extends AlgorithmAdapter {
    private static final int PUSH = 0;
    private static final int PUSH_VISIBLE = 1;
    private static final int POP_VISIBLE = 2;
    private static final int POP = 3;

    private int[] dataList = new int[]{26, 32, 46};
    private String[] colorList = new String[]{"#339900", "#e82a4b", "#ffcc00"};

    private StackContainer stackContainer;
    private Label pushLabel;
    private Label popLabel;
    private Label emptyLabel;
    private Label stackLabel;
    private Label stepDescription;
    private List<AlgorithmButton> buttonList = new ArrayList<>();

    @Override
    protected void inflateStage() {
        stackContainer = new StackContainer();
        stackContainer.setSize(300, 400);
        stackContainer.setPosition(stage.getWidth() / 2 - stackContainer.getWidth() / 2 - 12.5f,
                stage.getHeight() - stackContainer.getHeight() - 380);
        stage.addActor(stackContainer);

        addLabels();

        for (int i = 0; i < dataList.length; i++) {
            AlgorithmButton button = new AlgorithmButton("" + dataList[i]);
            button.setVisible(false);
            button.setSize(250, 100);
            button.setBackgroundColor(Color.valueOf(colorList[i]));
            button.setPosition(-250, stage.getHeight() - 300);
            stage.addActor(button);
            buttonList.add(button);
        }
    }

    private void addLabels() {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(
                "font/big_size.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = Color.valueOf("#ca2934");
        pushLabel = new Label("Push", style);
        popLabel = new Label("Pop", style);
        pushLabel.setPosition( 100, stage.getHeight() - 150);
        popLabel.setPosition( stackContainer.getX() + stackContainer.getWidth() + 100,
                stage.getHeight() - 150);
        pushLabel.setVisible(false);
        popLabel.setVisible(false);
        stage.addActor(pushLabel);
        stage.addActor(popLabel);

        Label.LabelStyle emptyStyle = new Label.LabelStyle();
        emptyStyle.font = bitmapFont;
        emptyStyle.fontColor = Color.valueOf("#f2f2f2");
        emptyLabel = new Label("Empty", emptyStyle);
        emptyLabel.setPosition(stage.getWidth() / 2 - emptyLabel.getWidth() / 2,
                stackContainer.getY() + stackContainer.getHeight() / 2 - emptyLabel.getHeight() / 2);
        stage.addActor(emptyLabel);

        Label.LabelStyle stackStyle = new Label.LabelStyle();
        stackStyle.font = bitmapFont;
        stackStyle.fontColor = Color.valueOf("#1d0b1c");
        stackLabel = new Label("Stack", stackStyle);
        stackLabel.setPosition(stage.getWidth() / 2 - stackLabel.getWidth() / 2,
                stackContainer.getY() - stackLabel.getHeight() - 10);
        stage.addActor(stackLabel);

        BitmapFont desFont = new BitmapFont(Gdx.files.internal(
                "data_structure/stack/stack.fnt"));
        Label.LabelStyle desStyle = new Label.LabelStyle();
        desStyle.font = desFont;
        desStyle.fontColor = Color.valueOf("#4A4A4A");
        stepDescription = new Label("", desStyle);
        stepDescription.setText("栈也是一种线性表结构\n" +
                "向栈中插入或者提取数据时遵循:\n" +
                "先进后出(FILO)的规律");
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(30, stage.getHeight() / 3 - 100);
        stage.addActor(stepDescription);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case PUSH_VISIBLE:
                Gdx.app.postRunnable(() -> {
                    stepDescription.setText("将一个元素压入到栈中时,\n" +
                            "需要将其置于栈顶位置\n" +
                            "时间复杂度: O(1)");
                    pushLabel.setVisible(true);
                });
                break;
            case PUSH:
                push(msg.arg1);
                break;
            case POP_VISIBLE:
                Gdx.app.postRunnable(() -> {
                    stepDescription.setText("移除当前栈的栈顶元素\n" +
                            "时间复杂度: O(1)");
                    popLabel.setVisible(true);
                    pushLabel.setVisible(false);
                });
                break;
            case POP:
                pop(msg.arg1);
                break;
        }
    }

    private void pop(int i) {
        AlgorithmButton button = buttonList.get(i);
        MoveByAction firstMove = Actions.moveBy(stage.getWidth() + button.getWidth(), 0);
        firstMove.setDuration(0.5f);
        MoveByAction secondMove = Actions.moveBy(0, 450 - i * 120);
        secondMove.setDuration(0.5f);
        SequenceAction sequence = Actions.sequence(secondMove, Actions.delay(0.1f, firstMove));
        if (i == 0)
            sequence.addAction(Actions.run(() -> {
                emptyLabel.setVisible(true);
                popLabel.setVisible(false);
                stepDescription.setText("Done!");
            }));
        button.addAction(sequence);
    }

    private void push(int i) {
        AlgorithmButton button = buttonList.get(i);
        button.setVisible(true);
        MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - button.getWidth() / 2,
                button.getY());
        firstMove.setDuration(0.5f);
        MoveByAction secondMove = Actions.moveBy(0,
                -(450 - i * 120));
        secondMove.setDuration(0.5f);
        SequenceAction sequence = Actions.sequence();
        if (i == 0)
            sequence.addAction(Actions.run(() -> emptyLabel.setVisible(false)));
        sequence.addAction(firstMove);
        sequence.addAction(Actions.delay(0.1f));
        sequence.addAction(secondMove);

        button.addAction(sequence);
    }

    @Override
    protected void algorithm() {
        await();

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(PUSH_VISIBLE, 0, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(PUSH, 0, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(PUSH, 1, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(PUSH, 2, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(POP_VISIBLE, 2, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(POP, 2, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(POP, 1, -1));
        });

        await((BeforeWaitCallback)() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(POP, 0, -1));
        });
    }
}

package com.danny_jiang.algorithm.data_structure.array;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapter extends AlgorithmAdapter {

    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int VISIBLE_SIX = 2;
    private static final int SHOW_ELEMENT = 3;
    private static final int ARRAY_DESCRIBE = 4;
    private static final int MOVE_OUT_SIX = 5;
    private static final int COMPLETE = 6;

    private int[] data = new int[]{2, 5, 8, 9};
    private List<ArrayElement> elementList = new ArrayList<>();
    private ArrayElement sixElement;
    private ArrayElement emptyElement;

    private List<Label> labelList = new ArrayList<>();
    private Label stepDescription;

    @Override
    protected void inflateStage() {
        BitmapFont bitmapFont = new BitmapFont(
                Gdx.files.internal("data_structure/Array/array.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = Color.valueOf("#696969");
        stepDescription = new Label("", style);
        stepDescription.setAlignment(Align.topLeft);
        stepDescription.setSize(stage.getWidth(), stage.getHeight() / 2 - next.getHeight() - 100);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(15, next.getHeight() + 10);
        stage.addActor(stepDescription);
        stepDescription.setText("数组是一种线性表结构\n它用一组连续的" +
                "内存空间\n来存储具有相同类型的数据");

        for (int i = 0; i < data.length; i++) {
            ArrayElement element = new ArrayElement("" + data[i]);
            element.setSize(180, 150);
            element.setOrigin(element.getWidth() / 2, element.getHeight() / 2);
            float elementPositionX = i < 2 ? i * 190 + 50 : stage.getWidth() + i * 190 + 50;
            float elementPositionY = stage.getHeight() * 3 / 4 - 50;
            element.setPosition(elementPositionX, elementPositionY);
            elementList.add(element);
            stage.addActor(element);

            Label label = new Label("arr[" + i + "]", style);
            label.setSize(120, 100);
            label.setFontScale(1.5f);
            label.setPosition(i * 190 + 50 + element.getWidth() / 2 - 60,
                    elementPositionY + element.getHeight());
            label.setVisible(false);
            labelList.add(label);
            stage.addActor(label);

        }

        sixElement = new ArrayElement("6");
        sixElement.setSize(180, 150);
        sixElement.setPosition(430, stage.getHeight() * 3 / 4 - 200);
        stage.addActor(sixElement);
        sixElement.setVisible(false);

        emptyElement = new ArrayElement("");
        emptyElement.setSize(200, 150);
        ArrayElement lastElement = elementList.get(elementList.size() - 1);
        emptyElement.setPosition(lastElement.getX() + lastElement.getWidth(),
                lastElement.getY());
        stage.addActor(emptyElement);
        emptyElement.setVisible(false);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void algorithm() {
        await();

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(SHOW_ELEMENT, 2, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(SHOW_ELEMENT, 3, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ARRAY_DESCRIBE, 0, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ARRAY_DESCRIBE, 1, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ARRAY_DESCRIBE, 2, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ARRAY_DESCRIBE, 3, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(VISIBLE_SIX)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(INSERT)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(MOVE_OUT_SIX)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DELETE)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(COMPLETE)));
    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case SHOW_ELEMENT:
                showElement(msg.arg1);
                break;
            case ARRAY_DESCRIBE:
                arrayDescription(msg.arg1);
                break;
            case INSERT:
                insertOperation();
            break;
            case DELETE:
                deleteOperation();
            break;
            case VISIBLE_SIX: {
                stepDescription.setText("将 6 插入到数组中.\n时间复杂度:O(n)");
                emptyElement.setVisible(true);
                sixElement.setVisible(true);
            }
            break;
            case MOVE_OUT_SIX:
                stepDescription.setText("将 6 从数组中删除,\n时间复杂度:O(n)");
                break;
            case COMPLETE:
                stepDescription.setText("Complete!");
                break;
        }
    }

    private void showElement(int index) {
        ArrayElement element = elementList.get(index);
        MoveByAction moveByAction = Actions.moveBy(-stage.getWidth(), 0);
        moveByAction.setDuration(0.5f);
        element.addAction(moveByAction);
        stage.addActor(element);
    }

    private void arrayDescription(int i) {
        Gdx.app.postRunnable(() -> {
            stepDescription.setText("可以通过下标来访问数组中的元素");
            SequenceAction sequence = Actions.sequence();
            final Label label = labelList.get(i);
            final ArrayElement element = elementList.get(i);
            ScaleToAction scaleLarge = Actions.scaleTo(1.1f, 1.1f);
            scaleLarge.setDuration(0.2f);
            scaleLarge.setTarget(element);
            ScaleToAction scaleSmall = Actions.scaleTo(1f, 1f);
            scaleSmall.setTarget(element);
            scaleSmall.setDuration(0.2f);

            sequence.addAction(Actions.run(() -> label.setVisible(true)));
            sequence.addAction(scaleLarge);
            sequence.addAction(Actions.delay(0.2f));
            sequence.addAction(scaleSmall);
            sequence.addAction(Actions.delay(0.2f));
            sequence.addAction(Actions.run(() -> label.setVisible(false)));

            stage.addAction(sequence);
        });
    }

    private void insertOperation() {
        ArrayElement element1 = elementList.get(3);
        MoveByAction moveByAction1 = Actions.moveBy(190, 0);
        moveByAction1.setDuration(0.5f);
        element1.addAction(moveByAction1);

        ArrayElement element2 = elementList.get(2);
        float originalX = element2.getX();
        float originalY = element2.getY();
        MoveByAction moveByAction2 = Actions.moveBy(190, 0);
        moveByAction2.setDuration(0.5f);
        element2.addAction(Actions.sequence(
                Actions.delay(0.5f), moveByAction2));

        MoveToAction moveToAction = Actions.moveTo(originalX, originalY);
        moveToAction.setDuration(0.5f);
        sixElement.addAction(Actions.sequence(Actions.delay(1),
                moveToAction, Actions.run(() -> emptyElement.setVisible(false))));
    }

    private void deleteOperation() {
        final float toX = 430;
        final float toY = stage.getHeight() * 3 / 4 - 200;
        final float originalX = sixElement.getX() - 10;
        final float originalY = sixElement.getY();

        ArrayElement element1 = elementList.get(3);

        ArrayElement element2 = elementList.get(2);

        RunnableAction run1 = Actions.run(() -> {
            MoveToAction moveToAction = Actions.moveTo(toX, toY);
            moveToAction.setDuration(0.5f);
            sixElement.addAction(moveToAction);
        });

        RunnableAction emptyAction = Actions.run(() -> {
            emptyElement.setPosition(originalX, originalY);
            emptyElement.setVisible(true);
        });

        RunnableAction run2 = Actions.run(() -> {
            MoveByAction moveByAction = Actions.moveBy(-190, 0);
            moveByAction.setDuration(0.5f);
            element2.addAction(moveByAction);
        });

        RunnableAction run3 = Actions.run(() -> {
            MoveByAction moveByAction = Actions.moveBy(-190, 0);
            moveByAction.setDuration(0.5f);
            element1.addAction(moveByAction);
        });

        RunnableAction run4 = Actions.run(() ->
                emptyElement.setVisible(false));

        stage.addAction(Actions.sequence(run1, Actions.delay(0.5f), emptyAction,
                run2, Actions.delay(0.5f), run3,
                Actions.delay(0.5f), run4));
    }

}

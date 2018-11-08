package com.danny_jiang.algorithm.data_structure.array;

import android.os.Message;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapter extends AlgorithmAdapter {

    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int VISIBLE_SIX = 2;

    private int[] data = new int[]{2, 5, 8, 9};
    private List<ArrayElement> elementList = new ArrayList<>();
    private ArrayElement sixElement;
    private ArrayElement emptyElement;

    @Override
    protected void inflateStage() {
        for (int i = 0; i < data.length; i++) {
            ArrayElement element = new ArrayElement("" + data[i]);
            element.setSize(180, 150);
            element.setPosition(i * 190 + 50, stage.getHeight() * 3 / 4);
            elementList.add(element);
            stage.addActor(element);
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
    protected void animation(Message msg) {
        switch (msg.what) {
            case INSERT:
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
                break;
            case DELETE:
                break;
            case VISIBLE_SIX:
                emptyElement.setVisible(true);
                sixElement.setVisible(true);
                break;
        }
    }

    @Override
    protected void algorithm() {
        await();

        await((BeforeWaitCallback)() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(VISIBLE_SIX)));

        await((BeforeWaitCallback)() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(INSERT)));
    }
}

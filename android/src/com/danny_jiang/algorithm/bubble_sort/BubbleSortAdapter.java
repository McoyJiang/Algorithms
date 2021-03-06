package com.danny_jiang.algorithm.bubble_sort;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.utils.AnimationUtils;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * void bubble_sort(int A[], int n) {
 * int temp;
 * for(int i = 0; i < n-1; i++) {
 * for(int j = 0; j < n - i - 1; j++) {
 * if(A[j] > A[j + 1] ) {
 * // here swapping of positions is being done.
 * temp = A[j];
 * A[j] = A[j];
 * A[j + 1] = temp;
 * }
 * }
 * }
 * }
 */
public class BubbleSortAdapter extends AlgorithmAdapter {
    private static final String TAG = BubbleSortAdapter.class.getSimpleName();

    private static final String SWITCH = "Now %d is larger than %d\n" +
            "So need to swap their positions\n" +
            "And move the cursor to the next";

    private static final String NOT_SWITCH = "%d is not larger than %d\n" +
            "No need to swap their positions\n" +
            "Only move the cursor to the next";

    private static final String FINISH = "Now the iterator finished.\n" +
            "Mark %d as found status\n" +
            "The move the cursor to the first";

    private static final int ITERATOR = 0;
    private static final int SWAP = 1;
    private static final int ITERATOR_FINISH = 2;
    private static final int COMPLETED = 3;

    private HorizontalGroup bubbleSortGroup;
    private Label stepDescription;
    private Image upArrow;
    private List<AlgorithmBall> actorList;

    private volatile int deadEnd = 6;
    private volatile int currentIteratorJ = -1;

    /*
     * Data use to display Bubble Sort Algorithm
     */
    private int[] sData = new int[]{45, 17, 23, 5, 76, 10, 4};

    @Override
    public void create() {
        super.create();

        updateStatus(0);

        stepDescription.setText("At the beginning, 45 > 17\n" +
                "Should swap 45 and 17 position\n" +
                "Try to click \"Next Step\"");
    }

    private void updateStepDescription(int index, boolean maxFound) {
        Integer i1 = Integer.parseInt(actorList.get(index).getText());
        if (maxFound) {
            stepDescription.setText(String.format(FINISH, i1));
        } else {
            Integer i2 = Integer.parseInt(actorList.get(index + 1).getText());
            if (i1 > i2) {
                stepDescription.setText(String.format(SWITCH, i1, i2));
            } else {
                stepDescription.setText(String.format(NOT_SWITCH, i1, i2));
            }
        }
    }

    private void updateStatus(int index) {
        if (index < deadEnd) {
            updateStepDescription(index, false);
        } else {
            updateStepDescription(index, true);
        }
        for (int k = 0; k < sData.length; k++) {
            if (k < index) {
                actorList.get(k).defaultStatus();
            }
            if (k == index && index < deadEnd) {
                actorList.get(index).activeStatus();
                actorList.get(index + 1).activeStatus();
            }
        }
        enableNextButton();
    }

    @Override
    protected void inflateStage() {
        actorList = new ArrayList<>();

        bubbleSortGroup = new HorizontalGroup();
        bubbleSortGroup.align(Align.center);
        bubbleSortGroup.space(30);
        bubbleSortGroup.setSize(stage.getWidth(), stage.getHeight() / 4);
        bubbleSortGroup.setPosition(0, stage.getHeight() * 3 / 4);
        stage.addActor(bubbleSortGroup);

        for (int i = 0; i < sData.length; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + sData[i]);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.setPosition(i * 150 + 50, 200);
            actorList.add(algorithmBall);
            bubbleSortGroup.addActor(algorithmBall);
        }

        upArrow = new Image(new Texture("up_arrow.png"));
        upArrow.setSize(100, 150);
        upArrow.setPosition(actorList.get(0).getX() + actorList.get(0).getWidth() / 2,
                bubbleSortGroup.getY() - upArrow.getHeight() + 100);
        stage.addActor(upArrow);


        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("font/hjd.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = new Color(1, 0, 0, 1);
        stepDescription = new Label("", style);
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - stepDescription.getHeight() - 20);
        stage.addActor(stepDescription);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void animation(Message msg) {
        switch (msg.what) {
            case ITERATOR:
                Log.d(TAG, "ITERATOR: " + msg.arg1);
                moveArrow(msg.arg1 + 1);
                break;
            case SWAP:
                Log.d(TAG, "SWAP: " + msg.arg1);
                switchChild(msg.arg1, msg.arg1 + 1);
                break;
            case ITERATOR_FINISH:
                Log.d(TAG, "ITERATOR_FINISH: " + msg.arg1);
                Gdx.app.postRunnable(() ->
                {
                    actorList.get(deadEnd).deadStatus();
                    moveArrow(0);
                    deadEnd--;
                });

                break;
            case COMPLETED:
                stepDescription.setText("Bubble sort completed !");
                break;
        }
    }

    @Override
    protected void algorithm() {
        Log.d(TAG, "algorithm: should wait");
        await();
        /**
         * Bubble Sort algorithm
         */
        Log.d(TAG, "algorithm: bubble sort started");
        for (int i = 0; i < sData.length; i++) {
            for (int j = 0; j < sData.length - i - 1; j++) {
                currentIteratorJ = j;
                try {
                    if (sData[j] > sData[j + 1]) {
                        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                                sDecodingThreadHandler.obtainMessage(SWAP, currentIteratorJ, -1)));
                    } else {
                        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                                sDecodingThreadHandler.obtainMessage(ITERATOR, currentIteratorJ, -1)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ITERATOR_FINISH, currentIteratorJ + 1, -1)
            ));
        }
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(COMPLETED)
        ));
    }

    private void moveArrow(final int index) {
        AlgorithmBall algorithmBall = actorList.get(index);
        float position = algorithmBall.getX();
        MoveToAction moveToAction = Actions.moveTo(position, upArrow.getY());
        moveToAction.setDuration(0.3f);
        RunnableAction updateAction = Actions.run(() -> updateStatus(index));
        SequenceAction sequence = Actions.sequence(Actions.delay(0.3f), moveToAction, updateAction);
        upArrow.addAction(sequence);
    }

    private void switchChild(int first, int second) {
        final AlgorithmBall actorFirst = actorList.get(first);
        final AlgorithmBall actorSecond = actorList.get(second);

        Action switchActors = AnimationUtils.curveSwitchActors(actorFirst, actorSecond, () -> {
            actorFirst.setIndex(second);
            actorSecond.setIndex(first);
            actorList.remove(first);
            actorList.add(first, actorSecond);
            actorList.remove(second);
            actorList.add(second, actorFirst);
        });
        RunnableAction swapArray = Actions.run(() -> swapArray(first));
        RunnableAction run = Actions.run(() -> moveArrow(second));
        stage.addAction(Actions.sequence(switchActors, swapArray, run));
    }

    private void swapArray(int j) {
        int temp = sData[j];
        sData[j] = sData[j + 1];
        sData[j + 1] = temp;
    }
}

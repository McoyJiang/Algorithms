package com.danny_jiang.algorithm.bubble_sort;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
    private static final String SWITCH = "Now %d is larger than %d\n" +
            "So need to swap their positions\n" +
            "And move the cursor to the next";

    private static final String NOT_SWITCH = "%d is not larger than %d\n" +
            "No need to swap their positions\n" +
            "Only move the cursor to the next";

    private static final String FINISH = "Now the iterator finished.\n" +
            "Mark %d as found status\n" +
            "The move the cursor to the first";

    private HorizontalGroup bubbleSortGroup;
    private Label stepDescription;
    private Image upArrow;
    private List<AlgorithmBall> actorList;

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
        if (index < i) {
            updateStepDescription(index, false);
        } else {
            updateStepDescription(index, true);
        }
        for (int k = 0; k < 7; k++) {
            if (k < index) {
                actorList.get(k).defaultStatus();
            }
            if (k == index && index < i) {
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

        for (int i = 0; i < 7; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + array[i]);
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
        stepDescription.setPosition(30, stage.getHeight() / 2 - upArrow.getHeight());
        stage.addActor(stepDescription);
    }

    @Override
    protected void initData() {
        array = new int[]{45, 17, 23, 5, 76, 10, 4};
        Collections.shuffle(Arrays.asList(array));
    }

    @Override
    public void render() {
        super.render();
        // 黄色清屏
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    private int i = 6;
    private int j = 0;

    @Override
    public void nextStep() {
        if (j < i) {
            if (array[j] > array[j + 1]) {
                switchChild(j, j + 1);
            } else {
                moveArrow(j + 1);
            }
            j++;
        } else {
            actorList.get(j).deadStatus();
            if (i == 0) {
                stepDescription.setText("Bubble sort completed !");
            } else {
                moveArrow(0);
                j = 0;
                i--;
            }
        }

        System.out.println(Arrays.toString(array));
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
        int temp = array[j];
        array[j] = array[j + 1];
        array[j + 1] = temp;
    }
}

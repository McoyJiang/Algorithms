package com.danny_jiang.algorithm.quick_sort;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.utils.AnimationUtils;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSortAdapter extends AlgorithmAdapter {
    private static final String TAG = QuickSortAdapter.class.getSimpleName();
    private static final int FIND_PIVOT = 1;
    private static final int PARTITION_COMPLETE = 2;
    private static final int SWAP = 3;
    private static final int ITERATOR_INDEX = 4;
    private static final int BOUNCE = 5;
    private static final int ITERATION_COMPLETE = 6;
    private static final int RESET_POSITION = 7;
    private static final int SWAP_SMALL = 8;
    private static final int SWAP_BIG = 9;

    private int sData[] = {10, 80, 40, 90, 30, 50, 70};

    private HorizontalGroup bubbleSortGroup;
    private Label stepDescription;
    private List<AlgorithmBall> actorList;

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

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(
                "quick_sort/quick_sort.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = new Color(1, 0, 0, 1);
        stepDescription = new Label("", style);
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(30, 280);
        stage.addActor(stepDescription);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        final int first = msg.arg1;
        final int second = msg.arg2;
        switch (msg.what) {
            case FIND_PIVOT:
                Gdx.app.postRunnable(() -> {
                    AlgorithmBall ball = actorList.get(first);
                    resetDescription("开始Partition, 基准值: " + ball.getText() + "\n点击Next开始遍历");
                    ball.activeStatus();
                    for (int i = second; i <= first; i++) {
                        MoveByAction moveByAction = Actions.moveBy(0, -60);
                        moveByAction.setDuration(0.5f);
                        actorList.get(i).addAction(moveByAction);
                    }
                });
                break;
            case SWAP_SMALL:
                Gdx.app.postRunnable(() -> resetDescription("遍历到 " + actorList.get(first).getText() +
                        ",因为" + actorList.get(first).getText()
                        + "小于基准值\n" + "需要交换左右下标的元素\n" + "同时将左右下标向右移一位"));
                break;
            case SWAP_BIG:
                Gdx.app.postRunnable(() -> resetDescription("遍历到 " + actorList.get(first).getText() +
                        ",因为" + actorList.get(first).getText() + "大于基准值"
                        + "\n所以不需要交换元素\n" + "同时只将右下标向右移一位"));
                break;
            case SWAP:
                Gdx.app.postRunnable(() -> switchChild(first, second, false));
                break;
            case BOUNCE:
                Gdx.app.postRunnable(() -> bounceActor(first));
                break;
            case ITERATOR_INDEX:
                Gdx.app.postRunnable(() -> highlightActor(first));
                break;
            case ITERATION_COMPLETE:
                Gdx.app.postRunnable(() -> resetDescription(
                        "遍历结束\n交换基准值和左下标的位置,并执行下一次Partition操作"));
                break;
            case PARTITION_COMPLETE:
                Gdx.app.postRunnable(() -> {
                    switchChild(first, second, true);
                });
                break;
            case RESET_POSITION:
                Gdx.app.postRunnable(() -> {
                    for (int i = second; i <= first; i++) {
                        MoveByAction moveByAction = Actions.moveBy(0, 60);
                        moveByAction.setDuration(0.5f);
                        actorList.get(i).addAction(moveByAction);
                    }
                });
                break;
        }
    }

    private void resetDescription(final String description) {
        RunnableAction emptyDescription = Actions.run(
                () -> stepDescription.setText(""));
        RunnableAction setDescription = Actions.run(
                () -> stepDescription.setText(description));
        stepDescription.addAction(Actions.sequence(emptyDescription,
                Actions.delay(0.3f), setDescription));
    }

    private void bounceActor(int first) {
        AlgorithmBall ball = actorList.get(first);
        MoveByAction moveUp = Actions.moveBy(0, 30);
        moveUp.setDuration(0.3f);
        MoveByAction moveDown = Actions.moveBy(0, -30);
        moveDown.setDuration(0.3f);
        RepeatAction bounceAction = Actions.repeat(2, Actions.sequence(moveUp, moveDown));
        SequenceAction sequence = Actions.sequence(bounceAction);
        sequence.addAction(Actions.run(() -> ball.defaultStatus()));
        sequence.addAction(Actions.run(this::signal));
        ball.addAction(sequence);
    }

    private void switchChild(int first, int second, boolean complete) {
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

        SequenceAction sequence = Actions.sequence(switchActors);
        if (complete) {
            RunnableAction run = Actions.run(() -> actorList.get(first).deadStatus());
            sequence.addAction(run);
        } else {
            sequence.addAction(Actions.run(() -> {
                actorFirst.defaultStatus();
                actorSecond.defaultStatus();
            }));
            sequence.addAction(Actions.run(this::signal));
        }

        stage.addAction(sequence);
    }

    private void highlightActor(int first) {
        AlgorithmBall ball = actorList.get(first);
        RunnableAction highlight = Actions.run(ball::iteratorStatus);
        RunnableAction wait = Actions.run(this::signal);
        ball.addAction(Actions.sequence(highlight, wait));
    }

    @Override
    protected void algorithm() {
        await();
        sort(sData, 0, sData.length - 1);
        stepDescription.setText("快速排序结束");
    }

    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    int partition(int arr[], int low, int high) {
        Log.e(TAG, "partition: arr is " + arr + " low is " + low + " high is " + high);
        final int pivot = arr[high];
        await((BeforeWaitCallback)() -> {
            Message message = sDecodingThreadHandler.obtainMessage(FIND_PIVOT);
            message.arg1 = high;
            message.arg2 = low;
            sDecodingThreadHandler.sendMessage(message);
        });
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            final int end = j;
            await((BeforeWaitCallback)() -> {
                Message message = sDecodingThreadHandler.obtainMessage(ITERATOR_INDEX, end, -1);
                sDecodingThreadHandler.sendMessage(message);
            });
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot) {
                Log.e(TAG, "should swap here");
                final int start = ++i;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                await((BeforeWaitCallback) () -> {
                    Message message = sDecodingThreadHandler.obtainMessage(SWAP_SMALL);
                    message.arg1 = end;
                    sDecodingThreadHandler.sendMessage(message);
                });
                await((BeforeWaitCallback) () -> {
                    Message message = sDecodingThreadHandler.obtainMessage(SWAP);
                    message.arg1 = start;
                    message.arg2 = end;
                    sDecodingThreadHandler.sendMessage(message);
                });
            } else {
                Log.e(TAG, "should not swap");
                await((BeforeWaitCallback) () -> {
                    Message message = sDecodingThreadHandler.obtainMessage(SWAP_BIG);
                    message.arg1 = end;
                    sDecodingThreadHandler.sendMessage(message);
                });
                await((BeforeWaitCallback) () -> {
                    Message message = sDecodingThreadHandler.obtainMessage(BOUNCE);
                    message.arg1 = end;
                    sDecodingThreadHandler.sendMessage(message);
                });
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        final int start = i + 1;
        final int end = high;
        arr[i + 1] = arr[high];
        arr[high] = temp;
        await((BeforeWaitCallback)() -> {
            Message message = sDecodingThreadHandler.obtainMessage(ITERATION_COMPLETE);
            message.arg1 = start;
            message.arg2 = end;
            sDecodingThreadHandler.sendMessage(message);
        });
        await((BeforeWaitCallback)() -> {
            Message message = sDecodingThreadHandler.obtainMessage(PARTITION_COMPLETE);
            message.arg1 = start;
            message.arg2 = end;
            sDecodingThreadHandler.sendMessage(message);
        });

        await((BeforeWaitCallback)() -> {
            Message message = sDecodingThreadHandler.obtainMessage(RESET_POSITION);
            message.arg1 = high;
            message.arg2 = low;
            sDecodingThreadHandler.sendMessage(message);
        });

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(int arr[], int low, int high) {
        Log.e(TAG, "sort start: " + Arrays.toString(sData));
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
        Log.e(TAG, "sort completed: " + Arrays.toString(sData));
    }
}
package com.danny_jiang.algorithm.quick_sort;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    private static final int PIVOT = 1;
    private static final int COMPLETE = 2;
    private static final int SWAP = 3;

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

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("font/hjd.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = new Color(1, 0, 0, 1);
        stepDescription = new Label("", style);
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(30, stage.getHeight() / 2 - 50);
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
            case PIVOT:
                Gdx.app.postRunnable(() -> {
                    AlgorithmBall ball = actorList.get(first);
                    ball.activeStatus();
                });
                break;
            case SWAP:
                Gdx.app.postRunnable(() -> switchChild(first, second, false));
                break;
            case COMPLETE:
                Gdx.app.postRunnable(() -> {
                    switchChild(first, second, true);
                });
                break;
        }
    }

    @Override
    protected void algorithm() {
        await();
        sort(sData, 0, sData.length - 1);
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
            Message message = sDecodingThreadHandler.obtainMessage(PIVOT);
            message.arg1 = high;
            sDecodingThreadHandler.sendMessage(message);
        });
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot) {
                Log.e(TAG, "should swap here");
                final int start = ++i;
                final int end = j;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                await((BeforeWaitCallback) () -> {
                    Message message = sDecodingThreadHandler.obtainMessage(SWAP);
                    message.arg1 = start;
                    message.arg2 = end;
                    sDecodingThreadHandler.sendMessage(message);
                });
            } else {
                Log.e(TAG, "should not swap");
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        final int start = i + 1;
        final int end = high;
        arr[i + 1] = arr[high];
        arr[high] = temp;
        await((BeforeWaitCallback)() -> {
            Message message = sDecodingThreadHandler.obtainMessage(COMPLETE);
            message.arg1 = start;
            message.arg2 = end;
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
        RunnableAction run = Actions.run(() -> actorList.get(first).deadStatus());

        SequenceAction sequence = Actions.sequence(switchActors);
        if (complete) {
            sequence.addAction(run);
        }
        stage.addAction(sequence);
    }

}
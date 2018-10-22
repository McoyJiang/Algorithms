package com.danny_jiang.algorithm.insert_sort;

import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.StringBuilder;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.utils.AnimationUtils;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertSortAdapter extends AlgorithmAdapter{
    private static final String TAG = InsertSortAdapter.class.getSimpleName();

    private static final int TAKE_OUT = 1;
    private static final int SWAP = 2;
    private static final int SWAP_FINISH = 3;
    private static final int COMPLETED = 4;

    /*
     * List to storage AlgorithmBall
     */
    private List<AlgorithmBall> ballList = new ArrayList<>();

    // algorithm description for each step
    private Label stepDescription;
    /*
     * Data use to display Insert Sort Algorithm
     */
    private int[] sData = new int[]{45, 17, 23, 5, 76, 10, 4};

    @Override
    public void create() {
        super.create();
        takeOutBall(0);
    }

    @Override
    protected void inflateStage() {
        for (int i = 0; i < sData.length; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + sData[i]);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.setPosition(i * 150 + 50, stage.getHeight() - 200);
            ballList.add(algorithmBall);
            stage.addActor(algorithmBall);
        }

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(
                "insertion_sort/insertion_sort.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = new Color(1, 0, 0, 1);
        stepDescription = new Label("", style);
        stepDescription.setText("有序数组: [45]\n" +
                "无序数组: [17, 23, 5, 76, 10, 4]");
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
        final int index = msg.arg1;
        switch (msg.what) {
            case TAKE_OUT:
                takeOutBall(index);
                break;
            case SWAP:
                swapBall(index, index + 1);
                break;
            case SWAP_FINISH:
                String string = (String) msg.obj;
                String[] strings = string.split(":");
                //String dataString = Arrays.toString(sData);
                Gdx.app.postRunnable(() -> {
                    stepDescription.setText("有序数组: " + strings[0] + "\n" +
                            "无序数组: " + strings[1]);
                });
                break;
            case COMPLETED:
                stepDescription.setText("Insertion Sort completed!");
                break;
        }
    }

    private void takeOutBall(int ballIndex) {
        AlgorithmBall algorithmBall = ballList.get(ballIndex);
        MoveByAction upMove = Actions.moveBy(0, -500);
        upMove.setDuration(0.5f);
        algorithmBall.addAction(upMove);
    }

    private void swapBall(final int first, final int second) {
        final AlgorithmBall actorFirst = ballList.get(first);
        final AlgorithmBall actorSecond = ballList.get(second);

        Action switchActors = AnimationUtils.curveSwitchActors(actorFirst, actorSecond, () -> {
            actorFirst.setIndex(second);
            actorSecond.setIndex(first);
            ballList.remove(first);
            ballList.add(first, actorSecond);
            ballList.remove(second);
            ballList.add(second, actorFirst);
        });
        stage.addAction(Actions.sequence(switchActors));
    }

    private volatile int currentIndexJ = 0;
    private volatile int currentIndexI = 0;

    @Override
    public void algorithm() {
        await();
        for (int j = 1; j < sData.length; j++) {
            currentIndexJ = j;
            await((BeforeWaitCallback)() -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(TAKE_OUT, currentIndexJ, -1)));
            int key = sData[j];
            int i = j - 1;
            while ((i > -1) && (sData[i] > key)) {
                currentIndexI = i;
                sData[i + 1] = sData[i];
                await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                        sDecodingThreadHandler.obtainMessage(SWAP, currentIndexI, -1)
                ));
                i--;
            }
            sData[i + 1] = key;
            final StringBuilder stringBuilder = new StringBuilder("[");
            for (int a = 0; a <= currentIndexJ; a++) {
                if (a == currentIndexJ) {
                    stringBuilder.append(sData[a] + "]");
                } else {
                    stringBuilder.append(sData[a] + ", ");
                }
            }
            stringBuilder.append(":[");
            for (int a = currentIndexJ + 1; a < sData.length; a++) {
                if (a == sData.length - 1) {
                    stringBuilder.append(sData[a] + "]");
                } else {
                    stringBuilder.append(sData[a] + ", ");
                }
            }
            await((BeforeWaitCallback) () -> {
                Message message = sDecodingThreadHandler.obtainMessage(SWAP_FINISH);
                message.obj = stringBuilder.toString();
                sDecodingThreadHandler.sendMessage(message);
            });
        }
        System.out.println("sort completed: array is " + Arrays.toString(sData));
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(COMPLETED, currentIndexI, -1)
        ));
    }
}

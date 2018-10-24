package com.danny_jiang.algorithm.insert_sort;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
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
    private static final int COMPARE_FINISH = 5;
    private static final int BACK_TO_ORIGINAL = 6;

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
        //takeOutBall(0);
        ballList.get(0).deadStatus();
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
        final int second = msg.arg2;
        Object obj = msg.obj;
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
            case COMPARE_FINISH:
                Gdx.app.postRunnable(() -> {
                    AlgorithmBall ball = (AlgorithmBall) obj;
                    final AlgorithmBall firstBall = ballList.get(index);
                    //final AlgorithmBall secondBall = ballList.get(second);
                    float x = firstBall.getX() - firstBall.getWidth() - 50;
                    float y = firstBall.getY();
                    MoveToAction moveToAction = Actions.moveTo(x, y);
                    moveToAction.setDuration(0.5f);
                    RunnableAction run = Actions.run(() -> {
                        Log.e("AAA", "before:" + ballList.toString());
                        ballList.remove(index);
                        ballList.add(index, ball);
                        ball.deadStatus();
                        Log.e("AAA", "after:" + ballList.toString());
                    });
                    RunnableAction next = Actions.run(() -> nextStep());
                    ball.addAction(Actions.sequence(moveToAction, run, next));
                });
                break;
            case BACK_TO_ORIGINAL:
                Gdx.app.postRunnable(() -> {
                    final AlgorithmBall firstBall = ballList.get(index);
                    MoveByAction upMove = Actions.moveBy(0, 200);
                    upMove.setDuration(0.5f);
                    RunnableAction next = Actions.run(() -> {
                        firstBall.deadStatus();
                        nextStep();
                    });
                    firstBall.addAction(Actions.sequence(upMove, next));
                });
                break;
            case COMPLETED:
                stepDescription.setText("Insertion Sort completed!");
                break;
        }
    }

    private void takeOutBall(int ballIndex) {
        AlgorithmBall algorithmBall = ballList.get(ballIndex);
        MoveByAction upMove = Actions.moveBy(0, -200);
        upMove.setDuration(0.5f);
        algorithmBall.addAction(upMove);
    }

    private void swapBall(final int first, final int second) {
        AlgorithmBall firstBall = ballList.get(first);
        float x = firstBall.getX() + firstBall.getWidth() + 50;
        MoveToAction moveToAction = Actions.moveTo(x, firstBall.getY());
        moveToAction.setDuration(0.3f);
        RunnableAction removeBallAction = Actions.run(() -> {
            AlgorithmBall ball = ballList.remove(first);
            ballList.add(second, ball);
        });
        RunnableAction next = Actions.run(() -> nextStep());
        SequenceAction sequence = Actions.sequence(moveToAction, next, removeBallAction);
        firstBall.addAction(sequence);
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
            final AlgorithmBall tempBall = ballList.get(j);
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
            if ((i + 1) == currentIndexJ) {
                await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                        sDecodingThreadHandler.obtainMessage(BACK_TO_ORIGINAL, currentIndexJ, -1)
                ));
            } else {
                final Message message = sDecodingThreadHandler.obtainMessage(COMPARE_FINISH, tempBall);
                message.arg1 = currentIndexI;
                await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(message));
            }
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

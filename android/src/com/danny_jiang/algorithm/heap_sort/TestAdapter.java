package com.danny_jiang.algorithm.heap_sort;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.utils.AnimationUtils;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestAdapter extends ApplicationAdapter {

    private static final String TAG = TestAdapter.class.getSimpleName();

    private final Object sLock = new Object();
    private HandlerThread sDecodingThread;
    private Handler sDecodingThreadHandler;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private int[] array = new int[]{10, 4, 24, 43, 1};
    private volatile int currentIndex = 0;

    private HorizontalGroup bubbleSortGroup;
    private List<AlgorithmBall> actorList;
    protected Stage stage;
    private Image upArrow;

    private Runnable animationRunnable = () -> {
        try {
            Log.e(TAG, "animationRunnable run on "
                    + Thread.currentThread().getId() + " currentIndex is " + currentIndex);
            if (array[currentIndex] > array[currentIndex + 1]) {
                switchChild(currentIndex, currentIndex + 1);
            } else {
                moveArrow(currentIndex);
                Thread.sleep(500);
                lock.lock();
                condition.signal();
                lock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private void moveArrow(final int index) {
        AlgorithmBall algorithmBall = actorList.get(index);
        float position = algorithmBall.getX();
        MoveToAction moveToAction = Actions.moveTo(position, upArrow.getY());
        moveToAction.setDuration(0.3f);
        SequenceAction sequence = Actions.sequence(Actions.delay(0.3f), moveToAction);
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
        RunnableAction run = Actions.run(() -> moveArrow(second));
        RunnableAction signal = Actions.run(() -> {
            lock.lock();
            condition.signal();
            lock.unlock();
        });
        stage.addAction(Actions.sequence(switchActors, run, signal));
    }

    private Runnable algorithmRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "AlgorithmRunnable run on " + Thread.currentThread().getId());
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length - 1; j++) {
                    lock.lock();
                    currentIndex = j;
                    sDecodingThreadHandler.post(animationRunnable);
                    try {
                        condition.await();
                        if (array[j] > array[j + 1]) {
                            swapArray(j);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
            Log.e(TAG, "after sort array is " + Arrays.toString(array));
        }
    };

    @Override
    public void create() {
        super.create();
        stage = new Stage();
        Image bg = new Image(new Texture(Gdx.files.internal("bg/visualizer_bg.png")));
        bg.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);
        Gdx.input.setInputProcessor(stage);

        inflateStage();

        synchronized (sLock) {
            if (sDecodingThread != null) return;

            sDecodingThread = new HandlerThread("FrameSequence decoding thread",
                    Process.THREAD_PRIORITY_BACKGROUND);
            sDecodingThread.start();
            sDecodingThreadHandler = new Handler(sDecodingThread.getLooper());
            new Thread(algorithmRunnable).start();
        }
    }

    protected void inflateStage() {
        actorList = new ArrayList<>();

        bubbleSortGroup = new HorizontalGroup();
        bubbleSortGroup.align(Align.center);
        bubbleSortGroup.space(30);
        bubbleSortGroup.setSize(stage.getWidth(), stage.getHeight() / 4);
        bubbleSortGroup.setPosition(0, stage.getHeight() * 3 / 4);
        stage.addActor(bubbleSortGroup);

        for (int i = 0; i < array.length; i++) {
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
    }

    private void swapArray(int j) {
        int temp = array[j];
        array[j] = array[j + 1];
        array[j + 1] = temp;
    }

    @Override
    public void render() {
        super.render();
        super.render();
        // 黄色清屏
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}

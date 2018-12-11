package com.danny_jiang.algorithm.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AlgorithmGroup extends Group {

    protected HandlerThread sDecodingThread;
    protected Handler sDecodingThreadHandler;
    protected Runnable algorithmRunnable = this::algorithm;
    protected ReentrantLock sReenterLock = new ReentrantLock(true);
    protected Condition sCondition = sReenterLock.newCondition();

    // Common widget
    protected AlgorithmButton nextStepBtn;

    protected Stage stage;
    protected Label stepDescription;
    protected final Image visualizerBg;

    public AlgorithmGroup(Stage stage, Image visualizerBg) {
        this.stage = stage;
        this.visualizerBg = visualizerBg;
        setTouchable(Touchable.childrenOnly);
        setSize(stage.getWidth(), stage.getHeight());
        startThread();
    }

    public void init() {
        nextStepBtn = new AlgorithmButton(">");
        nextStepBtn.setBackgroundColor(Color.valueOf("#36802d"));
        nextStepBtn.setSize(200, 100);
        nextStepBtn.setPosition(stage.getWidth() / 2, 10);
        addActor(nextStepBtn);
        nextStepBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                signal();
            }
        });
    }

    protected void enableNextBtn() {
        nextStepBtn.setBackgroundColor(Color.valueOf("#36802d"));
        nextStepBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                signal();
            }
        });
    }

    public void disableNextBtn() {
        nextStepBtn.setBackgroundColor(Color.valueOf("#b9babd"));
        nextStepBtn.clearListeners();
    }

    protected void startThread() {
        sDecodingThread = new HandlerThread("FrameSequence decoding thread",
                Process.THREAD_PRIORITY_BACKGROUND);
        sDecodingThread.start();
        sDecodingThreadHandler = new Handler(sDecodingThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                animation(msg);
            }
        };
        new Thread(algorithmRunnable).start();
    }

    protected void await() {
        await(null, null);
    }

    protected void await(AlgorithmAdapter.BeforeWaitCallback beforeWaitCallback) {
        await(beforeWaitCallback, null);
    }

    protected void await(AlgorithmAdapter.BeforeWaitCallback beforeWaitCallback, AlgorithmAdapter.WaitFinishCallback waitFinishCallback) {
        try {
            sReenterLock.lock();
            if (beforeWaitCallback != null)
                beforeWaitCallback.beforeWait();
            sCondition.await();
            if (waitFinishCallback != null)
                waitFinishCallback.waitInterrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sReenterLock.unlock();
        }
    }

    public void signal() {
        sReenterLock.lock();
        sCondition.signal();
        sReenterLock.unlock();
    }

    protected abstract void animation(Message msg);

    protected abstract void algorithm();
}

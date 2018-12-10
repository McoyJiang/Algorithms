package com.danny_jiang.algorithm.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.views.BaseGdxActor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AlgorithmAdapter extends ApplicationAdapter {

    /*
     * async component
     */
    private HandlerThread sDecodingThread;
    protected Handler sDecodingThreadHandler;
    private ReentrantLock sReenterLock = new ReentrantLock(true);
    private Condition sCondition = sReenterLock.newCondition();

    public interface BeforeWaitCallback {
        void beforeWait();
    }

    public interface WaitFinishCallback {
        void waitInterrupt();
    }

    private Runnable algorithmRunnable = this::algorithm;

    protected Stage stage;
    protected Image visualizerBg;
    protected AlgorithmButton next;
    private ClickListener nextClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            //disableNextButton();
            nextStep();
        }
    };

    @Override
    public void create() {
        super.create();
        initAsyncComponent();
        initData();

        stage = new Stage();
        Image bg = new Image(new Texture(Gdx.files.internal("bg/background.png")));
        bg.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);
        Gdx.input.setInputProcessor(stage);

        visualizerBg = new Image(new Texture(Gdx.files.internal("bg/visual_bg.png")));
        visualizerBg.setSize(stage.getWidth() - 30, stage.getHeight() / 2 - 20);
        visualizerBg.setPosition(15, stage.getHeight() / 2);
        stage.addActor(visualizerBg);

        next = new AlgorithmButton("next");
        enableNextButton();
        next.setSize(180, 100);
        next.setPosition(stage.getWidth() - 250, 10);
        stage.addActor(next);
        inflateStage();
    }

    private void initAsyncComponent() {
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

    protected void await(BeforeWaitCallback beforeWaitCallback) {
        await(beforeWaitCallback, null);
    }

    protected void await(BeforeWaitCallback beforeWaitCallback, WaitFinishCallback waitFinishCallback) {
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

    public void nextStep() {
        signal();
    }

    public void enableNextButton() {
//        next.setRegion(new TextureRegion(new Texture(
//                Gdx.files.internal("next_step.png")
//        )));
        next.addListener(nextClickListener);
    }

    public void disableNextButton() {
//        next.setRegion(new TextureRegion(new Texture(
//                Gdx.files.internal("next_step_disable.png")
//        )));
        next.removeListener(nextClickListener);
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

    @Override
    public void dispose() {
        super.dispose();
        if (sDecodingThread != null) {
            sDecodingThread.quit();
        }
    }

    public abstract void animation(Message msg);

    protected abstract void algorithm();

    protected abstract void inflateStage();

    protected abstract void initData();
}
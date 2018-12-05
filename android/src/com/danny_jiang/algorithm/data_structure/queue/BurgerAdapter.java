package com.danny_jiang.algorithm.data_structure.queue;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.SnapshotArray;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class BurgerAdapter extends AlgorithmAdapter {

    private static final int START_PRODUCT = 1;
    private static final int START_CONSUME = 2;
    private static final int PRODUCING = 3;
    private static final int CONSUMING = 4;

    private HorizontalGroup verticalGroup;

    @Override
    protected void inflateStage() {
        verticalGroup = new HorizontalGroup();
        verticalGroup.setDebug(true);
        verticalGroup.center();
        verticalGroup.space(-120);
        verticalGroup.setSize(100, 100);
        verticalGroup.setPosition(stage.getWidth() / 2 - verticalGroup.getWidth() / 2,
                visualizerBg.getY() + (visualizerBg.getHeight() - verticalGroup.getHeight()) / 2);
        stage.addActor(verticalGroup);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case START_PRODUCT:
                producingRunning = true;
                break;
            case START_CONSUME:
                consumingRunning = true;
                break;
            case PRODUCING:
                produce();
                break;
            case CONSUMING:
                consume();
                break;
        }
    }

    private void consume() {
        Log.e("BurgerAdapter", "gdx consume：");
        Gdx.app.postRunnable(() -> {
            SnapshotArray<Actor> children = verticalGroup.getChildren();
            Actor burger = children.get(0);

            MoveToAction moveToAction = Actions.moveTo(stage.getWidth() / 2
                    + burger.getWidth(), burger.getY());
            moveToAction.setDuration(0.2f);
            moveToAction.setTarget(burger);
            stage.addAction(Actions.sequence(moveToAction,
                    Actions.run(() -> {
                        verticalGroup.removeActor(burger);
                    })));
        });
    }

    private void produce() {
        Log.e("BurgerAdapter", "gdx 生产：");
        Gdx.app.postRunnable(() -> {
            Image burger = new Image(new TextureRegion(
                    new Texture("data_structure/queue/cheese-burger.png")));
            burger.setSize(100, 100);
            burger.setPosition(0, visualizerBg.getY()
                    + visualizerBg.getHeight() / 2 - burger.getHeight() / 2);
            stage.addActor(burger);

            MoveToAction moveToAction = Actions.moveTo(stage.getWidth() / 2
                    - burger.getWidth() / 2, burger.getY());
            moveToAction.setDuration(0.2f);
            moveToAction.setTarget(burger);
            stage.addAction(Actions.sequence(moveToAction,
                    Actions.run(() -> {
                        burger.setFillParent(true);
                        verticalGroup.addActor(burger);
                    })));
        });
    }

    @Override
    protected void algorithm() {
        executorService.submit(new Producer("producer1"));
        executorService.submit(new Consumer("consumer1"));
        executorService.submit(new Consumer("consumer2"));
        await();

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendEmptyMessage(START_PRODUCT));
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendEmptyMessage(START_CONSUME));
    }

    private static LinkedBlockingDeque<Integer> blockingLinkedQueue =
            new LinkedBlockingDeque<>(5);
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    private boolean producingRunning = false;
    private boolean consumingRunning = false;

    class Producer implements Runnable {
        private String name;

        public Producer(String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                try {
                    if (producingRunning) {
                        Log.e("BurgerAdapter", "生产：" + name);
                        blockingLinkedQueue.put(0);
                        sDecodingThreadHandler.sendMessage(
                                sDecodingThreadHandler.obtainMessage(PRODUCING, 0, 0));
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e1) {
                    Log.e("BurgerAdapter", "error: " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable {
        private String name;
        public Consumer(String name) {
            this.name = name;
        }
        public void run() {
            while (true) {
                try {
                    if (consumingRunning) {
                        Log.e("BurgerAdapter", "consuming：" + name);
                        blockingLinkedQueue.take();
                        sDecodingThreadHandler.sendMessage(
                                sDecodingThreadHandler.obtainMessage(CONSUMING, 0, 0));
                        Thread.sleep(300);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

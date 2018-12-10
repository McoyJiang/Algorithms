package com.danny_jiang.algorithm.data_structure.queue;

import android.graphics.Point;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;
import com.danny_jiang.algorithm.data_structure.queue.group.BlockingQueueGroup;
import com.danny_jiang.algorithm.data_structure.queue.group.QueueIntroGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BurgerAdapter extends AlgorithmAdapter {

    public static final int DEMO_IN = 1;
    public static final int DEMO_OUT = 2;
    public static final int SHOW_QUEUE = 3;
    public static final int ENQUEUE = 4;
    public static final int DEQUEUE_VISIBLE = 5;
    public static final int DEQUEUE = 6;
    public static final int PRODUCING = 7;
    public static final int CONSUMING = 8;
    public static final int START_BLOCKING_QUEUE = 9;
    public static final int NEXT_PAGE = 10;

    private Label stepDescription;

    private BlockingQueueGroup blockingQueueGroup;
    private QueueIntroGroup queueIntroGroup;

    private AssetManager assetManager;
    ScrollPane scrollPane;
    @Override
    protected void inflateStage() {
        next.setText("Next");
        disableNextButton();
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextPage();
            }
        });
        AlgorithmButton prev = new AlgorithmButton("Prev");
        prev.setSize(180, 100);
        prev.setPosition(30, 10);
        stage.addActor(prev);
        prev.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prevPage();
            }
        });

        BitmapFont desFont = new BitmapFont(Gdx.files.internal(
                "data_structure/queue/queue.fnt"));
        Label.LabelStyle desStyle = new Label.LabelStyle();
        desStyle.font = desFont;
        desStyle.fontColor = Color.valueOf("#4A4A4A");
        stepDescription = new Label("", desStyle);
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(30, stage.getHeight() / 3 - 100);
        stage.addActor(stepDescription);

        assetManager = new AssetManager();
        assetManager.load("font/uiskin.json", Skin.class);
        assetManager.finishLoading();
        Skin skin = assetManager.get("font/uiskin.json", Skin.class);
        final Table table = new Table();

        scrollPane = new ScrollPane(table, skin);
        scrollPane.setSize(stage.getWidth(), stage.getHeight());
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setCancelTouchFocus(false);
        scrollPane.setTouchable(Touchable.childrenOnly);
        scrollPane.setFlickScroll(false);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setFlingTime(0);
        ScrollPane.ScrollPaneStyle scrollPaneStyle =
                new ScrollPane.ScrollPaneStyle(
                        null, null,
                        null, null, null);
        scrollPane.setStyle(scrollPaneStyle);

        blockingQueueGroup = new BlockingQueueGroup(stage, stepDescription, visualizerBg);
//        stage.addActor(blockingQueueGroup);
//        blockingQueueGroup.setVisible(false);

        queueIntroGroup = new QueueIntroGroup(this, stage, stepDescription,  visualizerBg);
//        stage.addActor(queueIntroGroup);

        table.add(queueIntroGroup)
                .width(stage.getWidth()).height(stage.getHeight());

        table.add(blockingQueueGroup)
                .width(stage.getWidth()).height(stage.getHeight());

        stage.addActor(scrollPane);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void animation(Message msg) {
        switch (msg.what) {
            case NEXT_PAGE:
                nextPage();
                break;
            case DEMO_IN:
                queueIntroGroup.demoIn();
                break;
            case DEMO_OUT:
                queueIntroGroup.demoOut();
                break;
            case SHOW_QUEUE:
                queueIntroGroup.showQueue();
                break;
            case ENQUEUE:
                queueIntroGroup.enqueue(msg.arg1);
                break;
            case DEQUEUE_VISIBLE:
                queueIntroGroup.showDequeue();
                break;
            case DEQUEUE:
                queueIntroGroup.dequeue(msg.arg1);
                break;
            case START_BLOCKING_QUEUE:
                showBlockingQueue();
                break;
            case PRODUCING:
                blockingQueueGroup.produce(this);
                break;
            case CONSUMING:
                blockingQueueGroup.consume(msg.arg1);
                break;
        }

    }

    private int currentPageIndex = 0;
    private void nextPage() {
        Gdx.app.postRunnable(() -> {
            scrollPane.setScrollX(stage.getWidth() * ++currentPageIndex);
        });
    }
    private void prevPage() {
        Gdx.app.postRunnable(() -> {
            scrollPane.setScrollX(stage.getWidth() * --currentPageIndex);
        });
    }

    private void showBlockingQueue() {
        queueIntroGroup.hide();
        blockingQueueGroup.show();
    }

    @Override
    protected void algorithm() {
        await();

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(NEXT_PAGE, 0, 0)));

//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(DEMO_IN, 0, 0)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(DEMO_OUT, 0, 0)));

//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(SHOW_QUEUE, 0, 0)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(ENQUEUE, 0, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(ENQUEUE, 1, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(ENQUEUE, 2, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(DEQUEUE_VISIBLE, 2, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(DEQUEUE, 0, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(DEQUEUE, 1, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(DEQUEUE, 2, -1)));
//
//        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
//                sDecodingThreadHandler.obtainMessage(START_BLOCKING_QUEUE, 0, -1)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 0, -1)));
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 1, -1)));
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 2, -1)));
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 3, -1)));
        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 4, -1)));

        await();

        for (int i = 0; i < 30; i++) {
            await((BeforeWaitCallback) () -> {
                try {
                    sDecodingThreadHandler.sendMessage(
                            sDecodingThreadHandler.obtainMessage(CONSUMING,
                                    blockingQueueGroup.getBurgerProcessingQueue().getChildren().size - 1, -1));

                    Thread.sleep(500);

                    sDecodingThreadHandler.sendMessage(
                            sDecodingThreadHandler.obtainMessage(PRODUCING, 0, -1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
        }
    }
}

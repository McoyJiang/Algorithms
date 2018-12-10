package com.danny_jiang.algorithm.data_structure.queue;

import android.graphics.Point;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BurgerAdapter extends AlgorithmAdapter {

    private static final int DEMO_IN = 1;
    private static final int DEMO_OUT = 2;
    private static final int SHOW_QUEUE = 3;
    private static final int ENQUEUE = 4;
    private static final int DEQUEUE_VISIBLE = 5;
    private static final int DEQUEUE = 6;
    private static final int PRODUCING = 7;
    private static final int CONSUMING = 8;
    private static final int START_BLOCKING_QUEUE = 9;

    private Random random = new Random();
    private String[] burger_list = new String[]{
            "data_structure/queue/burger1.png",
            "data_structure/queue/burger2.png",
            "data_structure/queue/burger3.png",
            "data_structure/queue/burger4.png",
            "data_structure/queue/burger5.png"
    };
    private QueueHorizontalGroup blockingQueueGroup;
    private AlgorithmButton produceQueueGroup;
    private AlgorithmButton consumeQueueGroup;

    private Label stepDescription;

    private QueueIntroGroup queueIntroGroup;
    @Override
    protected void inflateStage() {
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

        blockingQueueGroup = new QueueHorizontalGroup();
        blockingQueueGroup.setVisible(false);
        blockingQueueGroup.setBackgroundColor(Color.valueOf("#B0B2AE"));
        blockingQueueGroup.space(30);
        blockingQueueGroup.center();
        blockingQueueGroup.setSize(visualizerBg.getWidth() - 100, visualizerBg.getHeight() / 2 - 20);
        blockingQueueGroup.setPosition(visualizerBg.getX() + 50,
                visualizerBg.getY() + blockingQueueGroup.getHeight() - 20);
        blockingQueueGroup.setOriginX(blockingQueueGroup.getX() + blockingQueueGroup.getWidth() / 2);
        blockingQueueGroup.setOriginY(blockingQueueGroup.getY() + blockingQueueGroup.getHeight() / 2);

        produceQueueGroup = new AlgorithmButton("Producer");
        produceQueueGroup.setBackgroundColor(Color.valueOf("#66cdaa"));
        produceQueueGroup.setSize(360, 260);
        produceQueueGroup.setPosition(blockingQueueGroup.getX(), visualizerBg.getY() + 15);
        produceQueueGroup.setOrigin(produceQueueGroup.getX() + produceQueueGroup.getWidth() / 2,
                produceQueueGroup.getY() + produceQueueGroup.getHeight() / 2);
        stage.addActor(produceQueueGroup);
        produceQueueGroup.setVisible(false);

        consumeQueueGroup = new AlgorithmButton("Consumer");
        consumeQueueGroup.setBackgroundColor(Color.valueOf("#f97e77"));
        consumeQueueGroup.setSize(360, 260);
        consumeQueueGroup.setPosition(visualizerBg.getX() + visualizerBg.getWidth()
                - consumeQueueGroup.getWidth() - 50, visualizerBg.getY() + 15);
        consumeQueueGroup.setOrigin(consumeQueueGroup.getX() + consumeQueueGroup.getWidth() / 2,
                consumeQueueGroup.getY() + consumeQueueGroup.getHeight() / 2);
        stage.addActor(consumeQueueGroup);
        consumeQueueGroup.setVisible(false);

        stage.addActor(blockingQueueGroup);

        queueIntroGroup = new QueueIntroGroup(stage, stepDescription,  visualizerBg);
        queueIntroGroup.setTouchable(Touchable.childrenOnly);
        queueIntroGroup.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(queueIntroGroup);
        queueIntroGroup.init();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
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
                produce();
                break;
            case CONSUMING:
                consume(msg.arg1);
                break;
        }

    }

    private void showBlockingQueue() {
        queueIntroGroup.hide();
        blockingQueueGroup.setVisible(true);
        produceQueueGroup.setVisible(true);
        consumeQueueGroup.setVisible(true);
    }

    private void consume(final int i) {
        Gdx.app.postRunnable(() -> {
            Image button = imageList.remove(i);
            MoveByAction firstMove = Actions.moveBy(
                    consumeQueueGroup.getOriginX() - button.getX() - button.getWidth() / 2,
                    consumeQueueGroup.getOriginY() - blockingQueueGroup.getOriginY());
            firstMove.setDuration(0.5f);
            MoveByAction secondMove = Actions.moveBy(stage.getWidth(), 0);
            secondMove.setDuration(0.5f);

            RunnableAction removeAction = Actions.run(() -> button.remove());

            SequenceAction sequence = Actions.sequence(firstMove,
                    Actions.delay(0.1f), secondMove, removeAction);
            button.addAction(sequence);
        });
    }

    private List<Image> imageList = new ArrayList<>();

    private void produce() {
        Gdx.app.postRunnable(() -> {
            Image button = new Image(new Texture(burger_list[random.nextInt(5)])) {
                @Override
                public float getPrefWidth() {
                    return 120;
                }

                @Override
                public float getPrefHeight() {
                    return 120;
                }
            };
            button.setZIndex(1000);
            imageList.add(0, button);
            button.setSize(120, 120);
            button.setPosition(produceQueueGroup.getOriginX() - button.getWidth() / 2,
                    produceQueueGroup.getOriginY() - button.getHeight() / 2);
            stage.addActor(button);

            MoveToAction firstMove = Actions.moveTo(blockingQueueGroup.getX(),
                    blockingQueueGroup.getY() - button.getHeight() / 2);
            firstMove.setDuration(0.5f);

            Point point = getGroupPosition();
            MoveToAction secondMove = Actions.moveTo(point.x, point.y);
            secondMove.setDuration(0.5f);

            SequenceAction sequence = Actions.sequence(firstMove, Actions.delay(0.1f),
                    secondMove, Actions.run(() -> blockingQueueGroup.addActorAt(0, button)),
                    Actions.run(() -> signal()));

            button.addAction(sequence);
        });
    }

    private Point getGroupPosition() {
        Point position = new Point();
        if (blockingQueueGroup.getChildren().size == 0) {
            position.x = (int) blockingQueueGroup.getOriginX() - 60;
            position.y = (int) blockingQueueGroup.getOriginY() - 60;
        } else {
            Image image = (Image) blockingQueueGroup.getChildren().get(0);
            position.x = (int) (image.getX() - image.getWidth() / 2);
            position.y = (int) (blockingQueueGroup.getY() + image.getY());
        }
        return position;
    }

    @Override
    protected void algorithm() {
        await();

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEMO_IN, 0, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEMO_OUT, 0, 0)));

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(SHOW_QUEUE, 0, 0)));

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ENQUEUE, 0, -1));
        });

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ENQUEUE, 1, -1));
        });

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(ENQUEUE, 2, -1));
        });

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEQUEUE_VISIBLE, 2, -1));
        });

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEQUEUE, 0, -1));
        });

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEQUEUE, 1, -1));
        });

        await((BeforeWaitCallback) () -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEQUEUE, 2, -1));
        });

        await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(START_BLOCKING_QUEUE, 0, -1)));

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
                                    blockingQueueGroup.getChildren().size - 1, -1));

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

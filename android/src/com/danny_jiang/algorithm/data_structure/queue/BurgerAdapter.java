package com.danny_jiang.algorithm.data_structure.queue;

import android.graphics.Point;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.utils.ShapeRendererUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BurgerAdapter extends AlgorithmAdapter {

    private static final int PRODUCING = 3;
    private static final int CONSUMING = 4;

    private Random random = new Random();
    private String[] burger_list = new String[]{
            "data_structure/queue/burger1.png",
            "data_structure/queue/burger2.png",
            "data_structure/queue/burger3.png",
            "data_structure/queue/burger4.png",
            "data_structure/queue/burger5.png"
    };
    private QueueHorizontalGroup blockingQueueGroup;
    private QueueHorizontalGroup produceQueueGroup;
    private QueueHorizontalGroup consumeQueueGroup;

    @Override
    protected void inflateStage() {
        blockingQueueGroup = new QueueHorizontalGroup();
        blockingQueueGroup.setBackgroundColor(Color.valueOf("#B0B2AE"));
        blockingQueueGroup.space(30);
        blockingQueueGroup.center();
        blockingQueueGroup.setSize(visualizerBg.getWidth() - 100, visualizerBg.getHeight() / 2 - 20);
        blockingQueueGroup.setPosition(visualizerBg.getX() + 50,
                visualizerBg.getY() + blockingQueueGroup.getHeight() - 20);
        blockingQueueGroup.setOriginX(blockingQueueGroup.getX() + blockingQueueGroup.getWidth() / 2);
        blockingQueueGroup.setOriginY(blockingQueueGroup.getY() + blockingQueueGroup.getHeight() / 2);
        stage.addActor(blockingQueueGroup);

        produceQueueGroup = new QueueHorizontalGroup();
        produceQueueGroup.setBackgroundColor(Color.BLUE);
        produceQueueGroup.setSize(360, 260);
        produceQueueGroup.setPosition(blockingQueueGroup.getX(), visualizerBg.getY() + 15);
        //stage.addActor(produceQueueGroup);

        consumeQueueGroup = new QueueHorizontalGroup();
        consumeQueueGroup.setBackgroundColor(Color.RED);
        consumeQueueGroup.setSize(360, 260);
        consumeQueueGroup.setPosition(visualizerBg.getX() + visualizerBg.getWidth()
                        - consumeQueueGroup.getWidth() - 50, visualizerBg.getY() + 15);
        //stage.addActor(consumeQueueGroup);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case PRODUCING:
                produce();
                break;
            case CONSUMING:
                consume(msg.arg1);
                break;
        }
    }

    private void consume(final int i) {
        Gdx.app.postRunnable(() -> {
            Image button = buttonList.remove(i);
            MoveByAction firstMove = Actions.moveBy(0,
                    -blockingQueueGroup.getHeight() / 2);
            firstMove.setDuration(0.5f);
            MoveByAction secondMove = Actions.moveBy(stage.getWidth(), 0);
            secondMove.setDuration(0.5f);

            RunnableAction removeAction = Actions.run(() -> button.remove());

            SequenceAction sequence = Actions.sequence(firstMove,
                    Actions.delay(0.1f), secondMove, removeAction);
            button.addAction(sequence);
        });
    }

    private List<Image> buttonList = new ArrayList<>();
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
            buttonList.add(0, button);
            button.setSize(120, 120);
            button.setPosition(-250, blockingQueueGroup.getY() - button.getHeight() - 10);
            stage.addActor(button);
            MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - button.getWidth() / 2,
                    button.getY());
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

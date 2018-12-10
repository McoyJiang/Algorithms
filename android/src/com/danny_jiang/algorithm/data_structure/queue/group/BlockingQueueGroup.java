package com.danny_jiang.algorithm.data_structure.queue.group;

import android.graphics.Point;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmButton;
import com.danny_jiang.algorithm.common.AlgorithmGroup;
import com.danny_jiang.algorithm.data_structure.queue.QueueHorizontalGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockingQueueGroup extends AlgorithmGroup {

    public static final int PRODUCING = 7;
    public static final int CONSUMING = 8;

    private Random random = new Random();
    private String[] burger_list = new String[]{
            "data_structure/queue/burger1.png",
            "data_structure/queue/burger2.png",
            "data_structure/queue/burger3.png",
            "data_structure/queue/burger4.png",
            "data_structure/queue/burger5.png"
    };

    private List<Image> burgerImageList = new ArrayList<>();

    private QueueHorizontalGroup burgerProcessingQueue;
    private AlgorithmButton produceQueueGroup;
    private AlgorithmButton consumeQueueGroup;

    public BlockingQueueGroup(Stage stage, Label stepDescription, Image visualizerBg) {
        super(stage, stepDescription, visualizerBg);
    }

    public void init() {
        super.init();
        burgerProcessingQueue = new QueueHorizontalGroup();
        burgerProcessingQueue.setBackgroundColor(Color.valueOf("#B0B2AE"));
        burgerProcessingQueue.space(30);
        burgerProcessingQueue.center();
        burgerProcessingQueue.setSize(visualizerBg.getWidth() - 100, visualizerBg.getHeight() / 2 - 20);
        burgerProcessingQueue.setPosition(visualizerBg.getX() + 50,
                visualizerBg.getY() + burgerProcessingQueue.getHeight() - 20);
        burgerProcessingQueue.setOriginX(burgerProcessingQueue.getX() + burgerProcessingQueue.getWidth() / 2);
        burgerProcessingQueue.setOriginY(burgerProcessingQueue.getY() + burgerProcessingQueue.getHeight() / 2);

        produceQueueGroup = new AlgorithmButton("Producer");
        produceQueueGroup.setBackgroundColor(Color.valueOf("#66cdaa"));
        produceQueueGroup.setSize(360, 260);
        produceQueueGroup.setPosition(burgerProcessingQueue.getX(), visualizerBg.getY() + 15);
        produceQueueGroup.setOrigin(produceQueueGroup.getX() + produceQueueGroup.getWidth() / 2,
                produceQueueGroup.getY() + produceQueueGroup.getHeight() / 2);
        addActor(produceQueueGroup);

        consumeQueueGroup = new AlgorithmButton("Consumer");
        consumeQueueGroup.setBackgroundColor(Color.valueOf("#f97e77"));
        consumeQueueGroup.setSize(360, 260);
        consumeQueueGroup.setPosition(visualizerBg.getX() + visualizerBg.getWidth()
                - consumeQueueGroup.getWidth() - 50, visualizerBg.getY() + 15);
        consumeQueueGroup.setOrigin(consumeQueueGroup.getX() + consumeQueueGroup.getWidth() / 2,
                consumeQueueGroup.getY() + consumeQueueGroup.getHeight() / 2);
        addActor(consumeQueueGroup);

        addActor(burgerProcessingQueue);
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

    @Override
    protected void algorithm() {
        await();

        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 0, -1)));
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 1, -1)));
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 2, -1)));
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 3, -1)));
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(PRODUCING, 4, -1)));

        await();

        for (int i = 0; i < 30; i++) {
            await(() -> {
                try {
                    sDecodingThreadHandler.sendMessage(
                            sDecodingThreadHandler.obtainMessage(CONSUMING,
                                    getBurgerProcessingQueue().getChildren().size - 1, -1));

                    Thread.sleep(500);

                    sDecodingThreadHandler.sendMessage(
                            sDecodingThreadHandler.obtainMessage(PRODUCING, 0, -1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
        }
    }

    public void consume(final int i) {
        Gdx.app.postRunnable(() -> {
            stepDescription.setText("当内部元素size小于5时\n" +
                    "处于等待状态的插入\n" +
                    "操作才会继续执行");
            Image button = burgerImageList.remove(i);
            MoveByAction firstMove = Actions.moveBy(
                    consumeQueueGroup.getOriginX() - button.getX() - button.getWidth() / 2,
                    consumeQueueGroup.getOriginY() - burgerProcessingQueue.getOriginY());
            firstMove.setDuration(0.5f);
            MoveByAction secondMove = Actions.moveBy(stage.getWidth(), 0);
            secondMove.setDuration(0.5f);

            RunnableAction removeAction = Actions.run(() -> button.remove());

            SequenceAction sequence = Actions.sequence(firstMove,
                    Actions.delay(0.1f), secondMove, removeAction);
            button.addAction(sequence);
        });
    }

    public void produce() {
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
            burgerImageList.add(0, button);
            button.setSize(120, 120);
            button.setPosition(produceQueueGroup.getOriginX() - button.getWidth() / 2,
                    produceQueueGroup.getOriginY() - button.getHeight() / 2);
            addActor(button);

            MoveToAction firstMove = Actions.moveTo(burgerProcessingQueue.getX(),
                    burgerProcessingQueue.getY() - button.getHeight() / 2);
            firstMove.setDuration(0.5f);

            Point point = getGroupPosition();
            MoveToAction secondMove = Actions.moveTo(point.x, point.y);
            secondMove.setDuration(0.5f);

            SequenceAction sequence = Actions.sequence(firstMove, Actions.delay(0.1f),
                    secondMove, Actions.run(() -> burgerProcessingQueue.addActorAt(0, button)),
                    Actions.run(() -> signal()));

            button.addAction(sequence);
        });
    }

    private Point getGroupPosition() {
        Point position = new Point();
        if (burgerProcessingQueue.getChildren().size == 0) {
            position.x = (int) burgerProcessingQueue.getOriginX() - 60;
            position.y = (int) burgerProcessingQueue.getOriginY() - 60;
        } else {
            Image image = (Image) burgerProcessingQueue.getChildren().get(0);
            position.x = (int) (image.getX() - image.getWidth() / 2);
            position.y = (int) (burgerProcessingQueue.getY() + image.getY());
        }
        return position;
    }

    public void show() {
        Gdx.app.postRunnable(() -> {
            setVisible(true);
            stepDescription.setText("比如一个容量为5的阻塞队列\n" +
                    "当其内部元素size已经为5时\n" +
                    "插入操作会进入等待状态");
        });
    }

    public QueueHorizontalGroup getBurgerProcessingQueue() {
        return burgerProcessingQueue;
    }
}

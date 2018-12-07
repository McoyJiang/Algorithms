package com.danny_jiang.algorithm.data_structure.queue;

import android.graphics.Point;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;
import com.danny_jiang.algorithm.views.BaseGdxActor;

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

    @Override
    protected void inflateStage() {
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

        initIntroGroup();
    }

    private List<Actor> demoActors = new ArrayList<>();
    private Image cashier;
    private Label stepDescription;
    private QueueContainer queueContainer;
    private Label pushLabel;
    private Label popLabel;
    private Label marketLabel;
    private Label queueLabel;
    private int[] dataList = new int[]{26, 32, 46};
    private String[] colorList = new String[]{"#339900", "#e82a4b", "#ffcc00"};
    private List<AlgorithmButton> buttonList = new ArrayList<>();
    private static final String[] person_list = new String[]{
            "data_structure/queue/queue_person1.png",
            "data_structure/queue/queue_person2.png",
            "data_structure/queue/queue_person3.png",
            "data_structure/queue/queue_person4.png"
    };

    private void initIntroGroup() {
        cashier = new Image(new TextureRegion(
                new Texture("data_structure/queue/cashier.png")));
        cashier.setSize(100, 100);
        cashier.setPosition(stage.getWidth() / 2 - cashier.getWidth() / 2,
                visualizerBg.getY() + 10);
        stage.addActor(cashier);

        queueContainer = new QueueContainer();
        queueContainer.setSize(300, stage.getHeight() / 2 * 0.6f);
        queueContainer.setPosition(stage.getWidth() / 2 - queueContainer.getWidth() / 2 - 12.5f,
                cashier.getY() + +cashier.getHeight() + 5);
        stage.addActor(queueContainer);

        for (int i = 0; i < person_list.length; i++) {
            BaseGdxActor actor = new BaseGdxActor(
                    new TextureRegion(new Texture(person_list[i])));
            actor.setSize(100, 120);
            actor.setPosition(-150, stage.getHeight() - actor.getHeight() - 50);
            demoActors.add(actor);
        }

        addLabels();

        for (int i = 0; i < dataList.length; i++) {
            AlgorithmButton button = new AlgorithmButton("" + dataList[i]);
            button.setVisible(false);
            button.setSize(250, 100);
            button.setBackgroundColor(Color.valueOf(colorList[i]));
            button.setPosition(-250, stage.getHeight() - 200);
            stage.addActor(button);
            buttonList.add(button);
        }
    }

    private void addLabels() {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(
                "font/big_size.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = Color.valueOf("#ca2934");
        pushLabel = new Label("Enqueue", style);
        popLabel = new Label("Dequeue", style);
        pushLabel.setPosition(100, visualizerBg.getY() + visualizerBg.getHeight() / 2 - pushLabel.getHeight() / 2);
        popLabel.setPosition(queueContainer.getX() + queueContainer.getWidth() + 100,
                visualizerBg.getY() + visualizerBg.getHeight() / 2 - popLabel.getHeight() / 2);
        pushLabel.setVisible(false);
        popLabel.setVisible(false);
        stage.addActor(pushLabel);
        stage.addActor(popLabel);

        Label.LabelStyle emptyStyle = new Label.LabelStyle();
        emptyStyle.font = bitmapFont;
        emptyStyle.fontColor = Color.valueOf("#999999");
        marketLabel = new Label("超\n市", emptyStyle);
        marketLabel.setPosition(stage.getWidth() / 2 - marketLabel.getWidth() / 2,
                queueContainer.getY() + queueContainer.getHeight() / 2 - marketLabel.getHeight() / 2);
        stage.addActor(marketLabel);

        Label.LabelStyle stackStyle = new Label.LabelStyle();
        stackStyle.font = bitmapFont;
        stackStyle.fontColor = Color.valueOf("#999999");
        queueLabel = new Label("队\n列", stackStyle);
        queueLabel.setPosition(stage.getWidth() / 2 - queueLabel.getWidth() / 2,
                queueContainer.getY() + queueContainer.getHeight() / 2 - queueLabel.getHeight() / 2);
        queueLabel.setVisible(false);
        stage.addActor(queueLabel);


        BitmapFont desFont = new BitmapFont(Gdx.files.internal(
                "data_structure/queue/queue.fnt"));
        Label.LabelStyle desStyle = new Label.LabelStyle();
        desStyle.font = desFont;
        desStyle.fontColor = Color.valueOf("#4A4A4A");
        stepDescription = new Label("", desStyle);
        stepDescription.setText("队列的操作就像在超市排队结账\n先到的人先结账走人");
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
        switch (msg.what) {
            case DEMO_IN:
                demoIn();
                break;
            case DEMO_OUT:
                demoOut();
                break;
            case SHOW_QUEUE:
                stepDescription.setText("队列也遵循先进先出(FIFO)原则\n" +
                        "最先入队的数据最先被访问");
                cashier.setVisible(false);
                marketLabel.setVisible(false);
                queueLabel.setVisible(true);
                break;
            case ENQUEUE:
                enqueue(msg.arg1);
                break;
            case DEQUEUE_VISIBLE:
                Gdx.app.postRunnable(() -> {
                    stepDescription.setText("移除当前栈的栈顶元素\n" +
                            "时间复杂度: O(1)");
                    popLabel.setVisible(true);
                    pushLabel.setVisible(false);
                });
                break;
            case DEQUEUE:
                dequeue(msg.arg1);
                break;
            case PRODUCING:
                produce();
                break;
            case CONSUMING:
                consume(msg.arg1);
                break;
            case START_BLOCKING_QUEUE:
                showBlockingQueue();
                break;
        }

    }

    private void showBlockingQueue() {
        marketLabel.setVisible(false);
        queueLabel.setVisible(false);
        queueContainer.setVisible(false);
        blockingQueueGroup.setVisible(true);
        produceQueueGroup.setVisible(true);
        consumeQueueGroup.setVisible(true);
    }

    private void enqueue(int i) {
        stepDescription.setText("向队列中插入元素时,\n" +
                "被插入元素按照先来后到\n" +
                "的原则,依次排好队列");
        pushLabel.setVisible(true);
        AlgorithmButton button = buttonList.get(i);
        button.setVisible(true);
        MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - button.getWidth() / 2,
                button.getY());
        firstMove.setDuration(0.5f);
        MoveByAction secondMove = Actions.moveBy(0,
                -(450 - i * 120));
        secondMove.setDuration(0.5f);
        SequenceAction sequence = Actions.sequence();
        if (i == 0)
            sequence.addAction(Actions.run(() -> marketLabel.setVisible(false)));
        sequence.addAction(firstMove);
        sequence.addAction(Actions.delay(0.1f));
        sequence.addAction(secondMove);

        button.addAction(sequence);
    }

    private void dequeue(int i) {
        stepDescription.setText("从队列中取出元素时,\n" +
                "先进入队列的元素优先被访问");
        AlgorithmButton button = buttonList.get(i);
        MoveByAction firstMove = Actions.moveBy(stage.getWidth() + button.getWidth(), 0);
        firstMove.setDuration(0.5f);
        MoveToAction secondMove = Actions.moveTo(button.getX(), queueContainer.getY() - button.getHeight() - 10);
        secondMove.setDuration(0.5f);
        SequenceAction sequence = Actions.sequence(secondMove, Actions.delay(0.1f, firstMove));
        if (i == 2) {
            sequence.addAction(Actions.run(() -> {
                marketLabel.setVisible(false);
                popLabel.setVisible(false);
                stepDescription.setText("Done!");
            }));
        } else {
            ParallelAction parallel = Actions.parallel();
            for (int j = i; j < buttonList.size(); j++) {
                MoveByAction moveByAction = Actions.moveBy(0, -(buttonList.get(j).getHeight() + 10));
                moveByAction.setDuration(0.5f);
                moveByAction.setTarget(buttonList.get(j));
                parallel.addAction(moveByAction);
            }
            sequence.addAction(parallel);
        }
        button.addAction(sequence);
    }

    private void demoIn() {
        Gdx.app.postRunnable(() -> {
            for (int i = person_list.length - 1; i >= 0; i--) {
                Actor actor = demoActors.get(i);
                stage.addActor(actor);
                MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - actor.getWidth() / 2,
                        stage.getHeight() - actor.getHeight() - 50);
                firstMove.setDuration(0.5f);
                MoveByAction secondMove = Actions.moveBy(0,
                        -(actor.getY() - queueContainer.getY()) + i * 110 + 30);
                secondMove.setDuration(0.5f);
                SequenceAction sequence = Actions.sequence(Actions.delay(i * 0.3f),
                        firstMove, secondMove);
                actor.addAction(sequence);
            }
        });
    }

    private void demoOut() {
        Gdx.app.postRunnable(() -> {
            for (int i = person_list.length - 1; i >= 0; i--) {
                Actor actor = demoActors.get(i);
                stage.addActor(actor);
                MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - actor.getWidth() / 2,
                        queueContainer.getY() + 5);
                firstMove.setDuration(0.5f);
                MoveByAction secondMove = Actions.moveBy(stage.getWidth() / 2 + 100, 0);
                secondMove.setDuration(0.5f);
                SequenceAction sequence = Actions.sequence(Actions.delay(i * 0.3f),
                        firstMove, secondMove);
                actor.addAction(sequence);
            }
        });
    }


    private void consume(final int i) {
        Gdx.app.postRunnable(() -> {
            Image button = imageList.remove(i);
//            MoveByAction firstMove = Actions.moveBy(0,
//                    -blockingQueueGroup.getHeight() / 2);
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

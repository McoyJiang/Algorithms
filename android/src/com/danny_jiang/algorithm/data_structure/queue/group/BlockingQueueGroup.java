package com.danny_jiang.algorithm.data_structure.queue.group;

import android.graphics.Point;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.danny_jiang.algorithm.common.AlgorithmButton;
import com.danny_jiang.algorithm.common.AlgorithmGroup;
import com.danny_jiang.algorithm.data_structure.queue.QueueHorizontalGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockingQueueGroup extends AlgorithmGroup {

    public static final int PRODUCING = 1;
    public static final int CONSUMING = 2;
    private static final int DES_1 = 3;
    private static final int DES_2 = 4;
    private static final int START_PRODUCING = 5;
    private static final int FINISH = 6;
    private static final int BEFORE_FINISH = 7;

    private Random random = new Random();
    private String[] burger_list = new String[]{
            "data_structure/queue/burger1.png",
            "data_structure/queue/burger2.png",
            "data_structure/queue/burger3.png",
            "data_structure/queue/burger4.png",
            "data_structure/queue/burger5.png"
    };

    private List<Image> burgerImageList = new ArrayList<>();

    private Image blockingQueueImage;
    private Image muiltThreadImage;
    private QueueHorizontalGroup burgerProcessingQueue;
    private AlgorithmButton produceQueueGroup;
    private AlgorithmButton consumeQueueGroup;

    public BlockingQueueGroup(Stage stage,  Image visualizerBg) {
        super(stage, visualizerBg);
    }

    public void init() {
        super.init();
        initDescription();
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

        blockingQueueImage = new Image(new TextureRegion(
                new Texture("data_structure/queue/blocking-queue.jpeg")));
        blockingQueueImage.setSize(visualizerBg.getWidth() - 20,
                visualizerBg.getHeight() / 2);
        blockingQueueImage.setPosition(visualizerBg.getX() + 10,
                visualizerBg.getY() + visualizerBg.getHeight() / 4);
        addActor(blockingQueueImage);
        muiltThreadImage = new Image(new TextureRegion(
                new Texture("data_structure/queue/multi-thread.jpeg")));
        muiltThreadImage.setSize(visualizerBg.getWidth() - 20,
                visualizerBg.getHeight() - 200);
        muiltThreadImage.setPosition(visualizerBg.getX() + 10,
                visualizerBg.getY() + 100);
        addActor(muiltThreadImage);
        muiltThreadImage.setVisible(false);

        produceQueueGroup.setVisible(false);
        consumeQueueGroup.setVisible(false);
        burgerProcessingQueue.setVisible(false);
    }

    private void initDescription() {
        BitmapFont bigFont = new BitmapFont(Gdx.files.internal(
                "font/big_size.fnt"));
        Label.LabelStyle bigStyle = new Label.LabelStyle();
        bigStyle.font = bigFont;
        bigStyle.fontColor = Color.valueOf("#6f8694");
        Label blockingQueueLabel = new Label("Blocking Queue", bigStyle);
        blockingQueueLabel.setSize(200, 100);
        blockingQueueLabel.setPosition(visualizerBg.getX(),
                visualizerBg.getY() - blockingQueueLabel.getHeight() - 15);
        addActor(blockingQueueLabel);

        BitmapFont desFont = new BitmapFont(Gdx.files.internal(
                "data_structure/queue/queue.fnt"));
        Label.LabelStyle desStyle = new Label.LabelStyle();
        desStyle.font = desFont;
        desStyle.fontColor = Color.valueOf("#4A4A4A");
        stepDescription = new Label("", desStyle);
        stepDescription.setSize(500, 350);
        stepDescription.setFontScale(2f);
        stepDescription.setPosition(visualizerBg.getX(),
                blockingQueueLabel.getY() - stepDescription.getHeight() - 5);


        stepDescription.setText("阻塞队列(BlockingQueue)是Java5\n" +
                "并发新特性中的内容,它提供了\n" +
                "两个附加操作:put()和take()");
        addActor(stepDescription);
    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case DES_1:
                Gdx.app.postRunnable(() -> {
                    stepDescription.setText("当队列中没有数据的情况下,\n" +
                            "take操作会被自动阻塞(挂起),\n" +
                            "直到有数据放入队列\n" +
                            "线程被自动唤醒");
                    blockingQueueImage.setDrawable(new TextureRegionDrawable(
                            new TextureRegion(new Texture(
                                    "data_structure/queue/take_block.jpeg"))));
                });
                break;
            case DES_2:
                Gdx.app.postRunnable(() -> {
                    stepDescription.setText("当队列中填满数据的情况下,\n" +
                            "put操作会被自动阻塞(挂起),\n" +
                            "直到队列中有空的位置,\n" +
                            "线程被自动唤醒");
                    blockingQueueImage.setDrawable(new TextureRegionDrawable(
                            new TextureRegion(new Texture(
                                    "data_structure/queue/put_block.jpeg"))));
                });
                break;
            case START_PRODUCING:
                blockingQueueImage.setVisible(false);
                produceQueueGroup.setVisible(true);
                consumeQueueGroup.setVisible(true);
                burgerProcessingQueue.setVisible(true);
                stepDescription.setText("比如一个容量为5的阻塞队列,\n" +
                        "当其内部元素已经为5个,\n" +
                        "插入操作会进入等待状态");
                break;
            case PRODUCING:
                produce(msg.arg1);
                break;
            case CONSUMING:
                consume(msg.arg1);
                break;
            case BEFORE_FINISH:
                enableNextBtn();
                break;
            case FINISH:
                blockingQueueImage.setVisible(false);
                produceQueueGroup.setVisible(false);
                consumeQueueGroup.setVisible(false);
                burgerProcessingQueue.setVisible(false);
                muiltThreadImage.setVisible(true);
                stepDescription.setText(
                        "\n\n" +
                        "多线程环境中,通过队列可以很容易\n" +
                        "实现数据共享.这也是我们在多线程\n" +
                        "环境下,使用BlockingQueue的原因.\n" +
                        "作为BlockingQueue的使用者\n" +
                        "我们再也不需要关心什么时候需\n" +
                        "要阻塞线程什么时候需要唤醒线程\n" +
                        "这一切BlockingQueue都一手包办了");
                break;
        }
    }

    @Override
    protected void algorithm() {
        await();

        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DES_1, 0, -1)));
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DES_2, 0, -1)));
        await(() -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(START_PRODUCING, 0, -1)));
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

        for (int i = 0; i < 5; i++) {
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

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(BEFORE_FINISH, 0, -1));
        });

        await(() -> {
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(FINISH, 0, -1));
        });
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

    public void produce(int index) {
        Gdx.app.postRunnable(() -> {
            if (index == 4) {
                enableNextBtn();
            } else {
                disableNextBtn();
            }
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

    public QueueHorizontalGroup getBurgerProcessingQueue() {
        return burgerProcessingQueue;
    }
}

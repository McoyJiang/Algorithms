package com.danny_jiang.algorithm.data_structure.queue.group;

import android.graphics.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;
import com.danny_jiang.algorithm.data_structure.queue.QueueHorizontalGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockingQueueGroup extends Group {

    private Stage stage;
    private Label stepDescription;
    private final Image visualizerBg;

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

    public BlockingQueueGroup(Stage stage, Label stepDescription, Image visualizerBg) {
        this.stage = stage;
        this.stepDescription = stepDescription;
        this.visualizerBg = visualizerBg;
    }

    public void init() {
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
        addActor(produceQueueGroup);
        produceQueueGroup.setVisible(false);

        consumeQueueGroup = new AlgorithmButton("Consumer");
        consumeQueueGroup.setBackgroundColor(Color.valueOf("#f97e77"));
        consumeQueueGroup.setSize(360, 260);
        consumeQueueGroup.setPosition(visualizerBg.getX() + visualizerBg.getWidth()
                - consumeQueueGroup.getWidth() - 50, visualizerBg.getY() + 15);
        consumeQueueGroup.setOrigin(consumeQueueGroup.getX() + consumeQueueGroup.getWidth() / 2,
                consumeQueueGroup.getY() + consumeQueueGroup.getHeight() / 2);
        addActor(consumeQueueGroup);
        consumeQueueGroup.setVisible(false);

        addActor(blockingQueueGroup);
    }

    public void consume(final int i) {
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

    public void produce(final AlgorithmAdapter adapter) {
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
                    Actions.run(() -> adapter.signal()));

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

    public void show() {
        blockingQueueGroup.setVisible(true);
        produceQueueGroup.setVisible(true);
        consumeQueueGroup.setVisible(true);
    }
}

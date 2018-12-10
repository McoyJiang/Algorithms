package com.danny_jiang.algorithm.data_structure.queue.group;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.common.AlgorithmButton;
import com.danny_jiang.algorithm.data_structure.queue.QueueContainer;
import com.danny_jiang.algorithm.views.BaseGdxActor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.danny_jiang.algorithm.data_structure.queue.BurgerAdapter.DEMO_IN;
import static com.danny_jiang.algorithm.data_structure.queue.BurgerAdapter.DEMO_OUT;
import static com.danny_jiang.algorithm.data_structure.queue.BurgerAdapter.DEQUEUE;
import static com.danny_jiang.algorithm.data_structure.queue.BurgerAdapter.DEQUEUE_VISIBLE;
import static com.danny_jiang.algorithm.data_structure.queue.BurgerAdapter.ENQUEUE;
import static com.danny_jiang.algorithm.data_structure.queue.BurgerAdapter.SHOW_QUEUE;

public class QueueIntroGroup extends Group {

    private static final String TAG = QueueIntroGroup.class.getSimpleName();
    private static final String[] person_list = new String[]{
            "data_structure/queue/queue_person1.png",
            "data_structure/queue/queue_person2.png",
            "data_structure/queue/queue_person3.png",
            "data_structure/queue/queue_person4.png"
    };
    private final AlgorithmAdapter adapter;

    private Stage stage;
    private Label stepDescription;
    private final Image visualizerBg;

    private Image cashier;
    private List<Actor> demoActors = new ArrayList<>();
    // person used to show people in SuperMarket
    private QueueContainer queueContainer;

    // AlgorithmButton in Queue
    private int[] dataList = new int[]{26, 32, 46};
    private String[] colorList = new String[]{"#339900", "#e82a4b", "#ffcc00"};
    private List<AlgorithmButton> buttonList = new ArrayList<>();

    private Label pushLabel;
    private Label popLabel;
    private Label marketLabel;
    private Label queueLabel;

    private HandlerThread sDecodingThread;
    private Handler sDecodingThreadHandler;
    private Runnable algorithmRunnable = this::algorithm;
    private ReentrantLock sReenterLock = new ReentrantLock(true);
    private Condition sCondition = sReenterLock.newCondition();

    public QueueIntroGroup(AlgorithmAdapter adapter, Stage stage,
                           Label stepDescription, Image visualizerBg) {
        this.adapter = adapter;
        this.stage = stage;
        this.stepDescription = stepDescription;
        this.visualizerBg = visualizerBg;
        setTouchable(Touchable.childrenOnly);
        setSize(stage.getWidth(), stage.getHeight());
        init();

        initThread();
    }

    private void initThread() {
        sDecodingThread = new HandlerThread("FrameSequence decoding thread",
                Process.THREAD_PRIORITY_BACKGROUND);
        sDecodingThread.start();
        sDecodingThreadHandler = new Handler(sDecodingThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                adapter.animation(msg);
            }
        };
        new Thread(algorithmRunnable).start();
    }


    // Common widget
    AlgorithmButton nextStepBtn;

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

        cashier = new Image(new TextureRegion(
                new Texture("data_structure/queue/cashier.png")));
        cashier.setSize(100, 100);
        cashier.setPosition(stage.getWidth() / 2 - cashier.getWidth() / 2,
                visualizerBg.getY() + 10);
        addActor(cashier);

        queueContainer = new QueueContainer();
        queueContainer.setSize(300, stage.getHeight() / 2 * 0.6f);
        queueContainer.setPosition(stage.getWidth() / 2 - queueContainer.getWidth() / 2 - 12.5f,
                cashier.getY() + +cashier.getHeight() + 5);
        addActor(queueContainer);
        addLabels();

        for (int i = 0; i < person_list.length; i++) {
            BaseGdxActor actor = new BaseGdxActor(
                    new TextureRegion(new Texture(person_list[i])));
            actor.setSize(100, 120);
            actor.setPosition(-150, stage.getHeight() - actor.getHeight() - 50);
            demoActors.add(actor);
        }
        for (int i = 0; i < dataList.length; i++) {
            AlgorithmButton button = new AlgorithmButton("" + dataList[i]);
            button.setVisible(false);
            button.setSize(250, 100);
            button.setBackgroundColor(Color.valueOf(colorList[i]));
            button.setPosition(-250, stage.getHeight() - 200);
            addActor(button);
            buttonList.add(button);
        }
    }

    private void algorithm() {
        await();

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEMO_IN, 0, 0)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEMO_OUT, 0, 0)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(SHOW_QUEUE, 0, 0)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ENQUEUE, 0, -1)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ENQUEUE, 1, -1)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(ENQUEUE, 2, -1)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEQUEUE_VISIBLE, 2, -1)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEQUEUE, 0, -1)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                sDecodingThreadHandler.obtainMessage(DEQUEUE, 1, -1)));

        await((AlgorithmAdapter.BeforeWaitCallback) () -> {
            Gdx.app.postRunnable(() -> nextStepBtn.setBackgroundColor(Color.valueOf("#b9babd")));
            sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEQUEUE, 2, -1));
        });
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

    private void addLabels() {
        stepDescription.setText("队列的操作就像在超市排队结账\n先到的人先结账走人");

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
        addActor(pushLabel);
        addActor(popLabel);

        Label.LabelStyle emptyStyle = new Label.LabelStyle();
        emptyStyle.font = bitmapFont;
        emptyStyle.fontColor = Color.valueOf("#999999");
        marketLabel = new Label("超\n市", emptyStyle);
        marketLabel.setPosition(stage.getWidth() / 2 - marketLabel.getWidth() / 2,
                queueContainer.getY() + queueContainer.getHeight() / 2 - marketLabel.getHeight() / 2);
        addActor(marketLabel);

        Label.LabelStyle stackStyle = new Label.LabelStyle();
        stackStyle.font = bitmapFont;
        stackStyle.fontColor = Color.valueOf("#999999");
        queueLabel = new Label("队\n列", stackStyle);
        queueLabel.setPosition(stage.getWidth() / 2 - queueLabel.getWidth() / 2,
                queueContainer.getY() + queueContainer.getHeight() / 2 - queueLabel.getHeight() / 2);
        queueLabel.setVisible(false);
        addActor(queueLabel);
    }

    public void demoIn() {
        Log.e(TAG, TAG + ": demoIn");
        for (int i = person_list.length - 1; i >= 0; i--) {
            Actor actor = demoActors.get(i);
            addActor(actor);
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
    }

    public void demoOut() {
        Gdx.app.postRunnable(() -> {
            for (int i = person_list.length - 1; i >= 0; i--) {
                Actor actor = demoActors.get(i);
                addActor(actor);
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

    public void enqueue(int i) {
        stepDescription.setText("向队列中插入元素时,\n" +
                "被插入元素按照先来后到\n" +
                "的原则,依次排好队列");
        pushLabel.setVisible(true);
        AlgorithmButton button = buttonList.get(i);
        button.setVisible(true);
        MoveToAction firstMove = Actions.moveTo(stage.getWidth() / 2 - button.getWidth() / 2,
                button.getY());
        firstMove.setDuration(0.5f);
        MoveToAction secondMove = Actions.moveTo(stage.getWidth() / 2 - button.getWidth() / 2,
                cashier.getY() + cashier.getHeight() + (button.getHeight() + 10) * i);
        secondMove.setDuration(0.5f);
        SequenceAction sequence = Actions.sequence();
        if (i == 0)
            sequence.addAction(Actions.run(() -> marketLabel.setVisible(false)));
        sequence.addAction(firstMove);
        sequence.addAction(Actions.delay(0.1f));
        sequence.addAction(secondMove);

        button.addAction(sequence);
    }

    public void dequeue(int i) {
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
                stepDescription.setText("所有元素都已出队!");
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

    public void hide() {
        Gdx.app.postRunnable(() -> setVisible(false));
    }

    public void showQueue() {
        Gdx.app.postRunnable(() -> {
            stepDescription.setText("这就是先进先出(FIFO)原则\n" +
                    "最先入队的数据最先被访问");
            cashier.setVisible(false);
            marketLabel.setVisible(false);
            queueLabel.setVisible(true);
        });
    }

    public void showDequeue() {
        Gdx.app.postRunnable(() -> {
            stepDescription.setText("移除当前栈的栈顶元素\n" +
                    "时间复杂度: O(1)");
            popLabel.setVisible(true);
            pushLabel.setVisible(false);
        });
    }
}

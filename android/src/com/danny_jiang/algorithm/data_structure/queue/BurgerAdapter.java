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

public class BurgerAdapter extends AlgorithmAdapter {

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
        blockingQueueGroup.init();

        queueIntroGroup = new QueueIntroGroup(stage, stepDescription,  visualizerBg);
        queueIntroGroup.init();

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
    }

    private int currentPageIndex = 0;
    private void nextPage() {
        Gdx.app.postRunnable(() ->
                scrollPane.setScrollX(stage.getWidth() * ++currentPageIndex));
    }
    private void prevPage() {
        Gdx.app.postRunnable(() ->
                scrollPane.setScrollX(stage.getWidth() * --currentPageIndex));
    }

    @Override
    protected void algorithm() {
    }
}

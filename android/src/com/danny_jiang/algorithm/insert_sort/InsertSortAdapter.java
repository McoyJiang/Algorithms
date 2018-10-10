package com.danny_jiang.algorithm.insert_sort;

import android.os.Message;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.Align;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.views.AlgorithmBall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertSortAdapter extends AlgorithmAdapter{
    private static final String TAG = InsertSortAdapter.class.getSimpleName();

    /*
     * List to storage AlgorithmBall
     */
    private List<AlgorithmBall> ballList = new ArrayList<>();
    private HorizontalGroup newBallGroup;

    /*
     * Data use to display Insert Sort Algorithm
     */
    private int[] sData = new int[]{45, 17, 23, 5, 76, 10, 4};

    @Override
    public void create() {
        super.create();
        takeOutBall(0);
    }

    private void takeOutBall(int ballIndex) {
        AlgorithmBall algorithmBall = ballList.get(ballIndex);

        ScaleToAction scaleAway = Actions.scaleTo(0, 0);
        scaleAway.setDuration(2);
        RunnableAction run = Actions.run(() -> {
            algorithmBall.remove();
            newBallGroup.addActor(algorithmBall);
        });
        ScaleToAction scaleIn = Actions.scaleTo(1, 1);
        scaleIn.setDuration(2);
        algorithmBall.addAction(Actions.sequence(scaleAway, run, scaleIn));
    }

    @Override
    protected void inflateStage() {
        newBallGroup = new HorizontalGroup();
        newBallGroup.align(Align.center);
        newBallGroup.space(30);
        newBallGroup.setSize(stage.getWidth(), stage.getHeight() / 4);
        newBallGroup.setPosition(0, stage.getHeight() * 3 / 4);
        stage.addActor(newBallGroup);

        for (int i = 0; i < sData.length; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + sData[i]);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.setPosition(i * 150 + 50, 200);
            ballList.add(algorithmBall);
            stage.addActor(algorithmBall);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void animation(Message msg) {

    }

    @Override
    public void algorithm() {
        await();
        for (int j = 1; j < sData.length; j++) {
            int key = sData[j];
            int i = j - 1;
            while ((i > -1) && (sData[i] > key)) {
                sData[i + 1] = sData[i];
                i--;
            }
            sData[i + 1] = key;
        }
        System.out.println("sort completed: array is " + Arrays.toString(sData));
    }
}

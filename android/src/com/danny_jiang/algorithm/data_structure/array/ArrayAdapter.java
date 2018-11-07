package com.danny_jiang.algorithm.data_structure.array;

import android.os.Message;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;

public class ArrayAdapter extends AlgorithmAdapter {

    private ArrayElement two;

    @Override
    protected void inflateStage() {
        two = new ArrayElement("2");
        two.setSize(200, 140);
        two.setPosition(200, 400);

        stage.addActor(two);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void animation(Message msg) {

    }

    @Override
    protected void algorithm() {

    }
}

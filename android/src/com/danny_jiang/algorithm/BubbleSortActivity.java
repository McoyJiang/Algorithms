package com.danny_jiang.algorithm;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.danny_jiang.algorithm.adapter.BubbleSortAdapter;

public class BubbleSortActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new BubbleSortAdapter());
    }
}

package com.danny_jiang.algorithm.activity;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.danny_jiang.algorithm.adapter.KruskalMSTAdapter;

public class KruskalMSTAlgoActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new KruskalMSTAdapter());
    }
}

package com.danny_jiang.algorithm.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.danny_jiang.algorithm.R;
import com.danny_jiang.algorithm.adapter.KruskalMSTAdapter;
import com.danny_jiang.algorithm.fragment.BubbleSortFragment;

public class KruskalMSTAlgoActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize(new KruskalMSTAdapter());
        setContentView(R.layout.common_activity_layout);


        BubbleSortFragment categorizationFragment = new BubbleSortFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.visualizer, categorizationFragment);
        trans.commit();
    }

    @Override
    public void exit() {

    }
}

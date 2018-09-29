package com.danny_jiang.algorithm.kruskal_mst;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.danny_jiang.algorithm.R;

public class KruskalMSTAlgoActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_layout);
    }

    @Override
    public void exit() {

    }
}

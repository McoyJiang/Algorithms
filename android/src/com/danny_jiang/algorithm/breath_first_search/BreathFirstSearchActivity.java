package com.danny_jiang.algorithm.breath_first_search;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.danny_jiang.algorithm.AlgorithmActivity;
import com.danny_jiang.algorithm.R;

import java.util.ArrayList;

public class BreathFirstSearchActivity extends AlgorithmActivity {

    @Override
    protected void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new BreathFirstSortTutorialFragment());
        fragmentList.add(new BreathFirstSortVisualizerFragment());
        fragmentList.add(new BreathSearchSortAnalyzeFragment());

        Fragment fragment = fragmentList.get(0);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.algo_content, fragment);
        trans.commit();
    }

    @Override
    public void exit() {

    }
}

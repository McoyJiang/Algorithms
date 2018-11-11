package com.danny_jiang.algorithm.data_structure.linkedlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.danny_jiang.algorithm.AlgorithmActivity;
import com.danny_jiang.algorithm.R;

public class LinkListActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new LinkListTutorialFragment());
        fragmentList.add(new LinkListVisualizerFragment());
        fragmentList.add(new LinkListQuizFragment());

        Fragment fragment = fragmentList.get(0);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.algo_content, fragment);
        trans.commit();
    }
}

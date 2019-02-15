package com.danny_jiang.algorithm.data_structure.linkedlist;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class LinkListActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new LinkListTutorialFragment());
        fragmentList.add(new LinkListVisualizerFragment());
    }
}

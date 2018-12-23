package com.danny_jiang.algorithm.data_structure.tree;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class TreeIntroActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new TreeTutorialFragment());
        fragmentList.add(new TreeVisualizerFragment());
    }
}

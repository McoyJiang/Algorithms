package com.danny_jiang.algorithm.data_structure.tree;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class BSTActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new BSTTutorialFragment());
        fragmentList.add(new TreeVisualizerFragment());
    }
}

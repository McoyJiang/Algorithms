package com.danny_jiang.algorithm.data_structure.tree;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class RBTreeActivity extends AlgorithmActivity {

    @Override
    protected void initFragments() {
        fragmentList.add(new RBTreeTutorialFragment());
        fragmentList.add(new RBTreeVisualizerFragment());
    }
}

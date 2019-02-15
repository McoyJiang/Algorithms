package com.danny_jiang.algorithm.data_structure.stack;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class StackActivity extends AlgorithmActivity {

    @Override
    protected void initFragments() {
        fragmentList.add(new StackTutorialFragment());
        fragmentList.add(new StackVisualizerFragment());
    }
}

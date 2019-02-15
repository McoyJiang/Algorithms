package com.danny_jiang.algorithm.data_structure.queue;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class QueueActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new QueueTutorialFragment());
        fragmentList.add(new QueueVisualizerFragment());
    }
}

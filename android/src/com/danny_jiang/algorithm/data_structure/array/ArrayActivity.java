package com.danny_jiang.algorithm.data_structure.array;

import com.danny_jiang.algorithm.AlgorithmActivity;

public class ArrayActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new ArrayTutorialFragment());
        fragmentList.add(new ArrayVisualizerFragment());
        fragmentList.add(new ArrayQuizFragment());
    }
}

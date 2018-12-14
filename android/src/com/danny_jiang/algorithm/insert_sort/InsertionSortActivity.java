package com.danny_jiang.algorithm.insert_sort;


import com.danny_jiang.algorithm.AlgorithmActivity;

public class InsertionSortActivity extends AlgorithmActivity {

    @Override
    protected void initFragments() {
        fragmentList.add(new InsertionSortTutorialFragment());
        fragmentList.add(new InsertionSortVisualizerFragment());
        fragmentList.add(new InsertionSortQuizFragment());
    }
}

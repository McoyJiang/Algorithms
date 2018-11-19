package com.danny_jiang.algorithm.bubble_sort;

import com.danny_jiang.algorithm.AlgorithmActivity;
import java.util.ArrayList;

public class BubbleSortActivity extends AlgorithmActivity {

    @Override
    protected void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new BubbleSortTutorialFragment());
        fragmentList.add(new BubbleSortVisualizerFragment());
        fragmentList.add(new BubbleSortAnalyzeFragment());
    }

    @Override
    public void exit() {

    }
}
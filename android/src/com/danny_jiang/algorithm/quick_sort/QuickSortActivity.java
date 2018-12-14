package com.danny_jiang.algorithm.quick_sort;


import com.danny_jiang.algorithm.AlgorithmActivity;
import java.util.ArrayList;

public class QuickSortActivity extends AlgorithmActivity {


    @Override
    protected void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new QuickSortTutorialFragment());
        fragmentList.add(new QuickSortVisualizerFragment());
        fragmentList.add(new QuickSortAnalyzeFragment());
    }
}

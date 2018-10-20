package com.danny_jiang.algorithm.quick_sort;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.danny_jiang.algorithm.AlgorithmActivity;
import com.danny_jiang.algorithm.R;

import java.util.ArrayList;

public class QuickSortActivity extends AlgorithmActivity {


    @Override
    protected void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new QuickSortTutorialFragment());
        fragmentList.add(new QuickSortVisualizerFragment());
        fragmentList.add(new QuickSortAnalyzeFragment());

        Fragment fragment = fragmentList.get(0);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.algo_content, fragment);
        trans.commit();
    }
}

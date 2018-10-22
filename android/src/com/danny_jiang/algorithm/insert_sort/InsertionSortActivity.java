package com.danny_jiang.algorithm.insert_sort;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.danny_jiang.algorithm.AlgorithmActivity;
import com.danny_jiang.algorithm.R;

import java.util.ArrayList;

public class InsertionSortActivity extends AlgorithmActivity {

    @Override
    protected void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new InsertionSortTutorialFragment());
        fragmentList.add(new InsertionSortVisualizerFragment());
        fragmentList.add(new InsertionSortQuizFragment());

        Fragment fragment = fragmentList.get(0);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.algo_content, fragment);
        trans.commit();
    }
}

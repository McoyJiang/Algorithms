package com.danny_jiang.algorithm.data_structure.array;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.danny_jiang.algorithm.AlgorithmActivity;
import com.danny_jiang.algorithm.R;

public class ArrayActivity extends AlgorithmActivity {
    @Override
    protected void initFragments() {
        fragmentList.add(new ArrayTutorialFragment());
        fragmentList.add(new ArrayVisualizerFragment());
        fragmentList.add(new ArrayQuizFragment());

//        Fragment fragment = fragmentList.get(0);
//        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//        trans.replace(R.id.algo_content, fragment);
//        trans.commit();
    }
}

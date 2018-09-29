package com.danny_jiang.algorithm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.danny_jiang.algorithm.fragment.BubbleSortFragment;
import com.danny_jiang.algorithm.fragment.BubbleSortTutorialFragment;

import java.util.ArrayList;
import java.util.List;

public class BubbleSortActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks  {

    private TabLayout tabLayout;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_layout);
        initFragments();

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TAB1"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB2"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB3"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = fragmentList.get(position);
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.algo_content, fragment);
                trans.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new BubbleSortTutorialFragment());
        fragmentList.add(new BubbleSortFragment());
        fragmentList.add(new BubbleSortTutorialFragment());
    }

    @Override
    public void exit() {

    }
}
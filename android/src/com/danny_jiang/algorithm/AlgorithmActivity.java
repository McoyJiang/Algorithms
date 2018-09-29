package com.danny_jiang.algorithm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import java.util.List;

public abstract class AlgorithmActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    protected List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_layout);
        initFragments();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TUTORIAL"));
        tabLayout.addTab(tabLayout.newTab().setText("VISUALIZER"));
        tabLayout.addTab(tabLayout.newTab().setText("ANALYSIS"));
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

    protected abstract void initFragments();

    @Override
    public void exit() {

    }
}

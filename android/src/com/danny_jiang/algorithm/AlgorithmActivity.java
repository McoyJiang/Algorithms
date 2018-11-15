package com.danny_jiang.algorithm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import java.util.ArrayList;
import java.util.List;

public abstract class AlgorithmActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    protected List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_layout);
        manager = getSupportFragmentManager();
        initFragments();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TUTORIAL"));
        tabLayout.addTab(tabLayout.newTab().setText("VISUALIZER"));
        //tabLayout.addTab(tabLayout.newTab().setText("Quiz"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                showFragment(fragmentList.get(position));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        showFragment(fragmentList.get(0));
    }

    private FragmentManager manager;
    private Fragment currentFragment=new Fragment();
    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment!=fragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.algo_content, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    protected abstract void initFragments();

    @Override
    public void exit() {

    }
}

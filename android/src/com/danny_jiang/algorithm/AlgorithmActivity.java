package com.danny_jiang.algorithm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.danny_jiang.algorithm.utils.JPushEventUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.analytics.android.api.BrowseEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

public abstract class AlgorithmActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    private static final String TAG = AlgorithmActivity.class.getSimpleName();

    protected List<Fragment> fragmentList = new ArrayList<>();
    protected TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_layout);
        manager = getSupportFragmentManager();
        initFragments();

        initTabs();
    }

    protected void initTabs() {
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TUTORIAL"));
        tabLayout.addTab(tabLayout.newTab().setText("VISUALIZER"));
        //tabLayout.addTab(tabLayout.newTab().setText("Quiz"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                showFragment(fragmentList.get(position));
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

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        String simpleName = getClass().getSimpleName();
        JAnalyticsInterface.onPageStart(this, simpleName);
        JPushEventUtils.onBrowseEvent(this, simpleName);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        String simpleName = getClass().getSimpleName();
        JAnalyticsInterface.onPageEnd(this, simpleName);
    }

    protected abstract void initFragments();

    @Override
    public void exit() {

    }
}

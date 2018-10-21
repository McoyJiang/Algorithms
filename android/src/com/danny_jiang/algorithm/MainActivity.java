package com.danny_jiang.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.danny_jiang.algorithm.breath_first_search.BreathFirstSearchActivity;
import com.danny_jiang.algorithm.breath_first_search.BreathFirstSearchAdapter;
import com.danny_jiang.algorithm.bubble_sort.BubbleSortActivity;
import com.danny_jiang.algorithm.heap_sort.HeapSortActivity;
import com.danny_jiang.algorithm.insert_sort.InsertSortAdapter;
import com.danny_jiang.algorithm.quick_sort.QuickSortActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView algorithmListView;
    private AlgorithmAdapter algorithmAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        algorithmListView = findViewById(R.id.recyclerView_Main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        algorithmListView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        initData();
        algorithmAdapter = new AlgorithmAdapter(this, mDatas);
        algorithmAdapter.setOnItemClickListener(new AlgorithmAdapter.OnItemClickListener() {

            @Override
            public void onLongClick(int position) {
            }

            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, BubbleSortActivity.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, BubbleSortActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, QuickSortActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, HeapSortActivity.class);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, BreathFirstSearchActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        algorithmListView.setAdapter(algorithmAdapter);
        //设置增加或删除条目的动画
        algorithmListView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        mDatas.add("Bubble Sort");
        mDatas.add("Insert Sort");
        mDatas.add("Quick Sort");
        mDatas.add("Heap Sort");
        mDatas.add("Breath First Search");
    }
}
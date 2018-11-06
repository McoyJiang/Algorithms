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
import com.danny_jiang.algorithm.insert_sort.InsertionSortActivity;
import com.danny_jiang.algorithm.quick_sort.QuickSortActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danny_jiang.algorithm.Algorithm.ARRAY;
import static com.danny_jiang.algorithm.Algorithm.BUBBLE_SORT;
import static com.danny_jiang.algorithm.Algorithm.INSERTION_SORT;
import static com.danny_jiang.algorithm.Algorithm.LINK_LIST;
import static com.danny_jiang.algorithm.Algorithm.QUICK_SORT;
import static com.danny_jiang.algorithm.Algorithm.STACK;

public class MainActivity extends AppCompatActivity {

    private RecyclerView algorithmListView;
    private List<AlgorithmGroup> algorithmGroupList = new ArrayList<>();
    private ExpandableAlgorithmAdapter adapter;

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
        //初始化数据
        initData();
        //设置Adapter
        adapter = new ExpandableAlgorithmAdapter(algorithmGroupList);
        algorithmListView.setAdapter(adapter);
    }

    private void initData() {
        algorithmGroupList.add(new AlgorithmGroup("数据结构",
                Arrays.asList(ARRAY, STACK, LINK_LIST)));

        algorithmGroupList.add(new AlgorithmGroup("排序",
                Arrays.asList(BUBBLE_SORT, INSERTION_SORT, QUICK_SORT)));
    }
}
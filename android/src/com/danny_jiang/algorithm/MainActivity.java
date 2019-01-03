package com.danny_jiang.algorithm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danny_jiang.algorithm.Algorithm.ARRAY;
import static com.danny_jiang.algorithm.Algorithm.BINARY_SEARCH_TREE;
import static com.danny_jiang.algorithm.Algorithm.BUBBLE_SORT;
import static com.danny_jiang.algorithm.Algorithm.INSERTION_SORT;
import static com.danny_jiang.algorithm.Algorithm.LINK_LIST;
import static com.danny_jiang.algorithm.Algorithm.QUEUE;
import static com.danny_jiang.algorithm.Algorithm.QUICK_SORT;
import static com.danny_jiang.algorithm.Algorithm.RED_BLACK_TREE;
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

        int[] arr = new int[]{11, 15, 6, 8, 9, 10};
        pairInArray(arr, arr.length, 45);
    }

    private boolean pairInArray(int[] arr, int length, int pair) {
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (arr[i] + arr[j] == 45) {
                    Log.e("AAA", "true");
                    return true;
                }
            }
        }
        Log.e("AAA", "false");
        return false;
    }

    private void initData() {
        algorithmGroupList.add(new AlgorithmGroup("数据结构",
                Arrays.asList(ARRAY, LINK_LIST, STACK, QUEUE)));

        algorithmGroupList.add(new AlgorithmGroup("树 Tree",
                Arrays.asList(BINARY_SEARCH_TREE, RED_BLACK_TREE)));

        algorithmGroupList.add(new AlgorithmGroup("排序",
                Arrays.asList(BUBBLE_SORT, INSERTION_SORT, QUICK_SORT)));

        algorithmGroupList.add(new AlgorithmGroup("图算法",
                Arrays.asList(BUBBLE_SORT, INSERTION_SORT, QUICK_SORT)));
    }
}
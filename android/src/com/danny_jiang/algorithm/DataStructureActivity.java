package com.danny_jiang.algorithm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
import static com.danny_jiang.algorithm.Algorithm.TREE_INTRODUCTION;

public class DataStructureActivity extends AppCompatActivity {

    private RecyclerView algorithmListView;
    private List<AlgorithmGroup> algorithmGroupList = new ArrayList<>();
    private ExpandableAlgorithmAdapter adapter;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = menuItem -> {
        Intent intent = new Intent();
        switch (menuItem.getItemId()) {
            case R.id.help_feedback:
                intent.setClass(getApplicationContext(), FeedbackActivity.class);
                break;
            case R.id.folk_on_github:
                Uri uri = Uri.parse("https://github.com/McoyJiang/Algorithms");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                break;
        }
        startActivity(intent);
        return true;
    };

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
                Arrays.asList(TREE_INTRODUCTION, BINARY_SEARCH_TREE, RED_BLACK_TREE)));

        algorithmGroupList.add(new AlgorithmGroup("排序",
                Arrays.asList(BUBBLE_SORT, INSERTION_SORT, QUICK_SORT)));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
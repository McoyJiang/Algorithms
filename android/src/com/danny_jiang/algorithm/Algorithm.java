package com.danny_jiang.algorithm;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.danny_jiang.algorithm.bubble_sort.BubbleSortActivity;
import com.danny_jiang.algorithm.data_structure.array.ArrayActivity;
import com.danny_jiang.algorithm.data_structure.linkedlist.LinkListActivity;
import com.danny_jiang.algorithm.data_structure.queue.QueueActivity;
import com.danny_jiang.algorithm.data_structure.stack.StackActivity;
import com.danny_jiang.algorithm.data_structure.tree.BSTActivity;
import com.danny_jiang.algorithm.data_structure.tree.BinaryTreeActivity;
import com.danny_jiang.algorithm.data_structure.tree.RBTreeActivity;
import com.danny_jiang.algorithm.data_structure.tree.TreeIntroActivity;
import com.danny_jiang.algorithm.insert_sort.InsertionSortActivity;
import com.danny_jiang.algorithm.quick_sort.QuickSortActivity;

@SuppressLint("ParcelCreator")
public enum Algorithm implements Parcelable {

    // Bisic Data Structure
    ARRAY("数组", R.drawable.ic_array, ArrayActivity.class),
    LINK_LIST("链表", R.drawable.linklist, LinkListActivity.class),
    STACK("栈", R.drawable.stack, StackActivity.class),
    QUEUE("队列", R.drawable.queue, QueueActivity.class),

    // Tree structure
    TREE_INTRODUCTION("树简介", R.drawable.tree_into, TreeIntroActivity.class),
    BINARY_TREE("二叉树", R.drawable.binary_tree, BinaryTreeActivity.class),
    BINARY_SEARCH_TREE("二叉搜索树", R.drawable.bst, BSTActivity.class),
    RED_BLACK_TREE("红黑树", R.drawable.red_black_tree, RBTreeActivity.class),
    // Graph algorithm

    // sort algorithm
    BUBBLE_SORT("冒泡排序", R.drawable.bubble_sort, BubbleSortActivity.class),
    INSERTION_SORT("插入排序", R.drawable.insertion_sort, InsertionSortActivity.class),
    QUICK_SORT("快速排序", R.drawable.quick_sort, QuickSortActivity.class);

    private String name;
    private int iconRes;
    private Class aClass;

    Algorithm(String name, int iconRes, Class aClass) {
        this.name = name;
        this.iconRes = iconRes;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public Class getClassName() {
        return aClass;
    }
}

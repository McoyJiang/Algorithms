package com.danny_jiang.algorithm;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.danny_jiang.algorithm.data_structure.array.ArrayActivity;

@SuppressLint("ParcelCreator")
public enum Algorithm implements Parcelable {

    ARRAY("数组", R.drawable.array, ArrayActivity.class),
    STACK("栈", R.drawable.bookstack, ArrayActivity.class),
    LINK_LIST("链表", R.drawable.array, ArrayActivity.class),

    BUBBLE_SORT("冒泡排序", R.drawable.array, ArrayActivity.class),
    INSERTION_SORT("插入排序", R.drawable.array, ArrayActivity.class),
    QUICK_SORT("快速排序", R.drawable.array, ArrayActivity.class);

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

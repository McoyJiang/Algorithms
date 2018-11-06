package com.danny_jiang.algorithm;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public enum Algorithm implements Parcelable {

    ARRAY("数组", R.drawable.array),
    STACK("栈", R.drawable.bookstack),
    LINK_LIST("链表", R.drawable.array),

    BUBBLE_SORT("冒泡排序", R.drawable.array),
    INSERTION_SORT("插入排序", R.drawable.array),
    QUICK_SORT("快速排序", R.drawable.array);

    private String name;
    private int iconRes;

    Algorithm(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
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
}

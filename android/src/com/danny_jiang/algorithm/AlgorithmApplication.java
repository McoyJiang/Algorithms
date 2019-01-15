package com.danny_jiang.algorithm;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class AlgorithmApplication extends Application {

    private static AlgorithmApplication instance;

    public static AlgorithmApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}

package com.danny_jiang.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weicome);
    }

    public void btnClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_data:
                intent.setClass(this, DataStructureActivity.class);
                break;
            case R.id.btn_algorithm:
                intent.setClass(this, DataStructureActivity.class);
                break;
            case R.id.btn_leetcode:
                intent.setClass(this, DataStructureActivity.class);
                break;
        }
        startActivity(intent);
    }
}

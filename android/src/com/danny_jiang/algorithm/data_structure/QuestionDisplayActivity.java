package com.danny_jiang.algorithm.data_structure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.danny_jiang.algorithm.R;

public class QuestionDisplayActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        webView = findViewById(R.id.questionWebView);
        webView.loadUrl("file:///android_asset/data_structure/leetcode_questions/test.html");
    }
}

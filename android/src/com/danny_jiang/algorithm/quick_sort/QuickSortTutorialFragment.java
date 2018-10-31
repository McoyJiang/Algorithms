package com.danny_jiang.algorithm.quick_sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.danny_jiang.algorithm.R;

public class QuickSortTutorialFragment extends Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bubble_sort_tutorial_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = ((WebView) view.findViewById(R.id.bubble_sort_tutorial_webview));
        WebSettings webSettings = webView.getSettings();
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //用于js调用Android
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebChromeClient(new WebChromeClient());
        //访问Android assets文件夹内的
        String url = "file:///android_asset/quick_sort/quick_sort_tutorial.html";
        webView.loadUrl(url);
    }
}

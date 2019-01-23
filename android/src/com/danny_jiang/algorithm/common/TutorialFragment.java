package com.danny_jiang.algorithm.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.danny_jiang.algorithm.R;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;

public abstract class TutorialFragment extends Fragment {
    private static final String TAG = TutorialFragment.class.getSimpleName();

    protected WebView webView;

    private IFLYAdListener mAdListener = new IFLYAdListener() {

        @Override
        public void onConfirm() {
            Log.e(TAG, "onConfirm");
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel");
        }

        @Override
        public void onAdReceive() {
            Log.e(TAG, "onAdReceive");
        }

        @Override
        public void onAdFailed(AdError adError) {
            Log.e(TAG, "onAdFailed");
        }

        @Override
        public void onAdClick() {
            Log.e(TAG, "onAdClick");
        }

        @Override
        public void onAdClose() {
            Log.e(TAG, "onAdClose");
        }

        @Override
        public void onAdExposure() {
            Log.e(TAG, "onAdExposure");
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bubble_sort_tutorial_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpWebView(view);

//        setupAdView(view);
    }

    private void setupAdView(View view) {
        //创建旗帜广告:adId:开发者在广告平台(http://www.voiceads.cn/)申请的广告位 ID
         IFLYBannerAd bannerAd = IFLYBannerAd.createBannerAd(getActivity(), "40118DCB22EDEDA3E098590B1151FFF3");
         bannerAd.setAdSize(IFLYAdSize.BANNER);
        // 设置广告尺寸
         bannerAd.setParameter(AdKeys.DEBUG_MODE, "true");
        // 设置为调试模式
         bannerAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
        // 下载广告前,弹窗提示
        // 请求广告,添加监听器
        bannerAd.loadAd(mAdListener);
        LinearLayout layout_ads = view.findViewById(R.id.tutorial_ad_layout);
        layout_ads.removeAllViews();
        layout_ads.addView(bannerAd);
    }

    private void setUpWebView(View view) {
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

        // 自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        loadUrl();
    }

    public abstract void loadUrl();
}

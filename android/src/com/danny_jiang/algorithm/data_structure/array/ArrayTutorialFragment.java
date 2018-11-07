package com.danny_jiang.algorithm.data_structure.array;

import com.danny_jiang.algorithm.common.TutorialFragment;

public class ArrayTutorialFragment extends TutorialFragment {

    @Override
    public void loadUrl() {
        //访问Android assets文件夹内的
        String url = "https://blog.csdn.net/zxm317122667/article/details/83781343";
        webView.loadUrl(url);
    }
}

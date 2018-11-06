package com.danny_jiang.algorithm.data_structure.array;

import com.danny_jiang.algorithm.common.TutorialFragment;

public class ArrayTutorialFragment extends TutorialFragment {

    @Override
    public void loadUrl() {
        //访问Android assets文件夹内的
        String url = "file:///android_asset/insertion_sort/" +
                "insertion_sort_tutorial.html";
        webView.loadUrl(url);
    }
}

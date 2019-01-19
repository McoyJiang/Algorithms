package com.danny_jiang.algorithm.data_structure.array;

import com.danny_jiang.algorithm.common.TutorialFragment;

public class ArrayTutorialFragment extends TutorialFragment {

    @Override
    public void loadUrl() {
        //访问Android assets文件夹内的
        String url = "https://mp.weixin.qq.com/s/YcOvI-W4-5PKMqYrhA3ymw";
        webView.loadUrl(url);
    }
}

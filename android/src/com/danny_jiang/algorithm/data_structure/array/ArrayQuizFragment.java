package com.danny_jiang.algorithm.data_structure.array;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danny_jiang.algorithm.R;
import com.danny_jiang.algorithm.common.TutorialFragment;

import material.danny_jiang.com.mcoysnaplibrary.page.McoyWebSnapPage;
import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;

public class ArrayQuizFragment extends TutorialFragment {

    @Override
    public void loadUrl() {
        webView.loadUrl("file:///android_asset/data_structure/Array/quiz/array_quiz.html");
    }
}

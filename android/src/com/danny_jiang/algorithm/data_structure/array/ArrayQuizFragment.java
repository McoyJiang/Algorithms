package com.danny_jiang.algorithm.data_structure.array;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danny_jiang.algorithm.R;

import material.danny_jiang.com.mcoysnaplibrary.page.McoyWebSnapPage;
import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;

public class ArrayQuizFragment extends Fragment {

    private McoySnapPageLayout pageLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_array, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageLayout = view.findViewById(R.id.snapPage);

        McoyWebSnapPage w1 = new McoyWebSnapPage(getContext(),
                "https://blog.csdn.net/zxm317122667/article/details/83962270");
        McoyWebSnapPage w2 = new McoyWebSnapPage(getContext(),
                "https://blog.csdn.net/zxm317122667/article/details/83999828");

        pageLayout.addSnapPage(w1);
        pageLayout.addSnapPage(w2);
    }
}

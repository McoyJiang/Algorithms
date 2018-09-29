package com.danny_jiang.algorithm.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danny_jiang.algorithm.R;

public class BubbleSortFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bubble_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BubbleSortVisualizerFragment categorizationFragment = new BubbleSortVisualizerFragment();
        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
        trans.replace(R.id.visualizer, categorizationFragment);
        trans.commit();
    }
}

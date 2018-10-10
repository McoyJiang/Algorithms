package com.danny_jiang.algorithm.heap_sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.danny_jiang.algorithm.insert_sort.InsertSortAdapter;

public class HeapSortVisualizerFragment extends AndroidFragmentApplication {
    private HeapSortAdapter heapSortAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        heapSortAdapter = new HeapSortAdapter();
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples = 4;
        return initializeForView(new InsertSortAdapter(), config);
    }
}

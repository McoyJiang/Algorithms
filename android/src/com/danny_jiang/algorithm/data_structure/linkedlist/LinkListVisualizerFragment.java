package com.danny_jiang.algorithm.data_structure.linkedlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.danny_jiang.algorithm.data_structure.array.ArrayAdapter;

public class LinkListVisualizerFragment extends AndroidFragmentApplication {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples = 4;
        return initializeForView(new LinkListAdapter(), config);
    }
}

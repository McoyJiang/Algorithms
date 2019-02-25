package com.danny_jiang.algorithm.data_structure.array;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;

public class ArrayQuizListFragment extends ListFragment {

    private static final String[] str = new String[]{
            "first","second","third","fourth","fifth"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, str));
    }

}

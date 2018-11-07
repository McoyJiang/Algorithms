package com.danny_jiang.algorithm;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class ExpandableAlgorithmAdapter extends ExpandableRecyclerViewAdapter<TitleViewHolder, SubTitleViewHolder> {

    public ExpandableAlgorithmAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        for (int i = 0; i < groups.size(); i++) {
            expandableList.expandedGroupIndexes[i] = true;
        }
    }

    @Override
    public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_algorithm_group, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public SubTitleViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_algorithm_child, parent, false);
        return new SubTitleViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(SubTitleViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {
        final Algorithm algorithm = ((AlgorithmGroup) group).getItems().get(childIndex);
        holder.setAlgorithm(algorithm);
}

    @Override
    public void onBindGroupViewHolder(TitleViewHolder holder,
                                      int flatPosition, ExpandableGroup group) {
        int backgroundColor = Color.GRAY;
        switch (flatPosition) {
            case 0:
                backgroundColor = Color.parseColor("#808000");
                break;
            case 1:
                backgroundColor = Color.parseColor("#D2B48C");
                break;
            case 2:
                backgroundColor = Color.MAGENTA;
                break;
            case 3:
                backgroundColor = Color.RED;
                break;
        }
        holder.setGroupTitle(group.getTitle());
        holder.setBackgroundColor(backgroundColor);
    }
}

class TitleViewHolder extends GroupViewHolder {

    private final TextView title;

    public TitleViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.text_group);
    }

    public void setGroupTitle(String name) {
        title.setText(name);
    }

    public void setBackgroundColor(int color) {
        title.setBackgroundColor(color);
    }
}

class SubTitleViewHolder extends ChildViewHolder {

    private final TextView subTitle;
    private final ImageView icon;
    private View.OnClickListener titleClickListener = v -> {
        Intent intent = new Intent();
        intent.setClass(v.getContext(), ((Class) v.getTag()));
        v.getContext().startActivity(intent);
    };

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitle = itemView.findViewById(R.id.text_child);
        subTitle.setOnClickListener(titleClickListener);
        icon = itemView.findViewById(R.id.image_child);
    }

    public void setAlgorithm(Algorithm algorithm) {
        subTitle.setText(algorithm.getName());
        subTitle.setTag(algorithm.getClassName());
        icon.setImageResource((algorithm.getIconRes()));
    }
}
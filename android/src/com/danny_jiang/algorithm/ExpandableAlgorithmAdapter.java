package com.danny_jiang.algorithm;

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
        holder.setGroupTitle(group.getTitle());
    }
}

class TitleViewHolder extends GroupViewHolder {

    private final TextView title;

    public TitleViewHolder(View itemView) {
        super(itemView);
        title =  itemView.findViewById(R.id.text_group);
    }

    public void setGroupTitle(String name) {
        title.setText(name);
    }
}

class SubTitleViewHolder extends ChildViewHolder {

    private final TextView subTitle;
    private final ImageView icon;

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitle = itemView.findViewById(R.id.text_child);
        icon = itemView.findViewById(R.id.image_child);
    }

    public void setAlgorithm(Algorithm algorithm) {
        subTitle.setText(algorithm.getName());
        icon.setImageResource((algorithm.getIconRes()));
    }
}
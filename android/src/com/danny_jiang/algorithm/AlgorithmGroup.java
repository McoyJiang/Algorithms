package com.danny_jiang.algorithm;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class AlgorithmGroup extends ExpandableGroup<Algorithm> {

    public AlgorithmGroup(String title, List<Algorithm> items) {
        super(title, items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlgorithmGroup)) return false;
        AlgorithmGroup genre = (AlgorithmGroup) o;
        return getTitle() == genre.getTitle();
    }
}
package com.danny_jiang.algorithm.data_structure.array;

import com.danny_jiang.algorithm.data_structure.LeetCodeQuestion;
import com.danny_jiang.algorithm.data_structure.LeetCodeQuestionListFragment;

public class ArrayQuizListFragment extends LeetCodeQuestionListFragment<LeetCodeQuestion.ArrayBean> {
    @Override
    public void initQuestionsData() {
        questionsDataList = questionBean.getArray();
    }
}

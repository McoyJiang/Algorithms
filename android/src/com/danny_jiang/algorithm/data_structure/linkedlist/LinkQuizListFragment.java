package com.danny_jiang.algorithm.data_structure.linkedlist;

import com.danny_jiang.algorithm.data_structure.LeetCodeQuestion;
import com.danny_jiang.algorithm.data_structure.LeetCodeQuestionListFragment;

public class LinkQuizListFragment extends LeetCodeQuestionListFragment<LeetCodeQuestion.LinklistBean> {
    @Override
    public void initQuestionsData() {
        questionsDataList = questionBean.getLinklist();
    }
}

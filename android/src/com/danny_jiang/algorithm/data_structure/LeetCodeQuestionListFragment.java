package com.danny_jiang.algorithm.data_structure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.danny_jiang.algorithm.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public abstract class LeetCodeQuestionListFragment<T extends LeetCodeQuestion.BaseBean> extends Fragment {

    protected LeetCodeQuestion questionBean;
    protected List<T> questionsDataList;

    private ListView arrayListView;
    private ArrayQuestionAdapter adapter;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            if (holder != null) {
                String url = questionsDataList.get(holder.currentPosition).getUrl();
                Intent intent = new Intent(getContext(), QuestionDisplayActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_array, null, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayListView = view.findViewById(R.id.arrayQuestionList);
        adapter = new ArrayQuestionAdapter();
        arrayListView.setAdapter(adapter);
    }

    public void initData() {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            inputStream = getResources().getAssets().open("data_structure/leetcode_questions.json");
            isr = new InputStreamReader(inputStream);
            reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                sb.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                isr.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String result = sb.toString();
        Gson gson = new Gson();
        questionBean = gson.fromJson(result, LeetCodeQuestion.class);
        initQuestionsData();
    }
    public abstract void initQuestionsData();

    class ArrayQuestionAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return questionsDataList == null ? 0 : questionsDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return questionsDataList == null ? null : questionsDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.question_item, null, false);
                holder = new ViewHolder();
                holder.questionTitle = convertView.findViewById(R.id.questionTitle);
                holder.questionName = convertView.findViewById(R.id.questionName);
                convertView.setTag(holder);
            }

            LeetCodeQuestion.BaseBean baseBean = questionsDataList.get(position);
            holder.questionTitle.setText(baseBean.getTitle());
            holder.questionName.setText(baseBean.getName());
            holder.currentPosition = position;
            convertView.setOnClickListener(clickListener);
            return convertView;
        }
    }

    class ViewHolder {
        public int currentPosition;
        public TextView questionTitle;
        public TextView questionName;
    }
}

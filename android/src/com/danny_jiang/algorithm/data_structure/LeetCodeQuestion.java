package com.danny_jiang.algorithm.data_structure;

import java.util.List;

public class LeetCodeQuestion {

    private List<ArrayBean> array;

    public List<ArrayBean> getArray() {
        return array;
    }

    public void setArray(List<ArrayBean> array) {
        this.array = array;
    }

    public static class BaseBean {
        /**
         * title : LeetCode 1
         * name : 2 Sum
         * url : website for this Question
         */

        private String title;
        private String name;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ArrayBean extends BaseBean{

    }
}

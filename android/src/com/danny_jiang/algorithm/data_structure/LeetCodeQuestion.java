package com.danny_jiang.algorithm.data_structure;

import java.util.List;

public class LeetCodeQuestion {

    private List<ArrayBean> array;
    private List<LinklistBean> linklist;

    public List<ArrayBean> getArray() {
        return array;
    }

    public void setArray(List<ArrayBean> array) {
        this.array = array;
    }

    public List<LinklistBean> getLinklist() {
        return linklist;
    }

    public void setLinklist(List<LinklistBean> linklist) {
        this.linklist = linklist;
    }

    public static class BaseBean {
        /**
         * title : LeetCode 1
         * name : 2 Sum
         * url : https://mp.weixin.qq.com/s?__biz=MzU3Mjc5NjAzMw==&mid=2247483794&idx=1&sn=8d846ec57f5475d4c2548904490b7015&chksm=fcca3c7ccbbdb56aac76d8ae715c02499e0ad07a3b140786a05ca91affcf694fafff67203ffe&token=2141675310&lang=zh_CN#rd
         */

        private String title;
        private String name;
        private String url;
        private String sequence;
        private String tag;

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

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    public static class ArrayBean extends BaseBean{
    }

    public static class LinklistBean extends BaseBean{
    }
}

package com.neil.fish.entity;

import java.util.List;

/**
 * Created by neil on 2017/10/17 0017.
 */

public class ResBodyBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * title : 学霸合伙开烧烤店
         * url : http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%D1%A7%B0%D4%BA%CF%BB%EF%BF%AA%C9%D5%BF%BE%B5%EA
         */

        private String id;
        private String title;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}

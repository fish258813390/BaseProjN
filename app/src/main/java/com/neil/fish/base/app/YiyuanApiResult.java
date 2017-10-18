package com.neil.fish.base.app;

/**
 * Created by neil on 2017/10/17 0017.
 * 易源Api数据结果返回
 */
public class YiyuanApiResult<T> {

    /**
     * showapi_res_code : 0
     * showapi_res_error :""
     * showapi_res_body : {"list":[{"id":"1","title":"十九大主要议程","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%CA%AE%BE%C5%B4%F3%D6%F7%D2%AA%D2%E9%B3%CC"},{"id":"2","title":"留学生成日本校花","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%C1%F4%D1%A7%C9%FA%B3%C9%C8%D5%B1%BE%D0%A3%BB%A8"},{"id":"3","title":"日本高铁英国首秀","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%C8%D5%B1%BE%B8%DF%CC%FA%D3%A2%B9%FA%CA%D7%D0%E3"},{"id":"4","title":"上海惊现神户型","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%C9%CF%BA%A3%BE%AA%CF%D6%C9%F1%BB%A7%D0%CD"},{"id":"5","title":"移位左转路口启用","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%D2%C6%CE%BB%D7%F3%D7%AA%C2%B7%BF%DA%C6%F4%D3%C3"},{"id":"6","title":"朴槿惠公审首发言","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%C6%D3%E9%C8%BB%DD%B9%AB%C9%F3%CA%D7%B7%A2%D1%D4"},{"id":"7","title":"日本钢铁造假惊人","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%C8%D5%B1%BE%B8%D6%CC%FA%D4%EC%BC%D9%BE%AA%C8%CB"},{"id":"8","title":"智商130成绩差","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%D6%C7%C9%CC130%B3%C9%BC%A8%B2%EE"},{"id":"9","title":"湖南现珍稀青檀树","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%BA%FE%C4%CF%CF%D6%D5%E4%CF%A1%C7%E0%CC%B4%CA%F7"},{"id":"10","title":"最帅环卫工成网红","url":"http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw&fr=top1000&wd=%D7%EE%CB%A7%BB%B7%CE%C0%B9%A4%B3%C9%CD%F8%BA%EC"}]}
     */

    private int showapi_res_code;

    private String showapi_res_error;

    private T showapi_res_body; //

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public T getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(T showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}

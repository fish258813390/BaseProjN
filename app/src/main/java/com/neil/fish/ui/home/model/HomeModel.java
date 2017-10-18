package com.neil.fish.ui.home.model;


import com.neil.fish.http.BaseModel;
import com.neil.fish.http.HttpClient;
import com.neil.fish.http.HttpObserver;
import com.neil.fish.service.yiyuan.HotNewService;

/**
 * Created by neil on 2017/10/18 0018.
 * 获取火热搜索信息
 */
public class HomeModel extends BaseModel{

    public void getHotSearchRank(int page, String showapi_appid, String showapi_sign) {
        HttpClient.getService(HotNewService.class).getHotSearchRankList(page, showapi_appid, showapi_sign);
    }


    public void getHotSearchRank(int page, String showapi_appid, String showapi_sign, HttpObserver httpObserver) {
        startRequest(HttpClient.getService(HotNewService.class).getHotSearchRank(page, showapi_appid, showapi_sign),httpObserver);
    }

}

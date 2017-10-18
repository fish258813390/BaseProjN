package com.neil.fish.service.yiyuan;

import com.neil.fish.base.app.YiyuanApiResult;
import com.neil.fish.entity.HotBean;
import com.neil.fish.entity.ResBodyBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/18 0018.
 * 易源Api热点新闻接口
 */
public interface HotNewService {

    /**
     *
     * @param page
     * @param showapi_appid
     * @param showapi_sign
     * @return
     */
    @FormUrlEncoded
    @POST("738-1")
    Observable<YiyuanApiResult<List<HotBean>>> getHotSearchRankList(@Field("n") int page, @Field("showapi_appid") String showapi_appid, @Field("showapi_sign") String showapi_sign);

    @FormUrlEncoded
    @POST("738-1")
    Observable<YiyuanApiResult<ResBodyBean>> getHotSearchRank(@Field("n") int page, @Field("showapi_appid") String showapi_appid, @Field("showapi_sign") String showapi_sign);

}

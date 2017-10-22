package com.neil.fish.ui.home.model;


import com.alibaba.fastjson.JSON;
import com.neil.fish.base.app.YiyuanApiResult;
import com.neil.fish.entity.ResBodyBean;
import com.neil.fish.http.BaseModel;
import com.neil.fish.http.HttpClient;
import com.neil.fish.http.HttpObserver;
import com.neil.fish.service.yiyuan.HotNewService;
import com.neil.fish.utils.LogUtils;
import com.neil.fish.widget.dialog.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by neil on 2017/10/18 0018.
 * 获取火热搜索信息
 */
public class HomeModel extends BaseModel {

    public void getHotSearchRank(int page, String showapi_appid, String showapi_sign) {
        HttpClient.getService(HotNewService.class).getHotSearchRankList(page, showapi_appid, showapi_sign);
    }


    public void getHotSearchRank(int page, String showapi_appid, String showapi_sign, HttpObserver httpObserver) {
        startRequest(HttpClient.getService(HotNewService.class).getHotSearchRank(page, showapi_appid, showapi_sign), httpObserver);
    }

    // 默认 retrofit 写法
    public void getHotSearchRankDefault(int page, String showapi_appid, String showapi_sign) {
        HotNewService hotNewService = HttpClient.getService(HotNewService.class);
        Call<YiyuanApiResult<ResBodyBean>> call = hotNewService.getHotSearchRankDefault(page, showapi_appid, showapi_sign);
        call.enqueue(new Callback<YiyuanApiResult<ResBodyBean>>() {
            @Override
            public void onResponse(Call<YiyuanApiResult<ResBodyBean>> call, Response<YiyuanApiResult<ResBodyBean>> response) {
                LoadingDialog.cancelDialogForLoading();
                LogUtils.d("当前线程:" + Thread.currentThread().getName());
                // 在主线程中执行的，所以可以进行更新UI数据的操作
                YiyuanApiResult<ResBodyBean> result = response.body();
                LogUtils.e("相应数据：---->" + result.toString());
            }

            @Override
            public void onFailure(Call<YiyuanApiResult<ResBodyBean>> call, Throwable t) {

            }
        });
    }

    // 结合rxjava 无封装  订阅Observer 或Subscriber都行
    public void getHotSearchByRxAndRetrofit(int page, String showapi_appid, String showapi_sign) {
        HotNewService hotNewService = HttpClient.getService(HotNewService.class);
        Observable<YiyuanApiResult<ResBodyBean>> observable = hotNewService.getHotSearchRank(page, showapi_appid, showapi_sign);
        // 关联被观察者
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YiyuanApiResult<ResBodyBean>>() {
                    @Override
                    public void onCompleted() {
                        LoadingDialog.cancelDialogForLoading();
                        LogUtils.e("请求完成了----end");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoadingDialog.cancelDialogForLoading();
                        LogUtils.e("异常了---->" + e.getMessage());
                    }

                    @Override
                    public void onNext(YiyuanApiResult<ResBodyBean> resBodyBeanYiyuanApiResult) {
                        LogUtils.d("当前线程:" + Thread.currentThread().getName());
                        LogUtils.e("相应数据：---->" + resBodyBeanYiyuanApiResult.toString());
                    }

                    @Override
                    public void onStart() {
                        LogUtils.e("请求开始----start");
                    }
                });

    }


    // 采用观察者模式对数据进行处理
    public void getHotSearch(int page, String showapi_appid, String showapi_sign,Observer observer){
        HotNewService hotNewService = HttpClient.getService(HotNewService.class);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });
        Observable<YiyuanApiResult<ResBodyBean>> observable = hotNewService.getHotSearchRank(page, showapi_appid, showapi_sign);
        // subscribeOn 使用http协议获取数据(io操作)
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}

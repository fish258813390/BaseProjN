package com.neil.fish.ui.home.presenter;

import com.alibaba.fastjson.JSON;
import com.neil.fish.base.app.BasePresenter;
import com.neil.fish.base.app.YiyuanApiResult;
import com.neil.fish.entity.ResBodyBean;
import com.neil.fish.http.HttpObserver;
import com.neil.fish.ui.home.model.HomeModel;
import com.neil.fish.ui.home.view.HomeView;
import com.neil.fish.utils.LogUtils;
import com.neil.fish.widget.dialog.LoadingDialog;

import rx.Observer;

/**
 * Created by neil on 2017/10/18 0018.
 * 提交测试
 */
public class HomePresenter extends BasePresenter<HomeView, HomeModel> {

    public void getHotList(int page, String showapi_appid, String showapi_sign) {

        mModel.getHotSearchRank(page, showapi_appid, showapi_sign, new HttpObserver<ResBodyBean>() {
            @Override
            public void onSuccess(ResBodyBean result) {

                LogUtils.e("<------回调返回数据---->" + JSON.toJSON(result));
            }
        });

//        mRxManage.add(mModel.getData(product_category_id, page_index).subscribe(new RxSubscriber<Object>(mContext, true) {
//            @Override
//            protected void _onNext(Object s) {
//                mView.showData(s.toString());
//            }
//
//            @Override
//            protected void _onError(String message) {
//                Log.e("onError", message);
//            }
//        }));
    }

    // 默认retrofit
    public void getHotListDefault(int page, String showapi_appid, String showapi_sign) {
        mModel.getHotSearchRankDefault(page, showapi_appid, showapi_sign);
    }

    // retrofit  + rxjava
    public void getHotListByRxAndRetrofit(int page, String showapi_appid, String showapi_sign) {
        mModel.getHotSearchByRxAndRetrofit(page, showapi_appid, showapi_sign);
    }

    public void getHotListNew(int page, String showapi_appid, String showapi_sign) {
        mModel.getHotSearch(page, showapi_appid, showapi_sign, new Observer<YiyuanApiResult<ResBodyBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoadingDialog.cancelDialogForLoading();
                LogUtils.e("获取数据失败:" + e.getMessage());
            }

            @Override
            public void onNext(YiyuanApiResult<ResBodyBean> result) {
                LoadingDialog.cancelDialogForLoading();
                ResBodyBean resBodyBean = result.getShowapi_res_body();
                mView.showData(resBodyBean == null ? "获取数据失败" : resBodyBean.toString());
            }
        });
    }

}

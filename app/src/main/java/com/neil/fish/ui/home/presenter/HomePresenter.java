package com.neil.fish.ui.home.presenter;

import com.alibaba.fastjson.JSON;
import com.neil.fish.base.app.BasePresenter;
import com.neil.fish.entity.ResBodyBean;
import com.neil.fish.http.HttpObserver;
import com.neil.fish.ui.home.model.HomeModel;
import com.neil.fish.ui.home.view.HomeView;
import com.neil.fish.utils.LogUtils;

/**
 * Created by neil on 2017/10/18 0018.
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
}

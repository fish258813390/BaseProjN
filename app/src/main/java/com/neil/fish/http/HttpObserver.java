package com.neil.fish.http;


import com.alibaba.fastjson.JSON;
import com.neil.fish.base.app.YiyuanApiResult;
import com.neil.fish.utils.LogUtils;
import com.neil.fish.utils.ToastUtils;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by liyugang on 10/26/15.
 */
public abstract class HttpObserver<T> implements Observer<YiyuanApiResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
//        LoadingUtils.dismiss(); // 全局对话框
        LogUtils.e("onError------>"+e.getMessage());
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showLong("请求超时，稍后再试");
            LogUtils.e("请求超时，稍后再试");
            return;
        }
        if (e instanceof HttpException) {
            String message = ((HttpException) e).message();
            if (null == message || "".equals(message)) {
                ToastUtils.showLong("服务器开小差了，稍后再试...");
                LogUtils.e("服务器开小差了，稍后再试...");
            } else {
                //    ToastUitl.showLong(message);
            }

        }
    }


    @Override
    public void onNext(YiyuanApiResult<T> t) {
        LogUtils.d("【请求成功，返回数据】--->" + JSON.toJSON(t.getShowapi_res_body()));
        if (t.getShowapi_res_code() == 0) {
            onSuccess(t.getShowapi_res_body());
        } else {
            onFailure(t.getShowapi_res_error());
        }
    }

    public abstract void onSuccess(T t);

    public void onNetworkError() {

    }

    public void onFailure(String errorMsg) {
//        LoadingUtils.dismiss();
        LogUtils.d("请求失败------->" + errorMsg);
        if (errorMsg.equals("请登录")) {
//            Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity_.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            BaseApplication.getInstance().startActivity(intent);

        }
//        if (!errorMsg.equals("没有数据") && !errorMsg.equals("暂无数据") && !errorMsg.equals("查询失败")) {
//            PromptUtils.showToast(errorMsg, 300);
//        }

    }
}

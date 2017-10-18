package com.neil.fish.http;



import com.neil.fish.base.app.BaseApplication;
import com.neil.fish.base.rx.RxSchedulers;
import com.neil.fish.utils.NetWorkUtils;

import rx.Observable;

/**
 * Created by liyugang on 9/22/16.
 */
public abstract class BaseModel {

    protected static void startRequest(Observable observable, HttpObserver observer, Observable.Transformer bindUntilEvent) {
        if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            observable.compose(RxSchedulers.io_main()).compose(bindUntilEvent).subscribe(observer);
        } else {
            observer.onNetworkError();
        }
    }

    protected static void startRequest(Observable observable, HttpObserver observer) {
        if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            observable.compose(RxSchedulers.io_main()).subscribe(observer);
        } else {
            observer.onNetworkError();
        }
    }


//    protected static void startRequestNew(Observable observable, HttpObserverNew observer) {
//        if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
//            observable.compose(RxSchedulers.io_main()).subscribe(observer);
//        } else {
//            observer.onNetworkError();
//        }
//    }

//    protected static void startRequest(Observable observable, MiguHttpObserver observer) {
//        if (NetWorkUtils.isConnectWithTip("网络未连接")) {
//            observable.compose(RxSchedulers.io_main()).subscribe(observer);
//        } else {
//            observer.onNetworkError();
//        }
//    }
}

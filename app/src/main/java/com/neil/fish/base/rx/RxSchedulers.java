package com.neil.fish.base.rx;

import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.neil.fish.http.Api.map;


/**
 * RxJava调度管理
 * Created by xsf
 * on 2016.08.14:50
 */
public class RxSchedulers {

    public static <T> Observable.Transformer<T, T> io_main() {
        Log.e("map", map.toString());
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}

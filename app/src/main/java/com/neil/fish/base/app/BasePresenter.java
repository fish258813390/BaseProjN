package com.neil.fish.base.app;

import android.content.Context;


import com.neil.fish.base.rx.RxManager;

import java.lang.ref.WeakReference;

/**
 * Created by wy on 2017/8/24.
 */
public class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public RxManager mRxManage = new RxManager();
    private WeakReference<T> mViewRef;
    public T mView;

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    //关联
    public void attach(T view) {
        mViewRef = new WeakReference<>(view);
    }

    //解除关联
    public void detach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    //获取view
    public T getView() {
        return mViewRef.get();
    }

    public void onStart() {

    }

    public void onDestroy() {
        mRxManage.clear();
    }

}

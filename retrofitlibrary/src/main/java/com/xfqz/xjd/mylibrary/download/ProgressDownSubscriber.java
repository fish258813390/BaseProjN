package com.xfqz.xjd.mylibrary.download;

import java.lang.ref.WeakReference;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 用于在http请求开始时,自动显示一个progressDialog
 * 在http请求结束时，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * <p>
 * <p>
 * 实现DownloadProgressListener 和自带的回调一起组成我们需要的回调结果
 * 传入DownInfo数据，通过糊掉设置DownInfo的不同状态，保存状态
 * 通过RxAndroid将进度回调到主线程中(如果不需要进度，最好去掉该处理避免主线程处理负担)
 * update 进度
 * </p>
 * Created by neil on 2017/11/5
 */
public class ProgressDownSubscriber<T> extends Subscriber<T> implements DownloadProgressListener {

    private WeakReference<HttpProgressOnNextListener> mSubscriberOnNextListener; // 弱引用结果回调
    private DownInfo downInfo; // 下载数据

    public ProgressDownSubscriber(DownInfo downInfo) {
        this.mSubscriberOnNextListener = new WeakReference<>(downInfo.getListener());
        this.downInfo = downInfo;
    }

    /**
     * 订阅开始时调用
     * 显示ProgerssDialog
     */
    @Override
    public void onStart() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
        downInfo.setState(DownState.START);
    }

    /**
     * 完成,隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
        downInfo.setState(DownState.FINISH);
    }

    /**
     * 对错误进行统一处理
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        // 停止下载
        HttpDownManager.getInstance().stopDownload(downInfo);
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
        downInfo.setState(DownState.ERROR);
    }

    /**
     * 将onNext方法中的返回结果交给activity 或framgent自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    @Override
    public void update(long read, long count, boolean done) {
        if (downInfo.getCountLength() > count) {
            read = downInfo.getCountLength() - count + read;
        } else {
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);
        if (mSubscriberOnNextListener.get() != null) {
            // 接收进度消息，造成UI阻塞，如果不需要进度可去掉实现逻辑
            rx.Observable.just(read).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    // 如果暂停或者停止状态延迟，不需要继续发送回调，影响显示
                    if (downInfo.getState() == DownState.PAUSE ||
                            downInfo.getState() == DownState.STOP) {
                        return;
                    }
                    downInfo.setState(DownState.DOWN);
                    mSubscriberOnNextListener.get().updateProgress(aLong, downInfo.getCountLength());
                }
            });
        }
    }
}

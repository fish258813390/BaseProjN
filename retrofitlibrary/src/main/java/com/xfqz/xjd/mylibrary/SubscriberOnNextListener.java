package com.xfqz.xjd.mylibrary;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable e);

}
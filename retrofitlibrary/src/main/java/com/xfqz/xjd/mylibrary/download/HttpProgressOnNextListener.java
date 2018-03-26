package com.xfqz.xjd.mylibrary.download;

/**
 * 回调封装类
 * 和DownloadProgressListener 不同，这里是下载这个过程中的监听回调，
 * DownloadProgressListener只是进度的监听,通过抽象类，可以自由选择需要覆盖的类，不需要完全覆盖
 * <p>
 * Created by neil on 2017/11/5 0005.
 */
public abstract class HttpProgressOnNextListener<T> {

    /**
     * 成功后的回调方法
     *
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 完成下载
     */
    public abstract void onComplete();

    /**
     * 更新下载进度
     *
     * @param readLength  读取的进度
     * @param countLength 总进度
     */
    public abstract void updateProgress(long readLength, long countLength);

    /**
     * 失败或错误方法 (主动调用,更加灵活)
     *
     * @param e
     */
    public void onError(Throwable e) {

    }

    /**
     * 暂停下载
     */
    public void onPause() {

    }

    /**
     * 停止下载销毁
     */
    public void onStop() {

    }

}

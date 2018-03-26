package com.xfqz.xjd.mylibrary.download;

/**
 * 成功回调处理
 * Created by neil on 2017/11/5 0005.
 */
public interface DownloadProgressListener {

    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);

}

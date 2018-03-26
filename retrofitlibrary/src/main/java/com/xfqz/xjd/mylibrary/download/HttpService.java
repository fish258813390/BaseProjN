package com.xfqz.xjd.mylibrary.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 断点续传下载
 * Created by neil on 2017/11/5 0005.
 */
public interface HttpService {

    /**
     * Streaming大文件需要加入这个判断，防止下载过程中写到内存中
     *
     * @param start
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}

package com.xfqz.xjd.mylibrary.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by neil on 2017/11/5 0005.
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 拦截请求,获得响应结果
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new DownloadResponseBody(response.body(),listener)).build();
    }


}

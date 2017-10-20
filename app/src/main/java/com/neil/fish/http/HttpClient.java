package com.neil.fish.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neil.fish.base.app.BaseApplication;
import com.neil.fish.utils.LogUtils;
import com.neil.fish.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class HttpClient {
    private static final String UTF8 = "UTF8";
    private static final String EXTRANET_BASE_URL = "http://www.syzx-edu.com/";
    private static final String INTRANET_BASE_URL = "http://www.syzx-edu.com/";//测试服务器

    private static String baseUrl;
    private static Retrofit retrofit;
    private static OkHttpClient httpClient;
    private static Map<String, Object> httpServiceMap = new HashMap<>();
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "retrofitCache");
            final Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            // 1.校验token是否有效
//                    if (StringUtils.isNotBlank(PreferenceUtils.getToken())) {
//                        request = request.newBuilder().headers(request.headers()).header("tokenid", getToken())
//                                .build();
//                    }

                            Headers headers = request.headers(); // 获取请求头
                            String url = request.url().toString();  // 请求地址
                            String requestParam = headers.toString();
                            LogUtils.d("请求信息: ", url + "," + requestParam);
                            // 2.配置缓存策略 及校验网络是否有效
                            String cacheControl = request.cacheControl().toString();
                            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                                request = request.newBuilder()
                                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                                        .build();
                            }

                            // 3.获得请求数据及相应数据
                            Response response = chain.proceed(chain.request());
                            okhttp3.MediaType mediaType = response.body().contentType();
                            ResponseBody originalBody = response.body();
                            String content = "";
                            if (null != originalBody) {
                                content = originalBody.string();
                            }
                            LogUtils.d("requestBody's contentType : " + mediaType.toString());


                            //请求头
                            LogUtils.d(request.url().toString(), content);
                            return response.newBuilder()
                                    .body(ResponseBody.create(mediaType, content))
                                    .build();

//                            return chain.proceed(request);
                        }
                    })
                    .cache(cache)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            //  获取retrofit对象
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient).build();
        }
        return retrofit;
    }

    public static void resetHttpClient() {
        httpClient = null;
        retrofit = null;
    }

    private static String getToken() {
//        return PreferenceUtils.getToken();
        return "1111";
    }

    private static String getBaseUrl() {
        if (baseUrl == null) {
//            baseUrl = Version.getApiSource().equals(Version.ApiSource.EXTRANET) ? EXTRANET_BASE_URL : INTRANET_BASE_URL;
            baseUrl = AppConfig.BASE_URL;
        }
        return baseUrl;
    }

    public static <T> T getService(Class<T> serviceClass) {
        String serviceName = serviceClass.getName();
        Object service = httpServiceMap.get(serviceName);
        if (service == null) {
            service = getRetrofit().create(serviceClass);
            httpServiceMap.put(serviceName, service);
        }
        return (T) service;
    }

}

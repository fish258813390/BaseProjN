package com.xfqz.xjd.mylibrary;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * lanji
 * 作者：User on 2016/11/29 10:24
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            System.out.print("静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newSession = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("Cookie", "JSESSIONID=" + newSession)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        System.out.print("response:--------"+response.code());
        if (response.code() == 200) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        Map<String, String> map = new HashMap<>();
//        map.put("mobile", BaseApplication.loginBean.getUser().mobile);
//        map.put("token", BaseApplication.loginBean.getToken().token);
        //map.put("app_id", NetConstants.APP_ID);
//        map.put("sign", CommonUtils.md5(NetConfig.AesCodeMsg(map)));

       // System.out.print("tokenssssss:---------"+NetConstants.APP_ID);
//        Gson gson = new Gson();
//        String gsonContent = gson.toJson(map);
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("data", gsonContent);
        //String response = OkHttpUtils.getInstance().postAsString(NetConstants.UPDATETOKEN, map);
        String response="suiyixiede";
        return response;
    }

}

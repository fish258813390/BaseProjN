package com.neil.fish.base.app;


import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.neil.fish.utils.LogUtils;


/**
 * 主工程
 * Created by ndy on 2016/05/19.
 */
public class BaseApplication extends Application {
    /**
     * 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了
     */
    private static BaseApplication mInstance;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    public static Context context;
    public static String version;//版本号
    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);

        context = getApplicationContext();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        //获取版本号

        //是否打印日志
        LogUtils.isDebug = true;
        initUmeng();


    }

    public static Context getAppContext() {
        return context;
    }

    private void initUmeng() {
        //推送
    }

    public static BaseApplication getApplication() {
        return mInstance;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

}

package com.xfqz.xjd.mylibrary.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 下载管理类
 * Created by neil on 2017/11/5 0005.
 */
public class HttpDownManager {

    /**
     * 正在下载的数据
     */
    private HashSet<DownInfo> downInfos;

    /**
     * 回调sub队列
     */
    private HashMap<String, ProgressDownSubscriber> subMap;

    /**
     * 单例
     */
    private volatile static HttpDownManager INSTANCE;

    private HttpDownManager() {
        downInfos = new HashSet<>();
        subMap = new HashMap<>();
    }

    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 开始下载
     * <p>
     * 开始下载需要记录下载的service，避免每次都重复创建，然后请求service接口，得到RespoonseBody数据后将数据流写入到本地文件中
     * 注意 6.0后需要提起那申请权限
     * </p>
     */
    public void startDownload(final DownInfo info) {
        // 正在下载的不处理
        if (info == null || subMap.get(info.getUrl()) != null) {
            return;
        }
        // 添加回调处理类
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(info);
        // 记录回调sub
        subMap.put(info.getUrl(), subscriber);
        // 获取service，多次请求公用一个service
        HttpService httpService;
        if (downInfos.contains(info)) {
            httpService = info.getHttpService();
        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(info.getConnectionTime(), TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(info.getBaseUrl())
                    .build();
            httpService = retrofit.create(HttpService.class);
            info.setHttpService(httpService);
        }

        // 得到rx对象上一次下载的位置，从此处开始下载
        httpService.download("bytes=" + info.getReadLength() + "-", info.getUrl())
                // 指定线程
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                // 失败后的retry配置
//                .retryWhen(new RetryWhenN)
                // 读取下载文写入件
                .map(new Func1<ResponseBody, DownInfo>() {

                    @Override
                    public DownInfo call(ResponseBody responseBody) {
                        // 写入文件
                        try {
                            writeCache(responseBody, new File(info.getSavePath()), info);
                        } catch (IOException e) {
                            // 失败抛异常处理
                            e.printStackTrace();
                        }
                        return info;
                    }
                })
                // 回调线程
                .observeOn(AndroidSchedulers.mainThread())
                // 数据回调
                .subscribe(subscriber);
    }

    /**
     * 停止下载
     *
     * @param info
     */
    public void stopDownload(DownInfo info) {
        if (info == null) {
            return;
        }
        info.setState(DownState.STOP);
        info.getListener().onStop();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getUrl());
            subscriber.unsubscribe(); // 取消订阅
            subMap.remove(info.getUrl());
        }

        // 同步数据库操作 ...

    }

    public void pauseDownload(DownInfo info) {
        if (info == null) {
            return;
        }
        info.setState(DownState.PAUSE);
        info.getListener().onPause();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getUrl());
            subscriber.unsubscribe();
            subMap.remove(info.getUrl());
        }

        // 需要将info信息写到数据库中
    }

    /**
     * 停止全部下载
     * 调用subscriber.unsubscribe()解除监听，然后remove记录的下载数据和sub回调,并且设置下载状态
     */
    public void stopAllDown() {
        for (DownInfo downInfo : downInfos) {
            stopDownload(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll() {
        for (DownInfo downInfo : downInfos) {
            pauseDownload(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }


    /**
     * 写入文件
     * <p>
     * 注意：一开始调用进度回调是第一次写入在进度回调之前，所以需要判断一次downinfo是否获取到下载总长度，
     * 没有就选择当前ResponseBody 读取长度为总长度
     * </p>
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public void writeCache(ResponseBody responseBody, File file, DownInfo info) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.getCountLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getCountLength();
        }
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 8];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }


}

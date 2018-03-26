package com.neil.fish.ui.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.neil.fish.R;
import com.neil.fish.widget.ProgressWebView;

/**
 * @author neil
 * @date 2018/1/10
 */

public class WebviewActivity extends Activity {

    private ProgressWebView progressWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_webview);
        progressWebView = findViewById(R.id.web_view);


        progressWebView.getSettings().setJavaScriptEnabled(true);
        progressWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
//        String url ="http://mp.weixin.qq.com/s?__biz=MzAwODQ5MTA2NQ==&mid=402389923&idx=1&sn=3c89c329e7bf83ce8ff2364726ebd6a7#rd";
        String url ="https://github.com/";
        progressWebView.loadUrl(url);
    }
}

package me.wally.gankio.controller;


import android.os.Bundle;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import me.wally.gankio.R;
import me.wally.gankio.UIApplication;

/**
 * Package: me.wally.gankio.controller
 * FileName: WebBroswerViewController
 * Date: on 2018/11/2  下午12:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class WebBrowserViewController extends BaseUIViewController {
    public static final String KEY_URL = "key_url";

    private WebView mWebView;

    private String targetUrl;

    public static WebBrowserViewController newInstance(String url) {
        WebBrowserViewController controller = new WebBrowserViewController();
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        controller.setArguments(args);
        return controller;
    }

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        targetUrl = getArguments().getString(KEY_URL);
    }

    @Override
    public int onLayoutId() {
        return R.layout.page_web_browser;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mWebView = getView().findViewById(R.id.web_view);
        //初始化WebView
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(targetUrl);
    }

    @Override
    public String getPageTitle() {
        return UIApplication.shareInstance().getResources().getString(R.string.page_web_browser_title);
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return super.onBackPressed();
        }
    }
}

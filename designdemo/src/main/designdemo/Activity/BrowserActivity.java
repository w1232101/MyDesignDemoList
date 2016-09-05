package com.example.administrator.designdemo.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.designdemo.R;
import com.example.administrator.designdemo.uitle.NetUtils;
import com.example.administrator.designdemo.uitle.StatusBarCompat;
import com.example.administrator.designdemo.view.CircleProgressView;

import butterknife.Bind;

/**
 * Created by hcc on 16/8/7 14:12
 * 100332338@qq.com
 * <p>
 * 浏览器界面
 */
public class BrowserActivity extends BaseActivity implements DownloadListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.circle_progress)
    CircleProgressView progressBar;

    @Bind(R.id.webView)
    WebView mWebView;

    @Bind(R.id.bg)
    View bg;

    private final Handler mHandler = new Handler();

    private String url, mTitle;

    private WebViewClientBase webViewClient = new WebViewClientBase();

    private static final String EXTRA_URL = "url";

    private static final String EXTRA_TITLE = "title";


    @Override
    public int getLayoutId() {

        return R.layout.activity_bilibili_html;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(EXTRA_URL);
            mTitle = intent.getStringExtra(EXTRA_TITLE);
        }


        setupWebView();
    }

    @Override
    public void initToolBar() {

        mToolbar.setNavigationIcon(R.drawable.action_button_back_pressed_light);
        mToolbar.setTitle(mTitle == null ? "详情" : mTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public void setStatusBarColor() {
        StatusBarCompat.compat(this);
    }


    public static void launch(Activity activity, String url, String title) {

        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        activity.startActivity(intent);
    }


    private void setupWebView() {

        progressBar.spin();
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //设定支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);// 设定支持缩放
        webSettings.setDisplayZoomControls(false);//去掉放缩控制条

        mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.setWebViewClient(webViewClient);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setDownloadListener(this);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetUtils.hasNetWorkConection(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);   // 根据cache-control决定是否从网络上取数据。
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);   //优先加载缓存
        }

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                AlertDialog.Builder b2 = new AlertDialog
                        .Builder(BrowserActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                result.confirm();
                            }
                        });

                b2.setCancelable(false);
                b2.create();
                b2.show();
                return true;
            }
        });
        //网页版夜间模式
//        MyThemeManager.setupWebView(this, mWebView, "#747D73", "#ccc", "#FF9933");

        mWebView.loadUrl(url);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        Log.i("wjx", "sp:"+sp.getInt("SkinKey", 0));
        bg.setVisibility( sp.getInt("SkinKey", 0)==0?View.GONE:View.VISIBLE);

    }

    // 改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回上一页面
//				WebActivity.this.finish();
                return true;
            } else {
                // System.exit(0);// 退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public class WebViewClientBase extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.stopSpinning();
            mWebView.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            view.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        try {
            startActivity(intent);
            return;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        mHandler.post(new Runnable() {

            @Override
            public void run() {

                mWebView.loadUrl("javascript:initialize()");
            }
        });
    }

    @Override
    protected void onPause() {

        mWebView.reload();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mWebView.destroy();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}

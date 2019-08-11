package com.tylx.leasephone.util.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityWebviewBinding;


public abstract class WebViewActivity extends BaseActivity implements IWebPageView {

    private String _title;
    private boolean _firstResume = true;
    private boolean _loadSuccess = false;
    ActivityWebviewBinding binding;

    protected abstract String getUrl();

    protected void goBack() {
        finish();
    }

    protected void goNext() {
        binding.webview.reload();
    }

    @Override
    protected void setTitle(String title) {
        _title = title;
        super.setTitle(title);
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_webview, _containerLayout, false);
        return binding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FIXME  设置左右按钮

        WebSettings settings = binding.webview.getSettings();
        binding.webview.setBackgroundColor(0);
        settings.setUseWideViewPort(true);
        binding.webview.setBackgroundColor(Color.TRANSPARENT);
//        if (Build.VERSION.SDK_INT >= 19) {//硬件加速器的使用
//            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else {
//            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        settings.setJavaScriptEnabled(true);
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading("正在加载页面…");
                _loadSuccess = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();

                if (_loadSuccess) {
                    if (_title == null) {
                        setTitle(view.getTitle());
                    }
                } else {
                    //ToastManager.manager.show(WebViewActivity.this, "加载页面失败, 请刷新重试");
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                _loadSuccess = false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (_firstResume) {
            binding.webview.loadUrl(getUrl());
            _firstResume = false;
        }
    }

    public void onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack();
        } else {
            goBack();
        }
    }
}

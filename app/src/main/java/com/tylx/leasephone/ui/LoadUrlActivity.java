package com.tylx.leasephone.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityLoadUrlBinding;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.view.MyWebView;

public class LoadUrlActivity extends BaseActivity {

    ActivityLoadUrlBinding binding;
    public MyWebView webView;
    String url;
    @Override
    protected void onPostInject() {
        super.onPostInject();

        url = getIntent().getStringExtra("url");
        webView = binding.webview;
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
////        webView.loadDataWithBaseURL(null, ((GoodsDetailActivity) mActivity).goodsModel.introducePic, "text/html; charset=UTF-8", null, null);
//        webView.getSettings().setJavaScriptEnabled(true);//启用js
//        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置不滚动
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
//                return true;
//            }
//        });
        webView.loadUrl(url);

    }
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_load_url, _containerLayout, false);
        return binding.getRoot();
    }
    public static void into(Activity mActivity,String url){
        Intent intent = new Intent(mActivity,LoadUrlActivity.class);
        intent.putExtra("url",url);
        mActivity.startActivity(intent);
    }
}

package com.tylx.leasephone.ui.activity.lease_shop.order;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tylx.leasephone.databinding.ActivityMyWebViewBinding;
import com.tylx.leasephone.util.activity.BaseActivity;

public class MyWebView extends BaseActivity {
    ActivityMyWebViewBinding binding;
    public static final int REQUEST_CODE = 39;
    public static void into(Activity mActivity,String url){
        Intent intent = new Intent(mActivity,MyWebView.class);
        intent.putExtra("url",url);
        mActivity.startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    protected void onPostInject() {
        super.onPostInject();
        String url = getIntent().getStringExtra("url");
        if(TextUtils.isEmpty(url)){
            showToast("未获取到相关链接~~");
            finish();
            return;
        }
        binding.webview.loadUrl(url);
        binding.webview.setWebViewClient(new WebViewClient() {

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
                if (url.contains("api.bookcoins.tk")) {
                    setResult(RESULT_OK);
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        binding = ActivityMyWebViewBinding.inflate(getLayoutInflater(),_containerLayout,false);
        return binding.getRoot();
    }
}

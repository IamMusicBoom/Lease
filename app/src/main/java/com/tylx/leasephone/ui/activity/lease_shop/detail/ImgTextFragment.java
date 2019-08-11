package com.tylx.leasephone.ui.activity.lease_shop.detail;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmetImgTextBinding;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.view.MyWebView;

/**
 * Created by wangm on 2017/6/22.
 * 图片参数介绍
 */

public class ImgTextFragment extends BaseFragment{
    public FragmetImgTextBinding binding;
    public MyWebView webView;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StringBuilder sb = new StringBuilder();//拼接一段HTML代码
sb.append("<html>");
sb.append("<head>");
sb.append("<title>欢迎你</title>");
sb.append("</head>");
sb.append("<body>");
sb.append("<h2>欢迎你访问<a href=\"http://www.crazyit.org\">" +"疯狂Java联盟</a></h2>");
sb.append("</body>");
sb.append("</html>");
        webView = binding.webview;
        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.loadDataWithBaseURL(null, ((GoodsDetailActivity) mActivity).goodsModel.introducePic, "text/html; charset=UTF-8", null, null);
        webView.getSettings().setJavaScriptEnabled(true);//启用js
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置不滚动
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }
        });

        binding.webview.setCallback(new MyWebView.GenericMotionCallback() {
            @Override
            public boolean onGenericMotionEvent(MotionEvent event) {
                return  ((GoodsDetailActivity) mActivity).binding.scrollView.onGenericMotionEvent(event);
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragmet_img_text, _containerLayout, false);
        return binding.getRoot();
    }
}

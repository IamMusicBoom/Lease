package com.tylx.leasephone.ui.activity.lease_shop.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmetParamterBinding;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.view.MyWebView;

/**
 * Created by wangm on 2017/6/22.
 * 文字参数介绍
 */

public class ParamterFtagmet extends BaseFragment{
    FragmetParamterBinding binding;
    MyWebView webView;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        StringBuilder sb = new StringBuilder();//拼接一段HTML代码
//        sb.append("<html>");
//        sb.append("<head>");
//        sb.append("<title>欢迎你</title>");
//        sb.append("</head>");
//        sb.append("<body>");
//        sb.append("<h2>欢迎你访问<a href=\"http://www.crazyit.org\">" +"疯狂Java联盟</a></h2>");
//        sb.append("</body>");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        sb.append("</html>");
        webView = binding.webview;
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        if(((GoodsDetailActivity) mActivity).goodsModel!=null && ((GoodsDetailActivity) mActivity).goodsModel.introducePar !=null){
            webView.loadDataWithBaseURL(null, ((GoodsDetailActivity) mActivity).goodsModel.introducePar, "text/html; charset=UTF-8", null, null);
        }

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
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragmet_paramter, _containerLayout, false);
        return binding.getRoot();
    }
}

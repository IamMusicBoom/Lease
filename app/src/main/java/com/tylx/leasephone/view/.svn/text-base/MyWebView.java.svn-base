package com.tylx.leasephone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by wangm on 2017/7/27.
 */

public class MyWebView extends WebView{
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (callback != null)
            return callback.onGenericMotionEvent(event);
        return super.onGenericMotionEvent(event);
    }
    //定义一个接口，把滚动事件传递出去
    public interface GenericMotionCallback {
        boolean onGenericMotionEvent(MotionEvent event);
    }
    GenericMotionCallback callback;

    public void setCallback(GenericMotionCallback callback) {
        this.callback = callback;
    }
}

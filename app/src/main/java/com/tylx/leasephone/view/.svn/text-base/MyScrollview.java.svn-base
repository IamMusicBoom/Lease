package com.tylx.leasephone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/6/13.
 */
public class MyScrollview extends ScrollView {
    _onScrollChange myScrollChange;

    public void setMyScrollChange(_onScrollChange myScrollChange) {
        this.myScrollChange = myScrollChange;
    }

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (myScrollChange != null)
            myScrollChange._onScrollChanged(l, t, oldl, oldt);
    }

    public interface _onScrollChange {
        public void _onScrollChanged(int l, int t, int oldl, int oldt);
    }
}

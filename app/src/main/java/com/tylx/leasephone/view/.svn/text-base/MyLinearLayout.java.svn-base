package com.tylx.leasephone.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by wangm on 2017/7/12.
 */

public class MyLinearLayout extends LinearLayout {
    private Context context;
    public MyLinearLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }



    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private void init() {
        setGravity(HORIZONTAL);
        if(getChildCount()==2){
            addView(new MyLinearLayout(context));
        }
    }
}

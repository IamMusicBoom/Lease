package com.tylx.leasephone.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;


/**
 * Created by wfpb on 15/7/7.
 * 导航栏
 */
public class NavigationBar extends RelativeLayout {

    public TextView mTxtTitle, nav_bar_righttext;
    public ImageView mIvBack, mIvRight;
    private TextView mLeft;
    private LinearLayout status_bar;
    private RelativeLayout layoutbar;

    public NavigationBar(Context context) {
        super(context);
        initWidthContext(context);
    }

    public NavigationBar(Context context, boolean w) {
        super(context);
        initWidthContext(context, w);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidthContext(context);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidthContext(context);
    }

    protected void initWidthContext(Context context) {
        initWidthContext(context, false);
    }

    protected void initWidthContext(Context context, boolean w) {
//        if (!w) {
//            LayoutInflater.from(context).inflate(R.layout.common_navigationbar, this, true);
//        }else{
        LayoutInflater.from(context).inflate(R.layout.common_navigationbar_w, this, true);
//        }

        mIvBack = (ImageView) findViewById(R.id.nav_bar_back);
        mIvRight = (ImageView) findViewById(R.id.nav_bar_right);
        mTxtTitle = (TextView) findViewById(R.id.nav_bar_title);
        mLeft = (TextView) findViewById(R.id.nav_bar_lefttext);
        status_bar = (LinearLayout) findViewById(R.id.status_bar);
        layoutbar = (RelativeLayout) findViewById(R.id.layoutbar);
        nav_bar_righttext = (TextView) findViewById(R.id.nav_bar_righttext);
    }

    public void showStatus() {
        status_bar.setVisibility(VISIBLE);
        //都没有设置值
        if ((mIvBack.getVisibility() | mLeft.getVisibility()) == 8 && mTxtTitle.getText().equals(""))
            layoutbar.setVisibility(GONE);
    }

    public void registerBackText(String text, OnClickListener listener) {
        mIvBack.setVisibility(View.GONE);
        mLeft.setVisibility(View.VISIBLE);
        layoutbar.setVisibility(VISIBLE);
        mLeft.setOnClickListener(listener);
        mLeft.setText(text);
    }

    public void setTitle(String title) {
        mTxtTitle.setVisibility(View.VISIBLE);
        layoutbar.setVisibility(VISIBLE);
        mTxtTitle.setText(title);
    }

    public CharSequence getTitle() {
        return mTxtTitle.getText();
    }

    public void setTitle(int resid) {
        mTxtTitle.setVisibility(View.VISIBLE);
        layoutbar.setVisibility(VISIBLE);
        mTxtTitle.setText(resid);
    }

    public void registerRightTitle(String title, OnClickListener listener) {

    }

    public void registerRight(OnClickListener listener, int resId) {
        layoutbar.setVisibility(VISIBLE);
        if (listener == null) {
            mIvRight.setVisibility(View.INVISIBLE);
        } else {
            mIvRight.setImageResource(resId);
            mIvRight.setVisibility(View.VISIBLE);
            mIvRight.setOnClickListener(listener);
        }
    }

    public void registerRightText(OnClickListener listener, String text) {
        layoutbar.setVisibility(VISIBLE);
        if (text == null) {
            nav_bar_righttext.setVisibility(View.INVISIBLE);
            return;
        }
        if (listener == null) {
            nav_bar_righttext.setVisibility(View.INVISIBLE);
        } else {
            nav_bar_righttext.setText(text);
            nav_bar_righttext.setVisibility(View.VISIBLE);
            nav_bar_righttext.setOnClickListener(listener);
        }
    }

    public void registerBack(OnClickListener listener, int resId) {
        layoutbar.setVisibility(VISIBLE);
        if (listener == null) {
            mIvBack.setVisibility(View.INVISIBLE);
        } else {
            mIvBack.setImageResource(resId);
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(listener);
        }
    }

    public void registerBack(OnClickListener listener) {
        layoutbar.setVisibility(VISIBLE);
        if (listener == null) {
            mIvBack.setVisibility(View.INVISIBLE);
        } else {
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(listener);
        }
    }

    public View getmTxtBack() {
        return mIvBack;
    }

}

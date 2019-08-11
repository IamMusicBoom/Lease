package com.tylx.leasephone.home_view_pager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by wangm on 2017/7/24.
 * 滑动平移动画
 */

public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}

/*
 * AUTHOR：Yolanda
 * 
 * DESCRIPTION：create the File, and add the content.
 *
 * Copyright © ZhiMore. All Rights Reserved
 *
 */
package com.tylx.leasephone.home_view_pager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tylx.leasephone.R;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;

import java.util.List;

/**
 * Created by Yolanda on 2016/5/5.
 *
 * @author Yolanda; QQ: 757699476
 * ViewPager的adapter
 */
public class BannerAdapter extends PagerAdapter {

    private Activity mContext;

    private List<String> resIds;

    public BannerAdapter(Activity context) {
        this.mContext = context;
    }

    public void update(List<String> resIds) {
        if (this.resIds != null)
            this.resIds.clear();
        if (resIds != null)
            this.resIds = resIds;
    }

    @Override
    public int getCount() {
        return resIds == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        // 如果是http://www.xx.com/xx.png这种连接，这里可以用ImageLoader之类的框架加载
//        imageView.setImageResource(resIds.get(position % resIds.size()));
//        if(null != mContext){
//            Glide.with(mContext).load(resIds.get(position % resIds.size())).centerCrop().error(R.mipmap.default_face).into(imageView);
            ProbjectUtil.loadImage(imageView, resIds.get(position % resIds.size()), R.mipmap.banner_default);
//        }
        container.addView(imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showToast(position % resIds.size()+"");
//            }
//        });
        return imageView;
    }
}

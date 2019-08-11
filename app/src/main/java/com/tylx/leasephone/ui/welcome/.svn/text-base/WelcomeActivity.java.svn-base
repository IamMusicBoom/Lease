package com.tylx.leasephone.ui.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


import com.tylx.leasephone.*;
import com.tylx.leasephone.databinding.ActivityWelcomeBinding;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class WelcomeActivity extends BaseActivity {
    ActivityWelcomeBinding binding;
    private List<ImageView> imageViews = new ArrayList<>();


    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_welcome, _containerLayout, false);
        return binding.getRoot();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView() {
        for (int id : WelcomeConfig.imageIds) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getResources().getDrawable(id));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }

        WelcomePagerAdapter mAdaper = new WelcomePagerAdapter(imageViews);
        binding.pager.setAdapter(mAdaper);
        binding.indicator.setViewPager(binding.pager);
    }

    private class WelcomePagerAdapter extends PagerAdapter {
        List<ImageView> imageViews;

        public WelcomePagerAdapter(List<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            if (imageViews != null) return imageViews.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            if (position == getCount() - 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatasStore.setFirstLaunch(false);
                        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                        finish();
                    }
                });
            }
            container.addView(imageView);
            return imageView;
        }
    }
}

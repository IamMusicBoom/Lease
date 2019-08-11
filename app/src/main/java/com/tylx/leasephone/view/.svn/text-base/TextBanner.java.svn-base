package com.tylx.leasephone.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.TextbannerBinding;

import java.util.ArrayList;

/**
 * Created by track on 2017/4/14.
 */

public class TextBanner extends LinearLayout implements View.OnClickListener {
    boolean isplayer;
    int index = 0;
    Handler handler;

    public TextBanner.onBannerOnClick getOnBannerOnClick() {
        return onBannerOnClick;
    }

    public void setOnBannerOnClick(TextBanner.onBannerOnClick onBannerOnClick) {
        this.onBannerOnClick = onBannerOnClick;
    }

    onBannerOnClick onBannerOnClick;

    public int getInterval() {
        return interval;
    }

    /***
     *  单位 s
     * @param interval
     */
    public void setInterval(int interval) {
        this.interval = interval * 1000;
    }

    int interval = 5000;

    public TextBanner(Context context) {
        super(context);
        init(context);
    }

    public TextBanner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    TextbannerBinding binding;

    void init(Context context) {
        setOrientation(VERTICAL);
        if (isInEditMode()) {
            TextView textView = new TextView(context);
            textView.setText("    我是BannerView");
            textView.setTextColor(0xffffffff);
            addView(textView);
            textView.setPadding(5, 13, 5, 13);
            setBackgroundColor(0xff939aa3);
            return;
        }
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.textbanner, this, false);
        addView(binding.getRoot());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (!isplayer || banner == null)
                    return;
                index++;
                if (index >= banner.size())
                    index = 0;

                animation();
                Heart();


            }
        };
        binding.setOnClickListener(this);
    }

    AlphaAnimation fanimation;
    AlphaAnimation tanimation;

    void animation() {
        if (fanimation == null) {
            fanimation = new AlphaAnimation(1, 0.3f);
            fanimation.setDuration(300);//设置动画持续时间
            fanimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.text.setText(banner.get(index));
                    binding.text.startAnimation(tanimation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tanimation = new AlphaAnimation(0.3f, 1);
            tanimation.setDuration(300);//设置动画持续时间
        }

        binding.text.startAnimation(fanimation);
    }

    void Heart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(interval);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private ArrayList<String> banner;

    public void setBanner(ArrayList<String> b) {
        if (isplayer)
            stop();
        banner = b;
    }

    public void stop() {
        isplayer = false;
        handler.removeMessages(1);
        index = 0;
    }

    public void pause() {
        isplayer = false;
        handler.removeMessages(1);
    }

    public void start() {
        if (isplayer)
            return;
        if (banner == null || banner.size() == 0)
            return;
        isplayer = true;
        binding.text.setText(banner.get(index));
        if (banner.size() > 1)
            Heart();
    }

    @Override
    public void onClick(View view) {
        if (onBannerOnClick != null) {
            onBannerOnClick.onClick(index, binding.text.getText().toString());
        }
    }

    public interface onBannerOnClick {
        public void onClick(int index, String banner);
    }
}

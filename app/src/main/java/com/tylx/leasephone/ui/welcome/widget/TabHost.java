package com.tylx.leasephone.ui.welcome.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.tylx.leasephone.R;
import com.tylx.leasephone.system.AppContext;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class TabHost extends LinearLayout {

    public TabHost(Context context) {
        super(context);
    }

    public TabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTabs(final List<TabInfo> tabInfos) {
        for (final TabInfo tabInfo : tabInfos) {
            ViewGroup tab = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.bottom_tab, this, false);
            ImageView imageView = (ImageView) tab.findViewById(R.id.tab_img);
            TextView titleView = (TextView) tab.findViewById(R.id.tab_title);
            imageView.setImageResource(tabInfo.drawableResource);
            titleView.setText(tabInfo.title);

            final int order = tabInfos.indexOf(tabInfo);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activeTab(order);
                    tabInfo.listener.onClick(v);
                }
            });
            addView(tab);
        }
        activeTab(0);
    }

    public void activeTab(int order) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            ViewGroup tab = (ViewGroup) getChildAt(i);
            tab.findViewById(R.id.tab_img).setEnabled(false);
            tab.findViewById(R.id.tab_title).setEnabled(false);
        }
        ViewGroup currentTab = (ViewGroup) getChildAt(order);
        currentTab.findViewById(R.id.tab_img).setEnabled(true);
        currentTab.findViewById(R.id.tab_title).setEnabled(true);
    }

    public static class TabInfo {
        public String title;
        public int drawableResource;
        public OnClickListener listener;

        public TabInfo(String title, int drawableResource, OnClickListener listener) {
            this.title = title;
            this.drawableResource = drawableResource;
            this.listener = listener;
        }

        public TabInfo(int title, int drawableResource, OnClickListener listener) {
            this.title = AppContext.getInstance().getString(title);
            this.drawableResource = drawableResource;
            this.listener = listener;
        }
    }
}

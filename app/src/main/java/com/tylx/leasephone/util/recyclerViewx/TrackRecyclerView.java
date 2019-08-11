package com.tylx.leasephone.util.recyclerViewx;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by track on 2017/4/28.
 */

public class TrackRecyclerView extends RecyclerView {
    Context mContext;

    public TrackRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public TrackRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public TrackRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setGridLayout(int span, int spacingInPixels) {
        this.addItemDecoration(new GridSpaceItemDecoration(span, spacingInPixels, false));
        this.setLayoutManager(new GridLayoutManager(mContext, span, 1, false));
    }

    public void setLinearLayout(int orientation, int spacingInPixels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, orientation, false);
        this.setLayoutManager(linearLayoutManager);
        this.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }
}

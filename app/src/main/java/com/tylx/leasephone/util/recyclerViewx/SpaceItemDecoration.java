package com.tylx.leasephone.util.recyclerViewx;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private boolean includeEdge;

    public SpaceItemDecoration(int spacing, boolean includeEdge) {
        this.space = spacing;
        this.includeEdge = includeEdge;
    }

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager mm = (LinearLayoutManager) parent.getLayoutManager();
            if (mm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (position != 0)
                    outRect.left = space;

                if (position == parent.getChildCount() && includeEdge) {
                    outRect.right = space;
                }
            } else {
                if (position != 0)
                    outRect.top = space;
                if (position == parent.getChildCount() && includeEdge) {
                    outRect.bottom = space;
                }
            }
        }
    }
}

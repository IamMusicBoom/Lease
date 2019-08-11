package com.tylx.leasephone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by track on 2017/4/20.
 */

public class TheTestView extends View {
    Paint paint;
    Scroller scroller;
    Context context;

    public TheTestView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TheTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TheTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    void init() {
        scroller = new Scroller(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.YELLOW);
        }
        canvas.drawRoundRect(0f, 0f, 30f, 30f, 5f, 5f, paint);
    }

    public void move(int x, int y) {
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), x, y);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}

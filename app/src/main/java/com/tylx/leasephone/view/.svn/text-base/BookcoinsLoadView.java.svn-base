package com.tylx.leasephone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by track on 2016/10/25.
 */

public class BookcoinsLoadView extends View {
    Paint textpaint;
    int paddind = 15;
    int StrokeWidth = 5;
    int arcpaddind = 30;
    int offset = 10;
    int textsize = 45;
    Context context;
    Handler handler;

    public BookcoinsLoadView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BookcoinsLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BookcoinsLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    Paint.FontMetricsInt mFontMetricsInt;
    Random random;

    void init() {
        textsize = dp2px(context, textsize);
        textpaint = new Paint();
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(textsize);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setFakeBoldText(true);
        textpaint.setAntiAlias(true);
        textpaint.setStyle(Paint.Style.STROKE);
        textpaint.setStrokeWidth(StrokeWidth);
        mFontMetricsInt = textpaint.getFontMetricsInt();
        random = new Random();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (!istart)
                    return;
                offset += 10;
                offset = offset >= 360 ? 10 : offset;
                postInvalidate();
                Sleep();
            }
        };
    }

    boolean istart;

    public void startAnimation()
    {
        if (istart)
            return;
        istart = true;
        Sleep();
    }

    public void stopAnimation()

    {
        istart = false;
    }

    void Sleep() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    if (istart)
                        handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * 将dp转换成px
     *
     * @param context
     * @param dpValue
     */
    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * density + 0.5f);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("B", getWidth() / 2, (getHeight() - mFontMetricsInt.ascent - mFontMetricsInt.descent) / 2, textpaint);
        RectF rectF = new RectF(paddind, paddind, getWidth() - paddind, getHeight() - paddind);
        canvas.drawArc(rectF, 90 + arcpaddind + offset, 180 - arcpaddind, false, textpaint);
        canvas.drawArc(rectF, 270 + arcpaddind + offset, 180 - arcpaddind, false, textpaint);
        rectF.inset(paddind, paddind);

        canvas.drawArc(rectF, 0 + arcpaddind+10 + offset * 2, 180 - arcpaddind-10, false, textpaint);
        canvas.drawArc(rectF, 180 + arcpaddind+10 + offset * 2, 180 - arcpaddind-10, false, textpaint);
        rectF.inset(paddind, paddind);

        canvas.drawArc(rectF, 30 + arcpaddind +20+ offset * 3, 180 - arcpaddind-20, false, textpaint);
        canvas.drawArc(rectF, 210 + arcpaddind +20+ offset * 3, 180 - arcpaddind-20, false, textpaint);
    }
}
